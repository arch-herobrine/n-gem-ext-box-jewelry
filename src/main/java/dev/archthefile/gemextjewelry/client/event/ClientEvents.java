package dev.archthefile.gemextjewelry.client.event;

import dev.archthefile.gemextjewelry.GemExtJewelry;
import dev.archthefile.gemextjewelry.client.model.GemRingBakedModel;
import dev.archthefile.gemextjewelry.client.model.GemRingModelManager;
import dev.archthefile.gemextjewelry.registry.GemExtJewelryRegistry;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Map;


@SuppressWarnings("removal")
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> models = event.getModels();

        GemExtJewelry.LOGGER.debug("=== MODEL KEYS (filter gem_ring) ===");
        for (ResourceLocation key : models.keySet()) {
            if (key.toString().contains("gemextjewelry")) {
                GemExtJewelry.LOGGER.debug("MODEL KEY = " + key);
            }
        }

        ModelResourceLocation emptyId = new ModelResourceLocation(new ResourceLocation(GemExtJewelry.MODID, "gem_ring"),"inventory");
        ModelResourceLocation setId = new ModelResourceLocation(new ResourceLocation(GemExtJewelry.MODID, "gem_ring_set"),"inventory");

        replaceModelIfPresent(models, emptyId, true, event.getModelBakery());
        replaceModelIfPresent(models, setId, false, event.getModelBakery());
    }

    private static void replaceModelIfPresent(Map<ResourceLocation, BakedModel> models, ResourceLocation id, boolean isBase, ModelBakery bake) {
        BakedModel orig = models.get(id);
        if (orig == null) {
            GemExtJewelry.LOGGER.debug("model not found for id=" + id);
            return;
        }
        GemExtJewelry.LOGGER.debug("found model for id=" + id + " originalClass=" + orig.getClass().getName());

        GemRingBakedModel wrapped = new GemRingBakedModel(orig, bake);
        models.put(id, wrapped);
        if (isBase) {
            GemRingModelManager.baseModel = wrapped;
            GemExtJewelry.LOGGER.debug("assigned baseModel = wrapped");
        } else {
            GemRingModelManager.setModel = wrapped;
            GemExtJewelry.LOGGER.debug("assigned setModel = wrapped");
        }
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex != 0) return 0xFFFFFFFF;
            if (stack.hasTag() && stack.getTag().contains("gemextjewelry:gem")) {
                String id = stack.getTag().getString("gemextjewelry:gem");
                if (id.contains("diamondgarnetaquamarinetourmalineemeraldperidotrose_quartzmoonstonerubysapphireamethysttopazlapis")) { // it's cursed :skull:
                    float h = (float) ((System.currentTimeMillis()) * 0.01) % 1.0f;
                    return Color.HSBtoRGB(h, 1.0f, 1.0f);
                } else if (id.contains("diamond")) {
                    return 0x4AEDD9;
                } else if (id.contains("garnet")) {
                    return 0xF15E2D;
                } else if (id.contains("aquamarine")) {
                    return 0x09C8FF;
                } else if (id.contains("tourmaline")) {
                    return 0x03BE7F;
                } else if (id.contains("emerald")) {
                    return 0x17DD62;
                } else if (id.contains("peridot")) {
                    return 0x1BD702;
                } else if (id.contains("rose_quartz")) {
                    return 0xED597E;
                } else if (id.contains("moonstone")) {
                    return 0xC7D7E3;
                } else if (id.contains("ruby")) {
                    return 0xDF1A40;
                } else if (id.contains("amethyst")) {
                    return 0xB38EF3;
                } else if (id.contains("topaz")) {
                    return 0xE7AD0C;
                } else if (id.contains("lapis")) {
                    return 0x5A82E2;
                }
            }
            return 0xFFFFFFFF;
        }, GemExtJewelryRegistry.GEM_RING.get());
    }
}
