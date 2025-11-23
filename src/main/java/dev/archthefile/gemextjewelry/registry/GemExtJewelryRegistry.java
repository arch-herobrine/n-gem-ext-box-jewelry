package dev.archthefile.gemextjewelry.registry;

import dev.archthefile.gemextjewelry.GemExtJewelry;
import dev.archthefile.gemextjewelry.items.GemRingItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GemExtJewelryRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GemExtJewelry.MODID);

    public static RegistryObject<Item> GEM_RING = ITEMS.register("gem_ring", () -> new GemRingItem(new Item.Properties().stacksTo(1)));
}
