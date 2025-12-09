package dev.archthefile.gemextjewelry.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

@SuppressWarnings("removal")
public class GemRingItem extends Item implements ICurioItem {
    public GemRingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext, UUID uuid, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        CompoundTag tag = stack.getOrCreateTag();

        if (tag.contains("gemextjewelry:gem")) {
            String id = tag.getString("gemextjewelry:gem");
            if (id.contains("diamond")) {
                builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "GemRing Attribute", 1.0f, AttributeModifier.Operation.ADDITION));
            }
            if (id.contains("garnet")) {
                builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "GemRing Attribute", 0.5f, AttributeModifier.Operation.ADDITION));
            }
        }
        return builder.build();
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("gemextjewelry:gem")) {
            String gemId = stack.getTag().getString("gemextjewelry:gem");
            Item gem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(gemId));
            if (gem != null) {
                Component gemName = Component.translatable(gem.getDescriptionId());
                return Component.translatable("item.gemextjewelry.gem_ring", gemName);
            }
        }
        return Component.translatable("item.gemextjewelry.empty_ring");
    }
}

