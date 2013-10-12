package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import net.akaishi_teacher.mhr.commands.AllTp;
import net.akaishi_teacher.mhr.commands.Despawn;
import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.mhr.commands.ReloadLangFile;
import net.akaishi_teacher.mhr.commands.SetJump;
import net.akaishi_teacher.mhr.commands.SetSpeed;
import net.akaishi_teacher.mhr.commands.Spawn;
import net.akaishi_teacher.mhr.commands.Tp;
import net.akaishi_teacher.mhr.cource.MHRCource;
import net.akaishi_teacher.util.command.CommandExecuter;

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

	private CommandExecuter cmdExecuter;

	private Language lang;

	private FileConfiguration cfg;

	private CommonHorseStats horseStats;

	private String langFilesDirString;

	private HorsesControler horsesControler;

	private HorseInfoConfig horseInfoCfg;

	private MHRSetSpeedRunnable setSpeedRunnable;

	private MHRFullTimeHealRunnable fullTimeHealRunnable;

	private MHRCource cource;

	@Override
	public void onEnable() {
		super.onEnable();
		ConfigurationSerialization.registerClass(HorseInfo.class);

		cfg = getConfig();
		logger = getLogger();
		loadConfig();

		listener = new MHRListeners(this);
		setSpeedRunnable = new MHRSetSpeedRunnable(this);
		fullTimeHealRunnable = new MHRFullTimeHealRunnable(this);
		getServer().getPluginManager().registerEvents(listener, this);
		getServer().getScheduler().runTaskTimer(this, setSpeedRunnable, 65, 100);
		getServer().getScheduler().runTaskTimer(this, fullTimeHealRunnable, 65, 1);

		cmdExecuter = new CommandExecuter();
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

		cource = new MHRCource();

		registerCommands();

		Runnable r = new Runnable() {
			@Override
			public void run() {
				horsesControler.init();
			}
		};
		getServer().getScheduler().runTaskLater(this, r, 60);

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
		horsesControler.despawnHorse();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setConfigValues();
		saveConfig();
		logger.info("MineHorseRacingPlugin Disabled.");
	}



	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return cmdExecuter.onCommand(sender, args);
	}



	/**
	 * コマンドを追加する処理をするメソッドです。
	 */
	protected void registerCommands() {
		cmdExecuter.addCommand(new Help(this, "", null, "This command is help command."));
		cmdExecuter.addCommand(new ReloadLangFile(this, "reloadLangFile", "mhr.reload.lang", "This command will reload the language file."));
		cmdExecuter.addCommand(new SetSpeed(this, "setspeed any", "mhr.horse.set", "This command will set speed to a horse."));
		cmdExecuter.addCommand(new SetJump(this, "setjump any", "mhr.horse.set", "This command will set jump strength to a horse."));
		cmdExecuter.addCommand(new Spawn(this, "spawn any", "mhr.horse.spawn", "This command will spawn horses."));
		cmdExecuter.addCommand(new Despawn(this, "despawn", "mhr.horse.despawn", "This command will despawn horse(s)."));
		cmdExecuter.addCommand(new AllTp(this, "alltp", "mhr.horse.tp", "Teleport a horse all."));
		cmdExecuter.addCommand(new Tp(this, "tp any", "mhr.horse.tp", "Teleport a horse."));
		//cmdExecuter.addCommand(new Start(this, "start", "mhr.cource.start", "Start MineHorseRacing."));
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



	public CommandExecuter getCmdExecutor() {
		return cmdExecuter;
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



	public MHRCource getMHRCource() {
		return cource;
	}
}

