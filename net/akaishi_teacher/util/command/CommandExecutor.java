package net.akaishi_teacher.util.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.bukkit.command.CommandSender;

/**
 * コマンドを実行するためのクラス
 * @author mozipi
 */
public class CommandExecutor {

	/**
	 * コマンドのHashSet
	 */
	protected HashSet<AbstractCommand> commandSet =
			new HashSet<AbstractCommand>();

	public CommandExecutor() {
		super();
	}



	/**
	 * コマンドを実行します。
	 * @param sender CommandSenderのインスタンス
	 * @param args コマンドの引数
	 * @return コマンドが実行できた場合はtrue。コマンドが見つからなくてもtrue。実行できなかった場合はfalse
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean onCommand(CommandSender sender, String[] args) {
		ArrayList l = new ArrayList<AbstractCommand>(commandSet);
		Collections.sort(l, new ComparatorCommandArgs());
		commandSet = new HashSet<AbstractCommand>(l);

		for (AbstractCommand command : commandSet) {
			if (CommandSearcher.search(command.pattern, args)) {
				if (command.getPermission() == null
						|| sender.hasPermission(command.getPermission())) {
					return command.execute(sender, new ArrayList<String>(Arrays.asList(args)));
				} else {
					sender.sendMessage("§4You don't have permission!(" + command.getPermission() + ")");
				}
			} else {
				if (CommandSearcher.search_notAnys(command.pattern, args)) {
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



	public HashSet<AbstractCommand> getCommandSet() {
		return commandSet;
	}

}

