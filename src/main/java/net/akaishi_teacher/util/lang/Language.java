package net.akaishi_teacher.util.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 言語ファイルを読み込む機能を持ったクラスです。<br>
 * 言語ファイルはYAMLではなくPropertiesクラスが提供するフォーマットです。<br>
 * 言語ファイル名は国コードに従う必要はなく、[lang].langというファイルをロードします。
 * @author mozipi
 */
public class Language {

	/**
	 * 言語ファイルがあるディレクトリ
	 */
	protected File langFileDir;

	/**
	 * 言語名
	 */
	protected String lang;

	/**
	 * ローカライズされた文字列のマップ
	 */
	protected HashMap<String, String> localizedStringMap = new HashMap<String, String>();

	/**
	 * ログ出力のためのインスタンスが格納された変数
	 */
	protected Logger logger;

	public Language(File langFileDir, String lang, Logger logger) {
		this.langFileDir = langFileDir;
		this.lang = lang;
		this.logger = logger;
		mkdir();
	}



	/**
	 * ディレクトリが存在しない場合、ディレクトリを生成します。
	 */
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



	/**
	 * 言語ファイルを読み込みます。読み込む前にlocalizedStringMapのclearメソッドを呼び出します。
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void loadLangFile() throws IOException, URISyntaxException {
		localizedStringMap.clear();
		Properties prop = new Properties();
		URI u = new URI(langFileDir.toURI().getPath() + "/" + lang + ".lang");
		File f = new File(u.getPath());
		FileInputStream fi = new FileInputStream(f);
		InputStreamReader isr = new InputStreamReader(fi, "UTF-8");
		prop.load(isr);
		for (Object obj : prop.keySet()) {
			if (obj instanceof String) {
				String key = (String) obj;
				localizedStringMap.put(key, prop.getProperty(key));
			}
		}
		fi.close();
		isr.close();
	}



	/**
	 * 言語ファイルが見つからなかった場合に、Streamを直接渡して言語ファイルを読み込みます。
	 * @param stream 言語ファイルのInputStreamReader
	 * @throws IOException
	 */
	public void loadDefaultLanguage(InputStreamReader stream) throws IOException {
		localizedStringMap.clear();
		Properties prop = new Properties();
		BufferedReader br = new BufferedReader(stream);
		prop.load(br);
		for (Object obj : prop.keySet()) {
			if (obj instanceof String) {
				String key = (String) obj;
				localizedStringMap.put(key, prop.getProperty(key));
			}
		}
		br.close();
		stream.close();
	}



	/**
	 * ローカライズされた文字列を取得します。
	 * @param key ローカライズされた文字列のキー
	 * @return キーをもとに取得したローカライズされた文字列
	 */
	public String get(String key) {
		String g = localizedStringMap.get(key);
		if (g == null) {
			throw new NullPointerException("The localized string not found! (Key=" + key + ")");
		}
		return g;
	}


	/**
	 * 言語名を取得します。
	 * @return 言語名
	 */
	public String getLang() {
		return lang;
	}



	/**
	 * 言語ファイルの変数定義を指定した文字列に置き換えます。<br>
	 * 変数定義は&lt;%引数名%&gt;です。
	 * @param str 置き換え元の文字列
	 * @param argsMap 変数定義名と置き換える文字列のマップ
	 * @return 置き換えた文字列
	 */
	public static String replaceArgs(final String str, final Map<String, String> argsMap) {
		String vstr = str;
		Iterator<String> itKeys = argsMap.keySet().iterator();
		Iterator<String> itValues = argsMap.values().iterator();
		while (itKeys.hasNext()) {
			String key = itKeys.next();
			String value = itValues.next();
			vstr = vstr.replaceAll("<%" + key + "%>", value);
		}
		return vstr;
	}

}
