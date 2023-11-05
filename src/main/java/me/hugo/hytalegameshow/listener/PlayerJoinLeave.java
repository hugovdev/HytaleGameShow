package me.hugo.hytalegameshow.listener;

import me.hugo.hytalegameshow.player.PlayerData;
import me.hugo.hytalegameshow.player.PlayerDataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerJoinLeave(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerDataManager.getOrCreate(player).startScoreboard(player);
        event.joinMessage(player.name().color(NamedTextColor.YELLOW).append(Component.text(" joined the Hytale Gameshow.")));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = playerDataManager.get(player);

        if (playerData != null) {
            if (playerData.getTeam() == null) playerDataManager.remove(player);
            else playerData.setBoard(null);
        }

        event.quitMessage(player.name().color(NamedTextColor.YELLOW).append(Component.text(" left the Hytale Gameshow.")));
    }

}
