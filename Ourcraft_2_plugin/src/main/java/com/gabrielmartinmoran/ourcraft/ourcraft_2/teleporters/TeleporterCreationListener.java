package com.gabrielmartinmoran.ourcraft.ourcraft_2.teleporters;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLocker;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class TeleporterCreationListener implements Listener {

    private RecipesLocker recipesLocker;

    public TeleporterCreationListener() {
        this.recipesLocker = new RecipesLocker();
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            if (this.hasPlacedLodestoneCompass(itemFrame, event.getPlayer())) {
                Location itemFrameLocation = event.getRightClicked().getLocation();
                Teleporter teleporter = Teleporter.fromStructure(this.getLodedCompass(event.getPlayer()), itemFrameLocation);
                if (teleporter == null) return;
                if (this.recipesLocker.canCreateTeleporter(event.getPlayer())) {
                    event.getPlayer().sendMessage("" + ChatColor.GREEN + "Se ha completado el teletransportador!");
                    TeleporterDataProvider.set(TeleporterDataProvider.serializeLocation(itemFrameLocation), teleporter);
                } else {
                    event.getPlayer().sendMessage("" + ChatColor.RED + "Tu nivel de magia no es lo suficientemente alto como para crear teletransportadores!");
                }
            }
            ;
        }
    }

    @EventHandler
    public void onPlayerClickBlock(PlayerInteractEvent event) {
        if (event.getItem() != null) return;
        if (event.getHand().equals(EquipmentSlot.HAND) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.LODESTONE)) {
            Location blockLocation = event.getClickedBlock().getLocation();
            Location searchLocation = new Location(blockLocation.getWorld(), blockLocation.getBlockX(), blockLocation.getBlockY() + 1, blockLocation.getBlockZ());
            String serializedSearchLocation = TeleporterDataProvider.serializeLocation(searchLocation);
            if (TeleporterDataProvider.hasData(serializedSearchLocation)) {
                Teleporter teleporter = TeleporterDataProvider.get(serializedSearchLocation);
                // Corroboramos la validez de teleporter actual
                if (!Teleporter.isValidStructure(searchLocation)) {
                    TeleporterDataProvider.remove(serializedSearchLocation);
                    event.getPlayer().sendMessage("" + ChatColor.RED + "Este teletransportador no parece seguir siendo válido!");
                    return;
                }
                // Corroboramos la validez de teleporter al que se quiere teletransportar
                if (!Teleporter.isValidStructure(teleporter.getTargetItemFrameLocation())) {
                    TeleporterDataProvider.remove(TeleporterDataProvider.serializeLocation(teleporter.getTargetItemFrameLocation()));
                    event.getPlayer().sendMessage("" + ChatColor.RED + "El teletransportador al que apunta ya no parece ser válido!");
                    return;
                }
                // Teletransportamos
                this.teleportPlayer(event.getPlayer(), teleporter.getTargetItemFrameLocation(), event.getClickedBlock().getLocation());
            }
        }
    }

    private boolean hasPlacedLodestoneCompass(ItemFrame itemFrame, Player player) {
        ItemStack item = itemFrame.getItem();
        return item.getType().equals(Material.AIR) && this.getLodedCompass(player) != null;
    }

    private ItemStack getLodedCompass(Player player) {
        ItemStack lodedCompass = player.getInventory().getItemInMainHand();
        if (lodedCompass == null) lodedCompass = player.getInventory().getItemInOffHand();
        if (lodedCompass == null || !lodedCompass.getType().equals(Material.COMPASS)) ;
        NBTItem nbt = new NBTItem(lodedCompass);
        if (nbt.hasKey("LodestoneTracked") && nbt.getByte("LodestoneTracked") == 1) return lodedCompass;
        return null;
    }

    private void teleportPlayer(Player player, Location teleportLocation, Location clickedLocation) {
        player.getWorld().playSound(clickedLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        player.getWorld().spawnParticle(Particle.PORTAL, clickedLocation.add(0.5d, 0, 0.5d), 200);
        player.teleport(teleportLocation);
        teleportLocation.getWorld().spawnParticle(Particle.DRAGON_BREATH, teleportLocation.add(0.5d, 0, 0.5d), 200);
    }
}
