package ekilord.solarflare.gui.sampleGui.renderer;

import dev.boxadactle.boxlib.math.geometry.Rect;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.boxlib.util.GuiUtils;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.ModUtil;
import ekilord.solarflare.gui.sampleGui.hud.HudRenderer;
import ekilord.solarflare.gui.sampleGui.position.Position;
import ekilord.solarflare.mixin.client.OverlayMessageTimeAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import oshi.util.tuples.Triplet;

@RendererMetadata(
        value = "hotbar",
        ignoreTranslations = true,
        allowMove = false,
        hasBackground = false,
        hasXYZ = false,
        hasChunkData = false,
        hasDirection = false,
        hasDirectionInt = false,
        hasBiome = false,
        hasMCVersion = false,
        hasDimension = false
)
public class HotbarRenderer implements HudRenderer {

    @Override
    public Rect<Integer> renderOverlay(GuiGraphics guiGraphics, int x, int y, Position pos) {
        Triplet<String, String, String> player = this.roundPosition(pos.position.getPlayerPos(), pos.position.getBlockPos(), CoordinatesDisplay.getConfig().decimalPlaces);

        Component xyz = definition("xyz",
                value(player.getA()),
                value(player.getB()),
                value(player.getC())
        );

        Component direction = definition("direction", resolveDirection(ModUtil.getDirectionFromYaw(pos.headRot.wrapYaw())));

        String biomestring = pos.world.getBiome(true);
        Component biome = GuiUtils.colorize(
                Component.literal(biomestring),
                CoordinatesDisplay.CONFIG.get().biomeColors ?
                        CoordinatesDisplay.BiomeColors.getBiomeColor(biomestring, CoordinatesDisplay.CONFIG.get().dataColor) :
                        CoordinatesDisplay.CONFIG.get().dataColor
        );

        Component all = translation("all", xyz, direction, biome);
        int i = GuiUtils.getLongestLength(all);

        Rect<Integer> r;

        if (ClientUtils.getClient().level != null && ClientUtils.getCurrentScreen() == null) {
            int j = guiGraphics.guiWidth() / 2;
            int k = guiGraphics.guiHeight() - 68 - 4;

            // make sure we don't render over any actionbar titles
            if (((OverlayMessageTimeAccessor)ClientUtils.getClient().gui).getOverlayMessageTime() == 0)
                drawInfo(guiGraphics, all, j - i / 2, k, GuiUtils.WHITE);

            r = new Rect<>(j - i / 2, k, i, 9);
        } else {
            drawInfo(guiGraphics, all, x, y, GuiUtils.WHITE);
            r = new Rect<>(x, y, i, 9);
        }

        return r;
    }
}
