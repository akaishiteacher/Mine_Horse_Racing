package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.commands.DespawnCommand;
import net.akaishi_teacher.mhr.commands.MineHorseRacingPlayerCommand;
import net.akaishi_teacher.mhr.commands.SpawnCommand;
import net.akaishi_teacher.mhr.commands.TpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * プレイヤーコマンドを実行するクラスです。
 */
public class PlayerCommandExecutor implements CommandExecutor {
	private MineHorseRacingPlugin plugin;
	private ArrayList<MineHorseRacingPlayerCommand> commands = new ArrayList<MineHorseRacingPlayerCommand>();

	public PlayerCommandExecutor(MineHorseRacingPlugin plugin) {
		this.plugin = plugin;

		// ここにコマンドを追加
		// MineHorseRacingPlayerCommandクラスを継承して新しいCommandクラスを作り、commandsに登録してください。
		// ex. commands.add(new SpawnCommand(this.plugin, "spawn")); と登録すると
		// コマンド "/command spawn [arg1] [arg2] ..." が追加されます。
		commands.add(new SpawnCommand(this.plugin, "spawn"));
		commands.add(new DespawnCommand(this.plugin, "despawn"));
		commands.add(new TpCommand(this.plugin, "tp"));
		// commands.add(new SampleCommand(this.plugin, "sample"));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command originalCommand, String label, String[] originalArgs) {
		if (sender instanceof Player) {
			// sender is Player
			if (originalArgs.length >= 1) {
				ArrayList<String> args = new ArrayList<String>(Arrays.asList(originalArgs));
				String cmd = args.remove(0);

				for (MineHorseRacingPlayerCommand command : this.commands) {
					if (command.getName().equalsIgnoreCase(cmd)) {
						return command.execute((Player) sender, args);
					}
				}
			} else {
				for (MineHorseRacingPlayerCommand cmd : this.commands) {
					sender.sendMessage("/" + label + " " + cmd.getName() + " " + cmd.getArgsFormat());
				}
				return true;
			}
		}
		return false;
	}
}
