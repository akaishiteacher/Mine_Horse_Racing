package net.akaishi_teacher.mhr.viewer;

import javax.swing.JPanel;

public abstract class ViewerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Viewer viewer;

	public ViewerPanel(Viewer viewer) {
		this.viewer = viewer;
		setSize(viewer.getSize());
	}

}
