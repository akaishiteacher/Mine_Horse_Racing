package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.commands.func.AbstractCommand;

public class Tp extends AbstractCommand {

	public Tp(MHR plugin, String pattern, String permission, String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		Location loc = null;
		boolean flag = false;
		Player p = null;
		int num = 0;
		try {
			num = Integer.parseInt(args.get(1));
		} catch (NumberFormatException e) {
			sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Err_NumberFormatException"));
		}
		if (args.size() >= 4) { //Flag option
			flag = Boolean.parseBoolean(args.get(3));
		}
		if (args.size() >= 3) { //To player option
			p = plugin.getServer().getPlayerExact(args.get(2));
			if (p != null) {
				loc = p.getLocation();
			} else {
				sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Err_PlayerNotFound"));
				return true;
			}
		} else {
			loc = castPlayer(sender).getLocation();
		}
		plugin.getHorsesControler().tp(num, loc, flag);
		HashMap<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("num", String.valueOf(num));
		replaceMap.put("toPlayer", p == null ? "Here" : p.getName());
		replaceMap.put("flag", String.valueOf(flag));
		sender.sendMessage(replaceArgs(plugin.getLang().getLocalizedString("Cmd_Out_Tp_Set"), replaceMap));
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_Tp");
	}

}
