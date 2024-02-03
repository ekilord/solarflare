package ekilord.solarflare.mixin.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {


    @Inject(
            method = "renderPlayerHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getMaxAirSupply()I",
                    shift = At.Shift.AFTER
            ),
            slice= @Slice(

            ),
            locals =
    )
    public void injectRenderPlayerHealth(CallbackInfo ci, Minecraft minecraft) {

    }
}
