package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashSet;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.util.command.AbstractCommand;

import org.bukkit.command.CommandSender;

/**
 * コマンド一覧を表示するコマンドです。
 * @author mozipi
 */
public final class Help extends MHRAbstractCommand {

	public Help(MHRCore mhr, String pattern, String permission, String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		HashSet<AbstractCommand> commandSet = mhr.getCmdExecutor().getCommandSet();
		for (AbstractCommand command : commandSet) {
			sender.sendMessage(command.getUsage(sender));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Help");
	}

}
