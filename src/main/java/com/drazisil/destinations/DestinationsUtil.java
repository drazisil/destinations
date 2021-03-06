package com.drazisil.destinations;

import org.bukkit.*;
import org.bukkit.permissions.Permission;

import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.permissions.PermissionDefault.OP;

public final class DestinationsUtil {

    public static final Permission DESTINATIONS_WAYSTONE_CREATE_PERM = new Permission(
            Destinations.MODID + ".waystone.create",
            "Permission to create a waystone",
            OP);

    public static final Permission DESTINATIONS_WAYSTONE_USE_PERM = new Permission(
            Destinations.MODID + ".waystone.use",
            "Permission to use a waystone",
            OP);

    public static final String DESTINATIONS_TELEPORT_RANDOM_PERM = "destinations.teleport.random";

    public static final NamespacedKey DESTINATIONS_TELEPORT_WAYSTONE_LOCATION_KEY
            = new NamespacedKey(Destinations.plugin, Destinations.MODID + ".teleport.waystone.location");


    public static Location generateRandomLocation(World playerWorld, WorldBorder border) {
        int borderSize = (int) border.getSize() / 4;

        int randomX = ThreadLocalRandom.current().nextInt((-(borderSize + 1)), borderSize);
        int randomZ = ThreadLocalRandom.current().nextInt((-(borderSize + 1)), borderSize);

        int safeY = playerWorld.getHighestBlockYAt(randomX, randomZ);

        Location newLocation = new Location(playerWorld, randomX, safeY, randomZ);

        loadChunkAtLocation(newLocation);

        if (playerWorld.getBlockAt(newLocation).isLiquid()
                || !border.isInside(newLocation)) {
            return generateRandomLocation(playerWorld, border);
        }

        return newLocation.add(0.5,1.5,0.5);
    }

    public static void loadChunkAtLocation(Location location) {
        World chunkWorld = location.getWorld();

        if (!(chunkWorld == null)) {
            try {
                Chunk targetChunk = location.getWorld().getChunkAt(location);
                targetChunk.load();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }



    }

}
