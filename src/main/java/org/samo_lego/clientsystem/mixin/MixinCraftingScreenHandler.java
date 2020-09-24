package org.samo_lego.clientsystem.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreenHandler.class)
public class MixinCraftingScreenHandler {

    @Inject(
            method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/ScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
                    shift = At.Shift.AFTER
            )
    )
    private void addLinkScreen(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        // Linked items
        for(int m = 0; m < 2; ++m) {
            for(int l = 0; l < 6; ++l) {
                //((CraftingScreenHandler) (Object) this).addSlot(new Slot(this.input, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }
    }
}
