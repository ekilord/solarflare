package ekilord.solarflare.mixin.betterend;

import de.leximon.fluidlogged.mixin.extensions.LevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.betterend.blocks.CavePumpkinVineBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CavePumpkinVineBlock.class)
public class CavePumpkinVineBlockMixin {
    @Redirect(method = "performBonemeal", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean redirectSetBlockAndUpdate(ServerLevel instance, BlockPos blockPos, BlockState blockState) {
        ((LevelExtension) instance).setBlockAndInsertFluidIfPossible(blockPos, blockState, 3);
        return true;
    }
}
