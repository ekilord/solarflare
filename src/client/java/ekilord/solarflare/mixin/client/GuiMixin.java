package ekilord.solarflare.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Mutable
    @Final
    @Shadow
    private final Minecraft minecraft;

    public GuiMixin(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Inject(method = "renderPlayerHealth", at = @At("TAIL"))
    public void injectRenderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        System.out.println("ez utana");
    }
}
