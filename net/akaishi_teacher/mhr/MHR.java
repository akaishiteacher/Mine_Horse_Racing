package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import net.akaishi_teacher.mhr.commands.CommandExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MineHorseRacingPluginの元になるクラス
 * @author mozipi
 */
public final class MHR extends JavaPlugin {

	private Logger logger;

	private MHRListeners listener;

	private CommandExecutor cmdExecutor;

	private Language lang;

	private FileConfiguration cfg;

	@Override
	public void onEnable() {
		super.onEnable();
		cfg = getConfig();
		logger = getLogger();
		logger.info("MineHorseRacingPlugin Enabled.");
		listener = new MHRListeners(this);
		getServer().getPluginManager().registerEvents(listener, this);
		cmdExecutor = new CommandExecutor(this);
		registerCommands();
		try {
			lang = createLanguageInstance();
			lang.loadLangFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("MineHorseRacingPlugin Disabled.");
		setConfigs();
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return cmdExecutor.onCommand(sender, args);
	}

	protected void registerCommands() {
	}

	protected Language createLanguageInstance() throws URISyntaxException {
		cfg.addDefault("lang", "ja_JP");
		String langStr = cfg.getString("lang");
		URI uri = new URI(getDataFolder().toURI().getPath() + "/lang/");
		return new Language(new File(uri.getPath()), langStr, logger);
	}

	protected void setConfigs() {
		cfg.set("lang", lang.getLang());
	}

}
