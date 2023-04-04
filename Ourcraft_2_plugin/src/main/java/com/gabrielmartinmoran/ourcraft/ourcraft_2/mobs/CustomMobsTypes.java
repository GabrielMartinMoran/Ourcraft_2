package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs.*;

public enum CustomMobsTypes {
    NONE(null),
    //HAUNTED(new Haunted()),
    //HOGLIN_TAMER(new HoglinTamer()),
    //CICLOPE(null),
    //HERALDO_MUERTE(null),
    KING_PHANTOM(new KingPhantom()),
    BIG_PHANTOM(new BigPhantom()),
    PHANTOM_RIDER(new PhantomRider()),
    INCAPACITATING_CREEPER(new IncapacitatingCreeper()),
    AMPLIFIED_CREEPER(new AmplifiedCreeper()),
    JUMPING_CREEPER(new JumpingCreeper()),
    SKELETON_WIZARD(new SkeletonWizard()),
    ORC(new Orc()),
    ORC_SHAMAN(new OrcShaman()),
    ORC_BRUTE(new OrcBrute()),
    OGRE(new Ogre()),
    ICE_SOUL(new IceSoul()),
    NECROMANCER(new Necromancer()),
    UNDEAD_WIZARD(new UndeadWizard()),
    MIMIC(new Mimic()),
    ENERGY_NODE(new EnergyNode()),
    REVENANT(new Revenant()),
    ENDER_MAGE(new EnderMage()),
    JOCKEY(new Jockey()),
    FIRE_ELEMENTAL(new FireElemental()),
    RAGER(new Rager()),
    REALENTING_SPIDER(new RealentingSpider()),
    ENDER_WARRIOR(new EnderWarrior()),
    POSSESED_PIGLIN(new PossessedPiglin()),
    POWERED_GHAST(new PoweredGhast()),
    DROWNED_PIRATE(new DrownedPirate()),
    WATER_DRIAD(new WaterDriad()),
    DROWNED_WIZARD(new DrownedWizard()),
    WITHER_BOSS(new WitherBoss());

    private CustomMob customMob;

    private CustomMobsTypes(CustomMob customMob) {
        this.customMob = customMob;
    }

    public CustomMob getCustomMob() {
        if (!customMob.hasType()) customMob.setType(this);
        return this.customMob;
    }
}
