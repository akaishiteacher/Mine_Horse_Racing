package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 馬をデスポーンするコマンド
 * <p/>
 * 個体番号を指定しなかった場合は
 * "/command name [個体番号]"
 */
public class DespawnCommand extends MineHorseRacingPlayerCommand {
	public DespawnCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		this.description = "馬をですぽーん";
		this.argsFormat = "[個体番号]";
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		this.plugin.getLogger().info("despawn args.length " + args.size());
		if (args.size() == 1) {
			int n;
			try {
				n = Integer.parseInt(args.get(0));
			} catch (NumberFormatException e) {
				return false;
			}
			this.plugin.killHorse(n);
		} else {
			this.plugin.purgeHorses();
		}
		return false;
	}
}
