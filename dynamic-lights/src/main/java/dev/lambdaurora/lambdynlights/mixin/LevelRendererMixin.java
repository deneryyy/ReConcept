/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.lambdaurora.lambdynlights.mixin;

import moe.denery.recodynlights.ReCoDynLights;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla lightmap hook.
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(
            method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void dynamicLight(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
         if (!blockAndTintGetter.getBlockState(blockPos).isSolidRender(blockAndTintGetter, blockPos))
            cir.setReturnValue(ReCoDynLights.INSTANCE.getLightMapFor(blockPos, cir.getReturnValue()));
    }
}
