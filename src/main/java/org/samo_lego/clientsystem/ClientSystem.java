package org.samo_lego.clientsystem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.samo_lego.clientsystem.gui.screen.LinkScreen;
import org.samo_lego.clientsystem.gui.screen.LinkScreenHandler;

@Environment(EnvType.CLIENT)
public class ClientSystem implements ClientModInitializer {
	public static final String MOD_ID = "clientsystem";
	private static KeyBinding keyBinding;
	public static ScreenHandlerType<LinkScreenHandler> LINK_SCREEN_HANDLER;

	@Override
	public void onInitializeClient() {
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.clientsystem.openLinkSystem", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_LEFT_SHIFT, // The keycode of the key
				"category.clientsystem.main"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				assert client.player != null;
				client.player.sendMessage(new LiteralText("Keybind was pressed."), false);
			}
		});

		LINK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("minecraft", "crafting_table"), LinkScreenHandler::new);
		ScreenRegistry.register(LINK_SCREEN_HANDLER, LinkScreen::new);
	}
}
