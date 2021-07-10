package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors.CustomArmorModifiers;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.SecureRandom;
import java.util.*;

public class CustomMeleeWeapon {

    private final double MIN_COPPER_COST = 10;
    private Material baseMaterial;
    private CustomItemsModelData modelData;
    private List<MeleeWeaponCharacteristics> characteristics;
    private int damage;
    private boolean hasCurseOfVanishing;

    public CustomMeleeWeapon(Material baseMaterial, CustomItemsModelData modelData, List<MeleeWeaponCharacteristics> characteristics, int damage, boolean hasCurseOfVanishing) {
        this.baseMaterial = baseMaterial;
        this.modelData = modelData;
        this.characteristics = characteristics;
        this.damage = damage;
        this.hasCurseOfVanishing = hasCurseOfVanishing;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(this.baseMaterial);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setCustomModelData(this.modelData.getModelData());
        meta.setLore(this.getLore());
        item.setItemMeta(meta);
        this.applyDamageModifier(item);
        this.applyEnchantments(item);
        NBTItem nbtItem = new NBTItem(item);
        this.applyCharacteristics(nbtItem);
        if(characteristics.contains(MeleeWeaponCharacteristics.THROWABE)) nbtItem.setFloat("throwingDamage", (float)this.damage);
        item = nbtItem.getItem();
        return item;
    }

    public long calculateCopperCost() {
        double cost = this.getBaseMaterialCost() + this.getDamageCost();
        cost *= this.getCharacteristicsCostMultiplier();
        if (this.hasCurseOfVanishing) cost -= cost * 0.2;
        if (cost < MIN_COPPER_COST) cost = MIN_COPPER_COST;
        return (long) Math.ceil(cost);
    }

    private void applyCharacteristics(NBTItem nbtItem) {
        for (MeleeWeaponCharacteristics characteristic: this.characteristics) {
            nbtItem.setBoolean(characteristic.getTag(), true);
        }
    }

    private double getBaseMaterialCost() {
        if (this.baseMaterial.equals(Material.IRON_SWORD)) return 120d;
        if (this.baseMaterial.equals(Material.IRON_SWORD)) return 100d;
        return 0d;
    }

    private double getCharacteristicsCostMultiplier() {
        double costMultiplier = 1d;
        for (MeleeWeaponCharacteristics characteristic: this.characteristics) {
            costMultiplier += characteristic.isPositive() ? 0.2 : -0.1;
        }
        return costMultiplier;
    }

    private double getDamageCost() {
        return Math.pow(2, this.damage);
    }

    private void applyDamageModifier(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ATTACK_DAMAGE.name(), this.damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        double attackSpeed = this.getAttackSpeed() - MeleeWeapon.BASE_ATTACK_SPEED;
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ATTACK_SPEED.name(), attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        item.setItemMeta(meta);
    }

    private void applyEnchantments(ItemStack item) {
        if (this.hasCurseOfVanishing) item.addEnchantment(Enchantment.VANISHING_CURSE, 1);
    }

    private double getAttackSpeed() {
        if (this.baseMaterial.equals(Material.IRON_SWORD)) return 1.6d;
        if (this.baseMaterial.equals(Material.IRON_AXE)) return 1d;
        return 0d;
    }

    private String getName() {
        SecureRandom rand = new SecureRandom();
        // https://donjon.bin.sh/fantasy/name/#type=set
        List<String> names = Arrays.asList(
                "Castigador de moratales", "Castigador de dragones", "Castigador de demonios",
                "Asesino de mortales", "Asesino de dragones","Asesino de demonios",
                "Carnicero de mortales", "Carnicero de dragones","Carnicero de demonios",
                "Rompe ilusiones", "Destructor de imperios", "Demoledor de esperanzas",
                "Molinillo de llamas", "Verdugo sublime", "Wavedoom", "Destino astral", "Venganza demoníaca",
                "Ascendencia infernal","Subyugador infernal", "Atracador del cielo", "Verdugo luminoso",
                "Rompecráneos", "Furia celestial", "Chaoscrusher", "Giantthorn", "Windblade", "Verdugo eterno",
                "Vindicador celestial", "Colmillo de escarcha", "Depredador celestial", "Tirano elíseo",
                "Tormenta astral", "Tempestad diabólica", "Wraithgrinder", "Lawlance", "Depredador sublime",
                "Subyugador diabólico", "Riverthorn", "Muerdemonios", "Némesis Elíseo", "Fiendpiercer",
                "Vengador abisal", "Ira luminosa", "Venganza astral", "Goblinlance", "Flamereaper", "Verminlance",
                "Campeón eterno", "Venganza sublime", "Vengador negro", "Foescythe", "Skyruin", "Fiendruin",
                "Furia abisal", "Wraithlance", "Ascendencia negra", "Crueldad sublime", "Chaosruin", "Foeblade",
                "Violencia perversa", "Venganza vil", "Ascensión maldita", "Vindicador malvado", "Violencia eterna"
        );
        return names.get(rand.nextInt(names.size()));
    }

    private ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<String>();
        for (MeleeWeaponCharacteristics characteristic: this.characteristics) {
            lore.add("" + ChatColor.GOLD + "- " + characteristic.getDisplayName());
        }
        if(lore.size() > 0) lore.add("");
        // Attributes section
        lore.add("" + ChatColor.GRAY + "When in Main Hand:");
        lore.add(" " + ChatColor.DARK_GREEN + this.damage + " Base Attack Damage");
        if (this.characteristics.contains(MeleeWeaponCharacteristics.THROWABE)) {
            lore.add(" " + ChatColor.DARK_GREEN + this.damage + " Base Throwing Damage");
        }
        lore.add(" " + ChatColor.DARK_GREEN + this.getAttackSpeed() + " Attack Speed");
        return lore;
    }
}
