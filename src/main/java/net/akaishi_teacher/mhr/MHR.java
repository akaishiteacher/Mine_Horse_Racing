package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.util.command.CommandExecutor;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.plugin.java.JavaPlugin;

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
		langName = plugin.getConfig().getString("lang", "ja_JP");
	}

	protected void registerCommands() {
		cmdExecutor.addCommand(new Help(this, "", null, "Helpコマンドです。"));
	}

	public CommandExecutor getCmdExecutor() {
		return cmdExecutor;
	}

	public Language getLang() {
		return lang;
	}

	private static String langName = null;

}
