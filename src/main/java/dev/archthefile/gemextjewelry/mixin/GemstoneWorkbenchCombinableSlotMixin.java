package dev.archthefile.gemextjewelry.mixin;

import com.Nuaah.NGemExtBoxMod.block.gui.slot.GemstoneWorkbenchCombinableSlot;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.archthefile.gemextjewelry.items.GemCharmItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GemstoneWorkbenchCombinableSlot.class, remap = true)
public class GemstoneWorkbenchCombinableSlotMixin {
    @ModifyReturnValue(
            method = "mayPlace",
            at = @At(value="RETURN",remap = true)
    )
    private boolean modifyMayPlaceCharm(boolean original, ItemStack stack) {
        if (stack.getItem() instanceof GemCharmItem) {
            return true;
        }
        return original;
    }
}
