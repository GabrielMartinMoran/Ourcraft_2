package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores.RawGold;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores.RawIron;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.Rock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationDecreaseEvents;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private AttributeLevelingHandler attributeLevelingHandler;
    private Random rand;

    public BlockBreakListener() {
        this.attributeLevelingHandler = new AttributeLevelingHandler();
        this.rand = new Random();
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent event) {
        for (Block block: event.blockList()) {
            if(block.getType().equals(Material.GOLD_ORE)) {
                block.getWorld().dropItem(block.getLocation(), new RawGold().getItem());
                block.setType(Material.AIR);
                continue;
            }
            if(block.getType().equals(Material.IRON_ORE)) {
                block.getWorld().dropItem(block.getLocation(), new RawIron().getItem());
                block.setType(Material.AIR);
                continue;
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        this.attributeLevelingHandler.onBlockBreak(player, event.getBlock());
        if (player != null && (player.getGameMode() != GameMode.CREATIVE) && event.getBlock().getType().equals(Material.STONE)) {
            if(event.getBlock().getDrops(player.getInventory().getItemInMainHand()).isEmpty()) event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new Rock().getItem());
        }
        if (player.getGameMode() != GameMode.CREATIVE)
            PlayerDataProvider.get(player).getHydrationManager().consumeHydration(HydrationDecreaseEvents.BLOCK_BREAK);
        this.handleCustomDrops(event);
    }

    private void handleCustomDrops(BlockBreakEvent event) {
        int lootingEnchantment = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        PotionEffect luckEffect = event.getPlayer().getPotionEffect(PotionEffectType.LUCK);
        int minDrop = 1;
        //if (luckEffect != null) minDrop += luckEffect.getAmplifier() + 1;
        int maxDrop = minDrop + lootingEnchantment;
        ItemStack toDrop = null;
        if (this.dropsRawIron(event)) toDrop = new RawIron().getItem();
        if (this.dropsRawGold(event)) toDrop = new RawGold().getItem();
        if (toDrop == null) return;
        toDrop.setAmount((int) ((Math.random() * (maxDrop - minDrop)) + minDrop));
        event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), toDrop);
        event.setDropItems(false);
    }

    private boolean dropsRawIron(BlockBreakEvent event) {
        return this.dropsRawOre(event) && event.getBlock().getType().equals(Material.IRON_ORE);
    }

    private boolean dropsRawGold(BlockBreakEvent event) {
        return this.dropsRawOre(event) && event.getBlock().getType().equals(Material.GOLD_ORE);
    }

    private boolean dropsRawOre(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return false;
        return (event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).size() > 0) && event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) == 0;
    }
}