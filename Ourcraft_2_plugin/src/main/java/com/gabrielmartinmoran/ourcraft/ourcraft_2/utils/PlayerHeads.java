package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import de.tr7zw.nbtapi.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class PlayerHeads {

    /*
    * URL de cabezas: https://minecraft-heads.com/custom-heads/search?searchword=&author=
    * */

    public static ItemStack get(String base64EncodedString, String name) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setDisplayName(name);
        skull.setItemMeta(meta);
        NBTItem item = new NBTItem(skull);
        NBTCompound skullOwner = item.getOrCreateCompound("SkullOwner");
        NBTCompound properties = skullOwner.getOrCreateCompound("Properties");
        properties.getOrCreateCompound("textures");
        NBTCompoundList textures = properties.getCompoundList("textures");
        NBTCompound headTexture = textures.addCompound();
        headTexture.setString("Value", base64EncodedString);
        skullOwner.setUUID("Id", UUID.nameUUIDFromBytes(base64EncodedString.getBytes()));
        skull = item.getItem();
        return skull;
    }
}
