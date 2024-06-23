package moe.denery.reconcept.entity.thrown;

import moe.denery.reconcept.entity.ReConceptEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ThrownItemEntity extends Projectile {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ThrownItemEntity.class, EntityDataSerializers.ITEM_STACK);

    protected float entityHitDamage = 1.0F;

    protected final Quaternionf rotRendering = new Quaternionf();
    protected volatile float velocityRotRenderingModifier = 1.0F;

    public ThrownItemEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    protected ThrownItemEntity(EntityType<? extends Projectile> entityType, double x, double y, double z, Level level, ItemStack item, float damage) {
        this(entityType, level);
        this.setPos(x, y, z);
        this.setItem(item);
        this.entityHitDamage = damage;
    }

    protected ThrownItemEntity(EntityType<? extends Projectile> entityType, LivingEntity livingEntity, Level level, ItemStack item, float damage) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - (double)0.1f, livingEntity.getZ(), level, item, damage);
        this.setOwner(livingEntity);
    }

    public ThrownItemEntity(Level level, LivingEntity livingEntity, ItemStack item, float damage) {
        this(ReConceptEntities.THROWN_ITEM, livingEntity, level, item, damage);
    }

    public ThrownItemEntity(Level level, LivingEntity livingEntity, ItemStack item) {
        this(ReConceptEntities.THROWN_ITEM, livingEntity, level, item, 1.0F);
    }

    @Override
    public void tick() {
        float h;
        super.tick();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            BlockState blockState = this.level().getBlockState(blockPos);
            if (blockState.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockPos);
                bl = true;
            } else if (blockState.is(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.level().getBlockEntity(blockPos);
                if (blockEntity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level(), blockPos, blockState, this, (TheEndGatewayBlockEntity)blockEntity);
                }
                bl = true;
            }
            ItemStack item = new ItemStack(this.getItem().getItem());
            ItemEntity dropEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), item);
            dropEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(dropEntity);
        }
        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.hitTargetOrDeflectSelf(hitResult);
        }
        this.checkInsideBlocks();
        Vec3 deltaMovement = this.getDeltaMovement();
        double d = this.getX() + deltaMovement.x;
        double e = this.getY() + deltaMovement.y;
        double f = this.getZ() + deltaMovement.z;
        this.updateRotation();
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.BUBBLE, d - deltaMovement.x * 0.25, e - deltaMovement.y * 0.25, f - deltaMovement.z * 0.25, deltaMovement.x, deltaMovement.y, deltaMovement.z);
            }
            h = 0.6F;
        } else {
            h = 0.99F;
        }
        this.setDeltaMovement(deltaMovement.scale(h));
        this.applyGravity();
        this.setPos(d, e, f);
        this.velocityRotRenderingModifier = (float) this.getDeltaMovement().length();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.03;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), this.entityHitDamage * (float) this.getDeltaMovement().length());
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        if (!this.getItem().isEmpty()) {
            compoundTag.put("Item", this.getItem().save(this.registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("Item", 10)) {
            CompoundTag compoundTag2 = compoundTag.getCompound("Item");
            this.setItem(ItemStack.parse(this.registryAccess(), compoundTag2).orElse(ItemStack.EMPTY));
        } else {
            this.setItem(ItemStack.EMPTY);
        }
        if (this.getItem().isEmpty()) {
            this.discard();
        }
    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM);
    }

    public void setItem(ItemStack itemStack) {
        this.getEntityData().set(DATA_ITEM, itemStack);
    }

    public Quaternionf getZRotationRendering() {
        return this.rotRendering;
    }

    public void rotateRendering() {
        float angle = (this.velocityRotRenderingModifier * 0.01F) * (180.0F / (float) Math.PI);
        this.rotRendering.rotateAxis(angle, new Vector3f(0.0F, 0.0F, 1.0F));
    }
}
