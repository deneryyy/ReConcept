package moe.denery.reconcept.block;

import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.block.ofuda.OfudaOfResurrectionBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ReConceptBlocks {
    public static final Block TUFT_BLOCK = register("tuft_of_grass_block", new RotatedPillarBlock(BlockBehaviour.Properties.of()
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

    public static final Block HIGANBANA = register("higanbana", new FlowerBlock(MobEffects.UNLUCK, 0.35F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)));

    public static final Block OFUDA_OF_RESURRECTION = register("ofuda_of_resurrection", new OfudaOfResurrectionBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .sound(SoundType.GRASS)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .instabreak()));

    public static final Block CONSTRUCTION_TABLE = register("construction_table", new SlabBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 3.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()));

    public static final Block POLISHING_TABLE = register("polishing_table", new SlabBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 3.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()));

    public static void registerBlocks() {}

    private static Block register(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, ReConcept.createReConcept(name), block);
    }
}
