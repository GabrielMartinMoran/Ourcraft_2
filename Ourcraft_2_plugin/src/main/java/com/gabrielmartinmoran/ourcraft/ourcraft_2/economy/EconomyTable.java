package com.gabrielmartinmoran.ourcraft.ourcraft_2.economy;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.AdvancedManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.ManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.RegularManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.SupremeManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers.DiamondDagger;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers.IronDagger;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword.DiamondGreatSword;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword.IronGreatSword;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows.BambooBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.TippedArrowsHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffectType;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EconomyTable {

    private static final SecureRandom rand = new SecureRandom();
    private static ArrayList<EconomyItem> pricesList = null;

    private static ArrayList<EconomyItem> getPricesList() {
        if (pricesList != null) return pricesList;

        pricesList = new ArrayList<EconomyItem>();
        // FARMER
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.WHEAT, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.WHEAT_SEEDS, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.CARROT, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.POTATO, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.BEETROOT, 6, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.BEETROOT_SEEDS, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.MELON, 10, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.MELON_SLICE, 1, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.MELON_SEEDS, 1, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.PUMPKIN, 6, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.PUMPKIN_SEEDS, 1, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.SUGAR_CANE, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.BAMBOO, 1, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FARMER, Material.CACTUS, 4, true, true));

        // CARTOGRAPHER
        int PAPER_PRICE = 12;
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.PAPER, PAPER_PRICE, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.COMPASS, 18, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.MAP, 30, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.CLOCK, 10, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.ITEM_FRAME, 12, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.WHITE_BANNER, 15, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.FLOWER_BANNER_PATTERN, 10, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.SKULL_BANNER_PATTERN, 50, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.CREEPER_BANNER_PATTERN, 100, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.MOJANG_BANNER_PATTERN, 150, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.GLOBE_BANNER_PATTERN, 150, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.PIGLIN_BANNER_PATTERN, 150, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CARTOGRAPHER, Material.WRITABLE_BOOK, 25, true, true));

        // BUTCHER
        pricesList.add(new EconomyItem(Villager.Profession.BUTCHER, Material.BEEF, 11, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.BUTCHER, Material.PORKCHOP, 8, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.BUTCHER, Material.CHICKEN, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.BUTCHER, Material.MUTTON, 11, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.BUTCHER, Material.RABBIT, 11, true, true));

        // FISHERMAN
        pricesList.add(new EconomyItem(Villager.Profession.FISHERMAN, Material.COD, 8, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FISHERMAN, Material.SALMON, 11, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FISHERMAN, Material.TROPICAL_FISH, 32, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FISHERMAN, Material.PUFFERFISH, 11, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FISHERMAN, Material.FISHING_ROD, 50, false, true));

        // ARMORER
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.COAL, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.IRON_INGOT, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.GOLD_INGOT, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.REDSTONE, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.LAPIS_LAZULI, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.QUARTZ, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.DIAMOND, 64, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.EMERALD, 64, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.NETHERITE_INGOT, 256, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.GOLDEN_HORSE_ARMOR, 120, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.IRON_HORSE_ARMOR, 200, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.ARMORER, Material.DIAMOND_HORSE_ARMOR, 300, true, true));

        // FLETCHER
        int BASE_TIPPED_ARROW_COST = 7;
        int TIPPED_ARROW_EFFECT_DURATION = 200 * Config.TICKS_PER_SECOND;
        int MAX_ARROW_AMPLIFIER = 2; // Cuenta desde el 0
        for (int amplifier = 0; amplifier <= MAX_ARROW_AMPLIFIER; amplifier++) {
            for (PotionEffectType effect : Arrays.asList(PotionEffectType.NIGHT_VISION, PotionEffectType.INVISIBILITY,
                    PotionEffectType.JUMP, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.SPEED,
                    PotionEffectType.SLOW, PotionEffectType.WATER_BREATHING, PotionEffectType.HEAL,
                    PotionEffectType.INCREASE_DAMAGE, PotionEffectType.POISON, PotionEffectType.INCREASE_DAMAGE,
                    PotionEffectType.WEAKNESS, PotionEffectType.SLOW_FALLING, PotionEffectType.SLOW_DIGGING)) {
                ItemStack tippedArrow = TippedArrowsHelper.getArrow(effect, amplifier, TIPPED_ARROW_EFFECT_DURATION);
                pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, tippedArrow, BASE_TIPPED_ARROW_COST * (amplifier + 1), false, true));
            }
        }
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, Material.ARROW, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, new BambooBow().getItem(), 40, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, Material.BOW, 80, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, Material.CROSSBOW, 80, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, Material.FLINT, 1, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, Material.FEATHER, 3, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.FLETCHER, Material.STRING, 10, true, true));

        // LIBRARIAN
        pricesList.add(new EconomyItem(Villager.Profession.LIBRARIAN, Material.PAPER, PAPER_PRICE, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LIBRARIAN, Material.BOOK, 30, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LIBRARIAN, Material.BOOKSHELF, 90, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LIBRARIAN, Material.BOOKSHELF, 90, true, true));
        int MAX_ENCHANTED_BOOK_COST = 500;
        for (Enchantment enchantment: Enchantment.values()) {
            for (int enchantmentLevel = 1; enchantmentLevel <= enchantment.getMaxLevel(); enchantmentLevel++) {
                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta enchantedBookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                enchantedBookMeta.addStoredEnchant(enchantment, enchantmentLevel, false);
                enchantedBook.setItemMeta(enchantedBookMeta);
                int costPerLevel = MAX_ENCHANTED_BOOK_COST / enchantment.getMaxLevel(); // Asi nos aseguramos que el costo de encantamientos de 1 solo nivel es el mas alto
                pricesList.add(new EconomyItem(Villager.Profession.LIBRARIAN, enchantedBook, costPerLevel * enchantmentLevel, true, true));
            }
        }

        // CLERIC
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.GLASS_BOTTLE, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new ManaPotion().getItem(), 45, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new RegularManaPotion().getItem(), 80, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new RegularManaPotion().getItem(), 150, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new AdvancedManaPotion().getItem(), 290, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new SupremeManaPotion().getItem(), 570, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new MagicEssence().getItem(), 64, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, new ManaEssence().getItem(), 32, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.GLOWSTONE_DUST, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.ROTTEN_FLESH, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.ENDER_PEARL, 20, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.GUNPOWDER, 10, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.BLAZE_POWDER, 10, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.NETHER_WART, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.CLERIC, Material.EXPERIENCE_BOTTLE, 20, true, true));

        // LEATHERWORKER
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, Material.LEATHER, 20, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, Material.RABBIT_HIDE, 20, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, new OrcSkin().getItem(), 40, true, false));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, new OgreSkin().getItem(), 200, true, false));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, new ReinforcedLeather().getItem(), 100, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, Material.LEAD, 40, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, Material.SADDLE, 200, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.LEATHERWORKER, Material.LEATHER_HORSE_ARMOR, 100, true, true));

        // SHEPHERD
        int WOOL_COST = 2;
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.BLACK_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.WHITE_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.BLUE_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.BROWN_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.CYAN_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.GRAY_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.GREEN_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.LIGHT_BLUE_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.LIGHT_GRAY_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.LIME_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.MAGENTA_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.ORANGE_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.PINK_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.RED_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.YELLOW_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.PURPLE_WOOL, WOOL_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.SHEPHERD, Material.PAINTING, 2, true, true));

        // MASON
        int GLAZED_TERRACOTA_COST = 6;
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.CLAY, 1, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.BRICK, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.BLACK_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.BLUE_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.BROWN_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.CYAN_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.GRAY_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.GREEN_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.LIME_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.MAGENTA_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.ORANGE_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.PINK_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.PURPLE_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.RED_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.WHITE_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.YELLOW_GLAZED_TERRACOTTA, GLAZED_TERRACOTA_COST, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.MASON, Material.QUARTZ, 2, true, true));

        // TOOLSMITH
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.COAL, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.IRON_INGOT, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.GOLD_INGOT, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.DIAMOND, 64, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.EMERALD, 128, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.NETHERITE_INGOT, 256, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.IRON_AXE, 75, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.DIAMOND_AXE, 175, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.IRON_SHOVEL, 35, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.DIAMOND_SHOVEL, 135, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.IRON_HOE, 50, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.TOOLSMITH, Material.DIAMOND_HOE, 150, false, true));

        // WEAPONSMITH
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.IRON_INGOT, 4, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.GOLD_INGOT, 2, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.DIAMOND, 64, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.EMERALD, 128, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.NETHERITE_INGOT, 256, true, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.SHIELD, 75, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, new IronDagger().getItem(), 35, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, new DiamondDagger().getItem(), 135, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.DIAMOND_SWORD, 150, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, Material.IRON_SWORD, 50, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, new IronGreatSword().getItem(), 150, false, true));
        pricesList.add(new EconomyItem(Villager.Profession.WEAPONSMITH, new DiamondGreatSword().getItem(), 300, false, true));

        return pricesList;
    }

    public static MerchantRecipe getTrade(Villager.Profession profession, int villagerLevel) {
        boolean isSelling = rand.nextDouble() <= 0.5d;
        List<EconomyItem> pList = getPricesList();
        List<EconomyItem> filtered = null;
        if (isSelling) {
            filtered = pList.stream().filter(mp -> mp.allowSell).collect(Collectors.toList());
        } else {
            filtered = pList.stream().filter(mp -> mp.allowBuy).collect(Collectors.toList());
        }
        filtered = filtered.stream().filter(mp -> mp.villagerProfession.equals(profession)).collect(Collectors.toList());
        if (filtered.size() == 0) return null;
        EconomyItem economyItem = filtered.get(rand.nextInt(filtered.size()));
        if (isSelling) return economyItem.getSellRecipe(villagerLevel);
        return economyItem.getBuyRecipe(villagerLevel);
    }
}
