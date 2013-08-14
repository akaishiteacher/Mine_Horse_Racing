package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 子コマンドのサンプル
 *
 * @author ilicodaisuki
 * @version 0.1.0
 */
public class SampleChildCommand extends BaseCommand {
	public SampleChildCommand(MineHorseRacingPlugin plugin, String label) {
		super(plugin, label);
		this.description = "子コマンド";
		this.argsFormat = "[text] ...";
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		if (!hasPermission(sender)) {
			return false;
		}
		for (String str : args) {
			this.plugin.sendPluginMessage(sender, str);
		}
		return true;
	}
}
