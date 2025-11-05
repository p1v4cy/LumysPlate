package Aone.lp;

import Aone.lp.Items.elytra.EnforcedDiamondElytraItem;
import Aone.lp.Items.elytra.EnforcedGoldElytraItem;
import Aone.lp.Items.elytra.EnforcedIronElytraItem;
import Aone.lp.Items.elytra.EnforcedNetheriteElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, "lp");

    public static final DeferredHolder<Item, EnforcedDiamondElytraItem> ENFORCED_DIAMOND_ELYTRA =
            ITEMS.register("enforced_diamond_elytra",
                    () -> new EnforcedDiamondElytraItem(new Item.Properties()
                            .durability(528)
                            .fireResistant()));

    public static final DeferredHolder<Item, EnforcedGoldElytraItem> ENFORCED_GOLD_ELYTRA =
            ITEMS.register("enforced_gold_elytra",
                    () -> new EnforcedGoldElytraItem(new Item.Properties()
                            .durability(112)
                            .fireResistant()));

    public static final DeferredHolder<Item, EnforcedIronElytraItem> ENFORCED_IRON_ELYTRA =
            ITEMS.register("enforced_iron_elytra",
                    () -> new EnforcedIronElytraItem(new Item.Properties()
                            .durability(240)
                            .fireResistant()));

    public static final DeferredHolder<Item, EnforcedNetheriteElytraItem> ENFORCED_NETHERITE_ELYTRA =
            ITEMS.register("enforced_netherite_elytra",
                    () -> new EnforcedNetheriteElytraItem(new Item.Properties()
                            .durability(592)
                            .fireResistant()));
}
