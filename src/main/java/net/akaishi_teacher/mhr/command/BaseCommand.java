package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * コマンドを作る時にベースとなるクラス
 * <p/>
 * 全ての実行可能なコマンドはこのクラスを継承する
 *
 * @author ilicodaisuki
 * @version 0.1.0
 */
public class BaseCommand extends CommandComponent {
	protected String description = "";
	protected String argsFormat = "";
	protected String permission = "";

	public BaseCommand(MineHorseRacingPlugin plugin, String label) {
		super(plugin, label);
	}

	public String getDescription() {
		return this.description;
	}

	public String getArgsFormat() {
		return this.argsFormat;
	}

	public String getPermission() {
		return this.permission;
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		if (sender instanceof Player) {
			return onCommand((Player) sender, args);
		} else if (sender instanceof ConsoleCommandSender) {
			return onCommand((ConsoleCommandSender) sender, args);
		} else if (sender instanceof RemoteConsoleCommandSender) {
			return onCommand((RemoteConsoleCommandSender) sender, args);
		} else if (sender instanceof BlockCommandSender) {
			return onCommand((BlockCommandSender) sender, args);
		}
		return false;
	}

	@Override
	public void collectUsages(CommandSender sender, ArrayList<String> labels, ArrayList<String> usages) {
		if (!(sender instanceof Player) || this.hasPermission((Player) sender)) {
			StringBuilder cmd = new StringBuilder();
			cmd.append("/");
			labels.add(this.label);
			for (String label : labels) {
				cmd.append(label).append(" ");
			}
			labels.remove(labels.lastIndexOf(this.label));
			cmd.append(this.argsFormat);

			usages.add(cmd.toString());
		}
	}

	protected boolean onCommand(Player sender, ArrayList<String> args) {
		return false;
	}

	protected boolean onCommand(ConsoleCommandSender sender, ArrayList<String> args) {
		return false;
	}

	protected boolean onCommand(RemoteConsoleCommandSender sender, ArrayList<String> args) {
		return false;
	}

	protected boolean onCommand(BlockCommandSender sender, ArrayList<String> args) {
		return false;
	}

	protected boolean hasPermission(Player player) {
		return player.hasPermission(this.permission);
	}
}
