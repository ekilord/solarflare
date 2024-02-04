package ekilord.solarflare.gui.sampleGui.config.screen;

import dev.boxadactle.boxlib.gui.BOptionScreen;
import dev.boxadactle.boxlib.gui.widget.BSpacingEntry;
import dev.boxadactle.boxlib.gui.widget.button.BBooleanButton;
import dev.boxadactle.boxlib.gui.widget.label.BCenteredLabel;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.ModUtil;
import ekilord.solarflare.gui.sampleGui.config.HudHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DeathPosScreen extends BOptionScreen implements HudHelper {

    public DeathPosScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected Component getName() {
        return Component.translatable("screen.coordinatesdisplay.deathpos", CoordinatesDisplay.VERSION_STRING);
    }

    @Override
    protected void initFooter(int startX, int startY) {
        this.setSaveButton(createBackButton(startX, startY, parent));

        this.setWiki(Component.translatable("button.coordinatesdisplay.wiki"), CoordinatesDisplay.WIKI_DEATHPOS);
    }

    @Override
    protected void initConfigButtons() {

        // display on death screen
        this.addConfigLine(new BBooleanButton(
                "button.coordinatesdisplay.displayondeathscreen",
                config().displayPosOnDeathScreen,
                newVal -> config().displayPosOnDeathScreen = newVal
        ));

        // display in chat
        this.addConfigLine(new BBooleanButton(
                "button.coordinatesdisplay.sendinchat",
                config().showDeathPosInChat,
                newVal -> config().showDeathPosInChat = newVal
        ));

        addConfigLine(new BSpacingEntry());

        addConfigLine(new BCenteredLabel(ModUtil.makeDeathPositionComponent(generatePositionData())));

    }
}