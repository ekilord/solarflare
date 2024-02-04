package ekilord.solarflare.gui.sampleGui.position;

import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.boxlib.util.WorldUtils;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.ModUtil;
import net.minecraft.core.BlockPos;

public class PlayerWorldData {

    String dimension;

    String biomeRegistryEntry;

    public PlayerWorldData(BlockPos player) {
        if (WorldUtils.getWorld() != null) {
            dimension = WorldUtils.getCurrentDimension();

            biomeRegistryEntry = ModUtil.printBiome(WorldUtils.getWorld().getBiome(player));
        } else {
            CoordinatesDisplay.LOGGER.warn("Client world is null! Resorting to default values.");

            dimension = "minecraft:overworld";
            biomeRegistryEntry = "minecraft:plains";
        }
    }

    public String getDimension(boolean formatted) {
        return formatted ? ClientUtils.parseIdentifier(dimension) : dimension;
    }

    public String getBiome(boolean formatted) {
        return formatted ? ClientUtils.parseIdentifier(biomeRegistryEntry) : biomeRegistryEntry;
    }
}

