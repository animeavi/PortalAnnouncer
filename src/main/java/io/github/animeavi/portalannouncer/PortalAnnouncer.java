package io.github.animeavi.portalannouncer;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.animeavi.portalannouncer.commands.ReloadConfig;
import io.github.animeavi.portalannouncer.events.PortalEvent;

public class PortalAnnouncer extends JavaPlugin {
    public static PortalAnnouncer plugin;
    public static FileConfiguration config;

    public void onEnable() {
        plugin = this;
        plugin.createConfig();
        updateValues();
        getServer().getPluginManager().registerEvents(new PortalEvent(), this);
        getCommand("pareload").setExecutor(new ReloadConfig());
    }

    public void onDisable() {
        plugin = null;
    }

    @SuppressWarnings("unused")
    private void createConfig() {
        try {
            File file;
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }
            if (!(file = new File(this.getDataFolder(), "config.yml")).exists()) {
                this.getLogger().info("Configuration not found, creating!");
                this.saveDefaultConfig();
            } else {
                this.getLogger().info("Configuration found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateValues() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    private Plugin resolvePlugin(String name) {
        Plugin temp = getServer().getPluginManager().getPlugin(name);

        if (temp == null) {
            return null;
        }

        return temp;
    }
}
