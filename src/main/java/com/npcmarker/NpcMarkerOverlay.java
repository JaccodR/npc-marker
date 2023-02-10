package com.npcmarker;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class NpcMarkerOverlay extends Overlay
{
    private final Client client;
    private final NpcMarkerPlugin plugin;
    private final NpcMarkerConfig config;
    private final NpcUtil npcUtil;

    @Inject
    private NpcMarkerOverlay(Client client, NpcMarkerPlugin plugin, NpcMarkerConfig config, NpcUtil npcUtil)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.npcUtil = npcUtil;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        for (NPC npc: plugin.getNpcs())
        {
            Color outlineColor = Color.CYAN;
            Color fillColor = new Color(0, 255, 255, 20);
            NPCComposition npcComposition = npc.getTransformedComposition();

            if (npcComposition != null)
            {
                Shape objectClickbox = npc.getConvexHull();
                if (objectClickbox != null)
                {
                    renderPoly(graphics, outlineColor, fillColor, objectClickbox);
                }
            }
        }
        return null;
    }


    private void renderPoly(Graphics2D graphics, Color outlineColor, Color fillColor, Shape polygon)
    {
        if (polygon != null)
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(outlineColor);
            graphics.setStroke(new BasicStroke((float) 2));
            graphics.draw(polygon);
            graphics.setColor(fillColor);
            graphics.fill(polygon);
        }
    }
}
