package me.emmy.artex.locale;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 23:09
 */
@Getter
public enum Locale {
    SERVER_NAME("server.name"),
    SERVER_REGION("server.region"),

    DISCORD("socials.discord"),
    YOUTUBE("socials.youtube"),
    TWITTER("socials.twitter"),
    WEBSITE("socials.website"),
    TIKTOK("socials.tiktok"),
    STORE("socials.store"),
    GITHUB("socials.github"),

    TEAMSPEAK("socials.teamspeak"),
    FACEBOOK("socials.facebook"),
    INSTAGRAM("socials.instagram"),

    ;

    private final String string;

    Locale(String string) {
        this.string = Artex.getInstance().getConfig().getString(string);
    }
}