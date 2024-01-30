package ekilord.solarflare.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import org.betterx.betterend.blocks.LanceleafSeedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LanceleafSeedBlock.class)
public class LanceleafSeedBlockMixin {
    @Redirect(method = "growAdult", at = @At(value = "INVOKE", target = "Lorg/betterx/bclib/util/BlocksHelper;upRay(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;I)I"))
    private int injectGrowAdult(LevelAccessor world, BlockPos pos, int maxDist) {
        int length = 0;
        for (int j = 1; j < maxDist && (world.getFluidState(pos.above(j)).is(FluidTags.WATER)); j++) {
            length++;
        }
        return length;
    }

    /*@Inject(method = "growAdult", at = @At("HEAD"), cancellable = true)
    public void growAdultInject(WorldGenLevel world, RandomSource random, BlockPos pos, CallbackInfo ci) {
        int height = MHelper.randRange(4, 6, random);
        int h = 0;
        for (int j = 1; j < height + 2 && (world.isEmptyBlock(pos.above(j)) || world.getFluidState(pos.above(j)).is(FluidTags.WATER)); j++) {
            h++;
        }
        if (h >= height + 1) {
            int rotation = random.nextInt(4);
            BlockPos.MutableBlockPos mut = (new BlockPos.MutableBlockPos()).set(pos);
            BlockState plant = (BlockState) EndBlocks.LANCELEAF.defaultBlockState().setValue(BlockProperties.ROTATION, rotation);
            BlocksHelper.setWithUpdate(world, mut, (BlockState)plant.setValue(BlockProperties.PENTA_SHAPE, BlockProperties.PentaShape.BOTTOM));
            BlocksHelper.setWithUpdate(world, mut.move(Direction.UP), (BlockState)plant.setValue(BlockProperties.PENTA_SHAPE, BlockProperties.PentaShape.PRE_BOTTOM));

            for(int i = 2; i < height - 2; ++i) {
                BlocksHelper.setWithUpdate(world, mut.move(Direction.UP), (BlockState)plant.setValue(BlockProperties.PENTA_SHAPE, BlockProperties.PentaShape.MIDDLE));
            }

            BlocksHelper.setWithUpdate(world, mut.move(Direction.UP), (BlockState)plant.setValue(BlockProperties.PENTA_SHAPE, BlockProperties.PentaShape.PRE_TOP));
            BlocksHelper.setWithUpdate(world, mut.move(Direction.UP), (BlockState)plant.setValue(BlockProperties.PENTA_SHAPE, BlockProperties.PentaShape.TOP));
        }
    ci.cancel();
    }*/
}
