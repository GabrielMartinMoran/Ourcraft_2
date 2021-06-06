package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ItemUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class ThrowableWeapons {

    private final String IS_THROWABLE_TAG = "isThrowable";
    private final String THROWED_WEAPON_TAG = "isThrowedWeapon";
    private ItemUtils itemUtils;
    private HashMap<Projectile, ItemStack> throwedItems;

    public ThrowableWeapons() {
        this.itemUtils = new ItemUtils();
        this.throwedItems = new HashMap<Projectile, ItemStack>();
    }

    public boolean isThrowable(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(IS_THROWABLE_TAG) && nbt.getBoolean(IS_THROWABLE_TAG);
    }

    public void onThrowableWeaponCollide(Projectile snowball, Entity target) {
        ItemStack item = ((Snowball) snowball).getItem();
        NBTItem itemNbt = new NBTItem(item);
        boolean broken = this.itemUtils.reduceItemDurability(item, snowball);
        if (!broken) {
            snowball.getWorld().dropItem(snowball.getLocation(), item);
        } else {
            snowball.getWorld().spawnParticle(Particle.ITEM_CRACK, snowball.getLocation(), 50);
        }
        if(target != null) {
            ((LivingEntity)target).damage(itemNbt.getFloat("throwingDamage"));
        }
    }

    public void throwWeapon(Player player, ItemStack item) {
        if(player.getGameMode() != GameMode.CREATIVE) {
            if (player.getInventory().getItemInMainHand().equals(item)) {
                player.getInventory().setItemInMainHand(null);
            } else {
                player.getInventory().setItemInOffHand(null);
            }
        }
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 100, 1);
        Snowball snowball = player.launchProjectile(Snowball.class);
        snowball.setItem(item);
        tagProjectile(snowball);
    }

    private void tagProjectile(Snowball projectile) {
        projectile.setMetadata(THROWED_WEAPON_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), true));
    }

    public boolean isThrowedWeapon(Projectile projectile) {
        List<MetadataValue> metadata = projectile.getMetadata(THROWED_WEAPON_TAG);
        if (metadata.size() > 0) return metadata.get(0).asBoolean();
        return false;
    }
}
