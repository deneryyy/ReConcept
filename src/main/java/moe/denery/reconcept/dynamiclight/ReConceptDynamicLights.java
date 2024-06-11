package moe.denery.reconcept.dynamiclight;

import net.minecraft.core.BlockPos;

public final class ReConceptDynamicLights {
    private ReConceptDynamicLights() {}

    public static final ReConceptDynamicLights INSTANCE = new ReConceptDynamicLights();

    private BlockPos playerPos;

    public BlockPos getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(BlockPos playerPos) {
        this.playerPos = playerPos;
    }
}
