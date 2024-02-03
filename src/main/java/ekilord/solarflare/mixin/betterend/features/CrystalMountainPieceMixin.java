package ekilord.solarflare.mixin.betterend.features;

import net.minecraft.world.level.levelgen.Heightmap;
import org.betterx.betterend.world.structures.piece.CrystalMountainPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CrystalMountainPiece.class)
public class CrystalMountainPieceMixin {
    @ModifyArg(method = "postProcess", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getOrCreateHeightmapUnprimed(Lnet/minecraft/world/level/levelgen/Heightmap$Types;)Lnet/minecraft/world/level/levelgen/Heightmap;"), index = 0)
    private Heightmap.Types modifyArgPostProcess(Heightmap.Types types2) {
        return types2 == Heightmap.Types.WORLD_SURFACE ? Heightmap.Types.OCEAN_FLOOR : Heightmap.Types.OCEAN_FLOOR_WG;
    }

    @ModifyArg(method = "crystal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getOrCreateHeightmapUnprimed(Lnet/minecraft/world/level/levelgen/Heightmap$Types;)Lnet/minecraft/world/level/levelgen/Heightmap;"), index = 0)
    private Heightmap.Types modifyArgCrystal(Heightmap.Types types2) {
        return types2 == Heightmap.Types.WORLD_SURFACE ? Heightmap.Types.OCEAN_FLOOR : Heightmap.Types.OCEAN_FLOOR_WG;
    }
}
