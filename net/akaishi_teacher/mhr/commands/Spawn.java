package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn extends MHRAbstractCommand {

	public Spawn(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			int num = Integer.parseInt(args.get(1));
			Player player = castPlayer(sender);
			if (player != null) {
				plugin.getHorsesControler().spawnHorses(num, player.getLocation(), player);
			}
		} catch (NumberFormatException e) {
			sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Err_NumberFormatException"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_Spawn");
	}

}
