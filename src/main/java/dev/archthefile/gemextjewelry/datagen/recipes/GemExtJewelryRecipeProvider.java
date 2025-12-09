package dev.archthefile.gemextjewelry.datagen.recipes;

import com.Nuaah.NGemExtBoxMod.regi.tag.NGemExtBoxModTags;
import dev.archthefile.gemextjewelry.GemExtJewelry;
import dev.archthefile.gemextjewelry.registry.GemExtJewelryRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

@SuppressWarnings("removal")
public class GemExtJewelryRecipeProvider extends RecipeProvider {
    public GemExtJewelryRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GemExtJewelryRegistry.GEM_RING.get())
                .pattern("IGI")
                .pattern("G G")
                .pattern("IGI")
                .define('G', Items.GOLD_INGOT)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .save(consumer, new ResourceLocation(GemExtJewelry.MODID, "empty_ring"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GemExtJewelryRegistry.GEM_CHARM.get())
                .pattern(" UU")
                .pattern("GNU")
                .pattern("GG ")
                .define('G', Items.GOLD_INGOT)
                .define('N', Items.NETHERITE_INGOT)
                .define('U', Items.GOLD_NUGGET)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GemExtJewelryRegistry.ULTIMATE_GEM.get())
                .pattern("GGG")
                .pattern("GNG")
                .pattern("GGG")
                .define('G', Ingredient.of(NGemExtBoxModTags.Items.COMBINE_GEMS))
                .define('N', Items.NETHER_STAR)
                .unlockedBy("has_star", has(Items.NETHER_STAR))
                .save(consumer);
    }
}
