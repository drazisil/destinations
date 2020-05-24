package com.drazisil.destinations;

import com.drazisil.destinations.event.EmeraldEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

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

            EmeraldEvent.handleEmeraldInteractEvent(itemUsed, event);
        }
    }
}
