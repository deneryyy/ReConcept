/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */


package dev.lambdaurora.lambdynlights.mixin;

import dev.lambdaurora.lambdynlights.DynamicLightSource;
import moe.denery.recodynlights.ReCoDynLights;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Inject(method = "getBlockLightLevel", at = @At("RETURN"), cancellable = true)
    private void onGetBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        int vanilla = cir.getReturnValueI();
        int entityLuminance = ((DynamicLightSource) entity).getLuminance();
        if (entityLuminance >= 15)
            cir.setReturnValue(entityLuminance);

        int posLuminance = (int) ReCoDynLights.INSTANCE.getDynamicLightLevel(pos);

        cir.setReturnValue(Math.max(Math.max(vanilla, entityLuminance), posLuminance));
    }
}
