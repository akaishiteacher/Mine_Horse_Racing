package net.akaishi_teacher.mhr.cource.commands;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;

import org.bukkit.command.CommandSender;

public class Start extends MHRAbstractCommand {

	public Start(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		String startMes = pl.getLang().getLocalizedString("Cmd_Out_Start_Start");
		String cowntdownMes = pl.getLang().getLocalizedString("Cmd_Out_Start_Countdown");
		String goMes = pl.getLang().getLocalizedString("Cmd_Out_Start_Go");
		CountdownClass c =  new CountdownClass(startMes, cowntdownMes, goMes);
		c.setTaskId(pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl,c, 0, 1));
		pl.getMHRCource().setStarted(true);
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return pl.getLang().getLocalizedString("Cmd_Usage_Start");
	}

	class CountdownClass implements Runnable {
		private int c;
		private int id;
		private String[] strs;
		public CountdownClass(String... strs) {
			this.strs = strs;
		}
		public void setTaskId(int id) {this.id = id;}
		@Override
		public void run() {
			if (c == 0)
			pl.getServer().broadcastMessage(strs[0]);
			if (c == 20*2)
				pl.getServer().broadcastMessage(strs[1]);
			if (c == 20*5) {
				pl.getServer().broadcastMessage(strs[2]);
				pl.getServer().getScheduler().cancelTask(id);
			}
			c++;
		}
	}

}
