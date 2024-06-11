package moe.denery.reconcept.block;

import moe.denery.reconcept.ReConcept;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ReConceptBlocks {
    public static final Block TUFT_BLOCK = register("tuft_block", new RotatedPillarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .instabreak()
            .sound(SoundType.GRASS)));

    public static final Block JVNE_FLOWER = register("jvne_flower", new FlowerBlock(MobEffects.HEAL, 0.35F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)));


    public static final Block AGONY_FLOWER = register("agony_flower", new FlowerBlock(MobEffects.DARKNESS, 0.35F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)));

    public static void registerBlocks() {}

    private static Block register(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, ReConcept.createReConcept(name), block);
    }
}
