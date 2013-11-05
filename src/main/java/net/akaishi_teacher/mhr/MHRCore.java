package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

import net.akaishi_teacher.mhr.commands.AllTeleport;
import net.akaishi_teacher.mhr.commands.AllTeleportLoc;
import net.akaishi_teacher.mhr.commands.Despawn;
import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.mhr.commands.ReloadLanguage;
import net.akaishi_teacher.mhr.commands.SetJump;
import net.akaishi_teacher.mhr.commands.SetSpeed;
import net.akaishi_teacher.mhr.commands.Spawn;
import net.akaishi_teacher.mhr.commands.Teleport;
import net.akaishi_teacher.mhr.commands.TeleportLoc;
import net.akaishi_teacher.mhr.course.MHRCourse;
import net.akaishi_teacher.mhr.course.PointCounter;
import net.akaishi_teacher.util.command.CommandExecutor;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

/**
 * Minecraftの競馬を補助するプラグインです。
 * @author mozipi
 */
public class MHRCore extends MHRFunc implements Deserializer {

	/**
	 * コマンドを実行するためのクラス
	 */
	private CommandExecutor cmdExecutor;

	/**
	 * 言語対応クラス
	 */
	private Language lang;

	/**
	 * 馬のステータスが存在するクラス
	 */
	private HorseStatus status;

	/**
	 * 馬の個別データを読み書きするためのクラス
	 */
	private ConfigurationForData horseDataConf;

	/**
	 * 馬をコントロールするためのクラス
	 */
	private HorseController controller;

	/**
	 * コース機能の処理部
	 */
	private MHRCourse mhrCourse;

	public MHRCore(Main plugin) {
		super(plugin);
	}

