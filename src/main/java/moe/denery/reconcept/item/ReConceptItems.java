package moe.denery.reconcept.item;

import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.item.throwing.ThrowingItem;
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
    public static final Item TUFT = register("tuft_of_grass", new Item(new Item.Properties()));
    public static final Item BARK = register("bark", new Item(new Item.Properties()));
    public static final Item WOODEN_MATERIAL = register("wooden_material", new Item(new Item.Properties()));
    public static final Item STONE = register("stone", new Item(new Item.Properties()));

    // instruments or usable items
    public static final Item UTILITY_KNIFE = register("flint_knife", new UtilityKnifeItem(new Item.Properties()
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Utility knife modifier", -0.7f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()).stacksTo(1).durability(25))
    );
    public static final Item UNLIT_TORCH = register("unlit_torch", new UnlitTorchItem(new Item.Properties()));
    public static final Item CACTUS_ON_A_STICK = register("cactus_on_a_stick", new ThrowingItem(new Item.Properties()
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Cactus on a stick modifier", -3.2f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()), 3.0F)
    );

    // block items
    public static final Item TUFT_BLOCK = registerBlock(ReConceptBlocks.TUFT_BLOCK);
    public static final Item JVNE_FLOWER = registerBlock(ReConceptBlocks.JVNE_FLOWER);
    public static final Item AGONY_FLOWER = registerBlock(ReConceptBlocks.AGONY_FLOWER);
    public static final Item HIGANBANA = registerBlock(ReConceptBlocks.HIGANBANA);
    public static final Item OFUDA_OF_RESURRECTION = registerBlock(ReConceptBlocks.OFUDA_OF_RESURRECTION);
    public static final Item CONSTRUCTION_TABLE = registerBlock(ReConceptBlocks.CONSTRUCTION_TABLE);
    public static final Item POLISHING_TABLE = registerBlock(ReConceptBlocks.POLISHING_TABLE);


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
