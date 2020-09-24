package org.samo_lego.clientsystem.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;

import java.util.List;

import static org.samo_lego.clientsystem.ClientSystem.LINK_SCREEN_HANDLER;

@Environment(EnvType.CLIENT)
public class LinkScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {

    private final CraftingInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public LinkScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public LinkScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(LINK_SCREEN_HANDLER, syncId);
        this.input = new CraftingInventory(this, 3, 3);
        this.result = new CraftingResultInventory();
        this.context = context;
        this.player = playerInventory.player;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 124, 35));

        int m;
        int l;

        // Linked items
        /*for(m = 0; m < 2; ++m) {
            for(l = 0; l < 6; ++l) {
                this.addSlot(new Slot(this.input, l + m * 2 + 6, l * 18, m * 18));
            }
        }*/

        // Crafting inventory
        for(m = 0; m < 3; ++m) {
            for(l = 0; l < 3; ++l) {
                this.addSlot(new Slot(this.input, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }
        // The player inventory
        for(m = 0; m < 3; ++m) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        // The player hotbar
        for(m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
        System.out.println("Link system creation of GUI");

    }

    @Environment(EnvType.CLIENT)
    public void updateSlotStacks(List<ItemStack> stacks) {
        for(int i = 0; i < stacks.size(); ++i) {
            this.getSlot(i).setStack(stacks.get(i));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.context.run((world, blockPos) -> {
                    itemStack2.getItem().onCraft(itemStack2, world, player);
                });
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onStackChanged(itemStack2, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.insertItem(itemStack2, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.insertItem(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack3 = slot.onTakeItem(player, itemStack2);
            if (index == 0) {
                player.dropItem(itemStack3, false);
            }
        }

        return itemStack;
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {

    }

    @Override
    public void clearCraftingSlots() {

    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return false;
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return 0;
    }

    @Override
    public int getCraftingHeight() {
        return 0;
    }

    @Override
    public int getCraftingSlotCount() {
        return 0;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return null;
    }
}
