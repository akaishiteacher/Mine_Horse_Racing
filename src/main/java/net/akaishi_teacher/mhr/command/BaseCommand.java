package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * コマンドのベースとなるクラス
 * 全てのコマンドはこのクラスを継承する
 *
 * @version 0.1.0
 */
public class BaseCommand {
	protected MineHorseRacingPlugin plugin;
	/**
	 * /command name args ... の nameにあたる部分
	 */
	protected String name;
	/**
	 * コマンドの説明
	 * helpで表示する
	 */
	protected String description;
	/**
	 * コマンドの引数の形式
	 * helpで表示する
	 */
	protected String argsFormat;
	/**
	 * このコマンドを実行するのに必要なパーミッションノード
	 */
	protected String permission;
	/**
	 * OPコマンドであればtrue
	 *
	 * @deprecated パーミッションノードでの判断で十分なので除去予定
	 */
	protected boolean isOpCommand;

	public BaseCommand(MineHorseRacingPlugin plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		this.description = "";
		this.argsFormat = "";
		this.permission = "";
		this.isOpCommand = false;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getArgsFormat() {
		return this.argsFormat;
	}

	public boolean execute(CommandSender sender, ArrayList<String> args) {
		if (sender instanceof Player) {
			return onCommand((Player) sender, args);
		} else if (sender instanceof ConsoleCommandSender) {
			return onCommand((ConsoleCommandSender) sender, args);
		} else if (sender instanceof RemoteConsoleCommandSender) {
			// @TODO ConsoleCommandSender と RemoteConsoleCommandSender の違いの調査
			return onCommand((RemoteConsoleCommandSender) sender, args);
		} else if (sender instanceof BlockCommandSender) {
			return onCommand((BlockCommandSender) sender, args);
		}
		return false;
	}

	private boolean onCommand(RemoteConsoleCommandSender sender, ArrayList<String> args) {
		return false;
	}

	/**
	 * コマンドブロックからコマンドが呼び出された場合に実行される？
	 *
	 * @param sender 送信元ブロック
	 * @param args   コマンド引数
	 * @return コマンドを捕捉した場合にtrueを返す
	 */
	protected boolean onCommand(BlockCommandSender sender, ArrayList<String> args) {
		return false;
	}

	/**
	 * プレイヤーからコマンドが呼び出された場合に実行される
	 *
	 * @param sender 呼び出したプレイヤー
	 * @param args   コマンド引数
	 * @return コマンドを捕捉した場合にtrueを返す
	 */
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		return false;
	}

	/**
	 * コンソールからコマンドが呼び出された場合に実行される
	 *
	 * @param sender 呼び出し元のコンソール
	 * @param args   コマンド引数
	 * @return コマンドを捕捉した場合にtrueを返す
	 */
	protected boolean onCommand(ConsoleCommandSender sender, ArrayList<String> args) {
		return false;
	}

	/**
	 * プレイヤーにコマンド実行権限があるかを判定する
	 *
	 * @param player 判定するプレイヤー
	 * @return 実行権限があればtrueを返す
	 */
	protected boolean hasPermissions(Player player) {
		// @TODO パーミッションの判定。 各種プラグインの判定
		return player.hasPermission(this.permission);
//		return (this.permission.isEmpty() && !this.isOpCommand) ||
//				(!this.permission.isEmpty() && player.hasPermission(this.permission)) ||
//				(this.isOpCommand && player.isOp());
	}

	/**
	 * プレイヤーにコマンド実行権限がなかった場合に送信するメッセージ
	 *
	 * @param player メッセージ送信先のプレイヤー
	 */
	protected void sendNoPermissionMessage(Player player) {
		player.sendMessage("実行する権限がありません : " + this.permission);
	}
}
