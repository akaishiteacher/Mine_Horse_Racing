package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.mhr.commands.ReloadLangFile;
import net.akaishi_teacher.mhr.commands.SetJump;
import net.akaishi_teacher.mhr.commands.SetSpeed;
import net.akaishi_teacher.mhr.commands.func.CommandExecutor;

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

	private CommonHorseStats horseStats;

	private String langFilesDirString;

	@Override
	public void onEnable() {
		super.onEnable();
		cfg = getConfig();
		logger = getLogger();
		loadConfig();

		logger.info("MineHorseRacingPlugin Enabled.");

		listener = new MHRListeners(this);
		getServer().getPluginManager().registerEvents(listener, this);
		getServer().getScheduler().runTaskTimer(this, listener, 0, 100);

		cmdExecutor = new CommandExecutor(this);
		try {
			lang = createLanguageInstance();
			lang.loadLangFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		horseStats = createCommonHorseStatsInstance();

		registerCommands();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("MineHorseRacingPlugin Disabled.");
		setConfigValues();
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return cmdExecutor.onCommand(sender, args);
	}

	protected void registerCommands() {
		cmdExecutor.addCommand(new Help(this, "", null, "This command is help command."));
		cmdExecutor.addCommand(new ReloadLangFile(this, "reloadLangFile", "mhr.reload.lang", "This command will reload the language file."));
		cmdExecutor.addCommand(new SetSpeed(this, "setspeed any", "mhr.horse.set", "This command will set speed to a horse."));
		cmdExecutor.addCommand(new SetJump(this, "setjump any", "mhr.horse.set", "This command will set jump strength to a horse."));
	}

	protected Language createLanguageInstance() throws URISyntaxException {
		cfg.addDefault("lang", "ja_JP");
		String langStr = cfg.getString("lang");
		URI uri = new URI(getDataFolder().toURI().getPath() + "/lang/");
		return new Language(new File(langFilesDirString == null ? uri.getPath() : langFilesDirString), langStr, logger);
	}

	protected CommonHorseStats createCommonHorseStatsInstance() {
		cfg.addDefault("DefaultHorseSpeed", 2);
		cfg.addDefault("DefaultHorseJumpStrength", 2);
		return new CommonHorseStats(cfg.getDouble("DefaultHorseSpeed"), cfg.getDouble("DefaultHorseJumpStrength"));
	}

	protected void loadConfig() {
		langFilesDirString = cfg.getString("LangFilesDir");
	}

	protected void setConfigValues() {
		cfg.set("lang", lang.getLang());
		cfg.set("DefaultHorseSpeed", horseStats.getSpeed());
		cfg.set("DefaultHorseJumpStrength", horseStats.getJump());
		if (langFilesDirString != null) {
			cfg.set("LangFilesDir", langFilesDirString);
		}
	}

	public CommandExecutor getCmdExecutor() {
		return cmdExecutor;
	}

	public Language getLang() {
		return lang;
	}

	public CommonHorseStats getHorseStats() {
		return horseStats;
	}

}
