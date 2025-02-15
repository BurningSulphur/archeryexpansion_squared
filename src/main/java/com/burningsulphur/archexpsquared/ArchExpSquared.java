package  com.burningsulphur.archexpsquared;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BowItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArchExpSquared.MOD_ID)
public class ArchExpSquared
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "archexpsquared";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);


    //bow items
    public static final RegistryObject<Item> KAUPEN_BOW = ITEMS.register("kaupen_bow",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> LEAD_BOW = ITEMS.register("lead_bow",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> SILVER_BOW = ITEMS.register("silver_bow",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> ELECTRUM_BOW = ITEMS.register("electrum_bow",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> NECROMIUM_BOW = ITEMS.register("necromium_bow",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> ROSE_GOLD_BOW = ITEMS.register("rose_gold_bow",
            () -> new BowItem(new Item.Properties().durability(500)));

    public static final RegistryObject<Item> BLANK_BOW_A = ITEMS.register("blank_bow_a",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> BLANK_BOW_B = ITEMS.register("blank_bow_b",
            () -> new BowItem(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> BLANK_BOW_C = ITEMS.register("blank_bow_c",
            () -> new BowItem(new Item.Properties().durability(500)));

    // adding the propeties to the bow

    public class ModItemProperties {
        public static void addCustomItemProperties() { // put bows here
            makeBow(ArchExpSquared.KAUPEN_BOW.get());
            makeBow(ArchExpSquared.LEAD_BOW.get());
            makeBow(ArchExpSquared.SILVER_BOW.get());
            makeBow(ArchExpSquared.ELECTRUM_BOW.get());
            makeBow(ArchExpSquared.NECROMIUM_BOW.get());
            makeBow(ArchExpSquared.ROSE_GOLD_BOW.get());
            makeBow(ArchExpSquared.BLANK_BOW_A.get());
            makeBow(ArchExpSquared.BLANK_BOW_B.get());
            makeBow(ArchExpSquared.BLANK_BOW_C.get());
        }

        //fancy bow code:
        private static void makeBow(Item item) {
            ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
                if (p_174637_ == null) {
                    return 0.0F;
                } else {
                    return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                            p_174637_.getUseItemRemainingTicks()) / 20.0F;
                }
            });

            ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
            });
        }
    }


    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> KAUPEN_BOW.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(LEAD_BOW.get());
                output.accept(SILVER_BOW.get());
                output.accept(ELECTRUM_BOW.get());
                output.accept(NECROMIUM_BOW.get());
                output.accept(ROSE_GOLD_BOW.get());
                output.accept(BLANK_BOW_A.get());
                output.accept(BLANK_BOW_B.get());
                output.accept(BLANK_BOW_C.get());
            }).build());

    public ArchExpSquared(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ModItemProperties.addCustomItemProperties();
        }
    }
}
