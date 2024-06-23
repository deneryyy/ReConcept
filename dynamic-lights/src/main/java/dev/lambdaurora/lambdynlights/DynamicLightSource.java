/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.lambdaurora.lambdynlights;

import moe.denery.recodynlights.ReCoDynLights;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface DynamicLightSource {
    @NotNull
    Level getDynamicLightLevel();

    double getDynamicLightX();

    double getDynamicLightY();

    double getDynamicLightZ();

    int getLuminance();

    void setLuminance(int luminance);

    /**
     * Returns whether the dynamic light is enabled or not.
     *
     * @return {@code true} if the dynamic light is enabled, else {@code false}
     */
    default boolean isDynamicLightEnabled() {
        return ReCoDynLights.INSTANCE.containsDynamicLightSource(this); // May be extended later
    }

    /**
     * Sets whether the dynamic light is enabled or not.
     * <p>
     * Note: please do not call this function in your mod or you will break things.
     *
     * @param enabled {@code true} if the dynamic light is enabled, else {@code false}
     */
    @ApiStatus.Internal
    default void setDynamicLightEnabled(boolean enabled) {
        this.resetDynamicLight();
        if (enabled)
            ReCoDynLights.INSTANCE.addDynamicLightSource(this);
        else
            ReCoDynLights.INSTANCE.removeDynamicLightSource(this);
    }

    /**
     * Executed at each tick.
     */
    void dynamicLightTick();

    void resetDynamicLight();

    boolean updateDynamicLight(@NotNull LevelRenderer renderer);

    void scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer);
}
