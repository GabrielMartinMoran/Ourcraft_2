package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs.CustomMob;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CustomMobsSpawner {

    private Random rand;
    private HashMap<EntityType, MobReplacement[]> replacementsMap;

    public CustomMobsSpawner() {
        rand = new Random();
        this.replacementsMap = new HashMap<EntityType, MobReplacement[]>();
        this.replacementsMap.put(EntityType.ZOMBIE, new MobReplacement[]{
                //new MobReplacement(55, CustomMobsTypes.NONE),
                //new MobReplacement(20, CustomMobsTypes.HAUNTED),
                new MobReplacement(20, CustomMobsTypes.REVENANT),
                //new MobReplacement(10, CustomMobsTypes.CICLOPE),
                //new MobReplacement(10, CustomMobsTypes.HOGLIN_TAMER),
                //new MobReplacement(5, CustomMobsTypes.HERALDO_MUERTE),
        });
    }

    public void replaceIfNeeded(Creature creature) {
        NBTEntity nbtEntity = new NBTEntity(creature);
        if (nbtEntity.hasKey(CustomMob.CUSTOM_MOB_TAG) && nbtEntity.getBoolean(CustomMob.CUSTOM_MOB_TAG)) return;
        if (this.replacementsMap.containsKey(creature.getType())) {
            MobReplacement replacement = getReplacement(creature.getType());
            if(replacement.getMobType() != CustomMobsTypes.NONE) {
                replacement.spawn(creature.getWorld(), creature.getLocation());
                despawnCreature(creature);
            }
        }
    }

    private MobReplacement getReplacement(EntityType type) {
        MobReplacement[] cMobs = this.replacementsMap.get(type);
        ArrayList<Integer> weightedIndexes = new ArrayList<Integer>();
        for (int i = 0; i < cMobs.length; i++) {
            for(int j = 0; j < cMobs[i].getSpawnProbability(); j++) {
                weightedIndexes.add(i);
            }
        }
        return cMobs[weightedIndexes.get(rand.nextInt(weightedIndexes.size()))];
    }

    private void despawnCreature(Creature creature) {
        creature.remove();
    }
}
