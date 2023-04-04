package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CombatPearl;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponsResolver;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    private SpellsResolver spellsResolver;
    private MeleeWeaponsResolver meleeWeaponsResolver;
    private AttributeLevelingHandler attributeLevelingHandler;

    public ProjectileHitListener() {
        this.spellsResolver = new SpellsResolver();
        this.meleeWeaponsResolver = new MeleeWeaponsResolver();
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (this.isCombatPearl(projectile)) {
            this.resolveCombatPearl((EnderPearl) projectile);
            return;
        }
        if (this.spellsResolver.isSpellProjectile(projectile)) {
            this.spellsResolver.onProjectileHit(projectile, event.getHitEntity());
            projectile.remove();
            return;
        }
        if (this.meleeWeaponsResolver.isThrowedWeapon(projectile)) {
            this.meleeWeaponsResolver.onThrowableWeaponCollide(projectile, event.getHitEntity());
        }
        if (projectile.getShooter() instanceof Player) {
            this.attributeLevelingHandler.onProjectileHit(event.getEntity(), event.getHitEntity(), event.getHitBlock());
        }
    }

    private boolean isCombatPearl(Projectile projectile) {
        return projectile instanceof EnderPearl && projectile.getShooter() instanceof Player &&
                projectile.hasMetadata(CombatPearl.IS_COMBAT_PEARL_TAG);
    }

    private void resolveCombatPearl(EnderPearl projectile) {
        Player shooter = (Player) projectile.getShooter();
        if (projectile.hasMetadata(CombatPearl.IS_COMBAT_PEARL_TAG)) {
            projectile.getWorld().spawnParticle(Particle.DRAGON_BREATH, projectile.getLocation(), 400);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
            for (Entity entity : projectile.getNearbyEntities(CombatPearl.DAMAGE_RADIUS, CombatPearl.DAMAGE_RADIUS, CombatPearl.DAMAGE_RADIUS)) {
                if (entity instanceof LivingEntity && entity != shooter) {
                    ((LivingEntity) entity).damage(CombatPearl.DAMAGE_AMOUNT, shooter);
                }
            }
        }
    }
}
