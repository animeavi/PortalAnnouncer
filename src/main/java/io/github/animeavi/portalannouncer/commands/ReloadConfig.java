package io.github.animeavi.portalannouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.animeavi.portalannouncer.PortalAnnouncer;
import io.github.animeavi.portalannouncer.events.PortalEvent;
import net.md_5.bungee.api.ChatColor;

public class ReloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        PortalAnnouncer.updateValues();
        PortalEvent.updateValues();

        String msg = ChatColor.translateAlternateColorCodes('&',
                PortalAnnouncer.plugin.getConfig().getString("config-reloaded-message", "&2PortalAnnouncer configuration reloaded!"));

        PortalAnnouncer.plugin.getLogger().info(ChatColor.stripColor(msg));
        sender.sendMessage(msg);

        return true;
    }

}
