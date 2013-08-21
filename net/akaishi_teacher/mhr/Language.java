package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 言語ファイルを読み込む機能を持ったクラスです。<br>
 * 言語ファイルはYAMLではなくPropertiesクラスが提供するフォーマットです。<br>
 * 言語ファイル名は国コードに従う必要はなく、[lang].langというファイルをロードします。
 * @author mozipi
 */
public class Language {

	protected File langFileDir;

	protected String lang;

	protected HashMap<String, String> localizedStringMap = new HashMap<String, String>();

	protected Logger logger;

	public Language(File langFileDir, String lang, Logger logger) {
		this.langFileDir = langFileDir;
		this.lang = lang;
		this.logger = logger;
		mkdir();
	}

	protected void mkdir() {
		//言語ファイルがあるディレクトリが存在するかどうか。
		//存在しない場合はディレクトリ生成する。
		if (!langFileDir.exists()) {
			logger.warning("the Language files directory not found!");
			logger.info("This plugin will try to make the language files directory.");
			try {
				if (langFileDir.mkdirs()) {
					logger.info("Made language files directory.");
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadLangFile() throws IOException, URISyntaxException {
		Properties prop = new Properties();
		prop.load(
				new InputStreamReader(
						new FileInputStream(
								new File(
										new URI(langFileDir.toURI().getPath() + "/" + lang + ".lang").getPath()
										)
								), "UTF-8")
				);
		for (Object obj : prop.keySet()) {
			if (obj instanceof String) {
				String key = (String) obj;
				localizedStringMap.put(key, prop.getProperty(key));
			}
		}
	}

	public String getLocalizedString(String key) {
		String g = localizedStringMap.get(key);
		if (g == null) {
			throw new NullPointerException("the Localized string not found! (Key=" + key + ")");
		}
		return g;
	}

	public String getLang() {
		return lang;
	}

}
