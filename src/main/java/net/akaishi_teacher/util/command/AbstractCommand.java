package net.akaishi_teacher.util.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * コマンドを実装するための抽象クラス
 * @author mozipi
 */
public abstract class AbstractCommand {

	/**
	 * コマンドのパターン(anyは必須引数)
	 */
	protected String pattern;

	/**
	 * コマンドのパーミッション
	 */
	protected String permission;

	/**
	 * コマンドの説明
	 */
	protected String description;

	public AbstractCommand(String pattern, String permission,
			String description) {
		super();
		this.pattern = pattern;
		this.permission = permission;
		this.description = description;
	}



	public String getPattern() {
		return pattern;
	}



	public String getPermission() {
		return permission;
	}



	public String getDescription() {
		return description;
	}



	/**
	 * コマンドが一致するかを判定します。
	 * @param args コマンドの引数
	 * @return 引数に渡されたArrayListを元に判定し、一致する場合はtrue。一致しなければfalse
	 */
	public boolean isMatch(ArrayList<String> args) {
		if (CommandSearcher.search(pattern, (String[]) args.toArray())) {
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
	 * ただし、Consoleの場合、警告文をだし、nullを返します。
	 * @param sender CommandSenderのインスタンス
	 * @return CommandSenderのgetNameメソッドの文字列からプレイヤーに変換したPlayerクラスのインスタンス
	 */
	public static Player castPlayer(CommandSender sender) {
		if (sender.getName() == "Console") {
			sender.sendMessage("§4Console is can't command execute!");
			return null;
		} else {
			return sender.getServer().getPlayerExact(sender.getName());
		}
	}

}
