package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.data.HorseData;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ride extends MHRAbstractCommand {

	public Ride(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		Player player = null;
		int index = 0;
		HorseData data = null;

		//Get index.
		if (isNumber(args, 1)) {
			index = Integer.parseInt(args.get(1)) - 1;
		} else {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
			return true;
		}
		
		//Get player.
		if (hasOption(args, 2)) {
			if ((player = Bukkit.getPlayer(args.get(2))) != null) {} else {
				sender.sendMessage(mhr.getLang().get("Err_PlayerNotFound"));
				return true;
			}
		} else {
			if ((player = castPlayer(sender)) != null);
			else //By console or cmdBlock.
				return true;
		}

		//On ride.
		int foundIndex = mhr.getStatus().getHorseDatas().indexOf(new HorseData(index, null)); 
		if (foundIndex != -1) {
			data = mhr.getStatus().getHorseDatas().get(foundIndex);
			data.horse.setPassenger(player);
		} else {
			sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
			return true;
		}
		
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Player", player.getName());
		replaceMap.put("Index", String.valueOf(index + 1));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Ride_OnRide"), replaceMap));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Ride");
	}

}
