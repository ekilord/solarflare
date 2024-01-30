package ekilord.solarflare.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void injectCanSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos pos = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(pos);
        cir.setReturnValue(!blockState2.isSolid() || blockState2.getBlock() instanceof FenceGateBlock ||
                blockState2.getBlock() instanceof MovingPistonBlock || levelReader.getFluidState(pos).is(FluidTags.WATER));
        cir.cancel();
    }
}
