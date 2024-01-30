package ekilord.solarflare.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public class CropBlockMixin {

    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getRawBrightness(Lnet/minecraft/core/BlockPos;I)I"))
    private int injectCanSurvive(ServerLevel instance, BlockPos blockPos, int i) {
        return 15;
    }

    @Redirect(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelReader;canSeeSky(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean injectCanSurvive(LevelReader instance, BlockPos blockPos) {
        return true;
    }
}
