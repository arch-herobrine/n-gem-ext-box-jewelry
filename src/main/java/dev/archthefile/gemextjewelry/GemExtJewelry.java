package dev.archthefile.gemextjewelry;

import com.mojang.logging.LogUtils;
import dev.archthefile.gemextjewelry.registry.GemExtJewelryRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@SuppressWarnings("removal")
@Mod(GemExtJewelry.MODID)
public class GemExtJewelry {

    public static final String MODID = "gemextjewelry";
    private static final Logger LOGGER = LogUtils.getLogger();

    public GemExtJewelry() {
        this(FMLJavaModLoadingContext.get());
    }

    public GemExtJewelry(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        GemExtJewelryRegistry.ITEMS.register(bus);
        GemExtJewelryRegistry.RECIPE_SERIALIZER.register(bus);
    }
}
