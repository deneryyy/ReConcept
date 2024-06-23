package moe.denery.reconcept.item.throwing;

import moe.denery.reconcept.entity.thrown.ThrownItemEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ThrowingItem extends Item {
    protected final float throwDamage;

    public ThrowingItem(Properties properties, float throwDamage) {
        super(properties);
        this.throwDamage = throwDamage;
    }

    public ThrowingItem(Properties properties) {
        this(properties, 1.0F);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        if (!(livingEntity instanceof Player player))
            return;
        int j = this.getUseDuration(itemStack) - i;
        float f = BowItem.getPowerForTime(j);
        if (f < 0.1) {
            return;
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WIND_CHARGE_THROW, SoundSource.PLAYERS, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
        if (!level.isClientSide()) {
            this.shoot(level, player, itemStack, f * 3.0f, 1.0f);
        }
        itemStack.consume(1, player);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemStack);
    }

    protected void shoot(Level level, LivingEntity livingEntity, ItemStack itemStack, float f, float g) {
        Projectile projectile = this.createThrowingItem(level, livingEntity, itemStack);
        projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0f, f, g);
        level.addFreshEntity(projectile);
    }

    protected Projectile createThrowingItem(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        return new ThrownItemEntity(level, livingEntity, itemStack, this.throwDamage);
    }
}
