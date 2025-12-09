package dev.archthefile.gemextjewelry.client.event;

import com.Nuaah.NGemExtBoxMod.main.NGemExtBoxMod;
import dev.archthefile.gemextjewelry.GemExtJewelry;
import dev.archthefile.gemextjewelry.items.GemRingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = GemExtJewelry.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if(stack.hasTag() && stack.getTag().contains("gemextjewelry:gem")){
            tooltip.add(Component.translatable("tooltip.desc." + NGemExtBoxMod.MOD_ID + ".combine.gem").withStyle(ChatFormatting.GOLD));
            ResourceLocation id = new ResourceLocation(stack.getTag().getString("gemextjewelry:gem"));
            Item gem = ForgeRegistries.ITEMS.getValue(id);
            if(gem != null){
                if(id.getNamespace().equals("minecraft")){
                    tooltip.add(Component.translatable(gem.getDescriptionId()));
                } else {
                    tooltip.add(Component.translatable("tooltip." + NGemExtBoxMod.MOD_ID + "." + id.getPath()));
                }
            }
        }else if(stack.getItem() instanceof GemRingItem){
            tooltip.add(Component.translatable("tooltip.gemextjewelry.combinegems").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }
}
