package ekilord.solarflare.gui.sampleGui.config.screen;

import dev.boxadactle.boxlib.gui.BOptionScreen;
import dev.boxadactle.boxlib.gui.widget.BSpacingEntry;
import dev.boxadactle.boxlib.gui.widget.button.BBooleanButton;
import dev.boxadactle.boxlib.gui.widget.label.BCenteredLabel;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.config.HudHelper;
import ekilord.solarflare.gui.sampleGui.hud.CoordinatesHuds;
import ekilord.solarflare.gui.sampleGui.position.Position;
import ekilord.solarflare.gui.sampleGui.renderer.RendererMetadata;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class RenderScreen extends BOptionScreen implements HudHelper {

    Position pos;

    public RenderScreen(Screen parent) {
        super(parent);

        pos = this.generatePositionData();
    }

    @Override
    protected Component getName() {
        return Component.translatable("screen.coordinatesdisplay.render", CoordinatesDisplay.VERSION_STRING);
    }

    @Override
    protected void initFooter(int startX, int startY) {
        this.setSaveButton(createBackButton(startX, startY, parent));

        this.setWiki(Component.translatable("button.coordinatesdisplay.wiki"), CoordinatesDisplay.WIKI_RENDER);
    }

    @Override
    protected void initConfigButtons() {
        RendererMetadata metadata = CoordinatesHuds.getRenderer(config().renderMode).getMetadata();

        // background
        this.addConfigLine(new HudOption(
                "button.coordinatesdisplay.background",
                config().renderBackground,
                newVal -> config().renderBackground = newVal,
                metadata.hasBackground()
        ));

        this.addConfigLine(
                // XYZ
                new HudOption(
                        "button.coordinatesdisplay.xyz",
                        config().renderXYZ,
                        newVal -> config().renderXYZ = newVal,
                        metadata.hasXYZ()
                ),

                // chunk pos
                new HudOption(
                        "button.coordinatesdisplay.chunkpos",
                        config().renderChunkData,
                        newVal -> config().renderChunkData = newVal,
                        metadata.hasChunkData()
                )
        );

        this.addConfigLine(
                // direction
                new HudOption(
                        "button.coordinatesdisplay.direction",
                        config().renderDirection,
                        newVal -> config().renderDirection = newVal,
                        metadata.hasDirection()
                ),

                // direction int
                new HudOption(
                        "button.coordinatesdisplay.directionint",
                        config().renderDirectionInt,
                        newVal -> config().renderDirectionInt = newVal,
                        metadata.hasDirectionInt()
                )
        );

        this.addConfigLine(
                // biome
                new HudOption(
                        "button.coordinatesdisplay.biome",
                        config().renderBiome,
                        newVal -> config().renderBiome = newVal,
                        metadata.hasBiome()
                ),
                new HudOption(
                        "button.coordinatesdisplay.dimension",
                        config().renderDimension,
                        newVal -> config().renderDimension = newVal,
                        metadata.hasDimension()
                )
        );

        // mc version
        this.addConfigLine(new HudOption(
                "button.coordinatesdisplay.mcversion",
                config().renderMCVersion,
                newVal -> config().renderMCVersion = newVal,
                metadata.hasMCVersion()
        ));

        this.addConfigLine(new BSpacingEntry());

        // hud rendering
        this.addConfigLine(new BCenteredLabel(Component.translatable("label.coordinatesdisplay.preview")));
        this.addConfigLine(this.createHudRenderEntry(pos));

        // since minecraft's scrolling panels can't handle different entry sizes
        for (int i = 0; i < 4; i++) {
            this.addConfigLine(new BSpacingEntry());
        }

    }

    public static class HudOption extends BBooleanButton {
        public HudOption(String key, Boolean value, Consumer<Boolean> function, boolean configEnabled) {
            super(key, value, function);

            this.active = configEnabled;

            if (!configEnabled) {
                this.setTooltip(Tooltip.create(Component.translatable("message.coordintatesdisplay.disabled")));
            }
        }
    }
}
