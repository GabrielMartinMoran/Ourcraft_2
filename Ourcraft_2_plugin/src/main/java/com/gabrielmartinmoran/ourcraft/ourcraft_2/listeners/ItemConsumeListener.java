package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.PurifiedWaterBottle;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions.BaseManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class ItemConsumeListener implements Listener {

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item.getType().equals(Material.POTION)) {
            NBTItem nbt = new NBTItem(item);
            if(nbt.hasKey(BaseManaPotion.IS_MANA_POTION_TAG)) {
                PlayerDataProvider.get(player).getManaManager().recoverMana(nbt.getInteger(BaseManaPotion.MANA_RECOVER_AMOUNT_TAG));
                PlayerDataProvider.get(player).getHydrationManager().addHydration(2);
                return;
            }
            if(nbt.hasKey(PurifiedWaterBottle.IS_PURIFIED_WATER_TAG)) {
                PlayerDataProvider.get(player).getHydrationManager().addHydration(5);
                return;
            }
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            if(meta.getBasePotionData().getType().equals(PotionType.WATER)) {
                PlayerDataProvider.get(player).getHydrationManager().addHydration(4);
                PlayerDataProvider.get(player).getHydrationManager().handleNotPurifiedWaterDrinking(player);
                return;
            }
        }
        this.handleHydration(player, item);
    }

    private void handleHydration(Player player, ItemStack item) {
        double hydrationAmount = 0d;
        if (Arrays.asList(Material.SWEET_BERRIES, Material.HONEY_BOTTLE, Material.CARROT, Material.PUMPKIN_PIE,
                Material.BEETROOT).contains(item.getType())) hydrationAmount = 0.5d;
        if (Arrays.asList(Material.APPLE, Material.MELON_SLICE).contains(item.getType())) hydrationAmount = 1d;
        if (item.getType().toString().contains("STEW") || item.getType().toString().contains("SOUP"))
            hydrationAmount = 2d;
        if (Arrays.asList(Material.BREAD, Material.COOKIE).contains(item.getType())) hydrationAmount = -0.5d;
        if (item.getType().toString().contains("COOKED") || Arrays.asList(Material.BAKED_POTATO, Material.DRIED_KELP,
                Material.ROTTEN_FLESH, Material.CAKE).contains(item.getType())) hydrationAmount = -1d;
        if (hydrationAmount != 0d) PlayerDataProvider.get(player).getHydrationManager().addHydration(hydrationAmount);
    }
}
