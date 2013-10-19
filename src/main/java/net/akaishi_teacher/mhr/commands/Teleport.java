package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.other.SimpleLocation;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport extends MHRAbstractCommand {

	public Teleport(MHR mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			int id = 0;
			boolean flag = false;
			Player player = null;

			//Get id.
			id = Integer.parseInt(args.get(1))-1;

			//Assignment the location and flag.
			if (args.size() >= 3) {
				if (args.get(2).equals("true") || args.get(2).equals("false")) {
					//Get flag and player.
					flag = Boolean.parseBoolean(args.get(2));
					player = castPlayer(sender);
				} else {
					//Get player.
					player = mhr.getPlugin().getServer().getPlayer(args.get(2));
					//Found player?
					if (player == null) {
						//Player not found!
						sender.sendMessage(mhr.getLang().get("Err_PlayerNotFound"));
						return true;
					}
					//Get flag.
					flag = Boolean.parseBoolean(args.get(3));
				}
			} else {
				player = castPlayer(sender);
			}

			//Teleport horse.
			if (!mhr.getController().teleport(id, SimpleLocation.toSimpleLocation(player.getLocation()), flag)) {
				sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
				return true;
			}

			//Send message.
			HashMap<String, String> replaceMap = new HashMap<>();
			replaceMap.put("Id", String.valueOf(id));
			replaceMap.put("Player", player.getName());
			String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_Teleport_Teleported"), replaceMap);
			sender.sendMessage(sendA);
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Teleport");
	}

}
