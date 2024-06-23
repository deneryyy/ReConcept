package moe.denery.reconcept.client;

import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.client.renderer.entity.ThrownItemEntityRenderer;
import moe.denery.reconcept.entity.ReConceptEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ReConceptClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        // Block renderer settings
        BlockRenderLayerMap.INSTANCE.putBlock(ReConceptBlocks.AGONY_FLOWER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ReConceptBlocks.JVNE_FLOWER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ReConceptBlocks.HIGANBANA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ReConceptBlocks.OFUDA_OF_RESURRECTION, RenderType.cutout());

        // Item renderer settings
        ItemProperties.register(Items.TORCH, new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            }
            if (livingEntity.getUseItem() != itemStack) {
                return 0.0f;
            }
            return (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
        });
        ItemProperties.register(Items.TORCH, new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0f : 0.0f
        );

        EntityRendererRegistry.register(ReConceptEntities.THROWN_ITEM, ThrownItemEntityRenderer::new);

        DynamicLights.initialize();
    }
}
