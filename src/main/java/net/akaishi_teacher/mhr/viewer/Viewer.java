package net.akaishi_teacher.mhr.viewer;

import java.awt.Dimension;

import javax.swing.JFrame;

import net.akaishi_teacher.mhr.MHRCore;

public final class Viewer extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Windowの大きさ
	 */
	private Dimension windowSize;

	/**
	 * メインメニューのJPanel
	 */
	private MainPanel mainPanel;

	/**
	 * 馬の状態が見れるJPanel
	 */
	private HorseStatusPanel horseStatusPanel;

	/**
	 * MHRCoreのインスタンス
	 */
	private MHRCore mhrCore;

	public Viewer(MHRCore mhr) {
		mhrCore = mhr;
		windowSize = new Dimension(640, 480);
		initialize();
	}

	public void initialize() {
		setTitle("Mine Horse Racing Plugin - Viewer");
		setSize(windowSize);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPanels();
		changePanel(EnumViewPanel.MAIN, EnumViewPanel.HORSE_STATUS);
	}

	public void changePanel(EnumViewPanel viewing, EnumViewPanel willViewing) {
		visiblePanel(willViewing);
		invisiblePanel(viewing);
	}

	public void visiblePanel(EnumViewPanel panel) {
		switch (panel) {
		case MAIN:
			mainPanel.setVisible(true);
			break;
		case HORSE_STATUS:
			horseStatusPanel.setVisible(true);
			break;
		}
	}

	public void invisiblePanel(EnumViewPanel panel) {
		switch (panel) {
		case MAIN:
			mainPanel.setVisible(false);
			break;
		case HORSE_STATUS:
			horseStatusPanel.setVisible(false);
			break;
		}
	}

	public void repaintStatus() {
		horseStatusPanel.repaintStatus();
	}
	
	public MHRCore getMHR() {
		return mhrCore;
	}

	private void setPanels() {
		mainPanel = new MainPanel(this);
		horseStatusPanel = new HorseStatusPanel(this);
		
		add(mainPanel);
		add(horseStatusPanel);
		validate();
	}

}
