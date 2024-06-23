package moe.denery.reconcept.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import moe.denery.reconcept.entity.thrown.ThrownItemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ThrownItemEntityRenderer extends EntityRenderer<ThrownItemEntity> {
    private final ItemRenderer itemRenderer;

    public ThrownItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ThrownItemEntity itemEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightValue) {
        super.render(itemEntity, f, g, poseStack, multiBufferSource, lightValue);
        poseStack.pushPose();
        ItemStack item = itemEntity.getItem();
        poseStack.rotateAround(itemEntity.getZRotationRendering(), 0.0F, 0.0F, 0.0F);
        itemEntity.rotateRendering();
        poseStack.scale(0.75f, 0.75f, 0.75f);
        itemRenderer.renderStatic(item, ItemDisplayContext.FIXED, lightValue, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, itemEntity.level(), itemEntity.getId());
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ThrownItemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
