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

import java.util.stream.Stream;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class FindParameterDialog extends AbstractDialog {

    private final ParametersTable parametersTable;
    private Text nameField;

    private String[] inputNames;

    public FindParameterDialog(ParametersTable parametersTable, Shell shell) {
        super(shell);
        this.parametersTable = parametersTable;
    }

    @Override
    protected void initProperties() {
    }

    @Override
    protected Control createContentDialogArea(Composite parent) {
        getShell().setText("Поиск параметров");
        setTitle("Заполните форму");
        setMessage("Введите параметры: можно через Enter или через пробел или через запятую");
        setTitleImage(DImages.instance().img_Find72());

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
        label.setText("Параметры:");
        label.setFont(boldFont);
        GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);

        nameField = new Text(top, SWT.MULTI | SWT.BORDER);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false)
                .hint(SWT.DEFAULT, 200).applyTo(nameField);

        return top;
    }

    @Override
    protected void applyChanges() {
        inputNames = Stream.of(nameField.getText()
                .split("[\\s,\\n\\r=]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    public String[] getInputNames() {
        return inputNames;
    }
}
