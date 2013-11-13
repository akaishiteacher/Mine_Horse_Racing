package net.akaishi_teacher.mhr.common;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationForData {

	protected JavaPlugin plugin;

	protected String fileName;

	protected YamlConfiguration conf;

	protected Deserializer deserializer;
	
	public ConfigurationForData(JavaPlugin plugin, String fileName, Deserializer deserializer) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.deserializer = deserializer;
	}

	/**
	 * {@link YamlConfiguration}のインスタンスを生成し、読み込んで変数に格納します。
	 */
	public void loadConfig() {
		File file =
				new File(plugin.getDataFolder().getAbsolutePath() + "/" + fileName);
		if (conf == null) {
			//Make file.
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			deserializer.deserializes();
			conf = YamlConfiguration.loadConfiguration(file);
		} else {
			try {
				conf.load(file);
			} catch (IOException
					| InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * セーブします。
	 */
	public void saveConfig() {
		File file =
				new File(plugin.getDataFolder().getAbsolutePath() + "/" + fileName);
		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@link YamlConfiguration}クラスのインスタンスを返します。
	 * @return {@link YamlConfiguration}クラスのインスタンス
	 */
	public YamlConfiguration getConf() {
		return conf;
	}

}
