package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.serialization.CustomBlockStateSerialized;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks.*;
import com.google.gson.Gson;
import jline.internal.Nullable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBlockStateProvider {

    private static HashMap<String, CustomBlockState> states;
    private static HashMap<ItemStack, Class<? extends CustomBlockState>> customBlocksMap;

    private static final String DATA_PATH = "custom_blocks_data";

    public static void init() {
        customBlocksMap = new HashMap<>();
        customBlocksMap.put(new ManaGenerator().getItem(), ManaGeneratorBlockState.class);
        customBlocksMap.put(new TeleportingStone().getItem(), TeleportingStoneBlockState.class);
        customBlocksMap.put(new ManaTurret().getItem(), ManaTurretBlockState.class);
        customBlocksMap.put(new ManaStorager().getItem(), ManaStoragerBlockState.class);
        customBlocksMap.put(new ManaRepeater().getItem(), ManaRepeaterBlockState.class);
        customBlocksMap.put(new ManaBottler().getItem(), ManaBottlerBlockState.class);
        customBlocksMap.put(new ManaCharger().getItem(), ManaChargerBlockState.class);
        loadStates();
    }

    public static CustomBlockState get(String id) {
        return states.get(id);
    }

    public static CustomBlockState get(Block block) {
        return get(generateId(block));
    }

    public static boolean contains(String id) {
        return states.containsKey(id);
    }

    public static boolean contains(Block block) {
        return contains(generateId(block));
    }

    public static void put(String id, CustomBlockState state) {
        states.put(id, state);
    }

    public static void put(Block block, CustomBlockState state) {
        put(generateId(block), state);
    }

    public static void remove(Block block) {
        remove(generateId(block));
    }

    public static void remove(String id) {
        states.remove(id);
    }

    public static String generateId(Block block) {
        return generateId(block.getWorld(), block.getLocation());
    }

    public static String generateId(World world, Location location) {
        return String.format("%s|%s", world.getName(), location);
    }

    @Nullable
    public static CustomBlockState searchByInventory(Inventory inventory) {
        for (Map.Entry<String, CustomBlockState> set : states.entrySet()) {
            if (set.getValue().getInventory() == inventory) {
                return set.getValue();
            }
        }
        return null;
    }

    public static void putFromItem(ItemStack item, Block block, Player player) {
        ItemStack key = searchSimilarMappingItem(item);
        Class<? extends CustomBlockState> stateClass = customBlocksMap.get(key);
        try {
            CustomBlockState state =
                    stateClass.getDeclaredConstructor(String.class, World.class, Location.class).
                            newInstance(player.getName(), block.getWorld(), block.getLocation());
            put(block, state);
        } catch (AssertionError e) {
            Bukkit.getServer().getConsoleSender().sendMessage("" + ChatColor.RED + e);
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("" + ChatColor.RED + "Ha ocurrido un error al agregar " + "la información de un bloque personalizado desde un item -> Ignorando: " + e);
        }
    }

    public static boolean needsState(ItemStack item) {
        return searchSimilarMappingItem(item) != null;
    }

    @Nullable
    private static ItemStack searchSimilarMappingItem(ItemStack item) {
        for (ItemStack customBlockItem : customBlocksMap.keySet()) {
            if (customBlockItem.isSimilar(item)) {
                return customBlockItem;
            }
        }
        return null;
    }

    public static Collection<CustomBlockState> all() {
        return states.values();
    }

    public static void saveAll() {
        Bukkit.getServer().getConsoleSender().sendMessage("" + ChatColor.GREEN + "Guardando estados de bloques " +
                "personalizados de Ourcraft 2");
        int blockStatesLength = states.values().size();
        CustomBlockStateSerialized[] serializedStates = new CustomBlockStateSerialized[blockStatesLength];
        CustomBlockState[] blockStates = states.values().toArray(new CustomBlockState[blockStatesLength]);
        for (int i = 0; i < blockStatesLength; i++) {
            CustomBlockState state = blockStates[i];
            CustomBlockStateSerialized serializedState = state.serialize();
            serializedStates[i] = serializedState;
        }
        Gson gson = new Gson();
        saveJsonData(gson.toJson(serializedStates));
    }

    public static void loadStates() {
        states = new HashMap<>();
        String jsonData = loadJsonData();
        if (jsonData == null) return;
        Gson gson = new Gson();
        CustomBlockStateSerialized[] serializedStates = gson.fromJson(jsonData, CustomBlockStateSerialized[].class);
        for (CustomBlockStateSerialized serializedState : serializedStates) {
            for (Class<? extends CustomBlockState> customBlockClass : customBlocksMap.values()) {
                if (serializedState.className.equals(customBlockClass.getSimpleName())) {
                    CustomBlockState state;
                    try {
                        state = (CustomBlockState) customBlockClass.getMethod("deserialize",
                                CustomBlockStateSerialized.class).invoke(null, serializedState);
                        states.put(generateId(state.getWorld(), state.getLocation()), state);
                    } catch (Exception e) {
                        Bukkit.getServer().getConsoleSender().sendMessage("" + ChatColor.RED + "Ha ocurrido un error "
                                + "al tratar de cargar la información un bloque personalizado -> Ignorando: " + e);
                    }
                    break;
                }
            }
        }
    }

    @Nullable
    private static String loadJsonData() {
        if (!existsData()) return null;
        Path path = Paths.get(getJsonFilePath());
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return StringUtils.join(lines, "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getJsonDirectory() {
        return JavaPlugin.getPlugin(Main.class).getDataFolder() + "/" + DATA_PATH;
    }

    private static String getJsonFilePath() {
        return getJsonDirectory() + "/" + "custom_blocks_data.json";
    }

    public static boolean existsData() {
        File tempFile = new File(getJsonFilePath());
        return tempFile.exists();
    }

    private static void saveJsonData(String json) {
        File directory = new File(getJsonDirectory());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(getJsonFilePath());
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
