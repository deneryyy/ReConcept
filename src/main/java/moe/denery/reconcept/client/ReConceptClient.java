package moe.denery.reconcept.client;

import moe.denery.reconcept.block.ReConceptBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class ReConceptClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ReConceptBlocks.AGONY_FLOWER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ReConceptBlocks.JVNE_FLOWER, RenderType.cutout());

        // TODO For Future?
        /*
        CoreShaderRegistrationCallback.EVENT.register(callback -> {
            callback.register(ReConceptShaders.RECONCEPT_TRANSLUCENT_SHADER, DefaultVertexFormat.BLOCK, ReConceptShaders::setReconceptTranslucentShader);
        });
         */
    }
}
