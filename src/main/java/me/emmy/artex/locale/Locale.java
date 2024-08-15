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

    ;

    private String string;

    Locale(String string) {
        this.string = Artex.getInstance().getConfig().getString(string);
    }
}
