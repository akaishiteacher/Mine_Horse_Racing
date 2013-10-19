package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.mhr.status.HorseData;
import net.akaishi_teacher.mhr.status.HorseStatus;
import net.akaishi_teacher.util.command.CommandExecutor;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Minecraftの競馬を補助するプラグインです。
 * @author mozipi
 */
public class MHR {

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
	 * プラグイン
	 */
	private JavaPlugin plugin;

	public MHR(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void init() {
		//Configuration load.
		loadConfig();

		//Create the CommandExecutor instance.
		cmdExecutor = new CommandExecutor();

		//Load language file.
		lang = new Language(new File(plugin.getDataFolder().getAbsolutePath() + "/lang"), langName, plugin.getLogger());
		try {
			lang.loadLangFile();
		} catch (IOException | URISyntaxException e) {
			//Load default language file.
			try {
				ClassLoader cl = getClass().getClassLoader();
				URL fileURL = cl.getResource("default.txt");
				InputStream stream = fileURL.openStream();
				InputStreamReader isr = new InputStreamReader(stream, "UTF-8");
				lang.loadDefaultLanguage(isr);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		//Register commands.
		registerCommands();

		plugin.getLogger().info("MineHorseRacingPlugin enabled.");
	}

	public void disable() {
		plugin.getLogger().info("MineHorseRacingPlugin disabled.");
	}

	protected void loadConfig() {
		//Get language name to use.
		langName = plugin.getConfig().getString("lang", "ja_JP");

		//Get common horse status.
		double speed = plugin.getConfig().getDouble("Speed");
		double jump = plugin.getConfig().getDouble("jump");

		//Get horse datas.
		horseDataConf = new ConfigurationForData(plugin, "horsedatas.dat");
		horseDataConf.load();
		horseDataConf.getConf().addDefault("HorseDatas", new ArrayList<>());
		@SuppressWarnings("unchecked")
		ArrayList<HorseData> horseDatas =
				(ArrayList<HorseData>) horseDataConf.getConf().getList("HorseDatas");

		//Assignment to the "status" variable.
		status = new HorseStatus(horseDatas, speed, jump);
	}

	protected void registerCommands() {
		cmdExecutor.addCommand(new Help(this, "", null, "Helpコマンドです。"));
	}

	/**
	 * プラグインを取得します。
	 * @return プラグインのインスタンス
	 */
	public JavaPlugin getPlugin() {
		return plugin;
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

	private static String langName = null;

}
