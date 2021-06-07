package com.gabrielmartinmoran.ourcraft.ourcraft_2.leveling;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.Rock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class AttributeLevelingHandler {

    private HashMap<Material, Integer> blockBreakExperienceMap;
    private final int DEFAULT_BLOCK_BREAK_XP = 1;
    private final int WOOD_BLOCK_BREAK_XP = 2;
    private final ItemStack ROCK_ITEM = new Rock().getItem();

    public void onBlockMined(Player player, Block broken) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        ItemStack tool = player.getInventory().getItemInMainHand();
        String toolMaterialName = tool.getType().toString();
        PlayerData pData = PlayerDataProvider.get(player);
        if (toolMaterialName.endsWith("_AXE") || broken.getType().toString().endsWith("_LOG")) {
            pData.addAttributeXp(PlayerAttributes.CARPENTRY, 1);
            PlayerDataProvider.set(player, pData);
            return;
        }
        if (toolMaterialName.endsWith("_PICKAXE") || toolMaterialName.endsWith("_SHOVEL") || tool.isSimilar(ROCK_ITEM)) {
            pData.addAttributeXp(PlayerAttributes.MINING, 1);
            PlayerDataProvider.set(player, pData);
            return;
        }
        if (toolMaterialName.endsWith("_HOE") || broken.getType().toString().endsWith("_LEAVES") ||
            broken.getType().toString().endsWith("_SAPLIN") ||
            Arrays.asList(
                Material.SUGAR_CANE, Material.KELP, Material.SUGAR_CANE, Material.GRASS, Material.WHEAT,
                Material.POTATOES, Material.CARROTS, Material.BEETROOTS
            ).contains(broken.getType())) {
            pData.addAttributeXp(PlayerAttributes.FARMING, 1);
            PlayerDataProvider.set(player, pData);
            return;
        }
    }

    public void onBlockPlaced(Block block, Player player) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        PlayerData playerData = PlayerDataProvider.get(player);
        if (this.isFarmingBlockPlace(block)) playerData.addAttributeXp(PlayerAttributes.FARMING, 1);
    }

    public void onProjectileHit(Projectile projectile, Entity hitEntity, Block hitBlock) {
        Player player = (Player) projectile.getShooter();
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (hitEntity != null || hitBlock.getType().equals(Material.TARGET)) {
            PlayerData pData = PlayerDataProvider.get(player);
            pData.addAttributeXp(PlayerAttributes.RANGED, 1);
            PlayerDataProvider.set(player, pData);
        }
    }

    public void onItemCrafted(ItemStack result, Player player, int amount) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (this.isCarpentryCrafting(result)) PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.CARPENTRY, amount);
    }

    public void onPlayerMove(Player player, boolean isSwimming) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.RESISTANCE, isSwimming ? Config.SWIMMING_BLOCK_XP : Config.WALKING_BLOCK_XP);
    }

    private boolean isCarpentryCrafting(ItemStack recipeResult) {
        String stringType = recipeResult.getType().toString();
        return stringType.endsWith("_PLANKS") || stringType.endsWith("_SLAB") || stringType.endsWith("_STAIRS") ||
                stringType.endsWith("_FENCE") || stringType.endsWith("_FENCE_GATE")|| stringType.endsWith("_TRAPDOOR") ||
                stringType.endsWith("_DOOR") || stringType.endsWith("_BED") || stringType.endsWith("_SIGN") ||
                Arrays.asList(Material.BOOKSHELF, Material.CHEST, Material.BARREL).contains(recipeResult.getType());
    }

    private boolean isFarmingBlockPlace(Block block) {
        return block.getType().toString().contains("SEEDS") || block.getType().toString().endsWith("_SAPLIN") ||
                Arrays.asList(
                        Material.SUGAR_CANE, Material.COCOA_BEANS, Material.CARROTS, Material.POTATOES, Material.BEETROOTS
                ).contains(block.getType());
    }
}
