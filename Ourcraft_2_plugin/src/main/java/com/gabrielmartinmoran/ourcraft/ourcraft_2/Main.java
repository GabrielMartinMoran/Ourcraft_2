package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.commands.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLoader;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.CustomBlockTickerService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlockStateProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlocksStateSaverService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.WitherReplacementService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories.NBTInventoriesCache;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories.NBTInventoryInteractionListener;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners.*;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributesApplierService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.ManaRecoverService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsTrailsService;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.teleporters.TeleporterCreationListener;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.teleporters.TeleporterDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.ui.PlayerUIUpdater;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.StackSizeModifier;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponsEffectsService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Main extends JavaPlugin {

    private PlayerUIUpdater playerUIUpdater;
    private HydrationService hydrationService;
    private SpellsTrailsService spellsTrailsService;
    private ManaRecoverService manaRecoverService;
    private MeleeWeaponsEffectsService meleeWeaponsEffectsService;
    private AttributesApplierService attributesApplierService;
    private WitherReplacementService witherReplacementService;
    private CustomBlockTickerService customBlockTickerService;

    private CustomBlocksStateSaverService customBlocksStateSaverService;

    private RecipesLoader recipesLoader;

    private NBTInventoriesCache nbtInventoriesCache;

    @Override
    public void onEnable() {
        System.out.println("Plugin Ourcraft 2 iniciado correctamente!");

        this.nbtInventoriesCache = new NBTInventoriesCache();

        CustomBlockStateProvider.init();

        this.configureCommands();
        this.configureEventHandlers();
        this.configureRunnables();
        this.configureRecipes();
        this.setWorldBorder();
        //this.changeStackSizes();
    }

    @Override
    public void onDisable() {
        PlayerDataProvider.saveAll();
        TeleporterDataProvider.saveAll();
        CustomBlockStateProvider.saveAll();
    }

    private void configureCommands() {
        this.getCommand(Commands.SPELLBOOK.toString()).setExecutor(new SpellbookCommand());
        this.getCommand(Commands.DEBUG.toString()).setExecutor(new DebugCommands());
        this.getCommand(Commands.STATS.toString()).setExecutor(new StatsCommand());
        this.getCommand(Commands.PLAYERDATA.toString()).setExecutor(new PlayerDataCommands());
        this.getCommand(Commands.MEMORIES.toString()).setExecutor(new MemoriesCommand());
        this.getCommand(Commands.DIFFICULTYZONE.toString()).setExecutor(new DifficultyZoneCommand());
    }

    private void configureEventHandlers() {
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
        getServer().getPluginManager().registerEvents(new CraftingListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMovementListener(), this);
        getServer().getPluginManager().registerEvents(new ItemConsumeListener(), this);
        getServer().getPluginManager().registerEvents(new BagListener(this.nbtInventoriesCache), this);
        getServer().getPluginManager().registerEvents(new VillagerAcquireTradeListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileShootListener(), this);
        getServer().getPluginManager().registerEvents(new SmithingListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new EntityRegainHealthListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new BreedingListener(), this);
        getServer().getPluginManager().registerEvents(new ItemEnchantingListener(), this);
        getServer().getPluginManager().registerEvents(new PortalCreationListener(), this);
        getServer().getPluginManager().registerEvents(new WorldSaveListener(), this);
        //getServer().getPluginManager().registerEvents(new ItemStackModifierListener(), this);
        getServer().getPluginManager().registerEvents(new TeleporterCreationListener(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
        getServer().getPluginManager().registerEvents(new NBTInventoryInteractionListener(this.nbtInventoriesCache),
                this);
    }

    private void configureRunnables() {
        this.playerUIUpdater = new PlayerUIUpdater();
        this.hydrationService = new HydrationService();
        this.spellsTrailsService = new SpellsTrailsService();
        this.manaRecoverService = new ManaRecoverService();
        this.meleeWeaponsEffectsService = new MeleeWeaponsEffectsService();
        this.attributesApplierService = new AttributesApplierService();
        this.witherReplacementService = new WitherReplacementService();
        this.customBlockTickerService = new CustomBlockTickerService();
        this.customBlocksStateSaverService = new CustomBlocksStateSaverService();

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        scheduler.scheduleSyncRepeatingTask(this, this.playerUIUpdater, PlayerUIUpdater.EXECUTE_EACH_TICKS,
                PlayerUIUpdater.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.hydrationService, HydrationService.EXECUTE_EACH_TICKS,
                HydrationService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.spellsTrailsService, SpellsTrailsService.EXECUTE_EACH_TICKS,
                SpellsTrailsService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.manaRecoverService, ManaRecoverService.EXECUTE_EACH_TICKS,
                ManaRecoverService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.meleeWeaponsEffectsService,
                MeleeWeaponsEffectsService.EXECUTE_EACH_TICKS, MeleeWeaponsEffectsService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.attributesApplierService,
                AttributesApplierService.EXECUTE_EACH_TICKS, AttributesApplierService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.witherReplacementService,
                WitherReplacementService.EXECUTE_EACH_TICKS, WitherReplacementService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.customBlockTickerService,
                CustomBlockTickerService.EXECUTE_EACH_TICKS, CustomBlockTickerService.EXECUTE_EACH_TICKS);
        scheduler.scheduleSyncRepeatingTask(this, this.customBlockTickerService,
                CustomBlocksStateSaverService.EXECUTE_EACH_TICKS, CustomBlocksStateSaverService.EXECUTE_EACH_TICKS);
    }

    private void configureRecipes() {
        this.recipesLoader = new RecipesLoader();
        this.recipesLoader.loadRecipes();
    }

    private void setWorldBorder() {
        for (World world : Bukkit.getWorlds()) {
            world.getWorldBorder().setCenter(0d, 0d);
            world.getWorldBorder().setSize(Config.WORLD_RADIUS * 2);
        }
    }

    private void changeStackSizes() {
        StackSizeModifier.modifyStackSize(Material.POTION, 16);
    }
}
