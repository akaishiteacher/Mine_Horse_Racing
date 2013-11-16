package net.akaishi_teacher.mhr.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ChangeButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Viewer viewer;
	
	private EnumViewPanel openPanel;
	
	private EnumViewPanel closePanel;
	
	public ChangeButton(Viewer viewer, EnumViewPanel toPanel, EnumViewPanel closePanel, String text) {
		this.viewer = viewer;
		this.openPanel = toPanel;
		this.closePanel = closePanel;
		addActionListener(this);
		setText(text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		viewer.visiblePanel(openPanel);
		viewer.invisiblePanel(closePanel);
	}

}
