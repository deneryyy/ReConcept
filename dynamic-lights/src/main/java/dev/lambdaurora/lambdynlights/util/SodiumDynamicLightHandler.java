/*
 * Copyright © 2023 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.lambdaurora.lambdynlights.util;

import dev.lambdaurora.lambdynlights.LambDynLightsAlgorithms;
import moe.denery.recodynlights.ReCoDynLights;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface SodiumDynamicLightHandler {
	// Stores the current light position being used by ArrayLightDataCache#get
	// We use ThreadLocal because Sodium's chunk builder is multithreaded, otherwise it will break
	// catastrophically.
	ThreadLocal<BlockPos.MutableBlockPos> pos = ThreadLocal.withInitial(BlockPos.MutableBlockPos::new);

	static int getLightmap(BlockPos pos, int word, int lightmap) {
		// Equivalent to world.getBlockState(pos).isOpaqueFullCube(world, pos)
		if (/*LightDataAccess.unpackFO(word)*/ (word >>> 30 & 1) != 0)
			return lightmap;

		double dynamic = ReCoDynLights.INSTANCE.getDynamicLightLevel(pos);
		return LambDynLightsAlgorithms.getLightmapWithDynamicLight(dynamic, lightmap);
	}
}
