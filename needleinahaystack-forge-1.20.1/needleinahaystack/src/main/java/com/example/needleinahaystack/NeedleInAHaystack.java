package com.example.needleinahaystack;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(NeedleInAHaystack.MODID)
public class NeedleInAHaystack {
    public static final String MODID = "needleinahaystack";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ---- Blocks ----
    public static final RegistryObject<Block> GIANT_HAYSTACK = BLOCKS.register("giant_haystack",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(10.0F, 3600000.0F)   // hard to break by hand, nearly blast-proof
                    .sound(SoundType.GRASS)));

    // ---- Items ----
    public static final RegistryObject<Item> GIANT_HAYSTACK_ITEM = ITEMS.register("giant_haystack",
            () -> new BlockItem(GIANT_HAYSTACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HAY_BUNDLE = ITEMS.register("hay_bundle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NEEDLE = ITEMS.register("needle",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    // speed = destroy speed on the haystack, bonusYield = extra hay bundles per swing
    public static final RegistryObject<Item> WOODEN_PITCHFORK = pitchfork("wooden_pitchfork", 2.0F, 0);
    public static final RegistryObject<Item> IRON_PITCHFORK = pitchfork("iron_pitchfork", 6.0F, 1);
    public static final RegistryObject<Item> GOLDEN_PITCHFORK = pitchfork("golden_pitchfork", 14.0F, 2);
    public static final RegistryObject<Item> DIAMOND_PITCHFORK = pitchfork("diamond_pitchfork", 30.0F, 4);
    public static final RegistryObject<Item> NETHERITE_PITCHFORK = pitchfork("netherite_pitchfork", 60.0F, 7);

    private static RegistryObject<Item> pitchfork(String name, float speed, int bonusYield) {
        return ITEMS.register(name, () -> new PitchforkItem(speed, bonusYield, new Item.Properties().stacksTo(1)));
    }

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MODID))
                    .icon(() -> new ItemStack(NEEDLE.get()))
                    .displayItems((params, out) -> {
                        out.accept(GIANT_HAYSTACK_ITEM.get());
                        out.accept(HAY_BUNDLE.get());
                        out.accept(NEEDLE.get());
                        out.accept(WOODEN_PITCHFORK.get());
                        out.accept(IRON_PITCHFORK.get());
                        out.accept(GOLDEN_PITCHFORK.get());
                        out.accept(DIAMOND_PITCHFORK.get());
                        out.accept(NETHERITE_PITCHFORK.get());
                    })
                    .build());

    public NeedleInAHaystack() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TABS.register(bus);
    }
}
