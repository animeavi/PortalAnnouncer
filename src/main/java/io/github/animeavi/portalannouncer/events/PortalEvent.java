package io.github.animeavi.portalannouncer.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import io.github.animeavi.portalannouncer.PortalAnnouncer;
import net.md_5.bungee.api.ChatColor;

public class PortalEvent implements Listener {
    private static String defaultMessage;

    public PortalEvent() {
        updateValues();
    }

    public static void updateValues() {
        defaultMessage = PortalAnnouncer.config
                .getString("default-message", "&2{player} has entered {world}");
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == TeleportCause.NETHER_PORTAL ||
            event.getCause() == TeleportCause.END_PORTAL ||
            event.getCause() == TeleportCause.PLUGIN ||
            event.getCause() == TeleportCause.COMMAND) {
            String fromWorld = event.getFrom().getWorld().getName().toString();
            String toWorld = event.getTo().getWorld().getName().toString();

            if (fromWorld.equals(toWorld))
                return;

            announce(toWorld, event.getPlayer().getName());
        }
    }

    private void announce(String worldName, String playerName) {
        FileConfiguration config = PortalAnnouncer.config;
        worldName = worldName.toLowerCase();
        String niceName = config.getString(worldName + ".name", worldName);
        String msg = config.getString(worldName + ".message", defaultMessage);

        if (!config.getBoolean(worldName + ".announce", true)) {
            return;
        }

        if (config.getBoolean("announce-worlds-not-in-config", true) ||
            config.getBoolean(worldName + ".announce")) {
            msg = msg.replace("{player}", playerName).replace("{world}", niceName);

            PortalAnnouncer.plugin.getServer()
                .broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}
