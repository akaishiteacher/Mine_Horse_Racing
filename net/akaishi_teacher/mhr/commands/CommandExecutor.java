package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import net.akaishi_teacher.mhr.MHR;

import org.bukkit.command.CommandSender;

/**
 * コマンドを実行するためのクラス
 * @author mozipi
 */
public class CommandExecutor {

	protected MHR plugin;

	protected HashSet<AbstractCommand> commandSet =
			new HashSet<AbstractCommand>();

	public CommandExecutor(MHR plugin) {
		super();
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, String[] args) {
		for (AbstractCommand command : commandSet) {
			if (args.length != 0 && command.isNoLabel() == 1) {
				if (command.isLabelMatch(args[0])) {
					if (permissionCheck(sender, command.getPermission())) {
						ArrayList<String> argList = getArgs(args);
						if (command.getNeedArgsLength() <= argList.size()) {
							return command.execute(sender, argList);
						} else {
							sender.sendMessage(command.description);
						}
					} else {
						sender.sendMessage("§4You don't have permission! (" + command.getPermission() + ")");
						return true;
					}
				}
			} else {
				if (args.length == 0 && command.isNoLabel() == 0) {
					if (permissionCheck(sender, command.getPermission())) {
						return command.execute(sender, null);
					} else {
						sender.sendMessage("§4You don't have permission! (" + command.getPermission() + ")");
						return true;
					}
				}
			}
		}
		return true;
	}

	/**
	 * コマンドを追加します。
	 * @param command 追加したい{@link AbstractCommand}のインスタンス
	 * @return 既にコマンドが追加されている場合はfalse。コマンド追加ができた場合はtrue
	 */
	public boolean addCommand(AbstractCommand command) {
		return commandSet.add(command);
	}

	/**
	 * パーミッションをチェックします
	 * @param sender CommandSender
	 * @param permission Permission
	 * @return senderがパーミッションを持っている場合はtrue。持っていなければfalse
	 */
	public boolean permissionCheck(CommandSender sender, String permission) {
		return permission == null || sender.hasPermission(permission) ? true : false;
	}

	public static ArrayList<String> getArgs(String[] args) {
		args[0] = null;
		ArrayList<String> r = new ArrayList<String>(Arrays.asList(args));
		r.trimToSize();
		return r;
	}

}

