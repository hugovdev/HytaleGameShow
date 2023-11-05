package me.hugo.hytalegameshow.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private final HashMap<UUID, PlayerData> playerCache = new HashMap<>();

    public Collection<PlayerData> getOnlineCachedPlayers() {
        return getCachedPlayers().stream().filter(playerData -> {
            Player player = Bukkit.getPlayer(playerData.getPlayerUUID());
            return player != null && player.isOnline();
        }).toList();
    }

    public Collection<PlayerData> getCachedPlayers() {
        return playerCache.values();
    }

    public PlayerData get(Player player) {
        return playerCache.get(player.getUniqueId());
    }

    public PlayerData getOrCreate(Player player) {
        return playerCache.computeIfAbsent(player.getUniqueId(), (uuid -> new PlayerData(player)));
    }

    public void remove(Player player) {
        playerCache.remove(player.getUniqueId());
    }

    public void remove(UUID playerUUID) {
        playerCache.remove(playerUUID);
    }
}
