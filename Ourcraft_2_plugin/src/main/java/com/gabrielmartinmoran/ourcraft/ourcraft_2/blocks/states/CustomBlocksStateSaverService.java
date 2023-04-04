package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states;

public class CustomBlocksStateSaverService implements Runnable {

    public static int EXECUTE_EACH_TICKS = 20 * 60;

    @Override
    public void run() {
        CustomBlockStateProvider.saveAll();
    }
}
