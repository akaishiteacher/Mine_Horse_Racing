package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.other.SimpleLocation;
import net.akaishi_teacher.mhr.status.HorseData;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;

public class AllTeleportLoc extends MHRAbstractCommand {

	public AllTeleportLoc(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			boolean flag = false;
			SimpleLocation loc = null;
			double yaw = 0;
			double pitch = 0;

			if (args.size() >= 5) {
				//tploc x y z flag
				if (args.get(4).equals("true") || args.get(4).equals("false")) {
					flag = Boolean.parseBoolean(args.get(4));
				} else {
					//tploc x y z yaw pitch [flag]
					if (args.size() >= 6) {
						yaw = Double.parseDouble(args.get(4));
						pitch = Double.parseDouble(args.get(5));
						//tploc x y z yaw pitch flag
						if (args.size() >= 7) {
							flag = Boolean.parseBoolean(args.get(6));
						}
					} else {
						sender.sendMessage(getUsage(sender));
						return true;
					}
				}
			}

			//Get location.
			loc = new SimpleLocation(castWorld(sender).getName(), Double.parseDouble(args.get(1)), Double.parseDouble(args.get(2)), Double.parseDouble(args.get(3)), yaw, pitch);

			//Teleport horse.
			for (int i = 0; i < mhr.getStatus().getHorseDatas().size(); i++) {
				HorseData data = mhr.getStatus().getHorseDatas().get(i);
				if (!mhr.getController().teleport(data.id, loc, flag)) {
					sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
					return true;
				}
			}

			//Send message.
			HashMap<String, String> replaceMap = new HashMap<>();
			replaceMap.put("X", String.valueOf(loc.x));
			replaceMap.put("Y", String.valueOf(loc.y));
			replaceMap.put("Z", String.valueOf(loc.z));
			String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_AllTeleportLoc_Teleported"), replaceMap);
			sender.sendMessage(sendA);
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_AllTeleportLoc");
	}

}
