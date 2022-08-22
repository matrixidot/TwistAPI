package me.neo.twistapi;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the plugin.
 * Contains the hashmaps required for keeping track of player, twist and item, twist pairs
 */
public class TwistAPI extends JavaPlugin {
    /**
     * A multimap to keep track of players and their twists
     * Running a command automatically adds a twist and Item to this map
     * DO NOT manually change this
     */
    public static Multimap<Player, String> PlayerLinkedTwists = HashMultimap.create();
    /**
     * A hashmap that links items to their respective twist
     * ItemBuilder takes care of this but you can add it manually with
     * Neos_Scenarios.items.put(ItemStack, String)
     */
    public static Multimap<ItemStack, String> items = HashMultimap.create();
    /**
     *
     */
    public static List<Argument<?>> arguments = new ArrayList<>();
    /**
     * Method to load CommandAPI's config
     */
    @Override
    public void onLoad(){
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(true));
    }

    /**
     * Enabling of API logic
     */
    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);
        Bukkit.getServer().getLogger().info("Manhunt Twists plugin started.");
        Bukkit.getServer().getPluginManager().registerEvents(new CraftListener(), this);
    }

    /**
     * Literally nothing :D
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void addArguments(String[] args) {
        // Twist argument
        arguments.add(new StringArgument("Twist")
                // Replaces suggestions with predetermined ones. When hovering over these it shows the below information
                .replaceSuggestions(ArgumentSuggestions.strings(args)));
    }
}
