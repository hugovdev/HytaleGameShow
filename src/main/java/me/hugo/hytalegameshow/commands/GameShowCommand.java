package me.hugo.hytalegameshow.commands;

import me.hugo.hytalegameshow.HytaleGameShow;
import me.hugo.hytalegameshow.player.PlayerData;
import me.hugo.hytalegameshow.team.PlayerTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.EntitySelector;
import revxrsal.commands.bukkit.core.BukkitCommandExecutor;

import java.util.ArrayList;

@Command({"gameshow", "gs"})
public class GameShowCommand {

    @DefaultFor({"gameshow", "gs", "gameshow help", "gs help"})
    @Description("Help for the the main STK plugin.")
    private void help(BukkitCommandActor sender) {
        sender.reply(Component.text("Hytale Gameshow v" + HytaleGameShow.getInstance().getPluginMeta().getVersion() + " Plugin Help", NamedTextColor.GREEN));
        sender.reply(Component.empty());
        sendHelpLine(sender, "help", "Shows this help message.");
        sendHelpLine(sender, "addplayer", "Adds a player to certain team.");
        sendHelpLine(sender, "removeplayer", "Removes player from their team.");
        sendHelpLine(sender, "addpoints", "Adds certain amount of points to certain team.");
        sendHelpLine(sender, "removepoints", "Removes certain amount of points from certain team.");
        sender.reply(Component.empty());
        sendHelpLine(sender, "enable", "Enables the scoreboard system.");
        sendHelpLine(sender, "disable", "Disables the scoreboard system.");
    }

    private void sendHelpLine(BukkitCommandActor player, String subcommand, String description) {
        player.reply(Component.text("/gs " + subcommand, NamedTextColor.GREEN)
                .append(Component.text(" - ", NamedTextColor.WHITE))
                .append(Component.text(description, NamedTextColor.GRAY)));
    }

    @Subcommand("enable")
    @Description("Enables the scoreboard system.")
    private void enable(BukkitCommandActor sender) {
        HytaleGameShow.IS_ENABLED = true;
        HytaleGameShow.getInstance().getPlayerDataManager().getOnlineCachedPlayers().forEach(PlayerData::printScoreboard);
        sender.reply(Component.text("Enabled scoreboard!", NamedTextColor.GREEN));
    }

    @Subcommand("disable")
    @Description("Disables the scoreboard system.")
    private void disable(BukkitCommandActor sender) {
        HytaleGameShow.IS_ENABLED = false;

        HytaleGameShow.getInstance().getPlayerDataManager().getOnlineCachedPlayers().forEach(player -> player.getBoard().delete());
        sender.reply(Component.text("Disabled scoreboard!", NamedTextColor.GREEN));
    }

    @Subcommand("addplayer")
    @Description("Add a player to certain team.")
    private void addToTeam(BukkitCommandActor sender, PlayerTeam team, EntitySelector<Player> players) {
        players.forEach(player -> {
            HytaleGameShow.getInstance().getPlayerDataManager().getOrCreate(player).setTeam(team);
            sender.reply(Component.text("Set " + player.getName() + "'s team to " + team.name + " team!", NamedTextColor.GREEN));
        });
    }

    @Subcommand("removeplayer")
    @Description("Remove a player from certain team.")
    private void removeFromTeam(BukkitCommandActor sender, EntitySelector<Player> players) {
        players.forEach(player -> {
            HytaleGameShow.getInstance().getPlayerDataManager().getOrCreate(player).setTeam(null);
            sender.reply(Component.text("Reset " + player.getName() + "'s team!", NamedTextColor.GREEN));
        });
    }

    @Subcommand("addpoints")
    @Description("Add points to the team.")
    private void addPoints(BukkitCommandActor sender, PlayerTeam team, int points) {
        HytaleGameShow.getInstance().getTeamManager().addPoints(points, team);
        sender.reply(Component.text("Added " + points + " to " + team.name + " team!", NamedTextColor.GREEN));
    }

    @Subcommand("removepoints")
    @Description("Remove points to the team.")
    private void removePoints(BukkitCommandActor sender, PlayerTeam team, int points) {
        HytaleGameShow.getInstance().getTeamManager().removePoints(points, team);
        sender.reply(Component.text("Removed " + points + " from " + team.name + " team!", NamedTextColor.GREEN));
    }
}
