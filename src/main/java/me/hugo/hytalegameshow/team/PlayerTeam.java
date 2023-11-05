package me.hugo.hytalegameshow.team;

import me.hugo.hytalegameshow.HytaleGameShow;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerTeam {

    public final String name;
    public final TextColor color;

    private int points = 0;

    private final Set<UUID> players = new HashSet<>();

    public PlayerTeam(String name, TextColor color) {
        this.name = name;
        this.color = color;
    }

    public Set<Player> getOnlinePlayers() {
        return players.stream().map((Bukkit::getPlayer))
                .filter((player -> player != null && player.isOnline()))
                .collect(Collectors.toSet());
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        List<PlayerTeam> teams = new ArrayList<>(HytaleGameShow.getInstance().getTeamManager().getTeams());
        int teamIndex = teams.indexOf(this);

        HytaleGameShow.getInstance().getPlayerDataManager().getOnlineCachedPlayers()
                .stream().filter(playerData -> playerData.getBoard() != null)
                .forEach(onlinePlayer -> {
                    boolean isTeammed = onlinePlayer.getTeam() == this;
                    onlinePlayer.getBoard().updateLine((3 + (3 * teamIndex)), Component.text(isTeammed ? "Your Points: " : "Points: ").append(Component.text(points, NamedTextColor.YELLOW)));
                });
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        removePlayer(player.getUniqueId());
    }

    public void removePlayer(UUID playerUUID) {
        players.remove(playerUUID);
    }

    public void addPlayer(Player player) {
        addPlayer(player.getUniqueId());
    }

    public void addPlayer(UUID playerUUID) {
        players.add(playerUUID);
    }

}
