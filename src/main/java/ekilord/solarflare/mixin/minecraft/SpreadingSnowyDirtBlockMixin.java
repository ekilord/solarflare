package ekilord.solarflare.mixin.minecraft;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.SnowyDirtBlock.SNOWY;

@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class SpreadingSnowyDirtBlockMixin {

    @Inject(method = "canBeGrass", at = @At("HEAD"), cancellable = true)
    private static void injectCanBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos pos = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(pos);

        if (levelReader.getFluidState(pos).is(FluidTags.WATER) || levelReader.getBlockState(pos).is(Blocks.AIR)) {
            cir.setReturnValue(true);
        }
        else if (blockState2.is(Blocks.SNOW) && (Integer)blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            cir.setReturnValue(true);
        }
        else if (blockState2.isSolid()) {
            cir.setReturnValue(false);
        }
        cir.cancel();
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void injectRandomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        BlockPos pos = blockPos.above();

        if (!SpreadingSnowyDirtBlockInvoker.invokeCanBeGrass(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
        }
        else {
            SpreadingSnowyDirtBlock thisBlock = (SpreadingSnowyDirtBlock) (Object)this;
            BlockState blockState2 = thisBlock.defaultBlockState();

            for(int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                BlockPos pos2 = blockPos2.above();
                if (serverLevel.getBlockState(blockPos2).is(Blocks.DIRT) && (!serverLevel.getBlockState(pos2).isSolid())) {
                    serverLevel.setBlockAndUpdate(blockPos2, (BlockState)blockState2.setValue(SNOWY, serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW)));
                }
            }
        }
        ci.cancel();
    }
}