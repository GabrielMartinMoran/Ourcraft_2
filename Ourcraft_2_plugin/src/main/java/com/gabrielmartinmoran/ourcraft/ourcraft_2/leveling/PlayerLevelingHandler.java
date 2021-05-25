package com.gabrielmartinmoran.ourcraft.ourcraft_2.leveling;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.omg.IOP.TAG_CODE_SETS;

import java.util.HashMap;

public class PlayerLevelingService {

    private HashMap<Material, Integer> blockBreakExperienceMap;
    private final int DEFAULT_BLOCK_BREAK_XP = 1;
    private final int WOOD_BLOCK_BREAK_XP = 2;

    public PlayerLevelingService() {
        this.blockBreakExperienceMap = new HashMap<Material, Integer>();
        this.blockBreakExperienceMap.put(Material.COAL_ORE, 2);
        this.blockBreakExperienceMap.put(Material.IRON_ORE, 4);
        this.blockBreakExperienceMap.put(Material.OBSIDIAN, 4);
        this.blockBreakExperienceMap.put(Material.CRYING_OBSIDIAN, 4);
        this.blockBreakExperienceMap.put(Material.REDSTONE_ORE, 4);
        this.blockBreakExperienceMap.put(Material.LAPIS_ORE, 4);
        this.blockBreakExperienceMap.put(Material.NETHER_GOLD_ORE, 4);
        this.blockBreakExperienceMap.put(Material.GOLD_ORE, 6);
        this.blockBreakExperienceMap.put(Material.NETHER_QUARTZ_ORE, 6);
        this.blockBreakExperienceMap.put(Material.DIAMOND_ORE, 8);
        this.blockBreakExperienceMap.put(Material.EMERALD_ORE, 10);
        this.blockBreakExperienceMap.put(Material.ANCIENT_DEBRIS, 16);
        // Madera
        this.blockBreakExperienceMap.put(Material.OAK_LOG, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.SPRUCE_LOG, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.BIRCH_LOG, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.JUNGLE_LOG, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.ACACIA_LOG, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.DARK_OAK_LOG, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.CRIMSON_STEM, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.WARPED_STEM, WOOD_BLOCK_BREAK_XP);
        this.blockBreakExperienceMap.put(Material.MUSHROOM_STEM, WOOD_BLOCK_BREAK_XP);
    }

    public void onBlockMined(Player player, Block block) {
        Player player = event.getPlayer();
        if(player.getGameMode() != GameMode.SURVIVAL) return;
        Block block = event.getBlock();
        Material material = block.getBlockData().getMaterial();
        if (this.blockBreakExperienceMap.containsKey(material)) {
            this.incrementPlayerExperience(player, this.blockBreakExperienceMap.get(material));
        } else {
            this.incrementPlayerExperience(player, DEFAULT_BLOCK_BREAK_XP);
        }
    }

    private void incrementPlayerExperience(Player player, int xp) {
        PlayerData pData = PlayerDataProvider.get(player.getName());
        pData.totalXP += xp;
        this.checkLevelUp(player, pData);
        PlayerDataProvider.set(player.getName(), pData);
    }

    private void checkLevelUp(Player player, PlayerData pData) {

    }
}
