package me.emmy.artex.command;

import lombok.experimental.UtilityClass;
import me.emmy.artex.chat.command.MuteChatCommand;
import me.emmy.artex.chat.command.UnMuteChatCommand;
import me.emmy.artex.command.impl.ArtexCommand;
import me.emmy.artex.command.impl.gamemode.AdventureCommand;
import me.emmy.artex.command.impl.gamemode.CreativeCommand;
import me.emmy.artex.command.impl.gamemode.SpectatorCommand;
import me.emmy.artex.command.impl.gamemode.SurvivalCommand;
import me.emmy.artex.command.impl.server.ListCommand;
import me.emmy.artex.grant.command.GrantCommand;
import me.emmy.artex.grant.command.GrantsCommand;
import me.emmy.artex.grant.command.RemoveRankCommand;
import me.emmy.artex.grant.command.SetRankCommand;
import me.emmy.artex.instance.command.InstanceCommand;
import me.emmy.artex.rank.command.RankCommand;
import me.emmy.artex.rank.command.impl.*;
import me.emmy.artex.rank.utility.command.CreateDefaultRanksCommand;
import me.emmy.artex.tag.command.TagCommand;
import me.emmy.artex.tag.command.admin.impl.*;
import me.emmy.artex.util.Logger;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:13
 */
@UtilityClass
public class CommandUtility {

    /**
     * Register all commands based on their category.
     */
    public void registerCommands() {
        registerChatCommands();
        registerEssentialCommands();
        registerGrantCommands();
        registerRankCommands();
        registerTagCommands();
    }

    private void registerChatCommands() {
        Logger.debug("Registering chat commands...");

        new MuteChatCommand();
        new UnMuteChatCommand();
    }

    public void registerEssentialCommands() {
        Logger.debug("Registering essential commands...");

        new AdventureCommand();
        new CreativeCommand();
        new SpectatorCommand();
        new SurvivalCommand();

        new ListCommand();

        new ArtexCommand();

        new InstanceCommand();
    }

    public void registerGrantCommands() {
        Logger.debug("Registering grant commands...");

        new GrantCommand();
        new GrantsCommand();
        new RemoveRankCommand();
        new SetRankCommand();
    }

    public void registerRankCommands() {
        Logger.debug("Registering rank commands...");

        new RankCommand();

        new RankAddPermissionCommand();
        new RankCreateCommand();
        new RankDeleteCommand();
        new RankInfoCommand();
        new RankListCommand();
        new RankSetBoldCommand();
        new RankSetColorCommand();
        new RankSetDefaultCommand();
        new RankSetItalicCommand();
        new RankSetPrefixCommand();
        new RankSetSuffixCommand();
        new RankSetWeightCommand();

        new CreateDefaultRanksCommand();
    }

    public void registerTagCommands() {
        Logger.debug("Registering tag commands...");

        new TagCommand();
        new TagAdminCreateCommand();
        new TagAdminDeleteCommand();
        new TagAdminInfoCommand();
        new TagAdminListCommand();
        new TagAdminSetBoldCommand();
        new TagAdminSetColorCommand();
        new TagAdminSetDisplayNameCommand();
        new TagAdminSetIconCommand();
        new TagAdminSetItalicCommand();
    }
}
