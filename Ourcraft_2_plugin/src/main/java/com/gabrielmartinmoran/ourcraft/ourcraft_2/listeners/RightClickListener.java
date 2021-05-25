package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RightClickListener implements Listener {

    private PlayerUtils playerUtils;
    private SpellsResolver spellsResolver;

    public RightClickListener() {
        playerUtils = new PlayerUtils();
        spellsResolver = new SpellsResolver();
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (item != null && (action.equals(Action.RIGHT_CLICK_AIR) ||
                (action.equals(Action.RIGHT_CLICK_BLOCK) && !block.getBlockData().getMaterial().isInteractable()))) {
            if(this.spellsResolver.castsSpell(item)) {
                this.spellsResolver.resolveSpell(player, item);
                return;
            }
        }
    }


}
