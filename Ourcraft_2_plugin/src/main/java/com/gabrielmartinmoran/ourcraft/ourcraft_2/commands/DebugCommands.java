package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags.Bag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags.OgreSkinBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags.ReinforcedBag;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.CopperCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.GoldenCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.PlatinumCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins.SilverCoin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.ManaBattery;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.DistanceTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.PowerTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs.SpeedupTablet;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.AdvancedManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.ManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.RegularManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions.SupremeManaPotion;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.Rock;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.daggers.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.greatsword.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.BambooBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.CompoundBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.ReinforcedCompoundBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows.ReinforcedWoodenBow;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.CustomMobsTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class DebugCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage("" + ChatColor.RED + "Necesitas ser OP para poder ejecutar este comando!");
            return true;
        }
        String order = args[0];
        if (order.equalsIgnoreCase("unicodes")) {
            this.sendValidUnicodes(sender);
            return true;
        }
        if (order.equalsIgnoreCase("unicode")) {
            sender.sendMessage(String.valueOf((char) Integer.parseInt(args[1])));
            return true;
        }
        if (order.equalsIgnoreCase("custom_items")) {
            this.showCustomItems(((Player) sender).getPlayer());
            return true;
        }
        if (order.equalsIgnoreCase("summon") && args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                sender.sendMessage("Listado de custom mobs posibles de sumonear");
                for (CustomMobsTypes type : CustomMobsTypes.values()) {
                    sender.sendMessage("- " + type.toString());
                }
            } else {
                CustomMobsTypes.valueOf(args[1]).getCustomMob().spawn(((LivingEntity) sender).getWorld(),
                        ((LivingEntity) sender).getLocation());
            }
            return true;
        }
        if (order.equalsIgnoreCase("reload")) {
            JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            plugin.getServer().getPluginManager().enablePlugin(plugin);
            return true;
        }
        return false;
    }

    private void sendValidUnicodes(CommandSender sender) {
        int minUnicode = 0x9000;
        int maxUnicode = minUnicode + (256 * 4); // Iconos por pagina * cantidad de paginas

        String result = "";
        for (int i = minUnicode; i <= maxUnicode; i++) {
            result += String.valueOf((char) i) + Integer.toHexString(i) + " ";
        }
        sender.sendMessage(result);
    }

    private void showCustomItems(Player player) {
        List<CustomItem> customItems = Arrays.asList(
                new CopperCoin(), new SilverCoin(), new GoldenCoin(), new PlatinumCoin(), new Rock(),
                new WoodenDagger(),
                new GoldenDagger(), new StoneDagger(), new IronDagger(), new DiamondDagger(), new NetheriteDagger(),
                new WoodenGreatSword(), new GoldenGreatSword(), new StoneGreatSword(), new IronGreatSword(),
                new DiamondGreatSword(), new NetheriteGreatSword(), new BambooBow(), new ReinforcedWoodenBow(),
                new CompoundBow(), new ReinforcedCompoundBow(), new Bag(), new ReinforcedBag(), new OgreSkinBag(),
                new ReinforcedString(), new ReinforcedLeather(), new ManaEssence(), new MagicEssence(),
                new ManaPotion(),
                new RegularManaPotion(), new AdvancedManaPotion(), new SupremeManaPotion(),
                new SpellBook(SpellTypes.NONE, 0), new PurifiedWaterBottle(), new OgreSkin(),
                new CombatPearl(), new OrcSkin(), new ManaGenerator(), new TeleportingStone(), new ManaTurret(),
                new DistanceTablet(), new PowerTablet(), new SpeedupTablet(), new ManaStorager(), new ManaRepeater(),
                new ManaBottler(), new ManaCharger(), new ManaBattery()
        );
        int size = customItems.size() + (9 - (customItems.size() % 9));
        Inventory inventory = Bukkit.createInventory(null, size, "Custom items");
        for (CustomItem customItem : customItems) {
            inventory.addItem(customItem.getItem());
        }
        player.openInventory(inventory);
    }
}
