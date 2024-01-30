package ekilord.solarflare.mixin.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SaplingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getMaxLocalRawBrightness(Lnet/minecraft/core/BlockPos;)I"))
    private int injectRandomTick(ServerLevel instance, BlockPos blockPos) {
        return 15;
    }
}
