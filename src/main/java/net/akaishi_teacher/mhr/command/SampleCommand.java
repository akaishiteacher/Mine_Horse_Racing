package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * サンプルコマンドの実装クラス
 *
 * @version 0.1.0
 */
public class SampleCommand extends BaseCommand {
	public SampleCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		this.description = "さんぷる"; // 未実装 helpで表示する予定。
		this.argsFormat = "[String]"; // helpに表示
		this.permission = "mhr.sample"; // パーミッションノード
	}

	// ぷれいやーこまんど
	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		// 権限を持っているかの確認
		if (!hasPermissions(sender)) {
			// 権限を持っていない場合の処理
			sendNoPermissionMessage(sender);
			return true;
		}
		this.plugin.sendPluginMessage(sender, "This is Sample Command");
		for (String str : args) {
			this.plugin.sendPluginMessage(sender, "-> " + str);
		}
		return true;
	}

	// こんそーるこまんど
	@Override
	protected boolean onCommand(ConsoleCommandSender sender, ArrayList<String> args) {
		this.plugin.sendPluginMessage(sender, "This is Sample Console Command");
		for (String str : args) {
			this.plugin.sendPluginMessage(sender, "-> " + str);
		}
		return true;
	}
}
