package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * TPコマンドの実装クラス
 *
 * @version 0.1.0
 */
public class TpCommand extends BaseCommand {
	public TpCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		this.description = "てれぽーと"; // 未実装 helpで表示する予定。
		this.argsFormat = "(個体番号) [プレイヤー] [フラグ]"; // helpに表示
		this.permission = "mhr.horse.tp";
		this.isOpCommand = true;
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		// @TODO パーミッションの制限の仕方の確認。
		if (!sender.hasPermission(this.permission)) {
			sendNoPermissionMessage(sender);
			return true;
		}
		if (args.size() >= 1) {
			int n = Integer.parseInt(args.get(0));
			Horse horse = this.plugin.getHorse(n);
			if (horse == null) {
				// 該当する馬が存在しない
				this.plugin.sendPluginMessage(sender, "No such a horse");
				return true;
			}
			if (args.size() >= 2) {
				// OP限定
				if (!sender.isOp()) {
					sendNoPermissionMessage(sender);
					return true;
				}
				Player player = this.plugin.getServer().getPlayer(args.get(1));
				if (player == null) {
					// 該当するプレイヤーが存在しない
					this.plugin.sendPluginMessage(sender, "No such a player");
					return true;
				}
				if (args.size() == 2) {
					this.plugin.tpHorse(n, player);
//					this.plugin.sendPluginMessage(sender, "Tp Horse(id: " + n + " ) to " + player.getName());
					return true;
				} else if (args.size() == 3) {
					this.plugin.tpHorse(n, player, Boolean.parseBoolean(args.get(2)));
					return true;
				}
			} else {
				this.plugin.tpHorse(n, sender);
//				this.plugin.sendPluginMessage(sender, "Tp Horse(id: " + n + " ) to You");
				return true;
			}
		}
		return false;
	}
}