	@Override
	public void init() {

		//Create the CommandExecutor instance.
		cmdExecutor = new CommandExecutor();

		//Register commands.
		registerCommands();

		//Assignment controller.
		controller = new HorseController(this);

		//Server initialize schedule.
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				status.serverInit((MHRCore) getMHR(), status.getHorseDatas());
			}
		};
		//Register server initialize schedule.
		plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 40);
		//Start thread.
		plugin.getServer().getScheduler().runTaskTimer(plugin, new SetStatusThread(this), 60, 20);

		//Register event.
		plugin.getServer().getPluginManager().registerEvents(new NoDamageEvent(this), plugin);

		//Course function valid?
		plugin.getConfig().addDefault("CourseFunctionValid", true);
		if (plugin.getConfig().getBoolean("CourseFunctionValid")) {
			mhrCourse = new MHRCourse(getPlugin(), this);
			//Pre initialize.
			mhrCourse.preInit();
			//Initialize.
			mhrCourse.init();
		}

		plugin.getLogger().info("MineHorseRacingPlugin enabled.");
	}

	@Override
	public void disable() {
		//Course function valid?
		if (mhrCourse != null) {
			//Pre disable.
			mhrCourse.preDisable();
			//Disable.
			mhrCourse.disable();
		}

		//Set Configuration.
		setConfig();

		//Save configuration.
		horseDataConf.saveConfig();
		plugin.saveConfig();

		plugin.getLogger().info("MineHorseRacingPlugin disabled.");
	}


	@SuppressWarnings("unchecked")
	@Override
	public void preInit() {
		/* Configuration load state. */
		FileConfiguration config =
				plugin.getConfig();

		//Deserializes.
		deserializes();

		//Create instance of "ConfigurationForData".
		horseDataConf =
				new ConfigurationForData(plugin, "horsedatas.info", this);

		//Add defaults.
		config.addDefault("lang", "ja_JP");
		config.addDefault("Speed", 0.3);
		config.addDefault("Jump", 0.75);

		//Get language name to use.
		String langName = 
				config.getString("lang");

		//Get common horse status.
		double speed =
				config.getDouble("Speed");
		double jump =
				config.getDouble("Jump");

		//Load localized file.
		loadLocalizationFile(langName);

		//Get horse datas.
		horseDataConf.loadConfig();
		horseDataConf.getConf().addDefault("HorseDatas", new ArrayList<>());
		ArrayList<HorseData> horseDatas =
				(ArrayList<HorseData>) horseDataConf.getConf().getList("HorseDatas");

		//Assignment to the "status" variable.
		status = new HorseStatus(horseDatas, speed, jump, false);
	}

	@Override
	public void preDisable() {
		//Server end.
		status.serverEnd(this);
	}

	@Override
	public void setConfig() {
		//Set horsedatas.
		horseDataConf.getConf().set("HorseDatas", status.getHorseDatas());
		//Set common status.
		plugin.getConfig().set("Speed", status.getCommonStatus().getSpeed());
		plugin.getConfig().set("Jump", status.getCommonStatus().getJump());
		//Valid course function.
		plugin.getConfig().set("CourseFunctionValid", mhrCourse == null ? false : true);
	}

	@Override
	public void deserializes() {
		ConfigurationSerialization.registerClass(SimpleLocation.class);
		ConfigurationSerialization.registerClass(HorseData.class);
		ConfigurationSerialization.registerClass(PointCounter.class);
	}

	private void loadLocalizationFile(String langName) {
		lang = new Language(new File(plugin.getDataFolder().getAbsolutePath() + "/lang"), langName, plugin.getLogger());
		try {
			lang.loadLangFile();
		} catch (IOException | URISyntaxException e) {
			//Load default language file.
			try {
				copyDefaultLocalizationFile();
				try {
					lang.loadLangFile();
				} catch (URISyntaxException e1) {
					plugin.onDisable();
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void copyDefaultLocalizationFile() throws IOException {
		ClassLoader cl = getClass().getClassLoader();
		InputStream stream = cl.getResourceAsStream("default.txt");
		InputStreamReader isr = new InputStreamReader(stream, "UTF-8");
		lang.copyDefaultLanguage(isr);
		stream.close();
	}

	private void registerCommands() {
		cmdExecutor.addCommand(new Help(this, "", null, "Helpコマンドです。"));
		cmdExecutor.addCommand(new SetSpeed(this, "setspeed any", "mhr.horse.set", "馬の速度を設定します。"));
		cmdExecutor.addCommand(new SetJump(this, "setjump any", "mhr.horse.set", "馬のジャンプ力を設定します。"));
		cmdExecutor.addCommand(new Spawn(this, "spawn any", "mhr.horse.spawn", "馬をスポーンします。"));
		cmdExecutor.addCommand(new Despawn(this, "despawn", "mhr.horse.despawn", "馬をデスポーンします。"));
		cmdExecutor.addCommand(new Teleport(this, "tp any", "mhr.horse.tp", "馬をテレポートします。"));
		cmdExecutor.addCommand(new TeleportLoc(this, "tploc any any any any", "mhr.horse.tploc", "馬を指定した座標テレポートします。"));
		cmdExecutor.addCommand(new AllTeleport(this, "alltp", "mhr.horse.alltp", "すべての馬をテレポートします。"));
		cmdExecutor.addCommand(new AllTeleportLoc(this, "alltploc any any any", "mhr.horse.alltploc", "すべての馬を指定した座標にテレポートします。"));
		cmdExecutor.addCommand(new ReloadLanguage(this, "reloadlang", "mhr.reloadlang", "言語ファイルをリロードします。"));
	}

	/**
	 * {@link CommandExecutor}クラスのインスタンスを取得します。
	 * @return {@link CommandExecutor}のインスタンス
	 */
	public CommandExecutor getCmdExecutor() {
		return cmdExecutor;
	}

	/**
	 * {@link Language}クラスのインスタンスを取得します。
	 * @return {@link Language}クラスのインスタンス
	 */
	public Language getLang() {
		return lang;
	}

	/**
	 * {@link HorseStatus}クラスのインスタンスを取得します。
	 * @return {@link Language}クラスのインスタンス
	 */
	public HorseStatus getStatus() {
		return status;
	}

	/**
	 * {@link HorseController}クラスのインスタンスを取得します。
	 * @return {@link HorseController}クラスのインスタンス
	 */
	public HorseController getController() {
		return  controller;
	}

	/**
	 * {@link MHRCourse}クラスのインスタンスを取得します。
	 * @return {@link MHRCourse}クラスのインスタンス
	 */
	public MHRCourse getCourseFunc() {
		return mhrCourse;
	}

	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
