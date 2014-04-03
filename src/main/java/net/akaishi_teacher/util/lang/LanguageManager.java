package net.akaishi_teacher.util.lang;

import java.util.HashMap;

import net.akaishi_teacher.util.lang.Language;

/**
 * 多言語を実現できるようにしたクラスです。<br>
 * Languageクラスのみでは一つの言語しか保持できませんが、このクラスではLanguageクラスを操作し<br>
 * ユーザーにハイレベルなメソッドを提供します。
 * @author mozipi
 * @version 1.0.0
 */
public class LanguageManager {

	/**
	 * 言語マップ
	 */
	protected HashMap<String, Language> langMap = new HashMap<>();

	/**
	 * デフォルトの言語
	 */
	protected Language defaultLang;

	public LanguageManager(Language defaultLang) {
		this.defaultLang = defaultLang;
	}

	/**
	 * 言語を追加します。
	 * @param lang 言語情報が入ったインスタンス
	 */
	public void addLang(Language lang) {
		langMap.put(lang.getLang(), lang);
	}

	/**
	 * 指定された言語情報を削除します。
	 * @param lang 削除したい言語情報のインスタンス
	 */
	public void removeLang(Language lang) {
		removeLang(lang.getLang());
	}

	/**
	 * 指定された言語情報を削除します。
	 * @param lang 削除したい言語名
	 */
	public void removeLang(String langName) {
		langMap.remove(langName);
	}

	/**
	 * 指定された言語を保持しているかを返します。
	 * @param lang 検査したい言語
	 * @return 指定された言語を保持している場合はtrue
	 */
	public boolean hasLang(Language lang) {
		return hasLang(lang.getLang());
	}

	/**
	 * 指定された言語を保持しているかを返します。
	 * @param lang 検査したい言語
	 * @return 指定された言語を保持している場合はtrue
	 */
	public boolean hasLang(String langName) {
		return langMap.containsKey(langName);
	}

	/**
	 * ローカライズされた文字列を返します。<br>
	 * 指定された言語名の言語情報が存在しない場合はデフォルトの文字列を返します。
	 * @param langName 言語名
	 * @param key 取得する文字列のキー
	 * @return ローカライズされた文字列。指定された言語名の言語情報が存在しない場合はデフォルトの文字列
	 */
	public String get(String langName, String key) {
		if (langMap.containsKey(langName)) {
			return langMap.get(langName).get(key);
		} else {
			return defaultLang.get(key);
		}
	}

	/**
	 * ローカライズされた文字列を返します。<br>
	 * 指定された言語名の言語情報が存在しない場合はデフォルトの文字列を返します。
	 * @param lang 言語情報
	 * @param key 取得する文字列のキー
	 * @return ローカライズされた文字列。指定された言語名の言語情報が存在しない場合はデフォルトの文字列
	 */
	public String get(Language lang, String key) {
		if (langMap.containsKey(lang.getLang())) {
			return langMap.get(lang.getLang()).get(key);
		} else {
			return defaultLang.get(key);
		}
	}

	/**
	 * ファイル名から拡張子を取り除きます、
	 * @param filename 元のファイル名
	 * @return 拡張子を取り除いたファイル名
	 */
	public static String removeFileExtension(String filename) {
		int lastDotPos = filename.lastIndexOf('.');
		if (lastDotPos == -1) {
			return filename;
		} else if (lastDotPos == 0) {
			return filename;
		} else {
			return filename.substring(0, lastDotPos);
		}
	}

}
