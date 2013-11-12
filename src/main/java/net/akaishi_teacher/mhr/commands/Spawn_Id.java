package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.SimpleLocation;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn_Id extends MHRAbstractCommand {

	public Spawn_Id(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		int num = 0;
		int maxNum = -1;
		double x = 0;
		double y = 0;
		double z = 0;

		if (isNumber(args, 1)) {
			num = Integer.parseInt(args.get(1)) - 1;
		} else {
			String[] strs = args.get(1).split("-");
			try {
				num = Integer.parseInt(strs[0]) - 1;
				maxNum = Integer.parseInt(strs[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(mhr.getLang().get("Err_NotNumer"));
			} catch (IndexOutOfBoundsException e) {
				sender.sendMessage(getUsage(sender));
			}
		}
		

		int positionArgsNum = 3;
		int positionArgsNum_ = 5;
		if (args.size() >= positionArgsNum
				&& args.size() >= positionArgsNum_) {
			x = Double.parseDouble(args.get(2));
			y = Double.parseDouble(args.get(3));
			z = Double.parseDouble(args.get(4));
		} else {
			Player player = castPlayer(sender);
			x = player.getLocation().getX();
			y = player.getLocation().getY();
			z = player.getLocation().getZ();
		}

		//Location of the horse to be spawn.
		SimpleLocation loc = new SimpleLocation(castWorld(sender).getName(), x, y, z);
		
		if (maxNum != -1) {
			mhr.getController().spawns(num, maxNum - num, loc);
		} else {
			mhr.getController().spawn(num, loc);
		}
		
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Index", args.get(1));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Spawn_Id_Spawned"), replaceMap));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Spawn_Id");
	}

}
