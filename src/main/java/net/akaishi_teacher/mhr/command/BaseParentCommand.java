package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * 子コマンドを持つコマンドを作る時にベースとなるクラス
 * <p/>
 * 全ての親コマンドはこのクラスを継承する
 *
 * @author ilicodaisuki
 * @version 0.1.0
 */
public class BaseParentCommand extends CommandComponent {
	protected final ArrayList<CommandComponent> commands = new ArrayList<>();

	public BaseParentCommand(MineHorseRacingPlugin plugin, String name, ArrayList<CommandComponent> commands) {
		super(plugin, name);
		this.addAllCommand(commands);

	}

	public BaseParentCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
	}

	public BaseParentCommand addAllCommand(ArrayList<CommandComponent> commands) {
		this.commands.addAll(commands);
		return this;
	}

	public BaseParentCommand addCommand(CommandComponent command) {
		this.commands.add(command);
		return this;
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		if (args.size() >= 1) {
			String newCommand = args.remove(0);

			for (CommandComponent cmd : this.commands) {
				if (cmd.getLabel().equals(newCommand)) {
					if (cmd.execute(sender, args)) return true;
				}
			}
		}
		return false;
	}

	@Override
	public void collectUsages(CommandSender sender, ArrayList<String> label, ArrayList<String> usages) {
		if (commands.isEmpty()) return;

		label.add(this.label);
		for (CommandComponent cmd : commands) {
			cmd.collectUsages(sender, label, usages);
		}
		label.remove(label.lastIndexOf(this.label));
	}
}
