package moe.denery.reconcept.mixin.game;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.Properties.class)
public abstract class ItemPropertiesMixin {
    @Shadow public abstract <T> Item.Properties component(DataComponentType<T> dataComponentType, T object);

    @Unique
    private static final List<FoodProperties> RAW_FOOD_LIST = ImmutableList.of(
            Foods.BEEF,
            Foods.CHICKEN,
            Foods.COD,
            Foods.MUTTON,
            Foods.PORKCHOP,
            Foods.RABBIT,
            Foods.ROTTEN_FLESH,
            Foods.SALMON,
            Foods.TROPICAL_FISH
    );

    /**
     *  Changes vanilla food properties
     */
    @Inject(
            method = "food",
            at = @At("HEAD"),
            cancellable = true
    )
    private void changeFoodProperties(FoodProperties foodProperties, CallbackInfoReturnable<Item.Properties> cir) {
        if (RAW_FOOD_LIST.contains(foodProperties)) {
            FoodProperties modifiedFoodProperties = new FoodProperties(
                    foodProperties.nutrition(),
                    foodProperties.saturation(),
                    foodProperties.canAlwaysEat(),
                    foodProperties.eatSeconds(),
                    ImmutableList.<FoodProperties.PossibleEffect>builder().addAll(foodProperties.effects()).add(rawFoodPoison()).build()
            );
            cir.setReturnValue(this.component(DataComponents.FOOD, modifiedFoodProperties));
        }
    }

    @Unique
    private FoodProperties.PossibleEffect rawFoodPoison() {
        return new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.POISON, 1200, 1), 1.0F);
    }
}
