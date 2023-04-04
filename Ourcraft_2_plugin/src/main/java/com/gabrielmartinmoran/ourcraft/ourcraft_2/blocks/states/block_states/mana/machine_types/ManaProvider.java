package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output.ManaExporter;
import jline.internal.Nullable;

public interface ManaProvider extends ManaMachine {

    @Nullable
    public ManaExporter getManaExporter();
}
