package com.npcmarker;

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
			description = "Press this key to mark all newly spawned NPCs in range."
	)
	default Keybind keybind()
	{
		return new Keybind(KeyEvent.VK_UNDEFINED, InputEvent.CTRL_DOWN_MASK);
	}

	@ConfigItem(
			position = 1,
			keyName = "excludeRangeMelee",
			name = "Exclude warriors and rangers",
			description = "Will exclude spiritual warriors and rangers from being marked."
	)
	default boolean excludeRangeMelee() { return true; }
}
