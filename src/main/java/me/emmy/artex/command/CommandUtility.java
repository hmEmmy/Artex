package me.emmy.artex.command;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.chat.command.MuteChatCommand;
import me.emmy.artex.chat.command.UnMuteChatCommand;
import me.emmy.artex.command.impl.ArtexCommand;
import me.emmy.artex.command.impl.admin.ReloadCommand;
import me.emmy.artex.command.impl.admin.essential.CraftCommand;
import me.emmy.artex.command.impl.admin.essential.FlyCommand;
import me.emmy.artex.command.impl.admin.essential.HealCommand;
import me.emmy.artex.command.impl.admin.gamemode.AdventureCommand;
import me.emmy.artex.command.impl.admin.gamemode.CreativeCommand;
import me.emmy.artex.command.impl.admin.gamemode.SpectatorCommand;
import me.emmy.artex.command.impl.admin.gamemode.SurvivalCommand;
import me.emmy.artex.command.impl.admin.troll.FakeOpCommand;
import me.emmy.artex.command.impl.admin.troll.LaunchCommand;
import me.emmy.artex.command.impl.admin.troll.TrollCommand;
import me.emmy.artex.command.impl.user.ListCommand;
import me.emmy.artex.godmode.command.GodModeCommand;
import me.emmy.artex.grant.command.GrantCommand;
import me.emmy.artex.grant.command.GrantsCommand;
import me.emmy.artex.grant.command.RemoveRankCommand;
import me.emmy.artex.grant.command.SetRankCommand;
import me.emmy.artex.instance.command.InstanceCommand;
import me.emmy.artex.rank.command.RankCommand;
import me.emmy.artex.rank.command.impl.*;
import me.emmy.artex.rank.utility.command.CreateDefaultRanksCommand;
import me.emmy.artex.spawn.command.SetJoinLocationCommand;
import me.emmy.artex.spawn.command.TeleportToSpawnCommand;
import me.emmy.artex.tag.command.TagCommand;
import me.emmy.artex.tag.command.admin.impl.*;
import me.emmy.artex.util.Logger;
import me.emmy.artex.util.ProjectInfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

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

        new FakeOpCommand();
        new LaunchCommand();
        new TrollCommand();

        new CraftCommand();
        new HealCommand();
        new FlyCommand();

        new GodModeCommand();

        new SetJoinLocationCommand();
        new TeleportToSpawnCommand();

        new ReloadCommand();
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

        if (!ProjectInfo.AUTHOR.equals("Emmy")) Bukkit.shutdown();
    }
}
