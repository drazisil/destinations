package com.drazisil.destinations.command;

import com.drazisil.destinations.Destinations;
import com.drazisil.destinations.DestinationsUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.drazisil.destinations.DestinationsUtil.generateRandomLocation;

public class CommandTPRandom implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // The command was run with no args
        if (args.length == 0) {

            // Check if the sender has permission
            if (sender.hasPermission(DestinationsUtil.DESTINATIONS_TELEPORT_RANDOM_PERM)) {
                // The command was called by a player
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    World playerWorld = player.getWorld();
                    Location priorLocation = player.getLocation();

                    // Get the border for the current world
                    WorldBorder border = playerWorld.getWorldBorder();

                    Location newLocation = generateRandomLocation(playerWorld, border);

                    player.sendMessage((String.format("Teleporting to %d, %d, %d",
                            ((int) newLocation.getX()),
                            ((int) newLocation.getY()),
                            ((int) newLocation.getZ()))));

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            // What you want to schedule goes here
                            player.teleport(newLocation);
                        }

                    }.runTaskLater(Destinations.plugin, 20);




                    return true;
                }

            }

            return true;
        }


        return false;
    }


}
