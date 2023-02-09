package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@ConfigGroup("npcmarker")
public interface NpcMarkerConfig extends Config
{
	@ConfigItem(
			position = 0,
			keyName = "hotkey",
			name = "Hotkey",
			description = "Press this key to mark all NPCs in range."
	)
	default Keybind keybind()
	{
		return new Keybind(KeyEvent.VK_UNDEFINED, InputEvent.CTRL_DOWN_MASK);
	}
}
