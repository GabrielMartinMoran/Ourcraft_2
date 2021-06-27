package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StackSizeModifier {
    /**
     * Modify the maximum stack sizes of an item on the server.
     *
     * @param material Item to change maximum stack size of.
     * @param size The new maximum stack size.
     * @return
     */
    public static boolean modifyStackSize(Material material, int size) {
        if (material.getMaxStackSize() == size) return true;
        try {
            // Get the server package version.
            // In 1.14, the package that the server class CraftServer is in, is called "org.bukkit.craftbukkit.v1_14_R1".
            String packageVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            // Convert a Material into its corresponding Item by using the getItem method on the Material.
            Class<?> magicClass = Class.forName("org.bukkit.craftbukkit." + packageVersion + ".util.CraftMagicNumbers");
            Method method = magicClass.getDeclaredMethod("getItem", Material.class);
            Object item = method.invoke(null, material);
            // Get the maxItemStack field in Item and change it.
            Class<?> itemClass = Class.forName("net.minecraft.server." + packageVersion + ".Item");
            Field field = itemClass.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(item, size);
            // Change the maxStack field in the Material.
            Field mf = Material.class.getDeclaredField("maxStack");
            mf.setAccessible(true);
            mf.setInt(material, size);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(String.format("Reflection error while modifying maximum stack size of %s.", material.name()));
            return false;
        }
    }
}
