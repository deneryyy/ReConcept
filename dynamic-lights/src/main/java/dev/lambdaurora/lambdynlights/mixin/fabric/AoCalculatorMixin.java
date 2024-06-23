/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.lambdaurora.lambdynlights.mixin.fabric;

import moe.denery.recodynlights.ReCoDynLights;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Indigo lightmap hook.
 */
@Pseudo
@Mixin(targets = "net.fabricmc.fabric.impl.client.indigo.renderer.aocalc.AoCalculator", remap = false)
public class AoCalculatorMixin {
    @Dynamic
    @Inject(method = "getLightmapCoordinates", at = @At(value = "RETURN", ordinal = 0), require = 0, cancellable = true, remap = false)
    private static void onGetLightmapCoordinates(BlockAndTintGetter world, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!world.getBlockState(pos).isSolidRender(world, pos))
            cir.setReturnValue(ReCoDynLights.INSTANCE.getLightMapFor(pos, cir.getReturnValue()));
    }
}
