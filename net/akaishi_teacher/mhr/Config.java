package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Config {

	protected MHR plugin;

	/**
	 * Configファイル
	 */
	protected File configFile;

	/**
	 * YAMLConfigurationのインスタンス
	 */
	protected YamlConfiguration yamlConf;

	public Config(MHR plugin, File config) throws IOException {
		this.plugin = plugin;
		this.configFile = config;
		this.configFile.createNewFile();
		this.yamlConf = YamlConfiguration.loadConfiguration(this.configFile);
	}

	public abstract void loadConfig() throws IOException;

	public abstract void saveConfig() throws IOException;

	public abstract void reloadConfig() throws IOException;

	public YamlConfiguration getYamlConf() {
		return yamlConf;
	}

}
