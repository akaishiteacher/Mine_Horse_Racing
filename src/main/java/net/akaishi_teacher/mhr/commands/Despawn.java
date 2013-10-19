package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;

public class Despawn extends MHRAbstractCommand {

	public Despawn(MHR mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			boolean removeAll = args.size() >= 2 ? true : false;
			int id = 0;
			HashMap<String, String> replaceMap = new HashMap<>();

			if (removeAll) {
				//Despawn the horse.
				id = Integer.parseInt(args.get(1));
				if (!mhr.getController().despawn(id))
					sender.sendMessage(mhr.getLang().get("Err_HorseNotFound"));
			} else {
				//Despawn all horses.
				mhr.getController().despawns(0, mhr.getStatus().getHorseDatas().size());
			}

			//Replace arguments.
			if (removeAll)
				replaceMap.put("Id", String.valueOf(id));
			else
				replaceMap.put("Id", "ALL");

			String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_Despawn_Despawned"), replaceMap);
			sender.sendMessage(sendA);
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Despawn");
	}

}
