package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;

/**
 * コマンドを実装するための抽象クラス
 * @author mozipi
 */
public abstract class AbstractCommand {

	protected String label;

	protected String pattern;

	protected String permission;

	protected String description;

	protected MHR plugin;

	public AbstractCommand(MHR plugin, String label, String pattern, String permission,
			String description) {
		super();
		this.label = label;
		this.plugin = plugin;
		this.pattern = pattern;
		this.permission = permission;
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public String getPattern() {
		return pattern;
	}

	public String getPermission() {
		return permission;
	}

	public String getDescription() {
		return description;
	}

	public int getNeedArgsLength() {
		if (pattern == null) {
			return 0;
		}
		String[] patterns = pattern.split(" ");
		int rNum = 0;
		for (String p : patterns) {
			if (p.equals("r")) {
				++rNum;
			}
		}
		return rNum;
	}

	/**
	 * 実行されたコマンドのラベルがこのクラスのラベルと一致するかどうかを判定します。
	 * @param checkStr チェックするラベルの文字列
	 * @return ラベルが一致する場合はtrue。一致しなければfalse
	 */
	public boolean isLabelMatch(String checkStr) {
		return label == null || label == "" ? true :label.equals(checkStr);
	}

	/**
	 * ラベルが無い場合は0を返すメソッドです
	 * @return labelが空文字もしくはnullの場合は0。空文字でなくnullでもない場合はtrue
	 */
	public int isNoLabel() {
		return label == null || label == "" ? 0: 1;
	}

	public abstract boolean execute(CommandSender sender, ArrayList<String> args);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCommand other = (AbstractCommand) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}

}
