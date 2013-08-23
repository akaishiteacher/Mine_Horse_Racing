package net.akaishi_teacher.mhr;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLSupport {

	/**
	 * 引数に渡されたYAMLConfigurationからgetConfigurationSectionメソッドを呼び、sectionNameで検索しConfigurationSectionがnullの場合に<br>
	 * 新しくSectionを生成します。
	 * @param yaml YAMLConfiguration
	 * @param sectionName Sectionの名前
	 * @param map 新しく生成するときに引数に渡すMap
	 * @return 新しくSectionを生成した場合はtrue
	 */
	public static boolean newCreateSection(YamlConfiguration yaml, String sectionName, HashMap<?, ?> map) {
		if (yaml.getConfigurationSection(sectionName) == null) {
			yaml.createSection(sectionName, map);
			return true;
		}
		return false;
	}

	/**
	 * YamlConfiguration.getList(String)メソッドの戻り値をArrayListにキャストして戻り値として返します。
	 * @param yaml YamlConfiguration
	 * @param path 取得するListのPath
	 * @return YamlConfiguration.getList(String)メソッドの戻り値をArrayListにキャストしたインスタンス
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList getArrayList(YamlConfiguration yaml, String path) {
		return (ArrayList) yaml.getList(path);
	}

	/**
	 * YAMLConfiguration.getConfigurationSection.getValues(boolean)の戻り値をHashMapにキャストして戻り値として返します。
	 * @param yaml YamlConfiguration
	 * @param sectionName Section名
	 * @param arg0 getValuesの引数
	 * @return YAMLConfiguration.getConfigurationSection.getValues(boolean)の戻り値をHashMapにキャストしたインスタンス
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap getHashMap(YamlConfiguration yaml, String sectionName, boolean arg0) {
		return (HashMap) yaml.getConfigurationSection(sectionName).getValues(arg0);
	}

}
