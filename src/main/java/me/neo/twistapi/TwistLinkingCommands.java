package me.neo.twistapi;


import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Class that contains the built in commands. The only thing you need to do with this class is call the constructor
 * With a string array of Twists
 */
public class TwistLinkingCommands {


    /**
     *
     *
     */
    public TwistLinkingCommands() {
        link();
        unLink();
    }

    // Links the player to their twist
    private void link() {
        new CommandAPICommand("addTwist")
                .withArguments(TwistAPI.arguments)
                .withPermission(CommandPermission.OP)
                .executesPlayer((player, args) -> {
                    Player target = (Player) args[0];
                    String arg = (String) args[1];
                    // Checks if the target already has the twist
                    if (TwistAPI.PlayerLinkedTwists.containsEntry(target, arg)) {player.sendMessage(ChatColor.RED + target.getName() + " already has the " + (String) args[1] +  " twist!"); return;}
                    target.sendMessage(ChatColor.GREEN + "You have been granted the " + arg + " twist!");
                    TwistAPI.PlayerLinkedTwists.put(target, arg);
                    System.out.println(TwistAPI.PlayerLinkedTwists.entries());
                }).register();

    }

    private void unLink() {
        new CommandAPICommand("revokeTwist")
                .withArguments(TwistAPI.arguments)
                .withPermission(CommandPermission.OP)
                .executesPlayer((player, args) -> {
                    Player target = (Player) args[0];
                    String arg = (String) args[1];
                    // Checks if the twist is revoked
                    if (!TwistAPI.PlayerLinkedTwists.containsEntry(target, arg)) {player.sendMessage(ChatColor.RED + target.getName() + " already has the " + (String) args[1] +  " twist revoked"); return;}
                    target.sendMessage(ChatColor.GREEN + "Your " + arg + " twist has been revoked");
                    TwistAPI.PlayerLinkedTwists.remove(target, arg);
                    System.out.println(TwistAPI.PlayerLinkedTwists.entries());
                }).register();

    }

}
