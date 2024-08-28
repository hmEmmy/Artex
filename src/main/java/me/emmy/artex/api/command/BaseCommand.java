package me.emmy.artex.api.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.api.command.annotation.Completer;
import me.emmy.artex.util.CC;

/**
 * @author minnymin3
 */
public abstract class BaseCommand {

    public Artex main = Artex.getInstance();

    public BaseCommand() {
        main.getCommandFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
