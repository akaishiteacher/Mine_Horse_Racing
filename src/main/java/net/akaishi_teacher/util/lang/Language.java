package net.akaishi_teacher.util.lang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 言語ファイルを読み込む機能を持ったクラスです。<br>
 * 言語ファイルはYAMLではなくPropertiesクラスが提供するフォーマットです。<br>
 * 言語ファイル名は国コードに従う必要はなく、[lang].langというファイルをロードします。
 * @author mozipi
 * @version 1.1.0
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

	public Language(File langFileDir, String lang) {
		this.langFileDir = langFileDir;
		this.lang = lang;
		mkdir();
	}



	/**
	 * ディレクトリが存在しない場合、ディレクトリを生成します。
	 */
	protected void mkdir() {
		//言語ファイルがあるディレクトリが存在するかどうか。
		//存在しない場合はディレクトリ生成する。
		if (!langFileDir.exists()) {
			System.err.println("The language file directory not found!");
			System.out.println("This plugin will try make the language file directory.");
			try {
				if (langFileDir.mkdirs()) {
					System.out.println("Made the language file directory.");
				} else {
					System.err.println("This plugin tried make the language file directory. but I couldn't.");
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
	 * 言語ファイルが見つからなかった場合に、Streamを直接渡して言語ファイルをコピーします。
	 * @param stream 言語ファイルのInputStreamReader
	 * @throws IOException
	 */
	public void copyDefaultLanguage(InputStreamReader stream) throws IOException {
		//Create streams.
		BufferedReader br = new BufferedReader(stream);
		File file = new File(langFileDir, "/" + lang + ".lang");
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);

		//Write file.
		String str = null;
		while ((str = br.readLine()) != null) {
			bw.write(str);
			bw.newLine();
		}

		//Close streams.
		br.close();
		bw.close();
		osw.close();
		fos.close();
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
