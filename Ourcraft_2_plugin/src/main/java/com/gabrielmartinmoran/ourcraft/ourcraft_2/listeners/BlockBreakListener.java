package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.Rock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationDecreaseEvents;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.security.SecureRandom;

public class BlockBreakListener implements Listener {

    private AttributeLevelingHandler attributeLevelingHandler;
    private SecureRandom rand;

    public BlockBreakListener() {
        this.attributeLevelingHandler = new AttributeLevelingHandler();
        this.rand = new SecureRandom();
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent event) {
        /*
        for (Block block : event.blockList()) {
            // TODO: Add custom blocks drop
        }
        */
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        this.attributeLevelingHandler.onBlockBreak(player, event.getBlock());
        if (player != null && (player.getGameMode() != GameMode.CREATIVE) && event.getBlock().getType().equals(Material.STONE)) {
            if (event.getBlock().getDrops(player.getInventory().getItemInMainHand()).isEmpty())
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new Rock().getItem());
        }
        if (player.getGameMode() != GameMode.CREATIVE)
            PlayerDataProvider.get(player).getHydrationManager().consumeHydration(HydrationDecreaseEvents.BLOCK_BREAK);
        //this.handleCustomDrops(event);
    }

    private void handleCustomDrops(BlockBreakEvent event) {
        int lootingEnchantment = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        PotionEffect luckEffect = event.getPlayer().getPotionEffect(PotionEffectType.LUCK);
        int minDrop = 1;
        //if (luckEffect != null) minDrop += luckEffect.getAmplifier() + 1;
        int maxDrop = minDrop + lootingEnchantment;
        ItemStack toDrop = null;
        // TODO: Return items here based on the broken item
        if (toDrop == null) return;
        toDrop.setAmount((int) ((Math.random() * (maxDrop - minDrop)) + minDrop));
        event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), toDrop);
        event.setDropItems(false);
    }
}