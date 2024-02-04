package ekilord.solarflare.gui.sampleGui.renderer;

import dev.boxadactle.boxlib.math.geometry.Rect;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.boxlib.util.RenderUtils;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.ModUtil;
import ekilord.solarflare.gui.sampleGui.hud.HudRenderer;
import ekilord.solarflare.gui.sampleGui.position.Position;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import oshi.util.tuples.Triplet;

@RendererMetadata(
        value = "line",
        hasChunkData = false,
        hasDirectionInt = false,
        hasBiome = false,
        hasMCVersion = false,
        hasDimension = false
)
public class LineRenderer implements HudRenderer {

    private int calculateWidth(Component line, int p) {
        int a = GuiUtils.getLongestLength(line);

        return config().renderBackground ? p * 2 + a : a;
    }

    private int calculateHeight(int th, int p) {
        return config().renderBackground ? p * 2 + th : th;
    }

    @Override
    public Rect<Integer> renderOverlay(GuiGraphics guiGraphics, int x, int y, Position pos) {
        Triplet<String, String, String> player = this.roundPosition(pos.position.getPlayerPos(), pos.position.getBlockPos(), CoordinatesDisplay.getConfig().decimalPlaces);

        Component xtext = addTrailingSpace(definition("x", value(player.getA())));
        Component ytext = addTrailingSpace(definition("y", value(player.getB())));
        Component ztext = definition("z",value(player.getC()));

        Component direction = definition("direction", resolveDirection(ModUtil.getDirectionFromYaw(pos.headRot.wrapYaw())));

        Component a = Component.empty();
        if (config().renderXYZ) {
            a = next(next(xtext, ytext), ztext);
            a = addTrailingSpace(a);
        }
        if (config().renderDirection) a = next(a, direction);

        int p = config().padding;

        int w = calculateWidth(a, p);
        int h = calculateHeight(GuiUtils.getTextRenderer().lineHeight, p);

        if (config().renderBackground) {
            RenderUtils.drawSquare(guiGraphics, x, y, w, h, config().backgroundColor);
            drawInfo(guiGraphics, a, x + p, y + p, 0xFFFFFF);
        } else {
            drawInfo(guiGraphics, a, x, y, 0xFFFFFF);
        }

        return new Rect<>(x, y, w, h);
    }

    private Component next(Component t1, Component t2) {
        return t1.copy().append(t2);
    }

    private Component addTrailingSpace(Component input) {
        return input.copy().append(" ");
    }
}
