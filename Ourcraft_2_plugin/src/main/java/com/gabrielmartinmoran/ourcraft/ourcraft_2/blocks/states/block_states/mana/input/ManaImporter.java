package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlockStateProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.CustomBlockState;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types.ManaProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaExporter;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ManaImporter {

    private static final HashMap<String, Double> cachedDistances = new HashMap<>();
    private final Location location;
    private final double range;
    private ManaExporter connectedManaExporter;
    @Nullable
    private ItemStack receptionChannel;
    private int extractionRate;

    public ManaImporter(Location location, double range, int extractionRate) {
        this.range = range;
        this.location = location;
        this.extractionRate = extractionRate;
    }

    public void setReceptionChannel(@Nullable ItemStack receptionChannel) {
        this.receptionChannel = receptionChannel;
    }

    /**
     * Returns true if the extraction was successfully
     *
     * @return
     */
    public boolean extract(int amount) {
        if (this.connectedManaExporter == null || !this.connectedManaExporter.hasEnough(amount)) return false;
        this.connectedManaExporter.consume(amount);
        return true;
    }

    /**
     * Returns true if the extraction was successfully
     *
     * @return
     */
    public int extractAllPossible(int maxAmount) {
        if (this.connectedManaExporter == null) return 0;
        return this.connectedManaExporter.consumeAllPossible(maxAmount);
    }

    public boolean isConnected() {
        return this.connectedManaExporter != null;
    }

    public void refreshConnection() {
        if (this.receptionChannel == null) {
            this.connectedManaExporter = null;
        } else {
            this.connectedManaExporter = this.searchManaExporter();
        }
    }

    @Nullable
    public ManaExporter getConnectedManaExporter() {
        return this.connectedManaExporter;
    }

    @Nullable
    private ManaExporter searchManaExporter() {
        ArrayList<ManaExporter> pools = new ArrayList<>();
        for (CustomBlockState state : CustomBlockStateProvider.all()) {
            if (state instanceof ManaProvider && this.calculateDistance(state.getLocation()) <= this.range && this.calculateDistance(state.getLocation()) > 0 && // Check 0 to discard the same block that has the extractor
                    ((ManaProvider) state).getManaExporter() != null && // Check if the mana pool is null (in case of
                    // repeaters)
                    this.sharesChannel(((ManaProvider) state).getManaExporter())) {
                pools.add(((ManaProvider) state).getManaExporter());
            }
        }
        if (pools.isEmpty()) return null;
        pools.sort((p1, p2) -> {
            Integer a = p1.getCurrent();
            Integer b = p2.getCurrent();
            return b.compareTo(a);
        });
        return pools.get(0);
    }

    private double calculateDistance(Location otherLocation) {
        String key = String.format("%s%s%s|%s%s%s", otherLocation.getBlockX(), otherLocation.getBlockY(),
                otherLocation.getBlockZ(), this.location.getBlockX(), this.location.getBlockY(),
                this.location.getBlockZ());
        if (cachedDistances.containsKey(key)) return cachedDistances.get(key);
        double distance = otherLocation.distance(this.location);
        cachedDistances.put(key, distance);
        return distance;
    }

    private boolean sharesChannel(ManaExporter manaExporter) {
        ItemStack exporterChannel = manaExporter.getTransmissionChannel();
        return exporterChannel != null && exporterChannel.isSimilar(this.receptionChannel);
    }

    public boolean canExtract(int amount) {
        return this.isConnected() && this.connectedManaExporter.hasEnough(amount);
    }

    public int getExtractionRate() {
        return this.extractionRate;
    }

    public void setExtractionRate(int extractionRate) {
        this.extractionRate = extractionRate;
    }
}
