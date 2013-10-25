package net.akaishi_teacher.mhr.course;

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
		//Add defaults.
		mhr.getPlugin().getConfig().addDefault("IntervalOfCheckWalk", 1);
		
		//Get interval.
		int interval = mhr.getPlugin().getConfig().getInt("IntervalOfCheckWalk");
		
		//Register the check walking thread.
		registerCheckWalkingThread(interval);
	}

	@Override
	public void disable() {
		mhr.getPlugin().getLogger().info("MineHorseRacing course function disabled!");
	}

	@Override
	public void preDisable() {

	}


	@Override
	public void setConfig() {

	}

	@Override
	public void deserializes() {
	}

	protected void registerCheckWalkingThread(int interval) {
		//Register the check walking thread.
		mhr.getPlugin().getServer().getScheduler().runTaskTimer(mhr.getPlugin(), new CheckWalkingThread(mhr, interval), 0, 0);
	}

	/**
	 * MineHorseRacingPluginの処理部のクラスのインスタンスを返します。
	 * @return MineHorseRacingPluginの処理部のクラスのインスタンス
	 */
	public MHRCore getMHR() {
		return mhr;
	}

}
