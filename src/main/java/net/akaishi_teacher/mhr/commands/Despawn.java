package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Despawn extends MHRAbstractCommand {

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
					pl.getHorsesControler().despawnHorse(num);
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(pl.getLang().getLocalizedString("Cmd_Err_NumberFormatException"));
			}
		} else {
			pl.getHorsesControler().despawnHorse();
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return pl.getLang().getLocalizedString("Cmd_Usage_Despawn");
	}

}
