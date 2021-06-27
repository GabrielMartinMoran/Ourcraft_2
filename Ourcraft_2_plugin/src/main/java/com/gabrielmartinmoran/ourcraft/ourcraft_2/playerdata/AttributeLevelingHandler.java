package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.Rock;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AttributeLevelingHandler {

    private final ItemStack ROCK_ITEM = new Rock().getItem();

    public void onBlockBreak(Player player, Block broken) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        PlayerAttributes attribute = null;
        if (this.isCarpentryBlockBreak(broken, player.getInventory().getItemInMainHand()))
            attribute = PlayerAttributes.CARPENTRY;
        if (attribute == null && this.isMiningBlockBreak(broken, player.getInventory().getItemInMainHand()))
            attribute = PlayerAttributes.MINING;
        if (attribute == null && this.isFarmingBlockBreak(broken, player.getInventory().getItemInMainHand()))
            attribute = PlayerAttributes.FARMING;
        if (attribute != null) PlayerDataProvider.get(player).addAttributeXp(attribute, 1);
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
            pData.addAttributeXp(PlayerAttributes.RANGED, this.getProjectileHitExperience(projectile, hitEntity == null));
            PlayerDataProvider.set(player, pData);
        }
    }

    public void onItemCrafted(ItemStack result, Player player, int amount) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        int xpAmount = amount;
        if (result.getType().toString().contains("_PLANKS")) xpAmount = amount / 2;
        if (this.isCarpentryCrafting(result))
            PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.CARPENTRY, xpAmount);
    }

    public void onPlayerMove(Player player, boolean isSwimming) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.RESISTANCE, isSwimming ? Config.SWIMMING_BLOCK_XP : Config.WALKING_BLOCK_XP);
    }

    public void onBreeding(Player player) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.FARMING, Config.BREEDING_XP);
    }

    public void onItemEnchant(Player player, int expLevelCost) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.MAGIC, expLevelCost);
    }

    public void onSpellCasted(Player player, int level) {
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        double xp = Math.pow(level, Config.SPELL_CAST_XP_POW_FACTOR) * Config.SPELL_CAST_XP_MULTIPLIER;
        PlayerDataProvider.get(player).addAttributeXp(PlayerAttributes.MAGIC, xp);
    }

    private boolean isCarpentryBlockBreak(Block block, ItemStack tool) {
        String toolMaterialName = tool.getType().toString();
        // Checkeamos el log por si rompe con la mano
        return block.getType().getHardness() > 0 && (block.getType().toString().endsWith("_LOG") || toolMaterialName.endsWith("_AXE"));
    }

    private boolean isMiningBlockBreak(Block block, ItemStack tool) {
        String toolMaterialName = tool.getType().toString();
        return block.getType().getHardness() > 0 && (toolMaterialName.endsWith("_PICKAXE") || toolMaterialName.endsWith("_SHOVEL") || tool.isSimilar(ROCK_ITEM));
    }

    private boolean isFarmingBlockBreak(Block block, ItemStack tool) {
        String toolMaterialName = tool.getType().toString();
        return toolMaterialName.endsWith("_HOE") || block.getType().toString().endsWith("_LEAVES") ||
                block.getType().toString().endsWith("_SAPLIN") || Arrays.asList(
                Material.SUGAR_CANE, Material.KELP, Material.SUGAR_CANE, Material.GRASS, Material.SEAGRASS,
                Material.WHEAT, Material.POTATOES, Material.CARROTS, Material.BEETROOTS, Material.SWEET_BERRY_BUSH,
                Material.VINE, Material.TWISTING_VINES_PLANT, Material.WEEPING_VINES_PLANT, Material.MELON,
                Material.PUMPKIN, Material.NETHER_WART, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM,
                Material.WARPED_FUNGUS, Material.CRIMSON_FUNGUS
        ).contains(block.getType());
    }

    private boolean isCarpentryCrafting(ItemStack recipeResult) {
        String stringType = recipeResult.getType().toString();
        return stringType.endsWith("_PLANKS") || stringType.endsWith("_SLAB") || stringType.endsWith("_STAIRS") ||
                stringType.endsWith("_FENCE") || stringType.endsWith("_FENCE_GATE") || stringType.endsWith("_TRAPDOOR") ||
                stringType.endsWith("_DOOR") || stringType.endsWith("_BED") || stringType.endsWith("_SIGN") ||
                Arrays.asList(
                        Material.BOOKSHELF, Material.CHEST, Material.BARREL
                ).contains(recipeResult.getType());
    }

    private boolean isFarmingBlockPlace(Block block) {
        return block.getType().toString().contains("SEEDS") || block.getType().toString().endsWith("_SAPLIN") ||
                Arrays.asList(
                        Material.SUGAR_CANE, Material.COCOA_BEANS, Material.CARROTS, Material.POTATOES, Material.BEETROOTS,
                        Material.SWEET_BERRY_BUSH, Material.VINE, Material.TWISTING_VINES_PLANT, Material.WEEPING_VINES_PLANT,
                        Material.NETHER_WART, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.WARPED_FUNGUS,
                        Material.CRIMSON_FUNGUS

                ).contains(block.getType());
    }

    private String getSerializedBlockPosition(Projectile projectile) {
        if (projectile.hasMetadata("shootPosition")) {
            return projectile.getMetadata("shootPosition").get(0).asString();
        }
        return null;
    }

    private double getProjectileHitExperience(Projectile projectile, boolean hitBlock) {
        String serializedShootPosition = this.getSerializedBlockPosition(projectile);
        if (serializedShootPosition == null) return 1d;
        String[] coords = serializedShootPosition.split(";");
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        double z = Double.parseDouble(coords[2]);
        Location shootLocation = new Location(projectile.getLocation().getWorld(), x, y, z);
        double distance = projectile.getLocation().distance(shootLocation);
        double divisionFactor = hitBlock ? 8d : 2d;
        if (projectile instanceof Snowball) divisionFactor *= 2;
        return Math.floor(distance / divisionFactor);
    }
}
