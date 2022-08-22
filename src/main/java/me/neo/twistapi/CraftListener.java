package me.neo.twistapi;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.Collection;

/**
 * The class that checks if a player can craft an item based on the items twist and the player's twist (if any)
 */
public class CraftListener implements Listener {

    /**
     * Checks if the player is allowed to craft the item.
     * Will not affect vanilla items
     * @param event CraftItemEvent
     */
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        // Checks if the items hashmap contains the given itemstack
        if (TwistAPI.items.containsKey(event.getRecipe().getResult())) {
            // If it does then it will check if the player has the twist linked with the itemstack
            checks(event, TwistAPI.items.get(event.getRecipe().getResult()));
        }
    }

    private void checks(CraftItemEvent event, String twist) {
        Player player = (Player) event.getWhoClicked();
        // If the player has no twists
        if (!TwistAPI.PlayerLinkedTwists.containsKey(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You dont have a twist");
            return;
        }
        // IF the player has a twist but not the correct one
        if (!(TwistAPI.PlayerLinkedTwists.containsEntry(player, twist))) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You dont have access to this recipe!");
            player.sendMessage(ChatColor.RED + "Your twist: " + TwistAPI.PlayerLinkedTwists.asMap().get(player) + " You need " + twist);
        }
    }
}
