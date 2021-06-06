package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.ranged.RangedWeaponsResolver;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class ProjectileShotListener implements Listener {

    private RangedWeaponsResolver rangedWeaponsResolver;

    public ProjectileShotListener() {
        this.rangedWeaponsResolver = new RangedWeaponsResolver();
    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onProjectileShot(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if(projectile.getShooter() instanceof Player) {
            Player player = (Player) projectile.getShooter();
            ItemStack weapon = null;
            if(this.rangedWeaponsResolver.isCustomRangedWeapon(player.getInventory().getItemInMainHand())) weapon = player.getInventory().getItemInMainHand();
            if(this.rangedWeaponsResolver.isCustomRangedWeapon(player.getInventory().getItemInOffHand())) weapon = player.getInventory().getItemInOffHand();
            if (weapon != null) {
                this.rangedWeaponsResolver.onProjectileShoot(projectile, weapon);
            }
        }

    }
}
