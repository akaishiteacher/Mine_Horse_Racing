package net.akaishi_teacher.util.command;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * コマンドを実装するための抽象クラス
 * @author mozipi
 */
public abstract class AbstractCommand {

	/**
	 * コマンドのパターン(anyは必須引数)
	 */
	protected CommandPattern pattern;

	/**
	 * コマンドのパーミッション
	 */
	protected String permission;

	/**
	 * コマンドの説明
	 */
	protected String description;

	/**
	 * この変数までの引数を送信しない。それで使用される値です。
	 */
	protected int indexOfNotSendOptions = -1;

	public AbstractCommand(String pattern, String permission,
			String description) {
		super();
		this.pattern = new CommandPattern(pattern);
		this.permission = permission;
		this.description = description;
	}



	public AbstractCommand(String pattern, String permission,
			String description, int indexOfNotSendOptions) {
		this(pattern, permission, description);
		this.indexOfNotSendOptions = indexOfNotSendOptions;
	}



	/**
	 * コマンドパターンを返します。
	 * @return コマンドパターン
	 */
	public CommandPattern getPattern() {
		return pattern;
	}



	/**
	 * コマンドのパーミッションを返します。
	 * @return コマンドのパーミッション
	 */
	public String getPermission() {
		return permission;
	}



	/**
	 * コマンドの使い方を返します。
	 * @return コマンドの使い方
	 */
	public String getDescription() {
		return description;
	}



	public int getIndexOfNotSendOptions() {
		return indexOfNotSendOptions;
	}

	/**
	 * コマンドが一致するかを判定します。
	 * @param args コマンドの引数
	 * @return 引数に渡されたArrayListを元に判定し、一致する場合はtrue。一致しなければfalse
	 */
	public boolean isMatch(ArrayList<String> args) {
		if (pattern.match(args.toArray(new String[0]))) {
			return true;
		}
		return false;
	}



	/**
	 * コマンドを実行する抽象メソッドです。
	 * @param sender CommandSenderのインスタンス
	 * @param args コマンドの引数
	 * @return コマンドが実行できた場合はtrue
	 */
	public abstract boolean execute(CommandSender sender, ArrayList<String> args);



	/**
	 * コマンドの実行方法を返すメソッドです、
	 * @param sender CommandSenderのインスタンス
	 * @return コマンドの実行方法
	 */
	public abstract String getUsage(CommandSender sender);



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCommand other = (AbstractCommand) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}


	/**
	 * CommandSenderからPlayerに変換します。<br>
	 * ただし、ConsoleもしくはCommandBlockの場合、警告文をだし、nullを返します。
	 * @param sender CommandSenderのインスタンス
	 * @return CommandSenderのgetNameメソッドの文字列からプレイヤーに変換したPlayerクラスのインスタンス
	 */
	public static Player castPlayer(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§4Console is can't command execute!");
			return null;
		} else if (sender instanceof BlockCommandSender) {
			sender.sendMessage("§4CommandBlock is can't command execute!");
			return null;
		} else {
			return sender.getServer().getPlayerExact(sender.getName());
		}
	}

	/**
	 * CommandSenderからWorldに変換します。<br>
	 * ただし、Consoleの場合、警告文をだし、nullを返します。
	 * @param sender CommandSenderのインスタンス
	 * @return Consoleではないばあいは、Worldクラスのインスタンス。Consoleならnull
	 */
	public static World castWorld(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§4Console is can't command execute!");
			return null;
		} else if (sender instanceof BlockCommandSender) {
			return ((BlockCommandSender) (sender)).getBlock().getWorld();
		} else {
			return sender.getServer().getPlayerExact(sender.getName()).getWorld();
		}
	}

	/**
	 * 指定されたIndexの引数が、引数リストに存在するかを判定します。<br>
	 * これは、if (args.size() >= index)と同等です。
	 * @param args 判定する引数リスト
	 * @param index 判定したいindex
	 * @return if (args.size() >= index)の場合にtrue。
	 */
	public static boolean hasOption(ArrayList<String> args, int index) {
		if (args.size() > index) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 指定されたIndexの引数が、数値であるかどうかを判定します。
	 * @param args 引数リスト
	 * @param index 引数のIndex
	 * @return 指定されたIndexの引数が数値ならtrue
	 */
	public static boolean isNumber(ArrayList<String> args, int index) {
		try {
			Integer.parseInt(args.get(index));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
