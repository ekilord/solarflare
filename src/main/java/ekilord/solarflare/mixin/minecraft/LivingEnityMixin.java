package ekilord.solarflare.mixin.minecraft;

import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEnityMixin extends Entity implements Attackable {

    public LivingEnityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private int getCustomMaxAirSupply(){
        int respirationLevel = EnchantmentHelper.getRespiration((LivingEntity) (Object)this);
        return 60 + respirationLevel * 600;
    }

    @Inject(method = "decreaseAirSupply", at = @At("HEAD"), cancellable = true)
    private void injectDecreaseAirSupply(int i, CallbackInfoReturnable<Integer> cir) {
        if(i > getCustomMaxAirSupply()) cir.setReturnValue(getCustomMaxAirSupply() - 1);
        else cir.setReturnValue(i - 1);
        if(i % 20 == 0) System.out.println(i/20 + " seconds of air left");
        cir.cancel();
    }

    @Redirect(method = "increaseAirSupply", at = @At(value="INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMaxAirSupply()I"))
    public int redirectGetMaxAirSupply(LivingEntity instance){
        return getCustomMaxAirSupply();
    }

    @Redirect(method = "baseTick", at = @At(value="INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMaxAirSupply()I"))
    public int redirectBaseTickGetMaxAirSupply(LivingEntity instance){
        return getCustomMaxAirSupply();
    }
}
