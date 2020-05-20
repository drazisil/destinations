package com.drazisil.destinations;

import com.drazisil.destinations.command.CommandTPRandom;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Destinations extends JavaPlugin {

    public static Destinations plugin;

    public Destinations getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        plugin.saveDefaultConfig();
        try {
            Objects.requireNonNull(this.getCommand("tprandom")).setExecutor(new CommandTPRandom());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
