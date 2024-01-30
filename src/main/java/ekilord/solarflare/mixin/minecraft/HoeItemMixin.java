package ekilord.solarflare.mixin.minecraft;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HoeItem.class)
public class HoeItemMixin {
    @Redirect(method = "onlyIfAirAbove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"))
    private static boolean redirectOnlyIfAirAbove(BlockState instance) {
        return instance.isAir() || instance.getFluidState().is(FluidTags.WATER);
    }
}