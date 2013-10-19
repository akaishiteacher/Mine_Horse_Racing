package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.other.SimpleLocation;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;

public class TeleportLoc extends MHRAbstractCommand {

	public TeleportLoc(MHR mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			int id = 0;
			boolean flag = false;
			SimpleLocation loc = null;
			double yaw = 0;
			double pitch = 0;

			//Get id.
			id = Integer.parseInt(args.get(1))-1;

			if (args.size() >= 6) {
				//tploc x y z flag
				if (args.get(5).equals("true") || args.get(5).equals("false")) {
					flag = Boolean.getBoolean(args.get(5));
				} else {
					//tploc x y z yaw pitch [flag]
					if (args.size() >= 7) {
						yaw = Double.parseDouble(args.get(5));
						pitch = Double.parseDouble(args.get(6));
						//tploc x y z yaw pitch flag
						if (args.size() >= 8) {
							flag = Boolean.getBoolean(args.get(7));
						}
					} else {
						sender.sendMessage(getUsage(sender));
					}
				}
			}

			//Get location.
			loc = new SimpleLocation(castPlayer(sender).getWorld().getName(), Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4)), yaw, pitch);

			if (!mhr.getController().teleport(id, loc, flag)) {
				sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
				return true;
			}

			HashMap<String, String> replaceMap = new HashMap<>();
			replaceMap.put("X", String.valueOf(loc.x));
			replaceMap.put("Y", String.valueOf(loc.y));
			replaceMap.put("Z", String.valueOf(loc.z));
			replaceMap.put("Id", String.valueOf(id));
			String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_TeleportLoc_Teleported"), replaceMap);
			sender.sendMessage(sendA);
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_TeleportLoc");
	}

}
