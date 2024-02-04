package ekilord.solarflare.gui.sampleGui.config;

import dev.boxadactle.boxlib.gui.widget.BCustomRenderingEntry;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.ModUtil;
import ekilord.solarflare.gui.sampleGui.position.Position;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public interface HudHelper {

    default Position generatePositionData() {
        net.minecraft.world.phys.Vec3 pos = new net.minecraft.world.phys.Vec3(Math.random() * 1000, Math.random() * 5, Math.random() * 1000);
        BlockPos b = new BlockPos(ModUtil.doubleVecToIntVec(pos));
        ChunkPos chunkPos = new ChunkPos(b);
        float cameraYaw = (float) Math.random() * 180;
        float cameraPitch  = (float) Math.random() * 180;

        return Position.of(
                ModUtil.fromMinecraftVector(pos), chunkPos, b,
                cameraYaw, cameraPitch,
                new BlockPos(b.getX() + 20, b.getY() + 20, b.getZ() + 20), "minecraft:grass_block"
        );

    }

    default ekilord.solarflare.gui.sampleGui.config.ModConfig config() {
        return CoordinatesDisplay.getConfig();
    }

    default BCustomRenderingEntry createHudRenderEntry(Position pos) {
        return new BCustomRenderingEntry((drawContext, x, y, width, height, mouseX, mouseY, tickDelta) -> {
            CoordinatesDisplay.HUD.render(
                    drawContext,
                    pos,
                    (x + width / 2) - CoordinatesDisplay.HUD.getWidth() / 2,
                    y + 3,
                    CoordinatesDisplay.getConfig().renderMode,
                    ekilord.solarflare.gui.sampleGui.config.ModConfig.StartCorner.TOP_LEFT,
                    false
            );
        });
    }

}
