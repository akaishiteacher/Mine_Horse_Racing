package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * スポーンコマンドの実装クラス
 *
 * @version 0.1.0
 */
public class SpawnCommand extends BaseCommand {
	public SpawnCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);

		this.description = "すぽーんほーす"; // 未実装 helpで表示する予定。
		this.argsFormat = "(個体数)"; // helpに表示
		this.permission = "mhr.horse.spawn";
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		if (!hasPermissions(sender)) {
			sendNoPermissionMessage(sender);
			return true;
		}
		if (args.size() == 1) {
			int n = Integer.parseInt(args.get(0));
			for (int i = 0; i < n; ++i) {
				int id = this.plugin.spawnHorse(sender);
				this.plugin.sendPluginMessage(sender, "Spawned horse(ID: " + id + " )");
				this.plugin.getLogger().info(sender.getName() + " spawned horse(ID: " + id + " )");
			}
			return true;
		}
		return false;
	}
}
