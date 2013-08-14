package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * デスポーンコマンドの実装クラス
 *
 * @version 0.1.0
 */
public class DespawnCommand extends BaseCommand {
	public DespawnCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);

		this.description = "ですぽーんほーす"; // 未実装 helpで表示する予定。
		this.argsFormat = "[個体番号]"; // helpに表示
		this.permission = "mhr.horse.despawn";
		this.isOpCommand = true;
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		if (!hasPermissions(sender)) {
			sendNoPermissionMessage(sender);
			return true;
		}
		if (args.size() == 0) {
			this.plugin.purgeHorses();
			this.plugin.sendPluginMessage(sender, "Purged horses");
			return true;
		} else if (args.size() == 1) {
			int n = Integer.parseInt(args.get(0));
			this.plugin.removeHorse(n);
			this.plugin.sendPluginMessage(sender, "Removed horse(ID: " + n + " )");
			this.plugin.getLogger().info(sender.getName() + " removed horse(ID: " + n + " )");
			return true;
		}
		return false;
	}
}