package ekilord.solarflare.mixin.betterend;

import de.leximon.fluidlogged.mixin.extensions.LevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.betterend.world.features.CavePumpkinFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CavePumpkinFeature.class)
public class CavePumpkinFeatureMixin {
    @Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectIsEmptyBlock(WorldGenLevel instance, BlockPos blockPos) {
        return instance.isEmptyBlock(blockPos) || instance.getFluidState(blockPos).is(FluidTags.WATER);
    }

    @Redirect(method = "place", at = @At(value = "INVOKE", target = "Lorg/betterx/bclib/util/BlocksHelper;setWithoutUpdate(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private void redirectSetWithoutUpdate(LevelAccessor world, BlockPos pos, BlockState state) {
        ((LevelExtension) world).setBlockAndInsertFluidIfPossible(pos, state, 3);
    }
}
