package dev.archthefile.gemextjewelry.mixin;

import com.Nuaah.NGemExtBoxMod.block.entity.CombineGem;
import com.Nuaah.NGemExtBoxMod.block.entity.NGemExtBoxModCapabilities;
import com.Nuaah.NGemExtBoxMod.regi.EffectHandler;
import com.llamalad7.mixinextras.sugar.Local;
import dev.archthefile.gemextjewelry.items.GemCharmItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@SuppressWarnings("DiscouragedShift")
@Debug(export = true)
@Mixin(value = EffectHandler.class, remap = false)
public class EffectHandlerMixin {
    @Inject(
            method = "onPlayerTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getArmorSlots()Ljava/lang/Iterable;",
                    shift = At.Shift.BEFORE,
                    remap = true
            )
    )
    private static void injectOnPlayerTick(
            TickEvent.PlayerTickEvent event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusAir") int[] bonusAir,
            @Local(name = "bonusHeal") int[] bonusHeal,
            @Local(name = "bonusSpeed") float[] bonusSpeed,
            @Local(name = "bonusWaterSpeed") double[] bonusWaterSpeed,
            @Local(name = "bonusHealth") int[] bonusHealth,
            @Local(name = "isEyeInWater") boolean isEyeInWater
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (isEyeInWater && id.contains("aquamarine")) {
                                            bonusAir[0] += 3;
                                        }

                                        if (player.isInWater() && id.contains("tourmaline")) {
                                            bonusWaterSpeed[0] += 0.1;
                                        }

                                        if (id.contains("emerald")) {
                                            bonusHeal[0] += 5;
                                        }

                                        if (id.contains("peridot")) {
                                            bonusSpeed[0] += 0.1F;
                                        }

                                        if (id.contains("rose_quartz")) {
                                            bonusHealth[0]++;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (isEyeInWater && id.contains("aquamarine")) {
                                    bonusAir[0] += 3;
                                }

                                if (player.isInWater() && id.contains("tourmaline")) {
                                    bonusWaterSpeed[0] += 0.1;
                                }

                                if (id.contains("emerald")) {
                                    bonusHeal[0] += 5;
                                }

                                if (id.contains("peridot")) {
                                    bonusSpeed[0] += 0.1F;
                                }

                                if (id.contains("rose_quartz")) {
                                    bonusHealth[0]++;
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Inject(
            method = "onLivingFall",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getArmorSlots()Ljava/lang/Iterable;",
                    shift = At.Shift.BEFORE,
                    remap = true
            )
    )
    private static void injectOnLivingFall(
            LivingFallEvent event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusFallDamage") float[] bonusFallDamage
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (id.contains("moonstone")) {
                                            bonusFallDamage[0] += 0.1F;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (id.contains("moonstone")) {
                                    bonusFallDamage[0] += 0.1F;
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Inject(
            method = "onAttackEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;",
                    shift = At.Shift.BEFORE,
                    remap = true
            )
    )
    private static void injectOnAttackEntity(
            AttackEntityEvent event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusFire") int[] bonusFire
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (id.contains("ruby")) {
                                            bonusFire[0]++;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (id.contains("ruby")) {
                                    bonusFire[0]++;
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Inject(
            method = "onLivingHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getArmorSlots()Ljava/lang/Iterable;",
                    shift = At.Shift.BEFORE,
                    remap = true
            )
    )
    private static void injectBonusFireArmor(
            LivingHurtEvent event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusFireArmor") float[] bonusFireArmor
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (id.contains("ruby")) {
                                            bonusFireArmor[0] += 0.15F;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (id.contains("ruby")) {
                                    bonusFireArmor[0] += 0.15F;
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Inject(
            method = "onLivingHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;",
                    shift = At.Shift.BEFORE,
                    ordinal = 1,
                    remap = true
            )
    )
    private static void injectBonusPoison(
            LivingHurtEvent event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusPoison") int[] bonusPoison
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (id.contains("amethyst")) {
                                            bonusPoison[0] += 2;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (id.contains("amethyst")) {
                                    bonusPoison[0] += 2;
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Inject(
            method = "onBreakSpeed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;",
                    shift = At.Shift.BEFORE,
                    remap = true
            )
    )
    private static void injectOnBreakSpeed(
            PlayerEvent.BreakSpeed event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusDigSpeed") float[] bonusDigSpeed
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (id.contains("topaz")) {
                                            bonusDigSpeed[0] += 0.15F;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (id.contains("topaz")) {
                                    bonusDigSpeed[0] += 0.15F;
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Inject(
            method = "onExperienceDrop",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;",
                    shift = At.Shift.BEFORE,
                    remap = true
            )
    )
    private static void injectOnExperienceDrop(
            LivingExperienceDropEvent event,
            CallbackInfo ci,
            @Local(name = "player") Player player,
            @Local(name = "bonusXP") float[] bonusXP
    ) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        curiosInventory.ifPresent(curios -> {
            curios.getCurios().forEach((slotIndex, stacksHandler) -> {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        LazyOptional<CombineGem> capability = stack.getCapability(NGemExtBoxModCapabilities.GEM_CAP);
                        if (capability.isPresent() && stack.getItem() instanceof GemCharmItem) {
                            capability.ifPresent(cap -> {
                                for (ItemStack gem : cap.getGems()) {
                                    if (!gem.isEmpty()) {
                                        String id = gem.getItem().toString();
                                        if (id.contains("lapis")) {
                                            bonusXP[0] += 0.2F;
                                        }
                                    }
                                }
                            });
                        } else {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.contains("gemextjewelry:gem")) {
                                String id = tag.getString("gemextjewelry:gem");
                                if (id.contains("lapis")) {
                                    bonusXP[0] += 0.2F;
                                }
                            }
                        }
                    }
                }
            });
        });
    }
}
