package me.hugo.hytalegameshow.team;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerTeam {

    public final String name;
    public final TextColor color;

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
