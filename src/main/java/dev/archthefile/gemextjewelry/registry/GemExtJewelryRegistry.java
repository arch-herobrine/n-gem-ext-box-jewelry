package dev.archthefile.gemextjewelry.registry;

import dev.archthefile.gemextjewelry.GemExtJewelry;
import dev.archthefile.gemextjewelry.items.GemCharmItem;
import dev.archthefile.gemextjewelry.items.GemRingItem;
import dev.archthefile.gemextjewelry.recipe.GemRingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GemExtJewelryRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GemExtJewelry.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, GemExtJewelry.MODID);
    public static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GemExtJewelry.MODID);


    public static RegistryObject<Item> GEM_RING = ITEMS.register("gem_ring", () -> new GemRingItem(new Item.Properties().stacksTo(1)));
    public static RegistryObject<Item> GEM_CHARM = ITEMS.register("gem_charm", () -> new GemCharmItem(new Item.Properties().stacksTo(1)));
    public static RegistryObject<Item> ULTIMATE_GEM = ITEMS.register("diamondgarnetaquamarinetourmalineemeraldperidotrose_quartzmoonstonerubysapphireamethysttopazlapis", () -> new Item(new Item.Properties()));

    public static RegistryObject<RecipeSerializer<?>> GEM_RING_RECIPE = RECIPE_SERIALIZER.register("gem_ring_recipe", GemRingRecipeSerializer::new);

    public static final RegistryObject<CreativeModeTab> GEM_EXT_JEWELRY_MAIN = MOD_TABS.register("ngemextbox_main",
            () -> {
                return CreativeModeTab.builder()
                        .icon(() -> {
                            ItemStack ultimate_ring = new ItemStack(GemExtJewelryRegistry.GEM_RING.get());
                            ultimate_ring.getOrCreateTag().putString("gemextjewelry:gem", "gemextjewelry:diamondgarnetaquamarinetourmalineemeraldperidotrose_quartzmoonstonerubysapphireamethysttopazlapisdiamondgarnetaquamarinetourmalineemeraldperidotrose_quartzmoonstonerubysapphireamethysttopazlapis");
                            return ultimate_ring;
                        })
                        .title(Component.translatable("itemGroup.NGemExtBoxMain"))
                        .displayItems((param, output) -> {
                            GemExtJewelryRegistry.ITEMS.getEntries().forEach(item -> {
                                output.accept(item.get());
                            });
                        })
                        .build();
            });
}
