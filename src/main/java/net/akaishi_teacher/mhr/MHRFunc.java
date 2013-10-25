package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.config.Deserializer;

public abstract class MHRFunc implements Deserializer {

	/**
	 * The Plugin.
	 */
	protected Main plugin;
	
	public MHRFunc(Main plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * This method is called when plug-in is initialize.
	 */
	public abstract void init();
	
	/**
	 * This method is called when plug-in is initialize(before init).
	 */
	public abstract void preInit();
	
	/**
	 * This method is called when plug-in disabled.
	 */
	public abstract void disable();
	
	/**
	 * This method is called when plug-in disabled(before disable).
	 */
	public abstract void preDisable();
	
	/**
	 * Will configuration set.
	 */
	public abstract void setConfig();
	
	/**
	 * Get instance of "MHRFunc" class.
	 * @return The instance of "MHRFunc" class.
	 */
	public MHRFunc getMHR() {
		return this;
	}
	
	/**
	 * Get instance of "Main" class.
	 * @return The instance of "Main" class.
	 */
	public Main getPlugin() {
		return plugin;
	}
	
}
