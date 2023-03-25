package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsManager;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.MobDrop;
import com.google.gson.Gson;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomMob {

    public static final String IS_CUSTOM_MOB_TAG = "isCustomMob";
    public static final String CUSTOM_MOB_TYPE = "customMobType";

    private CustomMobsTypes type = null;

    public abstract void spawn(World world, Location location);

    protected Mob spawnEntity(World world, Location location, EntityType entityType) {
        Mob mob = (Mob) world.spawnEntity(location, entityType);
        this.tagCustomMob(mob);
        return mob;
    }

    protected Entity spawnMythicMobsEntity(Location location, String mmMobName) {
        MythicBukkit mbInstance = MythicBukkit.inst();
        if (mbInstance == null) {
            System.out.println("MythicMobs instance is null");
            return null;
        }
        ActiveMob activeMob = mbInstance.getMobManager().spawnMob(mmMobName, location);
        Entity mob = (Entity) activeMob.getEntity().getBukkitEntity();
        this.tagCustomMob(mob);
        return mob;
    }

    private void tagCustomMob(Entity mob) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        mob.setMetadata(IS_CUSTOM_MOB_TAG, new FixedMetadataValue(plugin, true));
        mob.setMetadata(CUSTOM_MOB_TYPE, new FixedMetadataValue(plugin, this.type.toString()));
    }

    protected ItemStack getLeatherEquipment(Material itemMaterial, Color color) {
        ItemStack item = new ItemStack(itemMaterial, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    public List<MobDrop> getCustomDrops() {
        return null;
    }

    public boolean hasCustomDrops() {
        return this.getCustomDrops() != null && this.getCustomDrops().size() > 0;
    }

    public List<Material> getNaturalDropsToRemove() {
        return new ArrayList<Material>();
    }

    public boolean preventNaturalDrops() {
        return false;
    }

    public int getCustomDropsRolls() {
        return 1;
    }

    public void setType(CustomMobsTypes type) {
        this.type = type;
    }

    public boolean hasType() {
        return this.type != null;
    }
}
