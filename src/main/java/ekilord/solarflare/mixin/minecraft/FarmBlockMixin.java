package ekilord.solarflare.mixin.minecraft;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @Redirect(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isSolid()Z"))
    private boolean injectCanSurvive(BlockState instance) {
        return !instance.getFluidState().is(FluidTags.WATER) || instance.isSolid();
    }
}
