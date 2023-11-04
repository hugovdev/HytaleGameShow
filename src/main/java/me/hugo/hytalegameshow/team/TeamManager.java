package me.hugo.hytalegameshow.team;

import me.hugo.hytalegameshow.HytaleGameShow;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamManager {

    private final HashMap<PlayerTeam, Integer> teamPoints = new HashMap<>();
    private final List<PlayerTeam> teams = new ArrayList<>();

    public TeamManager() {
        HytaleGameShow.getInstance().saveDefaultConfig();

        teams.add(readTeam("teams.1"));
        teams.add(readTeam("teams.2"));
    }

    public List<PlayerTeam> getTeams() {
        return teams;
    }

    public PlayerTeam createTeam(String name, TextColor color) {
        PlayerTeam team = new PlayerTeam(name, color);
        teams.add(team);

        return team;
    }

    public int getPoints(PlayerTeam team) {
        return teamPoints.getOrDefault(team, 0);
    }

    public void addPoints(int points, PlayerTeam team) {
        teamPoints.put(team, teamPoints.getOrDefault(team, 0) + points);
    }

    public void removePoints(int points, PlayerTeam team) {
        teamPoints.put(team, Math.max(teamPoints.getOrDefault(team, 0) - points, 0));
    }

    public void resetPoints(PlayerTeam team) {
        teamPoints.remove(team);
    }

    private PlayerTeam readTeam(String path) {
        FileConfiguration config = HytaleGameShow.getInstance().getConfig();

        return createTeam(config.getString(path + ".name", "NoName"),
                TextColor.fromHexString(config.getString(path + ".color", "#FFFFF")));
    }
}
