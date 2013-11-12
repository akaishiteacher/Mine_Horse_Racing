package net.akaishi_teacher.mhr;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Pluginの実行部分。詳細は{@link MHRCore MHR}クラスを参照してください。
 * @author mozipi
 * 
 * ----- 以下の文は、消去せず必ず含めてください。 -----
 * Copyright 2013 mozipi,akaichi_teacher,ilicodaisuki
 *
 * Apache License Version 2.0（「本ライセンス」）に基づいてライセンスされます。あなたがこのファイルを使用するためには、本ライセンスに従わなければなりません。本ライセンスのコピーは下記の場所から入手できます。
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 適用される法律または書面での同意によって命じられない限り、本ライセンスに基づいて頒布されるソフトウェアは、明示黙示を問わず、いかなる保証も条件もなしに「現状のまま」頒布されます。本ライセンスでの権利と制限を規定した文言については、本ライセンスを参照してください。
 */
public final class Main extends JavaPlugin {

	/**
	 * MineHorseRacingの処理部
	 */
	private MHRCore mhr;

	@Override
	public void onDisable() {
		super.onDisable();
		mhr.preDisable();
		mhr.disable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		mhr = new MHRCore(this);
		mhr.preInit();
		mhr.init();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return mhr.getCmdExecutor().onCommand(sender, args);
	}

}
