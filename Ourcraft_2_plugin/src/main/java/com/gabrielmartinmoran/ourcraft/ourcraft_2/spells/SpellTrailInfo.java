package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

import org.bukkit.Color;
import org.bukkit.Particle;

public class SpellTrailInfo {

    private Particle particle;
    private Color color;
    private int particleAmmunt;

    public SpellTrailInfo(Particle particle, Color color, int particleAmmunt) {
        this.particle = particle;
        this.color = color;
        this.particleAmmunt = particleAmmunt;
    }

    public Particle getParticle() {
        return particle;
    }

    public int getParticleAmmunt() {
        return particleAmmunt;
    }

    public Color getColor() {
        return color;
    }
}
