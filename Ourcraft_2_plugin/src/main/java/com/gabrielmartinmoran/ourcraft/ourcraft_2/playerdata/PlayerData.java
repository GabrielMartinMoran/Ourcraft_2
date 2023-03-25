package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLocker;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.PlayerHydration;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.deserializers.LocalDateTimeDeserializer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers.LocalDateTimeSerializer;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerNotifier;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.SkillsLevelingUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.bukkit.Bukkit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class PlayerData {

    private String playerName;
    private int difficultyZone;
    private PlayerMana mana;
    private HashMap<SpellTypes, SpellCooldown> spellsCooldown;
    private PlayerHydration hydration;
    private HashMap<PlayerAttributes, PlayerAttribute> attributes;
    private HashMap<String, String> memories;

    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.mana = new PlayerMana();
        this.hydration = new PlayerHydration();
        this.spellsCooldown = new HashMap<SpellTypes, SpellCooldown>();
        // Attributes
        this.attributes = new HashMap<PlayerAttributes, PlayerAttribute>();
        for (PlayerAttributes attribute : PlayerAttributes.values()) {
            this.attributes.put(attribute, new PlayerAttribute(attribute));
        }
        this.difficultyZone = 1;
        this.memories = new HashMap<String, String>();
    }

    public int getAttributeLevel(PlayerAttributes attribute) {
        if (this.attributes.containsKey(attribute)) return this.attributes.get(attribute).getLevel();
        return 1;
    }

    /**
     * @param attribute
     * @return xp floored as long
     */
    public long getAttributeXp(PlayerAttributes attribute) {
        if (this.attributes.containsKey(attribute)) return (long) Math.floor(this.attributes.get(attribute).getXp());
        return 0;
    }

    public void addAttributeXp(PlayerAttributes attribute, double amount) {
        boolean leveledUp = false;
        if (this.attributes.containsKey(attribute)) {
            leveledUp = this.attributes.get(attribute).addXp(amount);
        } else {
            PlayerAttribute pAttr = new PlayerAttribute(attribute);
            leveledUp = pAttr.addXp(amount);
            this.attributes.put(attribute, pAttr);
        }
        if (leveledUp) this.onAttributeLevelUp(attribute);
    }

    public void setAttributeLevel(PlayerAttributes attribute, int level) {
        if (this.attributes.containsKey(attribute)) {
            this.attributes.get(attribute).setLevel(level);
        } else {
            PlayerAttribute pAttr = new PlayerAttribute(attribute);
            pAttr.setLevel(level);
            this.attributes.put(attribute, pAttr);
        }
        this.onAttributeLevelUp(attribute);
    }

    private void onAttributeLevelUp(PlayerAttributes attribute) {
        int level = this.attributes.get(attribute).getLevel();
        if (level > Config.MAX_ATTRIBUTE_LEVEL) {
            this.attributes.get(attribute).setLevel(Config.MAX_ATTRIBUTE_LEVEL);
            return;
        }
        // En caso de que el atributo sea magia aumentamos el mana
        if (attribute.equals(PlayerAttributes.MAGIC)) {
            this.mana.setMaxMana((int) ((Config.INITIAL_MANA * level) + Math.pow(level - 1, Config.MANA_INCREASE_POW_FACTOR)));
            this.mana.setManaRecover(Config.INITIAL_MANA_RECOVER * level);
        }
        PlayerNotifier.notifySkillLevelUp(playerName, attribute.getDisplayName(), level);
        RecipesLocker.notifyRecipesUnlocked(playerName, attribute, level);
    }

    public PlayerMana getManaManager() {
        return this.mana;
    }

    public PlayerHydration getHydrationManager() {
        return this.hydration;
    }

    public boolean canCastByCooldown(SpellTypes spellType) {
        if (!this.spellsCooldown.containsKey(spellType)) return true;
        return this.spellsCooldown.get(spellType).isReady();
    }

    public void resetSpellCooldown(SpellTypes spellType, int level) {
        if (!this.spellsCooldown.containsKey(spellType))
            this.spellsCooldown.put(spellType, new SpellCooldown(spellType));
        this.spellsCooldown.get(spellType).resetCooldown(level);
    }

    public long getSpellCooldownSeconds(SpellTypes spellType) {
        if (!this.spellsCooldown.containsKey(spellType)) return 0;
        return this.spellsCooldown.get(spellType).getPendingCooldownSeconds();
    }

    public int getDifficultyZone() {
        return this.difficultyZone;
    }

    public void setDifficultyZone(int difficultyZone) {
        this.difficultyZone = difficultyZone;
    }

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public static PlayerData fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        Gson gson = gsonBuilder.create();
        PlayerData data = gson.fromJson(json, PlayerData.class);
        return data;
    }

    public HashMap<String, String> getMemories() {
        return this.memories;
    }
}
