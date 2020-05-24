package com.drazisil.destinations;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        // Only care about the main hand
        if (!(event.getHand() == EquipmentSlot.HAND)) return;


        Player player = event.getPlayer();
        ItemStack itemUsed = player.getInventory().getItemInMainHand();
        Material itemUsedType = itemUsed.getType();

        // Did player use an emerald?
        if (itemUsedType == Material.EMERALD) {

            player.sendMessage("You used an emerald!");

            event.setCancelled(true);

            // Is this a single emerald?
            if (player.getInventory().getItemInMainHand().getAmount() == 1) {
                // This would either be a waystone, or a fresh emerald

                // Does it have metadata?
                ItemMeta itemMeta = itemUsed.getItemMeta();

                if (!(itemMeta == null)) {

                    // Is it a waystone?
                    if (itemMeta.getDisplayName().equals("Waystone")) {

                        // It's a waystone!
                        player.sendMessage("You used a waystone!");
                        Location waystoneLocation = itemMeta.getPersistentDataContainer().get(
                                DestinationsUtil.DESTINATIONS_TELEPORT_WAYSTONE_LOCATION_KEY,
                                new LocationTagType()
                        );

                        player.sendMessage(String.format("Waystone is for to %s, %s, %s, %s",
                                // TODO: Check for NPE
                                Objects.requireNonNull(Objects.requireNonNull(waystoneLocation).getWorld()).getName(),
                                waystoneLocation.getX(),
                                waystoneLocation.getY(),
                                waystoneLocation.getZ()
                        ));


                        // Let's teleport!
                        player.teleport(waystoneLocation);
                        event.setCancelled(true);
                        return;
                    }

                    // It is not a waystone, make one
                    itemMeta.setDisplayName("Waystone");

                    Location playerLocation = player.getLocation();

                    itemMeta.getPersistentDataContainer().set(
                            DestinationsUtil.DESTINATIONS_TELEPORT_WAYSTONE_LOCATION_KEY,
                            new LocationTagType(),
                            playerLocation);

                    itemUsed.setItemMeta(itemMeta);

                    player.sendMessage(String.format("Waystone set to %s, %s, %s, %s",
                            // TODO: Check for NPE
                            Objects.requireNonNull(playerLocation.getWorld()).getName(),
                            playerLocation.getX(),
                            playerLocation.getY(),
                            playerLocation.getZ()
                    ));

                    event.setCancelled(true);
                }

            }

            // We have more then one emerald in the stack. It's not a waystone, can we make it one?
        }
    }
}
