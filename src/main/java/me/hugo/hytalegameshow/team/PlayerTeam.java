package me.hugo.hytalegameshow.player;

import net.kyori.adventure.text.format.NamedTextColor;

public class PlayerTeam {

    private final String name;
    private final NamedTextColor color;

    public PlayerTeam(String name, NamedTextColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public NamedTextColor getColor() {
        return color;
    }
}
