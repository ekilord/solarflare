package ekilord.solarflare.mixin;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;
import org.betterx.bclib.blocks.BaseTerrainBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseTerrainBlock.class)
public class BaseTerrainBlockMixin {

    @Inject(method = "canStay", at = @At("HEAD"), cancellable = true)
    public void injectCanStay(BlockState state, LevelReader worldView, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = pos.above();
        BlockState blockState = worldView.getBlockState(blockPos);

        if (worldView.getFluidState(blockPos).is(FluidTags.WATER)) {
            cir.setReturnValue(true);
        }
        else if (blockState.is(Blocks.SNOW) && (Integer) blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            cir.setReturnValue(true);
        }
        if (!blockState.isSolidRender(worldView, blockPos)) {
            cir.setReturnValue(true);
        }
        else {
            cir.setReturnValue(false);
        }
        cir.cancel();
    }
}
