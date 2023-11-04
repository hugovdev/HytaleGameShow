package me.hugo.hytalegameshow.commands;

import me.hugo.hytalegameshow.HytaleGameShow;
import me.hugo.hytalegameshow.team.PlayerTeam;
import net.kyori.adventure.text.Component;
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

    }

    @Subcommand("addplayer")
    @Description("Add a player to certain team.")
    private void addToTeam(Player sender, Player player, PlayerTeam team) {
        HytaleGameShow.getInstance().getPlayerDataManager().getOrCreate(player).setTeam(team);
        sender.sendMessage(Component.text("Set " + player.name() + "'s team to " + team.name + " team!"));
    }

    @Subcommand("removeplayer")
    @Description("Remove a player from certain team.")
    private void removeFromTeam(Player sender, Player player, PlayerTeam team) {
        HytaleGameShow.getInstance().getPlayerDataManager().getOrCreate(player).setTeam(null);
        sender.sendMessage(Component.text("Reset " + player.name() + "'s team!"));
    }

    @Subcommand("addpoints")
    @Description("Add points to the team.")
    private void addPoints(Player sender, PlayerTeam team, int points) {
        HytaleGameShow.getInstance().getTeamManager().addPoints(points, team);
        sender.sendMessage(Component.text("Added " + points + " to " + team.name + " team!"));
    }

    @Subcommand("removepoints")
    @Description("Remove points to the team.")
    private void removePoints(Player sender, PlayerTeam team, int points) {
        HytaleGameShow.getInstance().getTeamManager().removePoints(points, team);
        sender.sendMessage(Component.text("Removed " + points + " from " + team.name + " team!"));
    }
}
