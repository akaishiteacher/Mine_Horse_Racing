package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PatternRide extends MHRAbstractCommand {

	public PatternRide(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		int num = 0;
		int maxNum = -1;
		Player player = null;


		String[] strs = args.get(1).split("-");
		try {
			num = Integer.parseInt(strs[0]);
			maxNum = Integer.parseInt(strs[1]);
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err_NotNumer"));
		} catch (IndexOutOfBoundsException e) {
			sender.sendMessage(getUsage(sender));
		}

		player = Bukkit.getPlayer(args.get(2));
		if (player == null) {
			sender.sendMessage(mhr.getLang().get("Err_PlayerNotFound"));
			return true;
		}

		for (int i = num; i <= maxNum; i++) {
			int foundIndex = mhr.getStatus().getHorseDatas().indexOf(new HorseData(i, null));
			if (foundIndex != -1) {
				HorseData data = mhr.getStatus().getHorseDatas().get(foundIndex);
				if (data.getPlayer() == null) {
					data.horse.setPassenger(player);
					Map<String, String> replaceMap = new HashMap<>();
					replaceMap.put("Player", player.getName());
					replaceMap.put("Index", String.valueOf(i+1));
					sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_PatternRide_OnRide"), replaceMap));
					return true;
				}
			}
		}

		sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_PatternRide");
	}

}
