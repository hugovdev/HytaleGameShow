package me.hugo.hytalegameshow.commands;

import me.hugo.hytalegameshow.HytaleGameShow;
import me.hugo.hytalegameshow.team.PlayerTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;

@Command({"gameshow", "gs"})
public class GameShowCommand {

    @DefaultFor({"gameshow", "gs", "gameshow help", "gs help"})
    @Description("Help for the the main STK plugin.")
    private void help(Player sender) {
        sender.sendMessage(Component.text("Hytale Gameshow v" + HytaleGameShow.getInstance().getPluginMeta().getVersion() + " Plugin Help", NamedTextColor.GREEN));
        sender.sendMessage(Component.empty());
        sendHelpLine(sender, "help", "Shows this help message.");
        sendHelpLine(sender, "addplayer", "Adds a player to certain team.");
        sendHelpLine(sender, "removeplayer", "Removes player from their team.");
        sendHelpLine(sender, "addpoints", "Adds certain amount of points to certain team.");
        sendHelpLine(sender, "removepoints", "Removes certain amount of points from certain team.");
    }

    private void sendHelpLine(Player player, String subcommand, String description) {
        player.sendMessage(Component.text("/gs " + subcommand, NamedTextColor.GREEN)
                .append(Component.text(" - ", NamedTextColor.WHITE))
                .append(Component.text(description, NamedTextColor.GRAY)));
    }

    @Subcommand("addplayer")
    @Description("Add a player to certain team.")
    private void addToTeam(Player sender, Player player, PlayerTeam team) {
        HytaleGameShow.getInstance().getPlayerDataManager().getOrCreate(player).setTeam(team);
        sender.sendMessage(Component.text("Set " + player.getName() + "'s team to " + team.name + " team!", NamedTextColor.GREEN));
    }

    @Subcommand("removeplayer")
    @Description("Remove a player from certain team.")
    private void removeFromTeam(Player sender, Player player) {
        HytaleGameShow.getInstance().getPlayerDataManager().getOrCreate(player).setTeam(null);
        sender.sendMessage(Component.text("Reset " + player.getName() + "'s team!", NamedTextColor.GREEN));
    }

    @Subcommand("addpoints")
    @Description("Add points to the team.")
    private void addPoints(Player sender, PlayerTeam team, int points) {
        HytaleGameShow.getInstance().getTeamManager().addPoints(points, team);
        sender.sendMessage(Component.text("Added " + points + " to " + team.name + " team!", NamedTextColor.GREEN));
    }

    @Subcommand("removepoints")
    @Description("Remove points to the team.")
    private void removePoints(Player sender, PlayerTeam team, int points) {
        HytaleGameShow.getInstance().getTeamManager().removePoints(points, team);
        sender.sendMessage(Component.text("Removed " + points + " from " + team.name + " team!", NamedTextColor.GREEN));
    }
}
