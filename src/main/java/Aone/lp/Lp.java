package Aone.lp;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(Lp.MODID)
public class Lp {
    public static final String MODID = "lp";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final String PW = "r?c";

    public Lp(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModItems.ITEMS.register(modEventBus);
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        // Diamond
        combineToEnhancedElytra(event, left, right, Items.ELYTRA, Items.DIAMOND_CHESTPLATE, ModItems.ENFORCED_DIAMOND_ELYTRA.get().getDefaultInstance());
        combineToEnhancedElytra(event, left, right, Items.DIAMOND_CHESTPLATE, Items.ELYTRA, ModItems.ENFORCED_DIAMOND_ELYTRA.get().getDefaultInstance());

        // Gold
        combineToEnhancedElytra(event, left, right, Items.ELYTRA, Items.GOLDEN_CHESTPLATE, ModItems.ENFORCED_GOLD_ELYTRA.get().getDefaultInstance());
        combineToEnhancedElytra(event, left, right, Items.GOLDEN_CHESTPLATE, Items.ELYTRA, ModItems.ENFORCED_GOLD_ELYTRA.get().getDefaultInstance());

        // Iron
        combineToEnhancedElytra(event, left, right, Items.ELYTRA, Items.IRON_CHESTPLATE, ModItems.ENFORCED_IRON_ELYTRA.get().getDefaultInstance());
        combineToEnhancedElytra(event, left, right, Items.IRON_CHESTPLATE, Items.ELYTRA, ModItems.ENFORCED_IRON_ELYTRA.get().getDefaultInstance());

        // Netherite
        combineToEnhancedElytra(event, left, right, Items.ELYTRA, Items.NETHERITE_CHESTPLATE, ModItems.ENFORCED_NETHERITE_ELYTRA.get().getDefaultInstance());
        combineToEnhancedElytra(event, left, right, Items.NETHERITE_CHESTPLATE, Items.ELYTRA, ModItems.ENFORCED_NETHERITE_ELYTRA.get().getDefaultInstance());
    }

    private void combineToEnhancedElytra(AnvilUpdateEvent event, ItemStack left, ItemStack right, Item elytra, Item netheriteChestplate, ItemStack modItem) {
        if (left.is(elytra) && right.is(netheriteChestplate)) {
            long cost = 5;

            ItemEnchantments leftEnchs = left.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            ItemEnchantments rightEnchs = right.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            ItemEnchantments.Mutable combined = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

            for (var entry : leftEnchs.entrySet()) {
                combined.set(entry.getKey(), entry.getValue());
            }

            for (var entry : rightEnchs.entrySet()) {
                combined.set(entry.getKey(), entry.getValue());
            }

            modItem.set(DataComponents.ENCHANTMENTS, combined.toImmutable());
            modItem.set(DataComponents.RARITY, Rarity.EPIC);
            String customName = event.getName();
            assert customName != null;

            if (!customName.isEmpty()) {
                cost = 6;
                modItem.set(DataComponents.CUSTOM_NAME, Component.literal(customName));
            }

            event.setOutput(modItem);
            event.setMaterialCost(1);
            event.setCost(cost);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.ENFORCED_DIAMOND_ELYTRA.get());
            event.accept(ModItems.ENFORCED_GOLD_ELYTRA.get());
            event.accept(ModItems.ENFORCED_IRON_ELYTRA.get());
            event.accept(ModItems.ENFORCED_NETHERITE_ELYTRA.get());
        }
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

}
