package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ItemUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class MeleeWeaponsResolver {

    private final String THROWED_WEAPON_TAG = "isThrowedWeapon";
    private ItemUtils itemUtils;
    public static ArrayList<Projectile> throwedItems = new ArrayList<Projectile>();

    public MeleeWeaponsResolver() {
        this.itemUtils = new ItemUtils();
    }

    public boolean isThrowable(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.THROWABE.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.THROWABE.getTag());
    }

    public boolean isTwoHanded(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.TWO_HANDED.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.TWO_HANDED.getTag());
    }

    public boolean isImpractical(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.IMPRACTICAL.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.IMPRACTICAL.getTag());
    }

    public boolean isLifedrainer(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.LIFEDRAINER.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.LIFEDRAINER.getTag());
    }

    public boolean isIncapacitating(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.INCAPACITATING.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.INCAPACITATING.getTag());
    }

    public boolean isHeavy(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.HEAVY.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.HEAVY.getTag());
    }

    public boolean isBleeding(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.BLEEDING.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.BLEEDING.getTag());
    }

    public boolean isNumbing(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(MeleeWeaponCharacteristics.NUMBING.getTag()) && nbt.getBoolean(MeleeWeaponCharacteristics.NUMBING.getTag());
    }

    public void onThrowableWeaponCollide(Projectile snowball, Entity target) {
        ItemStack item = ((Snowball) snowball).getItem();
        boolean broken = this.itemUtils.reduceItemDurability(item, snowball);
        if (!broken) {
            snowball.getWorld().dropItem(snowball.getLocation(), item);
        } else {
            snowball.getWorld().spawnParticle(Particle.ITEM_CRACK, snowball.getLocation(), 50);
        }
        if (target != null) {
            double damage = this.getThrowingDamage(item, (LivingEntity) target);
            ((LivingEntity) target).damage(damage, (Entity) snowball.getShooter());
            PlayerDataProvider.get((Player) snowball.getShooter()).addAttributeXp(PlayerAttributes.RANGED, 1);
        }
        throwedItems.remove(snowball);
    }

    public void throwWeapon(Player player, ItemStack item) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            if (player.getInventory().getItemInMainHand().equals(item)) {
                player.getInventory().setItemInMainHand(null);
            } else {
                player.getInventory().setItemInOffHand(null);
            }
        }
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = player.launchProjectile(Snowball.class);
        snowball.setItem(item);
        tagProjectile(snowball);
        throwedItems.add(snowball);
    }

    private void tagProjectile(Snowball projectile) {
        projectile.setMetadata(THROWED_WEAPON_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), true));
    }

    public boolean isThrowedWeapon(Projectile projectile) {
        return projectile.hasMetadata(THROWED_WEAPON_TAG) && projectile.getMetadata(THROWED_WEAPON_TAG).get(0).asBoolean();
    }

    private double getThrowingDamage(ItemStack item, LivingEntity target) {
        NBTItem itemNbt = new NBTItem(item);
        float baseDamage = itemNbt.getFloat("throwingDamage");
        double damage = baseDamage;
        int sharpness = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
        if (sharpness > 0) damage += (0.5 * sharpness) + 0.5;
        int smite = item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
        if (smite > 0 && Arrays.asList(
                EntityType.ZOMBIE, EntityType.DROWNED, EntityType.GIANT, EntityType.HUSK,
                EntityType.PHANTOM, EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.WITHER_SKELETON,
                EntityType.STRAY, EntityType.WITHER, EntityType.ZOGLIN, EntityType.ZOMBIE_HORSE,
                EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN
        ).contains(target.getType())) damage += 1.5 * smite;
        int baneOfArthropods = item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
        if (baneOfArthropods > 0 && Arrays.asList(
                EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.SILVERFISH, EntityType.ENDERMITE
        ).contains(target.getType())) damage += 1.5 * baneOfArthropods;
        return damage;
    }

    public static boolean playerThrowedItem(Player player) {
        for (Projectile projectile : throwedItems) {
            if (projectile.getShooter().equals(player)) return true;
        }
        return false;
    }
}
