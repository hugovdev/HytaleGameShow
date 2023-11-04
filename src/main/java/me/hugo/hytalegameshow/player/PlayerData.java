package me.hugo.hytalegameshow.player;

import fr.mrmicky.fastboard.adventure.FastBoard;
import me.hugo.hytalegameshow.team.PlayerTeam;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {

    private final UUID playerUUID;
    private final FastBoard board;

    private PlayerTeam team;

    public PlayerData(Player player) {
        this.playerUUID = player.getUniqueId();
        this.board = new FastBoard(player);
    }

    public FastBoard getBoard() {
        return board;
    }

    public PlayerTeam getTeam() {
        return team;
    }

    public void setTeam(PlayerTeam team) {
        if (this.team != null) {
            this.team.removePlayer(playerUUID);
        }

        this.team = team;
        if (team != null) team.addPlayer(playerUUID);
    }
}
