package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponsResolver;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageListener implements Listener {

    private MeleeWeaponsResolver meleeWeaponsResolver;

    public EntityDamageListener() {
        this.meleeWeaponsResolver = new MeleeWeaponsResolver();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if ((entity instanceof LivingEntity || entity instanceof Player) && event.getDamager() instanceof Player) {
            if (SpellsResolver.entitiesDamagedBySpells.contains(entity)) {
                SpellsResolver.entitiesDamagedBySpells.remove(entity);
            } else if (event.getDamager() instanceof Player && MeleeWeaponsResolver.playerThrowedItem((Player) event.getDamager())) {
              return;
            } else {
                Player player = (Player) event.getDamager();
                if (player.getGameMode() != GameMode.CREATIVE) {
                    PlayerData playerData = PlayerDataProvider.get(((Player) event.getDamager()));
                    playerData.addAttributeXp(PlayerAttributes.MELEE, event.getFinalDamage() / 2d);
                }
            }
        }
        if (entity instanceof LivingEntity && event.getDamager() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) entity;
            LivingEntity damager = (LivingEntity) event.getDamager();
            ItemStack damagerItem = damager.getEquipment().getItemInMainHand();
            if (damagerItem == null || damagerItem.getType().equals(Material.AIR)) return;
            if (this.meleeWeaponsResolver.isLifedrainer(damagerItem)) {
                int toRecover = (int) Math.floor(event.getFinalDamage() / 4d);
                double totalHealth = damager.getHealth() + toRecover;
                if (damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < totalHealth)
                    totalHealth = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                damager.setHealth(totalHealth);
            }
            if (this.meleeWeaponsResolver.isIncapacitating(damagerItem)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Config.TICKS_PER_SECOND * 5, 1, true, false));
            }
            if (this.meleeWeaponsResolver.isBleeding(damagerItem)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Config.TICKS_PER_SECOND * 5, 1, true, false));
            }
            if (this.meleeWeaponsResolver.isNumbing(damagerItem)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Config.TICKS_PER_SECOND * 5, 0, true, false));
            }
        }
    }
}
