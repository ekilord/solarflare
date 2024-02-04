package ekilord.solarflare.gui.sampleGui.hud;

import dev.boxadactle.boxlib.math.geometry.Rect;
import dev.boxadactle.boxlib.math.geometry.Vec3;
import dev.boxadactle.boxlib.math.mathutils.NumberFormatter;
import dev.boxadactle.boxlib.util.GuiUtils;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.config.ModConfig;
import ekilord.solarflare.gui.sampleGui.position.Position;
import ekilord.solarflare.gui.sampleGui.renderer.RendererMetadata;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import oshi.util.tuples.Triplet;

public interface HudRenderer {

    // HUD HELPERS

    default ModConfig config() {
        return CoordinatesDisplay.getConfig();
    }

    default void drawInfo(GuiGraphics guiGraphics, Component component, int x, int y, int color) {
        guiGraphics.drawString(GuiUtils.getTextRenderer(), component, x, y, color, CoordinatesDisplay.CONFIG.get().hudTextShadow);
    }

    default void drawInfo(GuiGraphics guiGraphics, Component component, int x, int y) {
        drawInfo(guiGraphics, component, x, y, GuiUtils.WHITE);
    }



    // text helpers

    default String getTranslationKey() {
        return getNameKey() + ".";
    }

    default String getNameKey() {
        RendererMetadata metadata = this.getClass().getAnnotation(RendererMetadata.class);
        if (metadata != null) {
            if (!metadata.translationKey().isEmpty()) {
                return metadata.translationKey();
            } else {
                return "hud.coordinatesdisplay." + metadata.value();
            }
        } else {
            throw new RuntimeException("Cannot use hud text helpers without specifying a translation key!");
        }
    }

    default Component translation(String t, Object ...args) {
        return Component.translatable(getTranslationKey() + t, args);
    }

    default Component definition(Component t) {
        return GuiUtils.colorize(t, CoordinatesDisplay.getConfig().definitionColor);
    }

    default Component definition(String t) {
        return GuiUtils.colorize(Component.literal(t), CoordinatesDisplay.getConfig().definitionColor);
    }

    default Component definition(String k, Object ...args) {
        return definition(translation(k, args));
    }

    default Component value(String t) {
        return GuiUtils.colorize(Component.literal(t), CoordinatesDisplay.getConfig().dataColor);
    }

    default Component value(Component t) {
        return GuiUtils.colorize(t, CoordinatesDisplay.getConfig().dataColor);
    }

    default Component valueTranslation(String k, Object ...args) {
        return value(translation(k, args));
    }

    default Component resolveDirection(String direction, boolean useShort) {
        String key = "hud.coordinatesdisplay." + direction;
        if (useShort) {
            key += ".short";
        }
        return Component.translatable(key);
    }

    default Component resolveDirection(String direction) {
        return resolveDirection(direction, false);
    }



    // POSITION HELPER

    default Triplet<String, String, String> roundPosition(Vec3<Double> pos, Vec3<Integer> blockPos, int decimalPlaces) {
        if (decimalPlaces == 0) {
            return new Triplet<>(
                    Integer.toString(blockPos.getX()),
                    Integer.toString(blockPos.getY()),
                    Integer.toString(blockPos.getZ())
            );
        } else {
            var n = new NumberFormatter<Double>(decimalPlaces);
            return new Triplet<>(
                    n.formatDecimal(pos.getX()),
                    n.formatDecimal(pos.getY()),
                    n.formatDecimal(pos.getZ())
            );
        }
    }

    default NumberFormatter<Double> genFormatter() {
        return new NumberFormatter<>(CoordinatesDisplay.getConfig().decimalPlaces);
    }


    // HUD RENDERER METHOD

    Rect<Integer> renderOverlay(GuiGraphics guiGraphics, int x, int y, Position pos);

}