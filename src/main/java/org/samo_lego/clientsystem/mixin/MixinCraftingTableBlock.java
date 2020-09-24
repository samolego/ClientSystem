package org.samo_lego.clientsystem.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.samo_lego.clientsystem.gui.screen.LinkScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.samo_lego.clientsystem.ClientSystem.keyBinding;

@Mixin(CraftingTableBlock.class)
public class MixinCraftingTableBlock {

    private static final Text TITLE = new LiteralText("Linked Crafting");

    @Inject(method = "createScreenHandlerFactory(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/screen/NamedScreenHandlerFactory;", at = @At("RETURN"), cancellable = true)
    private void openLinkInventory(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<NamedScreenHandlerFactory> cir) {
        System.out.println(keyBinding.isPressed());
            cir.setReturnValue(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new LinkScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos)), TITLE));
    }
}
