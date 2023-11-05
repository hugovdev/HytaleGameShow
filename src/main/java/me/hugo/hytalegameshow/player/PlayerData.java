package me.hugo.hytalegameshow.player;

import fr.mrmicky.fastboard.adventure.FastBoard;
import me.hugo.hytalegameshow.HytaleGameShow;
import me.hugo.hytalegameshow.team.PlayerTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID playerUUID;
    private FastBoard board;

    private PlayerTeam team;

    public PlayerData(Player player) {
        this.playerUUID = player.getUniqueId();
    }

    public void startScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        this.board = new FastBoard(player);

        board.updateTitle(Component.text("THANKMAS 2023", NamedTextColor.AQUA, TextDecoration.BOLD));

        printScoreboard();
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public FastBoard getBoard() {
        return board;
    }

    public PlayerTeam getTeam() {
        return team;
    }

    public void printScoreboard() {
        List<Component> lines = new ArrayList<>();

        lines.add(Component.text("Hytale Gameshow", NamedTextColor.GRAY));

        HytaleGameShow.getInstance().getTeamManager().getTeams().forEach(team -> {
            boolean isTeammed = this.team == team;

            lines.add(Component.empty());
            lines.add(Component.text(team.name, team.color).decoration(TextDecoration.BOLD, isTeammed));
            lines.add(Component.text(isTeammed ? "Your Points: " : "Points: ").append(Component.text(team.getPoints(), NamedTextColor.YELLOW)));
        });

        lines.add(Component.empty());
        lines.add(Component.text("thankmas.skynode.pro", NamedTextColor.YELLOW));

        board.updateLines(lines);
    }

    public void setTeam(PlayerTeam team) {
        if (this.team != null) {
            this.team.removePlayer(playerUUID);
        }

        this.team = team;
        if (team != null) team.addPlayer(playerUUID);

        printScoreboard();
    }

    public void setBoard(FastBoard board) {
        this.board = board;
    }
}
