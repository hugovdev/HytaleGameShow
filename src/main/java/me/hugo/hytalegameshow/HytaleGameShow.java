package me.hugo.hytalegameshow;

import me.hugo.hytalegameshow.commands.GameShowCommand;
import me.hugo.hytalegameshow.listener.PlayerJoinLeave;
import me.hugo.hytalegameshow.player.PlayerDataManager;
import me.hugo.hytalegameshow.team.PlayerTeam;
import me.hugo.hytalegameshow.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.exception.CommandErrorException;

public final class HytaleGameShow extends JavaPlugin {

    private final PlayerDataManager playerDataManager = new PlayerDataManager();

    private TeamManager teamManager;

    private static HytaleGameShow instance;
    public static boolean IS_ENABLED = false;

    private BukkitCommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        teamManager = new TeamManager();

        commandHandler = BukkitCommandHandler.create(this);

        commandHandler.getAutoCompleter().registerParameterSuggestions(PlayerTeam.class,
                (SuggestionProvider.of(teamManager.getTeamIds())));

        commandHandler.registerValueResolver(PlayerTeam.class, (context -> teamManager.getTeamById(context.pop())));

        commandHandler.registerParameterValidator(PlayerTeam.class, ((value, parameter, actor) -> {
            if (value == null) {
                throw new CommandErrorException("This team doesn't exist!");
            }
        }));

        commandHandler.register(new GameShowCommand());
        commandHandler.enableAdventure();
        commandHandler.registerBrigadier();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinLeave(playerDataManager), this);
    }

    @Override
    public void onDisable() {
        commandHandler.unregisterAllCommands();
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public static HytaleGameShow getInstance() {
        return instance;
    }
}
