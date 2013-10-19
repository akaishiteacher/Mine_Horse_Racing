package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.other.SimpleLocation;
import net.akaishi_teacher.mhr.status.HorseData;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn extends MHRAbstractCommand {

	public Spawn(MHR mhr, String pattern, String permission, String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		int num = 0;
		double x = 0;
		double y = 0;
		double z = 0;

		num = Integer.parseInt(args.get(1));

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
		SimpleLocation loc = new SimpleLocation(castPlayer(sender).getWorld().getName(), x, y, z);

		//The fill.
		ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
		for (int i = 0; i < datas.size(); i++) {
			int index = datas.indexOf(new HorseData(i, null));
			if (index == -1) {
				mhr.getController().spawn(i, loc);
				num--;
			}
		}

		//Add a horses.
		if (num >= 1) {
			int baseId = datas.size();
			for (int i = 0; i < num; i++) {
				int id = baseId + i;
				mhr.getController().spawn(id, loc);
			}
		}

		//Send message.
		HashMap<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Num", String.valueOf(num));
		String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_Spawn_Spawned"), replaceMap);
		sender.sendMessage(sendA);

		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Spawn");
	}

}
