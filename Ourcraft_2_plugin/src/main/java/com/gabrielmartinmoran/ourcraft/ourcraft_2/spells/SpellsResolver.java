package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ItemUtils;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerUtils;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.SpellsUtils;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellsResolver {

    private ItemUtils itemUtils;
    private PlayerUtils playerUtils;
    private SecureRandom rand;
    private AttributeLevelingHandler attributeLevelingHandler;
    private HashMap<SpellTypes, SpellCastFunction<Player, ItemStack>> spellsMap;
    private static final String SPELL_PROJECTILE_TAG = "spellProjectile";
    private static final String SPELL_TYPE_TAG = "spellType";
    private static final String SPELL_LEVEL_TAG = "spellLevel";
    public static HashMap<Entity, SpellTrailInfo> flyingSpellEntities = new HashMap<Entity, SpellTrailInfo>();
    public static ArrayList<Entity> entitiesDamagedBySpells = new ArrayList<Entity>();

    @FunctionalInterface
    interface SpellCastFunction<Player, ItemStack> {
        public void apply(Player player, ItemStack stack, int level);
    }

    public SpellsResolver() {
        this.itemUtils = new ItemUtils();
        this.playerUtils = new PlayerUtils();
        this.attributeLevelingHandler = new AttributeLevelingHandler();
        this.rand = new SecureRandom();
        spellsMap = new HashMap<SpellTypes, SpellCastFunction<Player, ItemStack>>();
        spellsMap.put(SpellTypes.LIGHTNING, (player, item, level) -> this.castLightningSpell(player, item, level));
        spellsMap.put(SpellTypes.NECROMANCER, (player, item, level) -> this.castNecromancerSpell(player, item, level));
        spellsMap.put(SpellTypes.FIREBALL, (player, item, level) -> this.castFireballSpell(player, item, level));
        spellsMap.put(SpellTypes.HEALING, (player, item, level) -> this.castHealingSpell(player, item, level));
        spellsMap.put(SpellTypes.LEVITATE, (player, item, level) -> this.castLevitateSpell(player, item, level));
        spellsMap.put(SpellTypes.TELEPORT, (player, item, level) -> this.castTeleportSpell(player, item, level));
        spellsMap.put(SpellTypes.SLOW_FALL, (player, item, level) -> this.castSlowFallSpell(player, item, level));
        spellsMap.put(SpellTypes.FIRE_RESISTANCE, (player, item, level) -> this.castFireResistanceSpell(player, item, level));
        spellsMap.put(SpellTypes.MAGIC_ARROWS, (player, item, level) -> this.castMagicArrowsSpell(player, item, level));
        spellsMap.put(SpellTypes.MAGIC_MISSILES, (player, item, level) -> this.castMagicMissilesSpell(player, item, level));
        spellsMap.put(SpellTypes.POISON_CLOUD, (player, item, level) -> this.castPoisonCloudSpell(player, item, level));
        spellsMap.put(SpellTypes.MAKE_LEVITATE, (player, item, level) -> this.castMakeLevitateSpell(player, item, level));
        spellsMap.put(SpellTypes.NAUSEATING_ORB, (player, item, level) -> this.castNauseatingOrbSpell(player, item, level));
        spellsMap.put(SpellTypes.BLINDNESS_ORB, (player, item, level) -> this.castBlindnessOrbSpell(player, item, level));
        spellsMap.put(SpellTypes.REALENTIZING_ORB, (player, item, level) -> this.castRealentizingOrbSpell(player, item, level));
        spellsMap.put(SpellTypes.EXPLODING_ORB, (player, item, level) -> this.castExplodingOrbSpell(player, item, level));
        spellsMap.put(SpellTypes.GOOD_LUCK, (player, item, level) -> this.castGoodLuckSpell(player, item, level));
        spellsMap.put(SpellTypes.HASTE, (player, item, level) -> this.castHasteSpell(player, item, level));
        spellsMap.put(SpellTypes.MAGIC_SHOT, (player, item, level) -> this.castMagicShotSpell(player, item, level));
        spellsMap.put(SpellTypes.FIRE_RETARDANT_EFFORD, (player, item, level) -> this.castFireRetardantEffordSpell(player, item, level));
        spellsMap.put(SpellTypes.EXPLOSION, (player, item, level) -> this.castExplosionSpell(player, item, level));
        spellsMap.put(SpellTypes.SPEED_UP, (player, item, level) -> this.castSpeedUpSpell(player, item, level));
        spellsMap.put(SpellTypes.SPEED_UP_EFFORD, (player, item, level) -> this.castSpeedUpEffordSpell(player, item, level));
        spellsMap.put(SpellTypes.BURNING_ORB, (player, item, level) -> this.castBurningOrbSpell(player, item, level));
        spellsMap.put(SpellTypes.FAKE_LIFE, (player, item, level) -> this.castFakeLifeSpell(player, item, level));
        spellsMap.put(SpellTypes.SELF_HEALING, (player, item, level) -> this.castSelfHealingSpell(player, item, level));
        spellsMap.put(SpellTypes.CLEAN_STATUSES, (player, item, level) -> this.castCleanStatusesSpell(player, item, level));
    }

    public boolean castsSpell(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(SPELL_TYPE_TAG);
    }

    public void resolveSpell(Player player, ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        SpellTypes spellType = SpellTypes.fromInt(nbt.getInteger(SPELL_TYPE_TAG));
        int level = nbt.getInteger(SPELL_LEVEL_TAG);
        PlayerData pData = PlayerDataProvider.get(player);
        boolean creativeMode = player.getGameMode() == GameMode.CREATIVE;
        if (!pData.canCastByCooldown(spellType) && !creativeMode) {
            player.sendMessage("" + ChatColor.RED + "El conjuro " + spellType.getNameColor() + spellType.getName() + ChatColor.RED + " se esta recargando");
            return;
        }
        boolean isScroll = item.getType().equals(Material.PAPER);
        int manaCost = SpellsUtils.calculateSpellBookManaCost(spellType, level);
        if (isScroll) manaCost = 0;
        if (creativeMode || PlayerDataProvider.get(player).getManaManager().getMana() >= manaCost) {
            if (!creativeMode) {
                if (isScroll) {
                    this.destroyScroll(item, player);
                } else {
                    this.reduceItemDurability(item, player);
                    pData.getManaManager().spendMana(manaCost);
                }
                attributeLevelingHandler.onSpellCasted(player, level);
            }
            this.spellsMap.get(spellType).apply(player, item, level);
            pData.resetSpellCooldown(spellType, level);
        } else {
            player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "No tienes suficiente maná para lanzar el conjuro!");
        }
    }

    public SpellTypes getSpellType(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return SpellTypes.fromInt(nbt.getInteger(SPELL_TYPE_TAG));
    }

    public boolean isSpellProjectile(Projectile projectile) {
        return projectile.hasMetadata(SPELL_PROJECTILE_TAG) && projectile.getMetadata(SPELL_PROJECTILE_TAG).get(0).asBoolean();
    }

    public void onProjectileHit(Projectile projectile, Entity target) {
        if (entitiesDamagedBySpells.contains(target)) entitiesDamagedBySpells.remove(target);
        if (flyingSpellEntities.containsKey(projectile)) flyingSpellEntities.remove(projectile);
        SpellTypes spellType = SpellTypes.fromInt(projectile.getMetadata(SPELL_TYPE_TAG).get(0).asInt());
        int spellLevel = projectile.getMetadata(SPELL_LEVEL_TAG).get(0).asInt();
        projectile.remove();
        if (spellType == SpellTypes.MAGIC_MISSILES) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                this.damageEntity(lEntity, 4 * spellLevel, projectile);
                lEntity.removePotionEffect(PotionEffectType.LEVITATION);
            } else {
                projectile.getWorld().createExplosion(projectile.getLocation(), (int) Math.ceil(spellLevel / 2d), false, false, (Entity) projectile.getShooter());
            }
            projectile.getWorld().spawnParticle(Particle.CRIT_MAGIC, projectile.getLocation(), spellLevel * 40);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.POISON_CLOUD) {
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1f, 1f);
            AreaEffectCloud areaEffect = projectile.getWorld().spawn(projectile.getLocation(), AreaEffectCloud.class);
            areaEffect.addCustomEffect(new PotionEffect(PotionEffectType.POISON, (2 * spellLevel) * Config.TICKS_PER_SECOND, spellLevel - 1), true);
            areaEffect.setRadius(2 * spellLevel);
            areaEffect.setSource(projectile.getShooter());
            return;
        }
        if (spellType == SpellTypes.FIREBALL) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                this.damageEntity(lEntity, 2 * (spellLevel * spellLevel), projectile);
            }
            projectile.getWorld().spawnParticle(Particle.FLAME, projectile.getLocation(), 150 * spellLevel);
            return;
        }
        if (spellType == SpellTypes.MAKE_LEVITATE) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                lEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, (1 + (spellLevel * 2)) * Config.TICKS_PER_SECOND, spellLevel - 1));
            }
            projectile.getWorld().spawnParticle(Particle.SPELL, projectile.getLocation(), spellLevel * 60);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.NAUSEATING_ORB) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                lEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (10 + (spellLevel * 3)) * Config.TICKS_PER_SECOND, spellLevel - 1));
            }
            projectile.getWorld().spawnParticle(Particle.SLIME, projectile.getLocation(), spellLevel * 60);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.BLINDNESS_ORB) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                lEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (1 + (spellLevel * 2)) * Config.TICKS_PER_SECOND, spellLevel - 1));
            }
            projectile.getWorld().spawnParticle(Particle.ASH, projectile.getLocation(), spellLevel * 60);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.REALENTIZING_ORB) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                lEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (3 + (spellLevel * 2)) * Config.TICKS_PER_SECOND, spellLevel - 1));
            }
            projectile.getWorld().spawnParticle(Particle.SOUL, projectile.getLocation(), spellLevel * 60);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.EXPLODING_ORB) {
            projectile.getWorld().createExplosion(projectile.getLocation(), spellLevel, false, true, projectile);
            return;
        }
        if (spellType == SpellTypes.MAGIC_SHOT) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                this.damageEntity(lEntity, 4 * spellLevel, projectile);
            }
            projectile.getWorld().spawnParticle(Particle.DRAGON_BREATH, projectile.getLocation(), spellLevel * 30);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.BURNING_ORB) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                lEntity.setFireTicks((5 + (spellLevel * 3)) * Config.TICKS_PER_SECOND);
            }
            projectile.getWorld().spawnParticle(Particle.FLAME, projectile.getLocation(), spellLevel * 40);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1f, 1f);
            return;
        }
        if (spellType == SpellTypes.MAGIC_ARROWS) {
            if (target != null) {
                LivingEntity lEntity = (LivingEntity) target;
                int defaultNoDamageTicks = lEntity.getNoDamageTicks();
                lEntity.setNoDamageTicks(0);
                lEntity.damage(2 * spellLevel, (Entity) projectile.getShooter());
                lEntity.setNoDamageTicks(defaultNoDamageTicks);
            }
            projectile.getWorld().spawnParticle(Particle.CRIT, projectile.getLocation(), spellLevel * 50);
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.5f);
        }
    }

    public static void tagProjectile(Projectile projectile, SpellTypes spellType, int spellLevel) {
        projectile.setMetadata(SPELL_PROJECTILE_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), true));
        projectile.setMetadata(SPELL_TYPE_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), spellType.getId()));
        projectile.setMetadata(SPELL_LEVEL_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), spellLevel));
    }

    private void reduceItemDurability(ItemStack item, Player player) {
        boolean destroyed = this.itemUtils.reduceItemDurability(player, item);
        if (destroyed) {
            player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 100);
        }
    }

    private void destroyScroll(ItemStack item, Player player) {
        int stackSize = item.getAmount();
        if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                player.getInventory().getItemInMainHand().getItemMeta().equals(item.getItemMeta())) {
            if (stackSize > 1) {
                player.getInventory().getItemInMainHand().setAmount(stackSize - 1);
            } else {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
        } else {
            if (item.getAmount() > 1) {
                player.getInventory().getItemInOffHand().setAmount(stackSize - 1);
            } else {
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
        player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 100);
    }

    private Snowball throwSnowball(Player player) {
        return this.throwSnowball(player, null);
    }

    private Snowball throwSnowball(Player player, Color trailParticleColor) {
        Snowball snowball = player.launchProjectile(Snowball.class);
        flyingSpellEntities.put(snowball, new SpellTrailInfo(Particle.REDSTONE, trailParticleColor, 20));
        return snowball;
    }

    private void castLightningSpell(Player player, ItemStack item, int level) {
        int thunderRange = 9 + level;
        Location thunderLoc;
        Entity targetedEntity = this.playerUtils.getTargetedLivingEntity(player, thunderRange);
        if (targetedEntity != null) {
            thunderLoc = targetedEntity.getLocation();
        } else {
            thunderLoc = this.playerUtils.getTargetedBlock(player, thunderRange).getLocation();
        }
        for (int i = 0; i < level; i++) {
            if (targetedEntity != null) {
                player.getWorld().strikeLightningEffect(thunderLoc);
            } else {
                player.getWorld().strikeLightning(thunderLoc);
            }
        }
        if (targetedEntity != null) {
            this.damageEntity(((LivingEntity) targetedEntity), (2 * level) * level, player);
        }
    }

    private void castFireballSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1f, 1f);
        Fireball fireball = player.launchProjectile(Fireball.class);
        tagProjectile(fireball, SpellTypes.FIREBALL, level);
    }

    private void castNecromancerSpell(Player player, ItemStack item, int level) {
        int thunderRange = 10;
        Location location;
        Entity targetedEntity = this.playerUtils.getTargetedLivingEntity(player, thunderRange);
        if (targetedEntity != null) {
            location = targetedEntity.getLocation();
        } else {
            location = this.playerUtils.getTargetedBlock(player, thunderRange).getLocation();
        }
        // Lo subimos un bloque
        location.setY(location.getY() + 1);
        player.getWorld().spawnParticle(Particle.SPELL_WITCH, location, level * 100);
        player.getWorld().playSound(location, Sound.ENTITY_VEX_CHARGE, 1f, 0.3f);
        for (int i = 0; i < level; i++) {
            Monster wither = (Monster) player.getWorld().spawnEntity(location, EntityType.WITHER_SKELETON);
            if (targetedEntity != null) wither.setTarget((LivingEntity) targetedEntity);
            wither.getEquipment().setItemInMainHand(new ItemStack(Material.BONE, 1));
            NBTEntity entity = new NBTEntity(wither);
            entity.setString("DeathLootTable", "minecraft:empty");
        }
    }

    private void castHealingSpell(Player player, ItemStack item, int level) {
        ArrayList<Player> players = this.playerUtils.getNearbyPlayers(player, 3d + level);
        players.add(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1f, 0.5f);
        player.spawnParticle(Particle.CRIMSON_SPORE, player.getLocation(), level * 100);
        PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, (2 + (level * 2)) * Config.TICKS_PER_SECOND, 2);
        for (Player p : players) {
            p.addPotionEffect(effect);
        }
    }

    private void castLevitateSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 1f, 1f);
        player.spawnParticle(Particle.WHITE_ASH, player.getLocation(), level * 100);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, (3 + (level * 2)) * Config.TICKS_PER_SECOND, 2));
    }

    private void castTeleportSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), level * 100);
        Location location = player.getLocation();
        Vector dir = location.getDirection();
        dir.normalize();
        dir.multiply(5 + (level * 2));
        location.add(dir);
        player.teleport(location);
    }

    private void castSlowFallSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
        player.spawnParticle(Particle.ASH, player.getLocation(), level * 100);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, (3 + (level * 2)) * Config.TICKS_PER_SECOND, 1));
    }

    private void castFireResistanceSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1f, 1f);
        player.spawnParticle(Particle.CRIMSON_SPORE, player.getLocation(), 500);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (1 + (level * 2)) * Config.TICKS_PER_SECOND, 0));
    }

    private void castMagicArrowsSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1f, 1f);
        for (int i = 0; i < level; i++) {
            SpectralArrow arrow = player.launchProjectile(SpectralArrow.class);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            this.tagProjectile(arrow, SpellTypes.MAGIC_ARROWS, level);
        }
    }

    private void castMagicMissilesSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1f, 1f);
        int maxRange = 8 + (level * 2);
        Entity targetedEntity = this.playerUtils.getTargetedLivingEntity(player, maxRange);
        for (int i = 1; i <= 2 + level; i++) {
            ShulkerBullet bullet = player.launchProjectile(ShulkerBullet.class);
            tagProjectile(bullet, SpellTypes.MAGIC_MISSILES, level);
            bullet.setBounce(true);
            bullet.setGlowing(true);
            bullet.setTarget(targetedEntity);
            Vector velocity = bullet.getVelocity();
            Vector dir = player.getLocation().getDirection();
            dir.normalize();
            dir.multiply(1d + (this.rand.nextDouble() * 0.2d));
            velocity.add(dir);
            bullet.setVelocity(velocity);
        }
    }

    private void castPoisonCloudSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.GREEN);
        snowball.setItem(new ItemStack(Material.SLIME_BALL));
        tagProjectile(snowball, SpellTypes.POISON_CLOUD, level);
    }

    private void castMakeLevitateSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.WHITE);
        snowball.setItem(new ItemStack(Material.PHANTOM_MEMBRANE));
        tagProjectile(snowball, SpellTypes.MAKE_LEVITATE, level);
    }

    private void castNauseatingOrbSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.GREEN);
        snowball.setItem(new ItemStack(Material.SLIME_BALL));
        tagProjectile(snowball, SpellTypes.NAUSEATING_ORB, level);
    }

    private void castBlindnessOrbSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.GRAY);
        snowball.setItem(new ItemStack(Material.FIREWORK_STAR));
        tagProjectile(snowball, SpellTypes.BLINDNESS_ORB, level);
    }

    private void castRealentizingOrbSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.GRAY);
        snowball.setItem(new ItemStack(Material.FIREWORK_STAR));
        tagProjectile(snowball, SpellTypes.REALENTIZING_ORB, level);
    }

    private void castExplodingOrbSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.RED);
        snowball.setItem(new ItemStack(Material.MAGMA_CREAM));
        tagProjectile(snowball, SpellTypes.EXPLODING_ORB, level);
    }

    private void castGoodLuckSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_WOLOLO, 1f, 1f);
        player.spawnParticle(Particle.TOTEM, player.getLocation(), 500);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, (3 + (level * 2)) * Config.TICKS_PER_SECOND, level - 1));
    }

    private void castHasteSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 1f);
        player.spawnParticle(Particle.CRIT, player.getLocation(), 500);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (2 + (level * 3)) * Config.TICKS_PER_SECOND, (int) Math.floor(level / 2)));
    }

    private void castMagicShotSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.fromRGB(32, 124, 156));
        snowball.setItem(new ItemStack(Material.HEART_OF_THE_SEA));
        tagProjectile(snowball, SpellTypes.MAGIC_SHOT, level);
    }

    private void castFireRetardantEffordSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1f, 1f);
        player.spawnParticle(Particle.CRIMSON_SPORE, player.getLocation(), level * 25);
        int effectsDuration = (2 + (level * 3)) * Config.TICKS_PER_SECOND;
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, effectsDuration, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, effectsDuration, level));
    }

    private void castExplosionSpell(Player player, ItemStack item, int level) {
        player.getWorld().createExplosion(player.getLocation(), level, false, true);
    }

    private void castSpeedUpSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
        player.spawnParticle(Particle.WHITE_ASH, player.getLocation(), level * 25);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (5 + (level * 3)) * Config.TICKS_PER_SECOND, (int) Math.floor(level / 2)));
    }

    private void castSpeedUpEffordSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
        player.spawnParticle(Particle.WHITE_ASH, player.getLocation(), level * 25);
        int effectsDuration = (5 + (level * 3)) * Config.TICKS_PER_SECOND;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, effectsDuration, (int) Math.floor(level / 2)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, effectsDuration, level - 1));
    }

    private void castBurningOrbSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1f, 1f);
        Snowball snowball = this.throwSnowball(player, Color.RED);
        snowball.setItem(new ItemStack(Material.MAGMA_BLOCK));
        tagProjectile(snowball, SpellTypes.BURNING_ORB, level);
    }

    private void castFakeLifeSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1f, 0.2f);
        player.spawnParticle(Particle.WARPED_SPORE, player.getLocation(), level * 25);
        int effectDuration = (5 + (level * 3)) * Config.TICKS_PER_SECOND;
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, effectDuration, level - 1));
    }

    private void damageEntity(LivingEntity entity, double amount, Entity source) {
        if (!entitiesDamagedBySpells.contains(entity)) entitiesDamagedBySpells.add(entity);
        entity.damage(amount, source);
    }

    private void castSelfHealingSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1f, 1f);
        player.spawnParticle(Particle.CRIMSON_SPORE, player.getLocation(), level * 25);
        int time = (int) Math.floor((4.5d * level) - 2.5d);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 6));
    }

    private void castCleanStatusesSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 0.5f);
        player.spawnParticle(Particle.WHITE_ASH, player.getLocation(), level * 25);
        ArrayList<PotionEffectType> effectsToIgnore = new ArrayList<PotionEffectType>();
        if (level > 1) {
            effectsToIgnore.add(PotionEffectType.SPEED);
            effectsToIgnore.add(PotionEffectType.NIGHT_VISION);
            effectsToIgnore.add(PotionEffectType.INVISIBILITY);
            effectsToIgnore.add(PotionEffectType.LUCK);
        }
        if (level > 2) {
            effectsToIgnore.add(PotionEffectType.HERO_OF_THE_VILLAGE);
            effectsToIgnore.add(PotionEffectType.CONDUIT_POWER);
            effectsToIgnore.add(PotionEffectType.JUMP);
            effectsToIgnore.add(PotionEffectType.DOLPHINS_GRACE);
        }
        if (level > 3) {
            effectsToIgnore.add(PotionEffectType.FIRE_RESISTANCE);
            effectsToIgnore.add(PotionEffectType.FAST_DIGGING);
            effectsToIgnore.add(PotionEffectType.INCREASE_DAMAGE);
            effectsToIgnore.add(PotionEffectType.WATER_BREATHING);
        }
        if (level > 4) {
            effectsToIgnore.add(PotionEffectType.REGENERATION);
            effectsToIgnore.add(PotionEffectType.DAMAGE_RESISTANCE);
            effectsToIgnore.add(PotionEffectType.ABSORPTION);
        }
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (!effectsToIgnore.contains(effect.getType())) player.removePotionEffect(effect.getType());
        }
    }
}
