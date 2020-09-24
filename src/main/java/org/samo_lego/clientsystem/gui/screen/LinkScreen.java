package org.samo_lego.clientsystem.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LinkScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE_CRAFTING = new Identifier("textures/gui/container/crafting_table.png");
    private static final Identifier TEXTURE_SEARCH = new Identifier("textures/gui/container/creative_inventory/tab_item_search.png");
    private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private boolean narrow;

    public LinkScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        client.getTextureManager().bindTexture(TEXTURE_SEARCH);
        x = (width - backgroundWidth) / 2;
        y = height / 4 - backgroundHeight / 2;
        System.out.println(height);
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // Crafting
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE_CRAFTING);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.narrow = this.width < 379;
        //this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, (AbstractRecipeScreenHandler<?>)this.handler);
        this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
        this.children.add(this.recipeBook);
        this.setInitialFocus(this.recipeBook);
        this.addButton(new TexturedButtonWidget(this.x + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (buttonWidget) -> {
            this.recipeBook.reset(this.narrow);
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
            ((TexturedButtonWidget)buttonWidget).setPos(this.x + 5, this.height / 2 - 49);
        }));
    }
}
