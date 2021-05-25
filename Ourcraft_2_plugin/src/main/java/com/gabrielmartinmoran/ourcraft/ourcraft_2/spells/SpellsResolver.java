package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ItemUtils;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerUtils;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpellsResolver {

    private ItemUtils itemUtils;
    private PlayerUtils playerUtils;
    Random rand;
    private HashMap<SpellTypes, SpellCastFunction<Player, ItemStack>> spellsMap;
    private final int TICKS_PER_SECOND = 20;
    private final String SPELL_PROJECTILE_TAG = "spellProjectile";
    private final String SPELL_TYPE_TAG = "spellType";
    private final String SPELL_LEVEL_TAG = "spellLevel";

    @FunctionalInterface
    interface SpellCastFunction<Player, ItemStack> {
        public void apply(Player player, ItemStack stack, int level);
    }

    public  SpellsResolver() {
        this.itemUtils = new ItemUtils();
        this.playerUtils = new PlayerUtils();
        this.rand = new Random();
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
        spellsMap.put(SpellTypes.MAGIC_MISSILES, (player, item, level) -> this.castMagicMisislesSpell(player, item, level));
        spellsMap.put(SpellTypes.POISON_CLOUD, (player, item, level) -> this.castPoisonCloudSpell(player, item, level));
    }

    public boolean castsSpell(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey("spellType");
    }

    public void resolveSpell(Player player, ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        SpellTypes spellType = SpellTypes.fromInt(nbt.getInteger("spellType"));
        int level = nbt.getInteger("spellLevel");
        this.spellsMap.get(spellType).apply(player, item, level);
        if (player.getGameMode() != GameMode.CREATIVE) this.reduceItemDurability(item, player);
    }

    public boolean isSpellProjectile(Projectile projectile) {
        List<MetadataValue> metadata = projectile.getMetadata(SPELL_PROJECTILE_TAG);
        if (metadata.size() > 0) return metadata.get(0).asBoolean();
        return false;
    }

    public void onProjectileHit(Projectile projectile, Entity target) {
        SpellTypes spellType = SpellTypes.fromInt(projectile.getMetadata(SPELL_TYPE_TAG).get(0).asInt());
        int spellLevel = projectile.getMetadata(SPELL_LEVEL_TAG).get(0).asInt();
        if (spellType == SpellTypes.MAGIC_MISSILES) {
            LivingEntity lEntity = (LivingEntity)target;
            lEntity.damage(4 * spellLevel, projectile);
            //lEntity.addPotionEffect(new PotionEffect(PotionEffectType.HARM, TICKS_PER_SECOND / 2, (int)Math.floor(spellLevel / 2d)));
            target.getWorld().spawnParticle(Particle.CRIT_MAGIC, target.getLocation(), 200);
            target.getWorld().playSound(target.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 100, 1);
            lEntity.removePotionEffect(PotionEffectType.LEVITATION);
            return;
        }
        if (spellType == SpellTypes.POISON_CLOUD) {
            projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_WITHER_SHOOT, 100, 1);
            AreaEffectCloud areaEffect = projectile.getWorld().spawn(projectile.getLocation(), AreaEffectCloud.class);
            areaEffect.addCustomEffect(new PotionEffect(PotionEffectType.POISON, (2 * spellLevel) * TICKS_PER_SECOND, spellLevel - 1), true);
            areaEffect.setRadius(2 * spellLevel);
            areaEffect.setSource(projectile.getShooter());
            return;
        }
    }

    private void tagProjectile(Projectile projectile, SpellTypes spellType, int spellLevel) {
        projectile.setMetadata(SPELL_PROJECTILE_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), true));
        projectile.setMetadata(SPELL_TYPE_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), spellType.getId()));
        projectile.setMetadata(SPELL_LEVEL_TAG, new FixedMetadataValue(JavaPlugin.getPlugin(Main.class), spellLevel));
    }

    private void reduceItemDurability(ItemStack item, Player player) {
        org.bukkit.inventory.meta.Damageable iMeta = (org.bukkit.inventory.meta.Damageable) item.getItemMeta();
        int unbreakingEnchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        if ((this.rand.nextInt(100) + 1) < (100d/(unbreakingEnchantLevel+1))) {
            iMeta.setDamage(iMeta.getDamage() + 1);
            item.setItemMeta((ItemMeta) iMeta);
        }
        if(item.getType().getMaxDurability() <= iMeta.getDamage()) {
            if(player.getInventory().getItemInMainHand().hasItemMeta() &&
                    player.getInventory().getItemInMainHand().getItemMeta().equals(item.getItemMeta())) {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            } else {
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 100f, 1f);
            player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 750);
        }
    }

    private void castLightningSpell(Player player, ItemStack item, int level) {
        int thunderRange = 10;
        Location thunderLoc;
        Entity targetedEntity = this.playerUtils.getTargetedEntity(player, thunderRange);
        if (targetedEntity != null) {
            thunderLoc = targetedEntity.getLocation();
        } else {
            thunderLoc = this.playerUtils.getTargetedBlock(player, thunderRange).getLocation();
        }
        for (int i = 0; i < level; i++) {
            player.getWorld().strikeLightning(thunderLoc);
        }
    }

    private void castFireballSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 100, 1);
        for (int i = 0; i < level; i++) {
            Fireball fireball = player.launchProjectile(Fireball.class);
        }
    }

    private void castNecromancerSpell(Player player, ItemStack item, int level) {
        int thunderRange = 10;
        Location location;
        Entity targetedEntity = this.playerUtils.getTargetedEntity(player, thunderRange);
        if (targetedEntity != null) {
            location = targetedEntity.getLocation();
        } else {
            location = this.playerUtils.getTargetedBlock(player, thunderRange).getLocation();
        }
        // Lo subimos un bloque
        location.setY(location.getY() + 1);
        player.getWorld().spawnParticle(Particle.SPELL_WITCH, location, 750);
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
        player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 100, 1);
        player.spawnParticle(Particle.PORTAL, player.getLocation(), 500);
        PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, (2 + (level * 2)) * TICKS_PER_SECOND , 2);
        for (Player p: players) {
            p.addPotionEffect(effect);
        }
    }

    private void castLevitateSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 100, 1);
        player.spawnParticle(Particle.WHITE_ASH, player.getLocation(), 500);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, (3 + (level * 2)) * TICKS_PER_SECOND , 2));
    }

    private void castTeleportSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
        player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 500);
        Location location = player.getLocation();
        Vector dir = location.getDirection();
        dir.normalize();
        dir.multiply(5 + (level * 2));
        location.add(dir);
        player.teleport(location);
    }

    private void castSlowFallSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 100, 1);
        player.spawnParticle(Particle.ASH, player.getLocation(), 500);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, (3 + (level * 2)) * TICKS_PER_SECOND , 1));
    }

    private void castFireResistanceSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 100, 1);
        player.spawnParticle(Particle.CRIMSON_SPORE, player.getLocation(), 500);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (1 + (level * 2)) * TICKS_PER_SECOND , 0));
    }

    private void castMagicArrowsSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 1);
        int arrowsPerLevel = 3;
        for (int i = 0; i < (level * arrowsPerLevel); i++) {
            SpectralArrow arrow = player.launchProjectile(SpectralArrow.class);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            Vector velocity = arrow.getVelocity();
            Vector dir = player.getLocation().getDirection();
            dir.normalize();
            dir.multiply(1 + (1d * i));
            velocity.add(dir);
            arrow.setVelocity(velocity);
        }
    }

    private void castMagicMisislesSpell(Player player, ItemStack item, int level) {
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 100, 1);
        int maxRange = 8 + (level * 2);
        Entity targetedEntity = this.playerUtils.getTargetedEntity(player, maxRange);
        for (int i = 1; i <= 2 + level; i++) {
            ShulkerBullet bullet = player.launchProjectile(ShulkerBullet.class);
            tagProjectile(bullet, SpellTypes.MAGIC_MISSILES, level);
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
        player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 100, 1);
        Snowball snowball = player.launchProjectile(Snowball.class);
        snowball.setItem(new ItemStack(Material.SLIME_BALL));
        tagProjectile(snowball, SpellTypes.POISON_CLOUD, level);
    }
}
