package com.drazisil.destinations;

import com.drazisil.destinations.command.CommandTPRandom;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class Destinations extends JavaPlugin {

    public static Destinations plugin;

    public static Logger logger;


    public Destinations getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        logger = getLogger();

        plugin.saveDefaultConfig();

        // Register the command handler
        PluginCommand pluginCmd = this.getCommand("tprandom");

        if (pluginCmd == null) throw new Error("Unable to fetch command handler for `tprandom");

        pluginCmd.setExecutor(new CommandTPRandom());

        // Register the event listener
        getServer().getPluginManager().registerEvents(new EventListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
