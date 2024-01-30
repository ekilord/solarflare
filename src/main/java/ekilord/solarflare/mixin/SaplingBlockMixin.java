package ekilord.solarflare.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SaplingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin {
    @ModifyArg(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getMaxLocalRawBrightness(Lnet/minecraft/core/BlockPos;)I"), index = 0)
    private BlockPos injectRandomTick(BlockPos blockPos) {
        return new BlockPos(blockPos.getX(), 321, blockPos.getZ());
    }
}
