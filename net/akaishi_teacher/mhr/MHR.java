package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import net.akaishi_teacher.mhr.commands.AllTp;
import net.akaishi_teacher.mhr.commands.Despawn;
import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.mhr.commands.Init;
import net.akaishi_teacher.mhr.commands.ReloadLangFile;
import net.akaishi_teacher.mhr.commands.SetJump;
import net.akaishi_teacher.mhr.commands.SetSpeed;
import net.akaishi_teacher.mhr.commands.Spawn;
import net.akaishi_teacher.mhr.commands.Tp;
import net.akaishi_teacher.mhr.commands.func.CommandExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
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

	private HorsesControler horsesControler;

	private HorseInfoConfig horseInfoCfg;

	@Override
	public void onEnable() {
		super.onEnable();
		ConfigurationSerialization.registerClass(HorseInfo.class);

		cfg = getConfig();
		logger = getLogger();
		loadConfig();

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
		horsesControler = new HorsesControler(this);

		try {
			horseInfoCfg = createHorseInfoConfig();
			horseInfoCfg.loadConfig();
		} catch (URISyntaxException | IOException e1) {
			e1.printStackTrace();
		}

		registerCommands();

		logger.info("MineHorseRacingPlugin Enabled.");
	}



	@Override
	public void onDisable() {
		super.onDisable();
		try {
			horseInfoCfg.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setConfigValues();
		saveConfig();
		logger.info("MineHorseRacingPlugin Disabled.");
	}



	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return cmdExecutor.onCommand(sender, args);
	}



	/**
	 * コマンドを追加する処理をするメソッドです。
	 */
	protected void registerCommands() {
		cmdExecutor.addCommand(new Help(this, "", null, "This command is help command."));
		cmdExecutor.addCommand(new ReloadLangFile(this, "reloadLangFile", "mhr.reload.lang", "This command will reload the language file."));
		cmdExecutor.addCommand(new SetSpeed(this, "setspeed any", "mhr.horse.set", "This command will set speed to a horse."));
		cmdExecutor.addCommand(new SetJump(this, "setjump any", "mhr.horse.set", "This command will set jump strength to a horse."));
		cmdExecutor.addCommand(new Spawn(this, "spawn any", "mhr.horse.spawn", "This command will spawn horses."));
		cmdExecutor.addCommand(new Despawn(this, "despawn", "mhr.horse.despawn", "This command will despawn horse(s)."));
		cmdExecutor.addCommand(new AllTp(this, "alltp", "mhr.horse.tp", "Teleport a horse all."));
		cmdExecutor.addCommand(new Tp(this, "tp any", "mhr.horse.tp", "Teleport a horse."));
		cmdExecutor.addCommand(new Init(this, "init", "mhr.init", "Init MineHorseRacingPlugin"));
	}



	/**
	 * HorseInfoConfigクラスのインスタンスを生成し、戻り値として返すメソッドです。
	 * @return HorseInfoConfigクラスのインスタンス
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public HorseInfoConfig createHorseInfoConfig() throws URISyntaxException, IOException {
		URI uri = new URI(getDataFolder().toURI().getPath() + "/horseinfo.info");
		return new HorseInfoConfig(this, new File(uri.getPath()));
	}

	/**
	 * Languageクラスのインスタンスを生成し、戻り値として返すメソッドです。
	 * @return Languageクラスのインスタンス
	 * @throws URISyntaxException
	 */
	protected Language createLanguageInstance() throws URISyntaxException {
		cfg.addDefault("lang", "ja_JP");
		String langStr = cfg.getString("lang");
		URI uri = new URI(getDataFolder().toURI().getPath() + "/lang/");
		return new Language(new File(langFilesDirString == null ? uri.getPath() : langFilesDirString), langStr, logger);
	}



	/**
	 * HorseStatsクラスのインスタンスを生成し、戻り値として返すメソッドです。
	 * @return HorseStatsクラスのインスタンス
	 */
	protected CommonHorseStats createCommonHorseStatsInstance() {
		cfg.addDefault("DefaultHorseSpeed", 2);
		cfg.addDefault("DefaultHorseJumpStrength", 2);
		return new CommonHorseStats(cfg.getDouble("DefaultHorseSpeed"), cfg.getDouble("DefaultHorseJumpStrength"));
	}



	/**
	 * コンフィグをロードし、フィールド変数に代入するメソッドです。
	 */
	protected void loadConfig() {
		langFilesDirString = cfg.getString("LangFilesDir");
	}



	/**
	 * コンフィグにデータを設定する処理をするメソッドです。
	 */
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



	public HorsesControler getHorsesControler() {
		return horsesControler;
	}



	public HorseInfoConfig getHorseInfoCfg() {
		return horseInfoCfg;
	}

}

