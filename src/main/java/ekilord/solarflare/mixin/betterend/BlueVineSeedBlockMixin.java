package ekilord.solarflare.mixin.betterend;

import de.leximon.fluidlogged.mixin.extensions.LevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.betterend.blocks.BlueVineSeedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlueVineSeedBlock.class)
public class BlueVineSeedBlockMixin {
    @Redirect(method = "growAdult", at = @At(value = "INVOKE", target = "Lorg/betterx/bclib/util/BlocksHelper;upRay(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;I)I"))
    private int redirectUpRay(LevelAccessor world, BlockPos pos, int maxDist) {
        int length = 0;
        for (int j = 1; j < maxDist && (world.getFluidState(pos.above(j)).is(FluidTags.WATER)); j++) {
            length++;
        }
        return length;
    }

    @Redirect(method = "growAdult", at = @At(value = "INVOKE", target = "Lorg/betterx/bclib/util/BlocksHelper;setWithoutUpdate(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private void redirectSetWithoutUpdate(LevelAccessor world, BlockPos pos, BlockState state) {
        ((LevelExtension) world).setBlockAndInsertFluidIfPossible(pos, state, 3);
    }
}
