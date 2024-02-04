package ekilord.solarflare;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.boxlib.util.WorldUtils;
import ekilord.solarflare.gui.sampleGui.CoordinatesDisplay;
import ekilord.solarflare.gui.sampleGui.config.ModConfig;
import ekilord.solarflare.gui.sampleGui.position.Position;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;

public class SolarflareClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CoordinatesDisplay.init();

		HudRenderCallback.EVENT.register(this::renderHud);
	}

	private void renderHud(GuiGraphics guiGraphics, float tickDelta) {
		if (
				!ClientUtils.getOptions().hideGui
						&& CoordinatesDisplay.CONFIG.get().visible
						&& CoordinatesDisplay.shouldHudRender
		) {
			try {
				RenderSystem.enableBlend();

				ModConfig config = CoordinatesDisplay.getConfig();

				CoordinatesDisplay.HUD.render(
						guiGraphics,
						Position.of(WorldUtils.getCamera()),
						config.hudX,
						config.hudY,
						config.renderMode,
						config.startCorner,
						false,
						config.hudScale
				);
			} catch (NullPointerException exception) {
				CoordinatesDisplay.LOGGER.printStackTrace(exception);
			}
		}
	}

}