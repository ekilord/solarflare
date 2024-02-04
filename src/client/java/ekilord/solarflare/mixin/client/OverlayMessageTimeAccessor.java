package ekilord.solarflare.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface OverlayMessageTimeAccessor {

    @Accessor("overlayMessageTime")
    int getOverlayMessageTime();

}
