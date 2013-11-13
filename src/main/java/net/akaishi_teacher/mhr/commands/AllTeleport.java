package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.common.SimpleLocation;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AllTeleport extends MHRAbstractCommand {

	public AllTeleport(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		boolean flag = false;
		Player player = null;

		//Assignment the location and flag.
		if (args.size() >= 3) {
			if (args.get(1).equals("true") || args.get(1).equals("false")) {
				//Get flag and player.
				flag = Boolean.parseBoolean(args.get(1));
				player = castPlayer(sender);
			} else {
				//Get player.
				player = mhr.getPlugin().getServer().getPlayer(args.get(1));
				//Player found?
				if (player == null) {
					//Player not found!
					sender.sendMessage(mhr.getLang().get("Err_PlayerNotFound"));
					return true;
				}
				//Get flag.
				flag = Boolean.parseBoolean(args.get(2));
			}
		} else {
			player = castPlayer(sender);
		}

		//Teleport horse.
		for (int i = 0; i < mhr.getStatus().getHorseDatas().size(); i++) {
			HorseData data = mhr.getStatus().getHorseDatas().get(i);
			if (!mhr.getController().teleport(data.id, SimpleLocation.toSimpleLocation(player.getLocation()), flag)) {
				sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
				return true;
			}
		}

		//Send message.
		HashMap<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Player", player.getName());
		String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_AllTeleport_Teleported"), replaceMap);
		sender.sendMessage(sendA);;
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_AllTeleport");
	}

}
