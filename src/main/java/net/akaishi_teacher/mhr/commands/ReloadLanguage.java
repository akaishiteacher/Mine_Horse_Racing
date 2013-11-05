package net.akaishi_teacher.mhr.commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHRCore;

import org.bukkit.command.CommandSender;

public class ReloadLanguage extends MHRAbstractCommand {

	public ReloadLanguage(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			mhr.getLang().loadLangFile();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		sender.sendMessage("Language reloaded.(This message cannot change)");
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return "This command do language reload.(This message cannot change)";
	}

}
