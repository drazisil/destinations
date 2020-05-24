package com.drazisil.destinations;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static com.drazisil.destinations.DestinationsUtil.DESTINATIONS_TELEPORT_WAYSTONE_LOCATION_KEY;

public class WaystoneManager {

    public static void createWaystone(ItemStack waystone, ItemMeta itemMeta, Player player) {
        itemMeta.setDisplayName("Waystone");

        Location playerLocation = player.getLocation();

        itemMeta.getPersistentDataContainer().set(
                DESTINATIONS_TELEPORT_WAYSTONE_LOCATION_KEY,
                new LocationTagType(),
                playerLocation);



        World playerWorld = playerLocation.getWorld();

        if (playerWorld == null) throw new Error("Unable to fetch world for player");

        ArrayList<String> itemLore = new ArrayList<>();

        itemLore.add(String.format("%s, %s, %s, %s",
                playerLocation.getWorld().getName(),
                playerLocation.getX(),
                playerLocation.getY(),
                playerLocation.getZ()
        ));

        itemMeta.setLore(itemLore);

        waystone.setItemMeta(itemMeta);

        player.sendMessage(String.format("Waystone set to %s, %s, %s, %s",
                playerLocation.getWorld().getName(),
                playerLocation.getX(),
                playerLocation.getY(),
                playerLocation.getZ()
        ));

    }

    public static void useWaystone(ItemMeta waystoneMeta, Player player) {
        player.sendMessage("You used a waystone!");

        Location waystoneLocation = waystoneMeta.getPersistentDataContainer().get(
                DESTINATIONS_TELEPORT_WAYSTONE_LOCATION_KEY,
                new LocationTagType()
        );

        if (waystoneLocation == null) throw new Error("Unable to fetch location for waystone");

        World waystoneWorld = waystoneLocation.getWorld();
        if (waystoneWorld == null) throw new Error("Unable to fetch world for waystone location");

        player.sendMessage(String.format("Waystone is for to %s, %s, %s, %s",
                waystoneWorld.getName(),
                waystoneLocation.getX(),
                waystoneLocation.getY(),
                waystoneLocation.getZ()
        ));

        // Let's teleport!
        player.teleport(waystoneLocation);

    }
}
