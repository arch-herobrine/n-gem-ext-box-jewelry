package dev.archthefile.gemextjewelry.mixin;

import com.Nuaah.NGemExtBoxMod.regi.tag.NGemExtBoxModTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.archthefile.gemextjewelry.items.GemCharmItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(targets = "com.Nuaah.NGemExtBoxMod.block.gui.container.GemstoneWorkbenchMenu$1", remap = true)
public class GemWorkbenchMixin {
    @ModifyReturnValue(
            method = "mayPlace",
            at = @At(remap = true,value = "RETURN")
    )
    private boolean modifyMayPlace(boolean original, ItemStack stack, @Local(name = "combineSlot") ItemStack combineSlot) {
        if (combineSlot.getItem() instanceof GemCharmItem) {
            return stack.is(NGemExtBoxModTags.Items.COMBINE_GEMS);
        }
        return original;
    }
}
