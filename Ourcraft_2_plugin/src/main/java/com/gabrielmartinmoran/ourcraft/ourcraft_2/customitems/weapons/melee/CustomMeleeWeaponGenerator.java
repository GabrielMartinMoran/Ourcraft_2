package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors.CustomArmor;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors.CustomArmorModifiers;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;
import net.minecraft.server.v1_16_R3.ItemStack;
import org.bukkit.Material;

import java.security.SecureRandom;
import java.util.*;

public class CustomMeleeWeaponGenerator {

    private List<Material> weaponMaterials;
    private List<CustomItemsModelData> axesModels;
    private List<CustomItemsModelData> swordsModels;
    private SecureRandom rand;
    private final double CHARACTERISTIC_PROBABILITY = 0.25d;
    private final double CURSE_OF_VANISHING_MODIFIER_PROBABILITY = 0.15d;

    public CustomMeleeWeaponGenerator() {
        this.weaponMaterials = Arrays.asList(
                Material.IRON_SWORD, Material.IRON_AXE
        );
        this.swordsModels = Arrays.asList(
                CustomItemsModelData.CUSTOM_SWORD_001, CustomItemsModelData.CUSTOM_SWORD_002,
                CustomItemsModelData.CUSTOM_SWORD_003, CustomItemsModelData.CUSTOM_SWORD_004,
                CustomItemsModelData.CUSTOM_SWORD_005, CustomItemsModelData.CUSTOM_SWORD_006,
                CustomItemsModelData.CUSTOM_SWORD_007, CustomItemsModelData.CUSTOM_SWORD_008,
                CustomItemsModelData.CUSTOM_SWORD_009, CustomItemsModelData.CUSTOM_SWORD_010,
                CustomItemsModelData.CUSTOM_SWORD_011, CustomItemsModelData.CUSTOM_SWORD_012
        );
        this.axesModels = Arrays.asList(
                CustomItemsModelData.CUSTOM_AXE_001, CustomItemsModelData.CUSTOM_AXE_002,
                CustomItemsModelData.CUSTOM_AXE_003, CustomItemsModelData.CUSTOM_AXE_004,
                CustomItemsModelData.CUSTOM_AXE_005, CustomItemsModelData.CUSTOM_AXE_006,
                CustomItemsModelData.CUSTOM_AXE_007, CustomItemsModelData.CUSTOM_AXE_008,
                CustomItemsModelData.CUSTOM_AXE_009, CustomItemsModelData.CUSTOM_AXE_010,
                CustomItemsModelData.CUSTOM_AXE_011, CustomItemsModelData.CUSTOM_AXE_012
                );
        this.rand = new SecureRandom();
    }

    public CustomMeleeWeapon generateWeapon() {
        ArrayList<MeleeWeaponCharacteristics> characteristics = new ArrayList<MeleeWeaponCharacteristics>();
        for (MeleeWeaponCharacteristics characteristic : MeleeWeaponCharacteristics.values()) {
            if (this.rand.nextDouble() <= CHARACTERISTIC_PROBABILITY) characteristics.add(characteristic);
        }
        Material baseMaterial = this.getBaseMaterial(characteristics);
        boolean hasCurseOfVanishing = this.rand.nextDouble() <= CURSE_OF_VANISHING_MODIFIER_PROBABILITY;
        CustomItemsModelData modelData = this.getModelData(baseMaterial, characteristics);
        int damage = getRandIntBetween(1, 15);
        return new CustomMeleeWeapon(baseMaterial, modelData, characteristics, damage, hasCurseOfVanishing);
    }

    /**
     * Se utiliza para que si el arma es lanzable no devuelva espadas
     */
    private Material getBaseMaterial(ArrayList<MeleeWeaponCharacteristics> characteristics) {
        ArrayList<Material> materials = new ArrayList<Material>();
        for (Material material : this.weaponMaterials) {
            if (material.toString().contains("SWORD") && characteristics.contains(MeleeWeaponCharacteristics.THROWABE))
                continue;
            materials.add(material);
        }
        return materials.get(rand.nextInt(materials.size()));
    }

    private CustomItemsModelData getModelData(Material baseMaterial, ArrayList<MeleeWeaponCharacteristics> characteristics) {
        ArrayList<CustomItemsModelData> models = null;
        if (baseMaterial.toString().contains("AXE")) {
             models = new ArrayList<CustomItemsModelData>(this.axesModels);
             if (characteristics.contains(MeleeWeaponCharacteristics.TWO_HANDED) ||
                 characteristics.contains(MeleeWeaponCharacteristics.HEAVY)) {
                 models.remove(CustomItemsModelData.CUSTOM_AXE_001);
                 models.remove(CustomItemsModelData.CUSTOM_AXE_007);
             }
        }
        if (baseMaterial.toString().contains("SWORD")) {
            models = new ArrayList<CustomItemsModelData>(this.swordsModels);
        }
        return models.get(rand.nextInt(models.size()));
    }

    private int getRandIntBetween(int min, int max) {
        return this.rand.nextInt(max + 1 - min) + min;
    }
}