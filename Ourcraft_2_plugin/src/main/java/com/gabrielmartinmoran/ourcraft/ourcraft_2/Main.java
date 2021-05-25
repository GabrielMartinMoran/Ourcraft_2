package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.commands.SpellbookCommand;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.leveling.PlayerLevelingService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.recipes.RecipesLoader;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.ui.PlayerUIUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private PlayerUIUpdater playerUIUpdater;
    private RecipesLoader recipesLoader;

    @Override
    public void onEnable() {
        System.out.println("Plugin Ourcraft 2 iniciado correctamente!");
        // Commands
        this.getCommand("spellbook").setExecutor(new SpellbookCommand());
        // Event handlers
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);
        getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLevelingService(), this);
        getServer().getPluginManager().registerEvents(new WorldSaveListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
        this.configureRunnables();
        this.recipesLoader = new RecipesLoader();
        this.recipesLoader.loadRecipes();
    }

    @Override
    public void onDisable() {
        PlayerDataProvider.saveAll();
    }

    private void configureRunnables() {
        this.playerUIUpdater = new PlayerUIUpdater();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.playerUIUpdater, 0L, 1L);
    }

    // https://www.youtube.com/watch?v=GK4aQzxQSoQ
}
