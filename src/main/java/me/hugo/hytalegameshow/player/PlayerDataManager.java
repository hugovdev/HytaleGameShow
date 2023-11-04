package me.hugo.hytalegameshow.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private final HashMap<UUID, PlayerData> playerCache = new HashMap<>();

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
