package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors.CustomArmor;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ManaRecoverService implements Runnable {

    public static int EXECUTE_EACH_TICKS = 20; // 1 segundo

    @Override
    public void run() {
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            PlayerData playerData = PlayerDataProvider.get(player);
            playerData.getManaManager().setMaxManaModifier(this.getEquipmentManaModifier(player));
            playerData.getManaManager().recoverMana();
        }
    }

    private int getEquipmentManaModifier(Player player) {
        int maxManaModifier = 0;
        maxManaModifier += this.getItemManaModifier(player.getInventory().getHelmet());
        maxManaModifier += this.getItemManaModifier(player.getInventory().getChestplate());
        maxManaModifier += this.getItemManaModifier(player.getInventory().getLeggings());
        maxManaModifier += this.getItemManaModifier(player.getInventory().getBoots());
        return maxManaModifier;
    }

    private int getItemManaModifier(ItemStack item) {
        if (item == null) return 0;
        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasKey(CustomArmor.MAX_MANA_MODIFIER_TAG)) return 0;
        return nbtItem.getInteger(CustomArmor.MAX_MANA_MODIFIER_TAG);
    }
}
