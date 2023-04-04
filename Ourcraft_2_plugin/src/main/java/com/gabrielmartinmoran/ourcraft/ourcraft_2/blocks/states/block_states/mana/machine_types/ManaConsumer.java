package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.input.ManaImporter;

public interface ManaConsumer extends ManaMachine {

    public ManaImporter getManaImporter();
}
