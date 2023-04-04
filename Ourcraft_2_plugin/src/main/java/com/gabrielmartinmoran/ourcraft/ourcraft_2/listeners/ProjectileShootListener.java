package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CombatPearl;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.ranged.RangedWeaponsResolver;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class ProjectileShootListener implements Listener {

    private RangedWeaponsResolver rangedWeaponsResolver;
    private JavaPlugin plugin;

    public ProjectileShootListener() {
        this.rangedWeaponsResolver = new RangedWeaponsResolver();
        this.plugin = JavaPlugin.getPlugin(Main.class);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onProjectileShot(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        // Combat pearl
        if (projectile instanceof EnderPearl && projectile.getShooter() instanceof Player) {
            Player player = (Player) projectile.getShooter();
            ItemStack enderPearl = null;
            if (player.getInventory().getItemInMainHand().getType().equals(Material.ENDER_PEARL))
                enderPearl = player.getInventory().getItemInMainHand();
            if (enderPearl == null && player.getInventory().getItemInOffHand().getType().equals(Material.ENDER_PEARL))
                enderPearl = player.getInventory().getItemInOffHand();
            if (enderPearl != null) {
                NBTItem nbt = new NBTItem(enderPearl);
                if (nbt.hasKey(CombatPearl.IS_COMBAT_PEARL_TAG))
                    projectile.setMetadata(CombatPearl.IS_COMBAT_PEARL_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), true));
                return;
            }
        }
        if (projectile.getShooter() instanceof Player) {
            this.addShootPosition(projectile);
            Player player = (Player) projectile.getShooter();
            ItemStack weapon = null;
            if (this.rangedWeaponsResolver.isCustomRangedWeapon(player.getInventory().getItemInMainHand()))
                weapon = player.getInventory().getItemInMainHand();
            if (this.rangedWeaponsResolver.isCustomRangedWeapon(player.getInventory().getItemInOffHand()))
                weapon = player.getInventory().getItemInOffHand();
            if (weapon != null) {
                this.rangedWeaponsResolver.onProjectileShoot(projectile, weapon);
            }
        }
    }

    private void addShootPosition(Projectile projectile) {
        Location loc = projectile.getLocation();
        String serializedPos = Double.toString(loc.getX()) + ";" + Double.toString(loc.getY()) + ";" + Double.toString(loc.getZ());
        projectile.setMetadata("shootPosition", new FixedMetadataValue(this.plugin, serializedPos));
    }
}
