/*
 * Copyright © 2023 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.lambdaurora.lambdynlights.mixin.sodium;

import dev.lambdaurora.lambdynlights.util.SodiumDynamicLightHandler;
import me.jellysquid.mods.sodium.client.model.light.flat.FlatLightPipeline;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = FlatLightPipeline.class, remap = false)
public abstract class FlatLightPipelineMixin {
	@Inject(
			method = "getOffsetLightmap",
			at = @At(value = "RETURN", ordinal = 1),
			require = 0,
			remap = false,
			locals = LocalCapture.CAPTURE_FAILHARD,
			cancellable = true
	)
	private void lambdynlights$getLightmap(
			BlockPos pos,
			Direction face,
			CallbackInfoReturnable<Integer> cir,
			int word,
			int adjWord
	) {
		int lightmap = SodiumDynamicLightHandler.getLightmap(pos, adjWord, cir.getReturnValueI());
		cir.setReturnValue(lightmap);
	}
}
