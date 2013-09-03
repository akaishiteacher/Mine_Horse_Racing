package net.akaishi_teacher.mhr;

import org.bukkit.event.Listener;

/**
 * MineHorseRacingPluginのイベントリスナー&スレッド用クラス
 * @author mozipi
 */
public final class MHRListeners implements Listener {

	@SuppressWarnings("unused")
	private MHR plugin;

	public MHRListeners(MHR plugin) {
		super();
		this.plugin = plugin;
	}

}
