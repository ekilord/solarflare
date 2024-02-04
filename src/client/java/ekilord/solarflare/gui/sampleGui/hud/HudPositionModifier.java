package ekilord.solarflare.gui.sampleGui.hud;

import dev.boxadactle.boxlib.math.geometry.Dimension;
import dev.boxadactle.boxlib.math.geometry.Rect;
import dev.boxadactle.boxlib.math.geometry.Vec2;
import ekilord.solarflare.gui.sampleGui.config.ModConfig;

public interface HudPositionModifier {

    Vec2<Integer> translateVector(Vec2<Integer> original, Dimension<Integer> window, ModConfig.StartCorner currentCorner);

    Rect<Integer> translateRect(Rect<Integer> rect, Dimension<Integer> window, ModConfig.StartCorner currentCorner);

}
