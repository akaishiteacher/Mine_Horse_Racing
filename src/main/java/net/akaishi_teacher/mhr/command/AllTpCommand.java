package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * AllTPコマンドの実装クラス
 *
 * @version 0.1.0
 */
public class AllTpCommand extends BaseCommand {
	public AllTpCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		this.description = "おーるてれぽーと"; // 未実装 helpで表示する予定。
		this.argsFormat = "[プレイヤー] [フラグ]"; // helpに表示
		this.permission = "mhr.horse.tp";
		this.isOpCommand = true;
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		// @TODO パーミッションの制限の仕方の確認。
		if (!hasPermissions(sender)) {
			sendNoPermissionMessage(sender);
			return true;
		}
		if (args.size() >= 1) {
			Player player = this.plugin.getServer().getPlayer(args.get(0));
			if (player == null) {
				// 該当するプレイヤーが存在しない
				this.plugin.sendPluginMessage(sender, "No such a player");
				return true;
			}
			if (args.size() == 1) {
				this.plugin.tpAllHorse(player);
				return true;
			} else if (args.size() == 2) {
				this.plugin.tpAllHorse(player, Boolean.parseBoolean(args.get(1)));
				return true;
			}
		} else {
			this.plugin.tpAllHorse(sender);
			return true;
		}
		return false;
	}
}
