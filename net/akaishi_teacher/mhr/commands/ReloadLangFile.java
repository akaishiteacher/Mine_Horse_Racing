package net.akaishi_teacher.mhr.commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;

import org.bukkit.command.CommandSender;

public class ReloadLangFile extends MHRAbstractCommand {

	public ReloadLangFile(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			plugin.getLang().loadLangFile();
		} catch (IOException e) {
			sender.sendMessage("§4" + e.getMessage());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			sender.sendMessage("§4" + e.getMessage());
			e.printStackTrace();
		}
		sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Out_ReloadLangFile_Reloaded"));
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_ReloadLangFile");
	}

}
