package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.util.HotkeyListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

@Slf4j
@PluginDescriptor(
	name = "Npc Marker"
)
public class NpcMarkerPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private NpcMarkerConfig config;
	@Inject
	private NpcMarkerOverlay npcMarkerOverlay;
	@Inject
	private NpcUtil npcUtil;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private ConfigManager configManager;
	@Inject
	private KeyManager keyManager;

	@Getter(AccessLevel.PACKAGE)
	private final ArrayList<NPC> npcs = new ArrayList<NPC>();

	private final HotkeyListener hotkeyListener = new HotkeyListener(() -> config.keybind()) {
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (config.keybind().matches(e))
			{
				addNpcs();
			}
		}
	};

	@Override
	protected void startUp() throws Exception
	{
		reset();
		overlayManager.add(npcMarkerOverlay);
		keyManager.registerKeyListener(hotkeyListener);
	}

	@Override
	protected void shutDown() throws Exception
	{
		reset();
		overlayManager.remove(npcMarkerOverlay);
		keyManager.unregisterKeyListener(hotkeyListener);
	}

	private void addNpcs()
	{
		for (NPC npc: client.getNpcs())
		{
			if (npc != null || npc.getName() != null)
			{
				if (!(npcs.contains(npc)))
				{
					npcs.add(npc);
				}
			}
		}
	}

	private void reset()
	{
		npcs.clear();
	}


	@Provides
    NpcMarkerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NpcMarkerConfig.class);
	}
}
