package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlockStateProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.CustomBlockState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.HopperMinecart;

import java.util.Collection;

public class CustomBlockTickerService implements Runnable {

    public static int EXECUTE_EACH_TICKS = 10;

    @Override
    public void run() {
        for (CustomBlockState state : CustomBlockStateProvider.all()) {
            if (state.isLoaded()) {
                state.onTick();
                if (state.canReceiveItemsFromHopper()) this.checkHopperTransference(state);
            }
        }
    }

    protected void checkHopperTransference(CustomBlockState state) {
        Block block = state.getWorld().getBlockAt(state.getLocation());
        Block upBlock = block.getRelative(BlockFace.UP);
        if (upBlock != null && upBlock.getType() == Material.HOPPER && ((Hopper) upBlock.getBlockData()).isEnabled()) {
            state.tryGetFromExternalInventory(((org.bukkit.block.Hopper) upBlock.getState()).getInventory());
        }
        Collection<Entity> minecartsWithHopper = upBlock.getWorld().getNearbyEntities(block.getLocation(), 1.0, 1.1, 1.0, e -> (e instanceof HopperMinecart));
        for (Entity minecartWithHopper : minecartsWithHopper) {
            state.tryGetFromExternalInventory(((HopperMinecart) minecartWithHopper).getInventory());
        }
    }
}
