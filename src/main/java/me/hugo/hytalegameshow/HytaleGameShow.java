package me.hugo.hytalegameshow;

import me.hugo.hytalegameshow.commands.GameShowCommand;
import me.hugo.hytalegameshow.player.PlayerDataManager;
import me.hugo.hytalegameshow.team.PlayerTeam;
import me.hugo.hytalegameshow.team.TeamManager;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.exception.CommandErrorException;

import java.util.Optional;

public final class HytaleGameShow extends JavaPlugin {

    private final TeamManager teamManager = new TeamManager();
    private final PlayerDataManager playerDataManager = new PlayerDataManager();

    private static HytaleGameShow instance;

    private BukkitCommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;

        commandHandler = BukkitCommandHandler.create(this);

        commandHandler.getAutoCompleter().registerParameterSuggestions(PlayerTeam.class,
                (SuggestionProvider.of(teamManager.getTeams().stream().map(team -> team.name).toList())));

        commandHandler.registerValueResolver(PlayerTeam.class, (context -> {
            Optional<PlayerTeam> team = teamManager.getTeams().stream()
                    .filter(currentTeam -> currentTeam.name.equalsIgnoreCase(context.pop()))
                    .findFirst();

            return team.orElse(null);
        }));

        commandHandler.registerParameterValidator(PlayerTeam.class, ((value, parameter, actor) -> {
            if (value == null) {
                throw new CommandErrorException("This map doesn't exist!");
            }
        }));

        commandHandler.register(new GameShowCommand());
        commandHandler.registerBrigadier();
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
