package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.MeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponsResolver;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if ((entity instanceof LivingEntity || entity instanceof Player) && event.getDamager() instanceof Player) {
            if (SpellsResolver.entitiesDamagedBySpells.contains(entity)) {
                SpellsResolver.entitiesDamagedBySpells.remove(entity);
            } else if (event.getDamager() instanceof Player && MeleeWeaponsResolver.playerThrowedItem((Player) event.getDamager())) {
              return;
            } else {
                if (((Player) event.getDamager()).getGameMode() == GameMode.CREATIVE) return;
                PlayerData playerData = PlayerDataProvider.get(((Player) event.getDamager()));
                playerData.addAttributeXp(PlayerAttributes.MELEE, event.getFinalDamage() / 2d);
            }
        }
    }
}
