package dev.archthefile.gemextjewelry.event;

import dev.archthefile.gemextjewelry.GemExtJewelry;
import dev.archthefile.gemextjewelry.datagen.CuriosSlotProvider;
import dev.archthefile.gemextjewelry.datagen.tag.BlockTagProvider;
import dev.archthefile.gemextjewelry.datagen.tag.CuriosTagProvider;
import dev.archthefile.gemextjewelry.datagen.tag.ItemTagProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = GemExtJewelry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GemExtJewelryEvent {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        boolean server = event.includeServer();
        boolean client = event.includeClient();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();

        BlockTagProvider blockTags = new BlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(server, blockTags);
        generator.addProvider(
                server,
                new ItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper)
        );
        generator.addProvider(
                server,
                new CuriosTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper)
        );

        generator.addProvider(server, new CuriosSlotProvider(GemExtJewelry.MODID, packOutput, existingFileHelper, lookupProvider));
    }
}
