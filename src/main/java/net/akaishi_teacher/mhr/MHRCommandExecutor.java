package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.command.CommandComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * コマンド実行クラス
 *
 * @author ilicodaisuki
 * @version 0.1.0
 */
public final class MHRCommandExecutor implements CommandExecutor {
	private final MineHorseRacingPlugin plugin;
	private final ArrayList<CommandComponent> commands = new ArrayList<>();

	public MHRCommandExecutor(MineHorseRacingPlugin plugin) {
		this.plugin = plugin;
	}

	public MHRCommandExecutor addCommand(CommandComponent command) {
		this.commands.add(command);
		return this;
	}

	public boolean showUsage(CommandSender sender, String label) {
		ArrayList<String> labels = new ArrayList<>();
		labels.add(label);

		// senderが使えるコマンドの使い方を収集
		ArrayList<String> usages = new ArrayList<>();
		for (CommandComponent cmd : this.commands) {
			cmd.collectUsages(sender, labels, usages);
		}

		// 使い方を送信
		sender.sendMessage("===== " + this.plugin.getName() + " =====");
		for (String usage : usages) {
			sender.sendMessage(usage);
		}
		sender.sendMessage("==============================");

		return true;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (args.length >= 1) {
			ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(args));
			String newCommand = newArgs.remove(0);

			for (CommandComponent cmd : this.commands) {
				if (cmd.getLabel().equals(newCommand)) {
					if (cmd.execute(commandSender, newArgs)) return true;
				}
			}
		}
		return showUsage(commandSender, label);
	}
}
