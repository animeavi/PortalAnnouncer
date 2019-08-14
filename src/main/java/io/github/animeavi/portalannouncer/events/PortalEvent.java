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

            if (fromWorld == toWorld)
                return;

            announce(toWorld, event.getPlayer().getName());
        }
    }

    private void announce(String worldName, String playerName) {
        worldName = worldName.toLowerCase();
        String niceName = worldName;
        String msg = defaultMessage;
        FileConfiguration config = PortalAnnouncer.config;

        if ((config.getString(worldName + ".name") != null) &&
            (config.getString(worldName + ".message") != null) &&
             config.getBoolean(worldName + ".announce")) {
            msg = config.getString(worldName + ".message");
            niceName = config.getString(worldName + ".name");
        }

        if (config.getBoolean("announce-worlds-not-in-config", true) ||
            config.getBoolean(worldName + ".announce")) {
            msg = msg.replace("{player}", playerName).replace("{world}", niceName);

            PortalAnnouncer.plugin.getServer()
                .broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}
