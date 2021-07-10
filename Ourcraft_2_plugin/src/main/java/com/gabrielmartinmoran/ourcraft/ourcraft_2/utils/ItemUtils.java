package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;


import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class ItemUtils {

    private SecureRandom rand;

    public ItemUtils() {
        this.rand = new SecureRandom();
    }

    public boolean hasTag(ItemStack item, String tag) {
        NBTItem nbti = new NBTItem(item);
        return nbti.getInteger(tag) == 1;
    }

    public boolean reduceItemDurability(ItemStack item, Entity soundPlayerEntity) {
        org.bukkit.inventory.meta.Damageable iMeta = (org.bukkit.inventory.meta.Damageable) item.getItemMeta();
        int unbreakingEnchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        if ((this.rand.nextInt(100) + 1) < (100d/(unbreakingEnchantLevel+1))) {
            iMeta.setDamage(iMeta.getDamage() + 1);
            item.setItemMeta((ItemMeta) iMeta);
        }
        if(item.getType().getMaxDurability() <= iMeta.getDamage()) {
            soundPlayerEntity.getWorld().playSound(soundPlayerEntity.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
            return true;
        }
        return false;
    }

    // devuelve true si el item se destruyo
    public boolean reduceItemDurability(Player player, ItemStack item) {
        boolean broken = this.reduceItemDurability(item, player);
        if (broken) {
            if(player.getInventory().getItemInMainHand().hasItemMeta() &&
                    player.getInventory().getItemInMainHand().getItemMeta().equals(item.getItemMeta())) {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            } else {
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }
        }
        return broken;
    }
}
