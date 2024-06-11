package moe.denery.reconcept.item;

import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.item.tool.UnlitTorchItem;
import moe.denery.reconcept.item.tool.UtilityKnifeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public final class ReConceptItems {
    private static final List<Item> RECONCEPT_ITEMS = new ArrayList<>();

    // resource items
    public static final Item TUFT = register("tuft", new Item(new Item.Properties()));
    public static final Item BARK = register("bark", new Item(new Item.Properties()));
    public static final Item WOODEN_MATERIAL = register("wooden_material", new Item(new Item.Properties()));

    // instruments or usable items
    public static final Item UTILITY_KNIFE = register("utility_knife", new UtilityKnifeItem(new Item.Properties()
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Utility knife modifier", -1.4f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()).stacksTo(1).durability(25))
    );
    public static final Item UNLIT_TORCH = register("unlit_torch", new UnlitTorchItem(new Item.Properties()));

    // block items
    public static final Item TUFT_BLOCK = registerBlock(ReConceptBlocks.TUFT_BLOCK);
    public static final Item JVNE_FLOWER = registerBlock(ReConceptBlocks.JVNE_FLOWER);
    public static final Item AGONY_FLOWER = registerBlock(ReConceptBlocks.AGONY_FLOWER);


    private static final CreativeModeTab RECONCEPT_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(TUFT))
            .title(Component.translatable("itemGroup.reconcept.reconcept_tab"))
            .displayItems((context, entries) -> {
                for (Item reconceptItem : ReConceptItems.RECONCEPT_ITEMS) {
                    entries.accept(reconceptItem);
                }
            })
            .build();

    public static void registerItems() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ReConcept.createReConcept("reconcept_tab"), RECONCEPT_TAB);
    }

    private static Item register(String name, Item item) {
        try {
            return Registry.register(BuiltInRegistries.ITEM, ReConcept.createReConcept(name), item);
        } finally {
            ReConceptItems.RECONCEPT_ITEMS.add(item);
        }
    }

    private static Item registerBlock(Block block) {
        return ReConceptItems.registerBlock(block, new Item.Properties());
    }

    private static Item registerBlock(Block block, Item.Properties properties) {
        BlockItem item = new BlockItem(block, properties);
        try {
            return Registry.register(BuiltInRegistries.ITEM, BuiltInRegistries.BLOCK.getKey(block), item);
        } finally {
            ReConceptItems.RECONCEPT_ITEMS.add(item);
        }
    }
}
