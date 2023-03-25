package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class PlayerDataProvider {

    private static final String DATA_PATH = "players_data";

    private static HashMap<String, PlayerData> dataMap = new HashMap<String, PlayerData>();

    public static PlayerData get(Player player) {
        return get(player.getName());
    }

    public static PlayerData get(String playerName) {
        if (dataMap.containsKey(playerName)) return dataMap.get(playerName);
        String json = loadJsonData(playerName);
        if (json == null) return new PlayerData(playerName);
        PlayerData data = PlayerData.fromJson(json);
        dataMap.put(playerName, data);
        return data;
    }

    public static void set(Player player, PlayerData playerData) {
        set(player.getName(), playerData);
    }

    public static void set(String playerName, PlayerData playerData) {
        dataMap.put(playerName, playerData);
    }

    public static boolean hasData(String playerName) {
        File tempFile = new File(getJsonFullPath(playerName));
        return tempFile.exists();
    }

    public static void createData(String playerName) {
        PlayerData data = new PlayerData(playerName);
        saveJsonData(playerName, data.toJson());
    }

    public static void saveAll() {
        System.out.println("Guardando datos de jugadores de Ourcraft 2");
        for (HashMap.Entry<String, PlayerData> entry : dataMap.entrySet()) {
            saveJsonData(entry.getKey(), entry.getValue().toJson());
        }
    }

    private static String loadJsonData(String playerName) {
        if (!hasData(playerName)) return null;
        Path path = Paths.get(getJsonFullPath(playerName));
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return StringUtils.join(lines, "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getJsonFilename(String playerName) {
        return playerName + "_data.json";
    }

    private static String getJsonDir() {
        return JavaPlugin.getPlugin(Main.class).getDataFolder() + "/" + DATA_PATH;
    }

    private static String getJsonFullPath(String playerName) {
        return getJsonDir() + "/" + getJsonFilename(playerName);
    }

    private static void saveJsonData(String playerName, String json) {
        File directory = new File(getJsonDir());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(getJsonFullPath(playerName));
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
