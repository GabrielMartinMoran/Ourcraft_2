package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
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

public class MeleeWeaponsResolver {

    private final String THROWED_WEAPON_TAG = "isThrowedWeapon";
    private ItemUtils itemUtils;
    private HashMap<Projectile, ItemStack> throwedItems;

    public MeleeWeaponsResolver() {
        this.itemUtils = new ItemUtils();
        this.throwedItems = new HashMap<Projectile, ItemStack>();
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
            ((LivingEntity)target).damage(itemNbt.getFloat("throwingDamage"), (Entity) snowball.getShooter());
            PlayerDataProvider.get((Player)snowball.getShooter()).addAttributeXp(PlayerAttributes.RANGED, 1);
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
