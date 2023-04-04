package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationDecreaseEvents;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerUtils;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponsResolver;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClickListener implements Listener {

    private PlayerUtils playerUtils;
    private SpellsResolver spellsResolver;
    private MeleeWeaponsResolver meleeWeaponsResolver;

    public ClickListener() {
        playerUtils = new PlayerUtils();
        spellsResolver = new SpellsResolver();
        meleeWeaponsResolver = new MeleeWeaponsResolver();
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (item != null && (action.equals(Action.RIGHT_CLICK_AIR) ||
                (action.equals(Action.RIGHT_CLICK_BLOCK) && !block.getBlockData().getMaterial().isInteractable()))) {
            if (this.spellsResolver.castsSpell(item)) {
                this.spellsResolver.resolveSpell(player, item);
                return;
            }
            if (this.meleeWeaponsResolver.isThrowable(item)) {
                this.meleeWeaponsResolver.throwWeapon(player, item);
                return;
            }
        }
        this.handleHydration(event);
    }

    private void handleHydration(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        // Checkeamos solo 1 mano
        if (event.getHand().equals(EquipmentSlot.HAND) && item == null && (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR))) {
            List<Block> lineOfSight = player.getLineOfSight(null, 2);
            boolean waterFound = false;
            for (Block b : lineOfSight) {
                if (b.getType().equals(Material.AIR) || b.getType().equals(Material.CAVE_AIR)) continue;
                if (b.getType().equals(Material.WATER)) waterFound = true;
                break;
            }
            if (waterFound) {
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1f, 1f);
                Location particlesLoc = player.getLocation();
                particlesLoc.setY(particlesLoc.getY() + 1);
                player.spawnParticle(Particle.WATER_SPLASH, particlesLoc, 10);
                PlayerDataProvider.get(player).getHydrationManager().addHydration(2);
                PlayerDataProvider.get(player).getHydrationManager().handleNotPurifiedWaterDrinking(player);
            }
        }
        if (action.equals(Action.LEFT_CLICK_AIR)) {
            HydrationDecreaseEvents hEvent = HydrationDecreaseEvents.ATTACK;
            if (item == null) hEvent = HydrationDecreaseEvents.ATTACK_NO_WEAPON;
            if (player.getGameMode() != GameMode.CREATIVE)
                PlayerDataProvider.get(player).getHydrationManager().consumeHydration(hEvent);
        }
    }
}
