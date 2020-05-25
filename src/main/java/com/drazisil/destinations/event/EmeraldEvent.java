package com.drazisil.destinations.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.drazisil.destinations.DestinationsUtil.DESTINATIONS_WAYSTONE_CREATE_PERM;
import static com.drazisil.destinations.DestinationsUtil.DESTINATIONS_WAYSTONE_USE_PERM;
import static com.drazisil.destinations.WaystoneManager.createWaystone;
import static com.drazisil.destinations.WaystoneManager.useWaystone;

public class EmeraldEvent {

    public static void handleEmeraldInteractEvent(ItemStack emerald, PlayerInteractEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("You used an emerald!");

        // Is this a single emerald?
        if (player.getInventory().getItemInMainHand().getAmount() == 1) {
            // This would either be a waystone, or a fresh emerald

            // Does it have metadata?
            ItemMeta emeraldMeta = emerald.getItemMeta();

            if (!(emeraldMeta == null)) {

                // Is it a waystone?
                if (emeraldMeta.getDisplayName().equals("Waystone")) {

                    // It's a waystone!

                    // Is player attempting to rename?
                    Block clickedBlock = event.getClickedBlock();


//                    if (clickedBlock != null && clickedBlock.getType() == Material.ANVIL) {
//                        event.setCancelled(true);
//                        player.sendMessage("You are trying to use an anvil...");
//                        return;
//                    }


                    if (player.hasPermission(DESTINATIONS_WAYSTONE_USE_PERM)) {
                        event.setCancelled(true);
                        useWaystone(emeraldMeta, player);
                        return;
                    }
                }

                // It is not a waystone, make one if player has permission
                event.setCancelled(true);
                if (player.hasPermission(DESTINATIONS_WAYSTONE_CREATE_PERM)) {
                    event.setCancelled(true);
                    createWaystone(emerald, emeraldMeta, player);
                    return;
                }

            }

        }

        // We have more then one emerald in the stack. It's not a waystone, can we make it one?
        player.sendMessage("Please try a single emerald if you want to make a waystone.");
    }
}
