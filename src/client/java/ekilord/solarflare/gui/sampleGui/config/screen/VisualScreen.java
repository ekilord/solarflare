package ekilord.solarflare.gui.sampleGui.config.screen;

import dev.boxadactle.boxlib.gui.BOptionScreen;
import dev.boxadactle.boxlib.gui.widget.BSpacingEntry;
import dev.boxadactle.boxlib.gui.widget.button.BBooleanButton;
import dev.boxadactle.boxlib.gui.widget.button.BConfigScreenButton;
import dev.boxadactle.boxlib.gui.widget.button.BEnumButton;
import dev.boxadactle.boxlib.gui.widget.button.BToggleButton;
import dev.boxadactle.boxlib.gui.widget.label.BCenteredLabel;
import dev.boxadactle.boxlib.gui.widget.slider.BIntegerSlider;
import dev.boxadactle.boxlib.util.GuiUtils;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.config.HudHelper;
import ekilord.solarflare.gui.sampleGui.hud.CoordinatesHuds;
import ekilord.solarflare.gui.sampleGui.hud.HudPositionScreen;
import ekilord.solarflare.gui.sampleGui.position.Position;
import ekilord.solarflare.gui.sampleGui.renderer.RendererMetadata;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.function.Consumer;

public class VisualScreen extends BOptionScreen implements HudHelper {

    Position pos;

    BEnumButton<?> startCornerButton;
    AbstractWidget changeHudPosButton;

    public VisualScreen(Screen parent) {
        super(parent);

        pos = this.generatePositionData();
    }

    @Override
    protected Component getName() {
        return Component.translatable("screen.coordinatesdisplay.visual", CoordinatesDisplay.VERSION_STRING);
    }

    @Override
    protected void initFooter(int startX, int startY) {
        this.setSaveButton(createBackButton(startX, startY, parent));

        this.setWiki(Component.translatable("button.coordinatesdisplay.wiki"), CoordinatesDisplay.WIKI_VISUAL);
    }

    @Override
    protected void initConfigButtons() {

        // visible
        this.addConfigLine(new BBooleanButton(
                "button.coordinatesdisplay.visible",
                config().visible,
                newVal -> config().visible = newVal
        ));

        // decimal places
        this.addConfigLine(new DecimalPlacesSlider(
                "button.coordinatesdisplay.decimalPlaces",
                0, 5,
                config().decimalPlaces,
                newVal -> config().decimalPlaces = newVal
        ));

        startCornerButton = new BEnumButton<>(
                "button.coordinatesdisplay.startcorner",
                config().startCorner,
                ekilord.solarflare.gui.sampleGui.config.ModConfig.StartCorner.class,
                newVal -> config().startCorner = newVal,
                GuiUtils.AQUA
        );

        // display mode
        this.addConfigLine(
                new DisplayModeSelector(
                        newVal -> {
                            config().renderMode = newVal;
                            verifyButtons();
                        }
                ),
                startCornerButton
        );

        // text shadow
        this.addConfigLine(new BBooleanButton(
                "button.coordinatesdisplay.textshadow",
                config().hudTextShadow,
                newVal -> config().hudTextShadow = newVal
        ));

        this.addConfigLine(
                // biome colors
                new BBooleanButton(
                        "button.coordinatesdisplay.biomecolors",
                        config().biomeColors,
                        newVal -> config().biomeColors = newVal
                ),

                // dimension colors
                new BBooleanButton(
                        "button.coordinatesdisplay.dimensioncolors",
                        config().dimensionColors,
                        newVal -> config().dimensionColors = newVal
                )
        );

        // hud position screen
        changeHudPosButton = (AbstractWidget) addConfigLine(new BConfigScreenButton(
                Component.translatable("button.coordinatesdisplay.editHudPos"),
                this,
                HudPositionScreen::new
        ));

        this.addConfigLine(
                // padding
                new BIntegerSlider(
                        "button.coordinatesdisplay.padding",
                        0, 10,
                        config().padding,
                        newVal -> config().padding = newVal
                ),

                // text padding
                new BIntegerSlider(
                        "button.coordinatesdisplay.textpadding",
                        0, 20,
                        config().textPadding,
                        newVal -> config().textPadding = newVal
                )
        );


        this.addConfigLine(new BSpacingEntry());

        // hud rendering
        this.addConfigLine(new BCenteredLabel(Component.translatable("label.coordinatesdisplay.preview")));
        this.addConfigLine(this.createHudRenderEntry(pos));

        // since minecraft's scrolling panels can't handle different entry sizes
        for (int i = 0; i < 4; i++) {
            this.addConfigLine(new BSpacingEntry());
        }

        verifyButtons();

    }

    private void verifyButtons() {
        RendererMetadata metadata = CoordinatesHuds.getRenderer(config().renderMode).getMetadata();

        if (!metadata.ignoreTranslations()) {
            startCornerButton.active = true;
            startCornerButton.setTooltip(null);
        } else {
            startCornerButton.active = false;
            startCornerButton.setTooltip(Tooltip.create(Component.translatable("message.coordintatesdisplay.disabled")));
        }

        if (CoordinatesHuds.getRenderer(config().renderMode).getMetadata().allowMove()) {
            changeHudPosButton.active = true;
            changeHudPosButton.setTooltip(null);
        } else {
            changeHudPosButton.active = false;
            changeHudPosButton.setTooltip(Tooltip.create(Component.translatable("message.coordintatesdisplay.disabled")));
        }
    }

    public static class DecimalPlacesSlider extends BIntegerSlider {

        public DecimalPlacesSlider(String key, int min, int max, int value, Consumer<Integer> function) {
            super(key, min, max, value, function);

            updateMessage();
        }

        @Override
        protected String roundNumber(Integer input) {
            return input == 0 ? "0 (" + GuiUtils.getTranslatable("button.coordinatesdisplay.decimalPlaces.block_pos") + ")" : super.roundNumber(input);
        }
    }

    public static class DisplayModeSelector extends BToggleButton<String> {
        public DisplayModeSelector(Consumer<String> function) {
            super(
                    "button.coordinatesdisplay.displayMode",
                    CoordinatesDisplay.getConfig().renderMode.toLowerCase(),
                    CoordinatesHuds.registeredOverlays.keySet().stream().toList(),
                    function
            );
        }

        @Override
        public String to(Component input) {
            return "";
        }

        @Override
        public Component from(String  input) {
            return new Component() {
                @Override
                public Style getStyle() {
                    return null;
                }

                @Override
                public ComponentContents getContents() {
                    return null;
                }

                @Override
                public List<Component> getSiblings() {
                    return null;
                }

                @Override
                public FormattedCharSequence getVisualOrderText() {
                    return null;
                }
            };
        }
    }
}