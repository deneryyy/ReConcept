/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package moe.denery.recodynlights;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ReCoDynLightsMixinPlugin implements IMixinConfigPlugin {
    private final Object2BooleanMap<String> conditionalMixins = new Object2BooleanOpenHashMap<>();

    public ReCoDynLightsMixinPlugin() {
        boolean sodium05XInstalled = ReCoDynLightsMixinPlugin.isSodium05XInstalled();
        // For now sodium mixins are dynamic
        this.conditionalMixins.put("dev.lambdaurora.lambdynlights.mixin.sodium.ArrayLightDataCacheMixin", sodium05XInstalled);
        this.conditionalMixins.put("dev.lambdaurora.lambdynlights.mixin.sodium.FlatLightPipelineMixin", sodium05XInstalled);
        this.conditionalMixins.put("dev.lambdaurora.lambdynlights.mixin.sodium.LightDataAccessMixin", sodium05XInstalled);

        // TODO support lambdynlights
    }

    public static boolean isSodium05XInstalled() {
        return FabricLoader.getInstance().getModContainer("sodium").map(mod -> {
            try {
                return mod.getMetadata().getVersion().compareTo(Version.parse("0.5.0")) >= 0;
            } catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
        }).orElse(false);
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return this.conditionalMixins.getOrDefault(mixinClassName, true);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

}
