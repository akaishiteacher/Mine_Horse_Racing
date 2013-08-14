package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MHRCommandExecutor
 * <p/>
 * プレイヤーコマンドを扱うクラス
 *
 * @since 0.1.0
 */
public class MHRCommandExecutor implements CommandExecutor {
	/**
	 * 登録されているコマンドのリスト
	 */
	private ArrayList<BaseCommand> commands = new ArrayList<>();
	protected MineHorseRacingPlugin plugin;

	public MHRCommandExecutor(MineHorseRacingPlugin plugin) {
		this.plugin = plugin;

		// ---------- PlayerCommand追加ここから ----------
		commands.add(new SampleCommand(plugin, "sample"));
		commands.add(new SpawnCommand(plugin, "spawn"));
		commands.add(new DespawnCommand(plugin, "despawn"));
		commands.add(new TpCommand(plugin, "tp"));
		commands.add(new AllTpCommand(plugin, "alltp"));
		// ---------- PlayerCommand追加ここまで ----------
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (args.length >= 1) {
			ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(args));
			String newCommand = newArgs.remove(0);

			for (BaseCommand cmd : this.commands) {
				if (cmd.getName().equals(newCommand)) {
					if (cmd.execute(commandSender, newArgs)) return true;
				}
			}
		}

		commandSender.sendMessage("===== MineHorseRacingPlugin =====");
		for (BaseCommand cmd : this.commands) {
			commandSender.sendMessage("/" + label + " " + cmd.getName() + " " + cmd.getArgsFormat());
		}
		commandSender.sendMessage("==============================");

		return true;
	}
}
