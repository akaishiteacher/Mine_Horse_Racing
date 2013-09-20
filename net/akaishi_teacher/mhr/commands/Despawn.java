package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.util.command.AbstractCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Despawn extends AbstractCommand {

	public Despawn(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		if (args.size() >= 2) {
			try {
				int num = Integer.parseInt(args.get(1));
				Player player = castPlayer(sender);
				if (player != null) {
					plugin.getHorsesControler().despawnHorse(num);
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Err_NumberFormatException"));
			}
		} else {
			plugin.getHorsesControler().despawnHorse();
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_Despawn");
	}

}
