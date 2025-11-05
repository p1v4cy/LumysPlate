package Aone.lp.recipe;

import Aone.lp.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class EnforcedElytraRecipeCrafter extends CustomRecipe {

    public EnforcedElytraRecipeCrafter(CraftingBookCategory category) { super(category); }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        ItemStack elytra = ItemStack.EMPTY;
        ItemStack chestplate = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (stack.is(Items.ELYTRA)) {
                elytra = stack;
                continue;
            }

            if (stack.getItem() == Items.NETHERITE_CHESTPLATE) {
                chestplate = stack;
            }
        }

        return !elytra.isEmpty() && !chestplate.isEmpty();
    }

    @Override public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        ItemStack chestplate = ItemStack.EMPTY;
        ItemStack elytra = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (stack.is(Items.NETHERITE_CHESTPLATE)) {
                chestplate = stack; continue;
            }
            if (stack.is(Items.ELYTRA)) {
                elytra = stack; continue;
            }
        }
        ItemStack result = new ItemStack(ModItems.ENFORCED_NETHERITE_ELYTRA.get());
        if (!chestplate.has(DataComponents.ENCHANTMENTS) && !elytra.has(DataComponents.ENCHANTMENTS)) {
            return result;
        }

        ItemEnchantments chestplateEnchantments = chestplate.get(DataComponents.ENCHANTMENTS);
        ItemEnchantments elytraEnchantments = elytra.get(DataComponents.ENCHANTMENTS);

        if (chestplateEnchantments == null) {
            result.set(DataComponents.ENCHANTMENTS, elytraEnchantments);
            return result;
        }

        if (elytraEnchantments == null) {
            result.set(DataComponents.ENCHANTMENTS, chestplateEnchantments);
            return result;
        }

        ArrayList<Object> resultList = new ArrayList<>(chestplateEnchantments.size() + elytraEnchantments.size());
        Collections.addAll(resultList, chestplateEnchantments);
        Collections.addAll(resultList, elytraEnchantments);

        @SuppressWarnings("unchecked")
        ItemEnchantments resultArray = (ItemEnchantments) Array.newInstance(chestplateEnchantments.getClass().getComponentType(), 0);
        result.set(DataComponents.ENCHANTMENTS, resultArray);
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i * i1 >= 2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return new ItemStack(ModItems.ENFORCED_NETHERITE_ELYTRA);
    }

    @Override public RecipeSerializer<?> getSerializer() {
        // return ModRecipeSerializers.ENFORCED_ELYTRA_SERIALIZER.get();
        return null;
    }
}