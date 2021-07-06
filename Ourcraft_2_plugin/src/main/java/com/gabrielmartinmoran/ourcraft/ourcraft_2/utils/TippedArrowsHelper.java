package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

public class TippedArrowsHelper {

    public static ItemStack getArrow(PotionEffectType effect, int amplifier, int duration, int amount) {
        ItemStack tippedArrow = new ItemStack(Material.TIPPED_ARROW, amount);
        PotionMeta tippedArrowMeta = (PotionMeta) tippedArrow.getItemMeta();
        tippedArrowMeta.setColor(effect.getColor());
        tippedArrowMeta.setDisplayName(getArrowName(effect, amplifier));
        tippedArrowMeta.addCustomEffect(effect.createEffect(duration, amplifier), true);
        tippedArrow.setItemMeta(tippedArrowMeta);
        return tippedArrow;
    }

    public static ItemStack getArrow(PotionEffectType effect, int amplifier, int duration) {
        return getArrow(effect, amplifier, duration, 1);
    }


    public static String getArrowName(PotionEffectType effect, int amplifier) {
        String potionEffectName = WordUtils.capitalizeFully(effect.getName().replaceAll("_", " ").toLowerCase());
        if(effect.equals(PotionEffectType.HEAL)) potionEffectName = "Healing";
        if(effect.equals(PotionEffectType.HARM)) potionEffectName = "Harming";
        if(effect.equals(PotionEffectType.INCREASE_DAMAGE)) potionEffectName = "Strength";
        if(effect.equals(PotionEffectType.SLOW)) potionEffectName = "Slowness";
        if(effect.equals(PotionEffectType.SLOW_DIGGING)) potionEffectName = "Mining Fatigue";
        String name = "Arrow of " + potionEffectName;
        if(amplifier >= 1) name += " " + StringUtils.repeat("I", amplifier + 1);
        return name;
    }
}
