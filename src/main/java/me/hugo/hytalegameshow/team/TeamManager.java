package me.hugo.hytalegameshow.team;

import me.hugo.hytalegameshow.HytaleGameShow;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class TeamManager {

    private final Map<String, PlayerTeam> teams = new HashMap<>();

    public TeamManager() {
        FileConfiguration config = HytaleGameShow.getInstance().getConfig();

        ConfigurationSection teamsSection = config.getConfigurationSection("teams");

        if (teamsSection != null) {
            teamsSection.getKeys(false).forEach(teamId -> {
                String path = "teams." + teamId;

                teams.put(teamId, createTeam(config.getString(path + ".name", "NoName"),
                        TextColor.fromHexString(config.getString(path + ".color", "#FFFFF"))));
            });
        }
    }

    public Collection<PlayerTeam> getTeams() {
        return teams.values();
    }

    public Collection<String> getTeamIds() {
        return teams.keySet();
    }

    public PlayerTeam getTeamById(String teamId) {
        return teams.get(teamId);
    }

    public PlayerTeam createTeam(String name, TextColor color) {
        return new PlayerTeam(name, color);
    }

    public int getPoints(PlayerTeam team) {
        return team.getPoints();
    }

    public void addPoints(int points, PlayerTeam team) {
        team.setPoints(team.getPoints() + points);
    }

    public void removePoints(int points, PlayerTeam team) {
        int finalPoints = Math.max(team.getPoints() - points, 0);
        team.setPoints(finalPoints);
    }

    public void resetPoints(PlayerTeam team) {
        team.setPoints(0);
    }
}
