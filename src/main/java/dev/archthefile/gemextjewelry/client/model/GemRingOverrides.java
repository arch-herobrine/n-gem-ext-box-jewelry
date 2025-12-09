package dev.archthefile.gemextjewelry.client.model;

import dev.archthefile.gemextjewelry.GemExtJewelry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GemRingOverrides extends ItemOverrides {
    public static final GemRingOverrides INSTANCE = new GemRingOverrides();

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, ClientLevel world, LivingEntity entity, int seed) {
        return GemRingModelManager.getModelFor(stack);
    }
}
