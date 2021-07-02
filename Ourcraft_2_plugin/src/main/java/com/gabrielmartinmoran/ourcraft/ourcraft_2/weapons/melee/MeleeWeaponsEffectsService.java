package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MeleeWeaponsEffectsService implements Runnable {

    public static int EXECUTE_EACH_TICKS = 2;
    public static int POTION_EFFECT_TICKS = 20;

    private MeleeWeaponsResolver meleeWeaponsResolver;

    public MeleeWeaponsEffectsService() {
        this.meleeWeaponsResolver = new MeleeWeaponsResolver();
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            if (mainHandItem != null && !mainHandItem.getType().equals(Material.AIR)) {
                this.applyWeaponEffect(player, mainHandItem);
            }
        }
    }

    private void applyWeaponEffect(Player player, ItemStack item) {
        boolean hasItemInSecondaryHand = player.getInventory().getItemInOffHand() != null && !player.getInventory().getItemInOffHand().getType().equals(Material.AIR);
        if (this.meleeWeaponsResolver.isTwoHanded(item) && hasItemInSecondaryHand) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, POTION_EFFECT_TICKS, 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, POTION_EFFECT_TICKS, 0, false, false));
        }
        if (this.meleeWeaponsResolver.isImpractical(item)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, POTION_EFFECT_TICKS, 1, false, false));
        }
        if (this.meleeWeaponsResolver.isHeavy(item)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, POTION_EFFECT_TICKS, 0, false, false));
        }
    }

}
