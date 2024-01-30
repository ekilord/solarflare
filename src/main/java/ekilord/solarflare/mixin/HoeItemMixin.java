package ekilord.solarflare.mixin;

import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin {
    @Inject(method = "onlyIfAirAbove", at = @At("HEAD"), cancellable = true)
    private static void injectOnlyIfAirAbove(UseOnContext useOnContext, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(useOnContext.getClickedFace() != Direction.DOWN &&
                (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos().above()).isAir() ||
                        useOnContext.getLevel().getFluidState(useOnContext.getClickedPos().above()).is(FluidTags.WATER) ));
        cir.cancel();
    }
}