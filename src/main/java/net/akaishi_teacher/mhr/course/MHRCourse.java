package net.akaishi_teacher.mhr.course;

import org.bukkit.configuration.file.FileConfiguration;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.MHRFunc;
import net.akaishi_teacher.mhr.Main;
import net.akaishi_teacher.mhr.config.Deserializer;
import net.akaishi_teacher.mhr.course.thread.CheckWalkingThread;

/**
 * MineHorseRacingのコース機能の処理部です。
 * @author mozipi
 */
public final class MHRCourse extends MHRFunc implements Deserializer {

	/**
	 * MHRCoreクラスのインスタンス
	 */
	private MHRCore mhr;

	/**
	 * 馬が歩いているブロックを取得し、walkメソッドを呼び出すスケジュールタスク
	 */
	private CheckWalkingThread checkWalkingThread;
	
	public MHRCourse(Main plugin, MHRCore mhr) {
		super(plugin);
		this.mhr = mhr;
	}

	@Override
	public void init() {
		mhr.getPlugin().getLogger().info("MineHorseRacing course function enabled!");
	}

	@Override
	public void preInit() {
		FileConfiguration config = mhr.getPlugin().getConfig();
		
		//Add defaults.
		config.addDefault("IntervalOfCheckWalk", 1);
		
		//Get interval.
		int interval = config.getInt("IntervalOfCheckWalk");
		
		//Register the check walking thread.
		registerCheckWalkingThread(interval);
	}

	@Override
	public void disable() {
		//Set configuration.
		setConfig();
		
		mhr.getPlugin().getLogger().info("MineHorseRacing course function disabled!");
	}

	@Override
	public void preDisable() {

	}


	@Override
	public void setConfig() {
		FileConfiguration config = mhr.getPlugin().getConfig();
		
		//Set interval.
		config.set("IntervalOfCheckWalk", checkWalkingThread.interval);
	}

	@Override
	public void deserializes() {
	}

	protected void registerCheckWalkingThread(int interval) {
		checkWalkingThread = new CheckWalkingThread(mhr, interval);
		//Register the check walking thread.
		mhr.getPlugin().getServer().getScheduler().runTaskTimer(mhr.getPlugin(), checkWalkingThread, 0, 0);
	}

	/**
	 * MineHorseRacingPluginの処理部のクラスのインスタンスを返します。
	 * @return MineHorseRacingPluginの処理部のクラスのインスタンス
	 */
	public MHRCore getMHR() {
		return mhr;
	}

}
