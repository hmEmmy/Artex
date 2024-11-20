package me.emmy.artex.command;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.chat.command.MuteChatCommand;
import me.emmy.artex.chat.command.UnMuteChatCommand;
import me.emmy.artex.command.impl.ArtexCommand;
import me.emmy.artex.command.impl.admin.ReloadCommand;
import me.emmy.artex.command.impl.admin.essential.*;
import me.emmy.artex.command.impl.admin.gamemode.AdventureCommand;
import me.emmy.artex.command.impl.admin.gamemode.CreativeCommand;
import me.emmy.artex.command.impl.admin.gamemode.SpectatorCommand;
import me.emmy.artex.command.impl.admin.gamemode.SurvivalCommand;
import me.emmy.artex.command.impl.admin.server.AlertCommand;
import me.emmy.artex.command.impl.admin.server.BroadcastCommand;
import me.emmy.artex.command.impl.admin.troll.FakeOpCommand;
import me.emmy.artex.command.impl.admin.troll.LaunchCommand;
import me.emmy.artex.command.impl.admin.troll.TrollCommand;
import me.emmy.artex.command.impl.donator.MediaBroadcastCommand;
import me.emmy.artex.command.impl.user.JoinCommand;
import me.emmy.artex.command.impl.user.ListCommand;
import me.emmy.artex.command.impl.user.PingCommand;
import me.emmy.artex.conversation.command.MessageCommand;
import me.emmy.artex.conversation.command.ReplyCommand;
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
import me.emmy.artex.tag.command.admin.TagAdminCommand;
import me.emmy.artex.tag.command.admin.impl.*;
import me.emmy.artex.util.Logger;
import me.emmy.artex.util.ProjectInfo;
import org.bukkit.Bukkit;

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
        registerDonatorCommands();
    }

    private void registerChatCommands() {
        new MuteChatCommand();
        new UnMuteChatCommand();
    }

    public void registerEssentialCommands() {
        if (Artex.getInstance().getConfig().getBoolean("conversation.enabled")) {
            new MessageCommand();
            new ReplyCommand();
            new PingCommand();
        }

        new AdventureCommand();
        new CreativeCommand();
        new SpectatorCommand();
        new SurvivalCommand();

        new AlertCommand();
        new BroadcastCommand();

        new JoinCommand();
        new ListCommand();

        new ArtexCommand();

        new InstanceCommand();

        new FakeOpCommand();
        new LaunchCommand();
        new TrollCommand();

        new CraftCommand();
        new EnchantCommand();
        new FlyCommand();
        new HealCommand();
        new RenameCommand();
        new RepairCommand();

        new GodModeCommand();

        new SetJoinLocationCommand();
        new TeleportToSpawnCommand();

        new ReloadCommand();
    }

    public void registerGrantCommands() {
        new GrantCommand();
        new GrantsCommand();
        new RemoveRankCommand();
        new SetRankCommand();
    }

    public void registerRankCommands() {
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

        new RankHelpCommand();

        new CreateDefaultRanksCommand();
    }

    public void registerTagCommands() {
        new TagCommand();
        new TagAdminCommand();
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

    private void registerDonatorCommands() {
        new MediaBroadcastCommand();
    }
}
