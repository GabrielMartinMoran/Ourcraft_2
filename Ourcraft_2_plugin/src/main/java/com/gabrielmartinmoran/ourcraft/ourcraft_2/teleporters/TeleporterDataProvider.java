package com.gabrielmartinmoran.ourcraft.ourcraft_2.teleporters;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
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

public class TeleporterDataProvider {

    private static final String DATA_PATH = "teleporters_data";

    private static HashMap<String, Teleporter> dataMap = new HashMap<String, Teleporter>();

    public static Teleporter get(String serializedLocation) {
        if (dataMap.containsKey(serializedLocation)) return dataMap.get(serializedLocation);
        String json = loadJsonData(serializedLocation);
        if (json == null) return null;
        Teleporter data = Teleporter.fromJson(json);
        dataMap.put(serializedLocation, data);
        return data;
    }

    public static void set(String serializedLocation, Teleporter teleporter) {
        dataMap.put(serializedLocation, teleporter);
    }

    public static boolean hasData(String serializedLocation) {
        if (dataMap.containsKey(serializedLocation)) return true;
        File tempFile = new File(getJsonFullPath(serializedLocation));
        return tempFile.exists();
    }

    public static void saveAll() {
        System.out.println("Guardando datos de teleporters de Ourcraft 2");
        for (HashMap.Entry<String, Teleporter> entry : dataMap.entrySet()) {
            saveJsonData(entry.getKey(), entry.getValue().toJson());
        }
    }

    public static void remove(String serializedLocation) {
        dataMap.remove(serializedLocation);
        removeJsonData(serializedLocation);
    }

    public static String serializeLocation(Location location) {
        return location.getWorld().getName() + "(" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ")";
    }

    private static String loadJsonData(String serializedLocation) {
        if(!hasData(serializedLocation)) return null;
        Path path = Paths.get(getJsonFullPath(serializedLocation));
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return StringUtils.join(lines, "");
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static String getJsonFilename(String serializedLocation) {
        return serializedLocation + ".json";
    }

    private static String getJsonDir() {
        return JavaPlugin.getPlugin(Main.class).getDataFolder() + "/" + DATA_PATH;
    }

    private static String getJsonFullPath(String serializedLocation) {
        return getJsonDir() + "/" + getJsonFilename(serializedLocation);
    }

    private static void saveJsonData(String serializedLocation, String json) {
        File directory = new File(getJsonDir());
        if (! directory.exists()){
            directory.mkdirs();
        }
        File file = new File(getJsonFullPath(serializedLocation));
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void removeJsonData(String serializedLocation) {
        File directory = new File(getJsonDir());
        if (!directory.exists()) return;
        File file = new File(getJsonFullPath(serializedLocation));
        try{
            Files.deleteIfExists(file.toPath());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}