package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * コマンド実行可能なインターフェース
 *
 * @author ilicodaisuki
 * @version 0.1.0
 */
public abstract class CommandComponent {
	protected MineHorseRacingPlugin plugin;
	protected String label;

	public CommandComponent(MineHorseRacingPlugin plugin, String label) {
		this.plugin = plugin;
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public abstract boolean execute(CommandSender sender, ArrayList<String> args);

	public abstract void collectUsages(CommandSender sender, ArrayList<String> labels, ArrayList<String> usages);
}
