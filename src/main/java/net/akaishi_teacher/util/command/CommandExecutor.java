package net.akaishi_teacher.util.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.bukkit.command.CommandSender;

/**
 * コマンドを実行するためのクラス
 * @author mozipi
 */
public class CommandExecutor {

	/**
	 * コマンドのHashSet
	 */
	protected LinkedHashSet<AbstractCommand> commandSet =
			new LinkedHashSet<AbstractCommand>();



	/**
	 * コマンドを実行します。
	 * @param sender CommandSenderのインスタンス
	 * @param args コマンドの引数
	 * @return コマンドが実行できた場合はtrue。コマンドが見つからなくてもtrue。実行できなかった場合はfalse
	 */
	public boolean onCommand(CommandSender sender, String[] args) {
		//Sort of commands.
		LinkedList<AbstractCommand> l = new LinkedList<AbstractCommand>(commandSet);
		Collections.sort(l, new ComparatorCommandArgs());

		//Assignment in sorted commands.
		commandSet = new LinkedHashSet<AbstractCommand>(l);

		for (AbstractCommand command : commandSet) {
			//Match pattern?
			if (command.getPattern().match(args)) {
				//Sender has permission of the command?
				if (command.getPermission() == null
						|| sender.hasPermission(command.getPermission())) {
					if (command.getIndexOfNotSendOptions() == -1) {
					return command.execute(
							sender, new ArrayList<String>(Arrays.asList(args)));
					} else {
						return command.execute(
								sender, new ArrayList<String>(Arrays.asList(splitArgs(command.getIndexOfNotSendOptions(), args))));
					}
				} else {
					//Sender don't have permission,
					sender.sendMessage("§4You don't have permission!(" + command.getPermission() + ")");
				}
			} else {
				if (command.getPattern().match_notAnys(args)) {
					sender.sendMessage(command.getUsage(sender));
					return true;
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



	/**
	 * 指定した位置までの引数を除去します。
	 * @param index コマンドを切る位置
	 * @param args 元の引数
	 * @return 指定した位置までの引数を除去した配列
	 */
	public String[] splitArgs(int index, String[] args) {
		String[] result = new String[args.length - index];
		for (int i = index; i < args.length; i++) {
			result[i - index] = args[index];
		}
		return result;
	}



	public HashSet<AbstractCommand> getCommandSet() {
		return commandSet;
	}

}

