package ru.mpk.client.service.nebula;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TrayMessage extends PopupDialog {
	
	private String text;
	
	private Label lText;

	public TrayMessage(Shell shell, String text) {
		super(shell, SWT.NONE, false, false, false, false, false, null, "Сообщение");
		this.text = text;
	}
	
	protected Control createDialogArea(Composite parent) {  
		Composite top = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(top);
		top.setLayout(new GridLayout(1, false));
		
		lText = new Label(top, SWT.NONE);
		lText.setText(text);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lText);
		return top;
	}
	
	protected Control createContents(Composite parent) {
		Control control =  super.createContents(parent);
		return control;
	}
	
	protected void adjustBounds() {
		Rectangle rectWindow = getParentShell().getBounds();
		Point size = getShell().getSize();
        getShell().setLocation(rectWindow.x + (rectWindow.width-size.x)/2, rectWindow.y + 2);
	}
	
	public boolean close() {
		return super.close();
	}
}
