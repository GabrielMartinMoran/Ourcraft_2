package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;

import javax.swing.text.html.parser.Entity;

public class ProjectileHitListener implements Listener {

    private SpellsResolver spellsResolver;

    public ProjectileHitListener() {
        this.spellsResolver = new SpellsResolver();
    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (this.spellsResolver.isSpellProjectile(projectile)) {
            this.spellsResolver.onProjectileHit(projectile, event.getHitEntity());
            projectile.remove();
            return;
        }
    }
}
