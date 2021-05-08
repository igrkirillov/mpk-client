package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
public class LoaderInfoPanel extends Composite {

    private final StackLayout stackLayout;
    private final ProgressBar pb;
    private final Text text;

    public LoaderInfoPanel(Composite parent) {
        super(parent, SWT.NONE);

        stackLayout = new StackLayout();
        setLayout(stackLayout);

        pb = new ProgressBar(this, SWT.HORIZONTAL | SWT.INDETERMINATE);
        text = new Text(this, SWT.NONE | SWT.SINGLE);

        stackLayout.topControl = pb;
        layout();
    }

    public void setProgress() {
        stackLayout.topControl = pb;
        layout();
        pb.update();
        update();
    }

    public void setText(String info) {
        text.setText(info);
        stackLayout.topControl = text;
        layout();
    }
}
