package Aone.lp.Items.elytra;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class EnforcedGoldElytraItem extends ArmorItem {

    public EnforcedGoldElytraItem(Properties properties) {
        super(ArmorMaterials.GOLD, Type.CHESTPLATE, properties.rarity(Rarity.RARE));
    }

    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) return;

        if (!(player instanceof LocalPlayer localPlayer)) return;

        if (localPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof EnforcedGoldElytraItem) {
            if (ElytraItem.isFlyEnabled(stack)) {
                boolean touchingGround = localPlayer.onGround();
                boolean isFallFlying = localPlayer.isFallFlying();

                if (localPlayer.input.jumping && !touchingGround && !isFallFlying) {
                    localPlayer.startFallFlying();
                }
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.PHANTOM_MEMBRANE) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (flightTicks % 20 == 0) stack.hurtAndBreak(1, entity, Objects.requireNonNull(EquipmentSlot.CHEST));
        return true;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 100;
    }

}
