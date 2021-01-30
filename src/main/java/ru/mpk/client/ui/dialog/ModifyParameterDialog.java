package ru.mpk.client.ui.dialog;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.ui.ParametersTable;

import javax.annotation.Nonnull;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class ModifyParameterDialog extends AbstractDialog {

    private final ParametersTable parametersTable;
    private final String initValue;
    private Text nameField, valueField;

    private String name;
    private String inputValue;

    public ModifyParameterDialog(ParametersTable parametersTable, @Nonnull String name, String initValue, Shell shell) {
        super(shell);
        this.parametersTable = parametersTable;
        this.name = name;
        this.initValue = initValue;
    }

    @Override
    protected void initProperties() {
        nameField.setText(name != null ? name : "");
        valueField.setText(initValue != null ? initValue : "");
        nameField.setEnabled(name == null);
        if (name != null) {
            valueField.selectAll();
        }
    }

    @Override
    protected Control createContentDialogArea(Composite parent) {
        getShell().setText("Параметер");
        setTitle("Заполните форму");
        setMessage(name);
        setTitleImage(DImages.instance().img_Modify72());

        Composite top = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().margins(2, 2).numColumns(2).applyTo(top);
        GridDataFactory.swtDefaults()
                .align(SWT.FILL, SWT.TOP)
                .grab(true, true)
                .applyTo(top);

        FontDescriptor boldDescriptor = FontDescriptor.createFrom(top.getFont()).setStyle(SWT.BOLD);
        Font boldFont = boldDescriptor.createFont(top.getDisplay());

        Label label;

        label = new Label(top, SWT.NONE);
        label.setText("Параметер:");
        label.setFont(boldFont);
        GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);

        nameField = new Text(top, SWT.SINGLE | SWT.BORDER);
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER)
                .hint(300, SWT.DEFAULT).applyTo(nameField);

        label = new Label(top, SWT.NONE);
        label.setText("Значение:");
        label.setFont(boldFont);
        GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);

        valueField = new Text(top, SWT.SINGLE | SWT.BORDER);
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER)
                .hint(300, SWT.DEFAULT).applyTo(valueField);

        return top;
    }

    @Override
    protected void applyChanges() {
        inputValue = valueField.getText();
    }

    public String getInputValue() {
        return inputValue;
    }

    public String getName() {
        return name;
    }
}
