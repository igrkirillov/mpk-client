package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AddressDetailsPanel extends Composite {

    private final Text fullName;
    private final Text status;
    private final Button dbButton;

    private final AddressesPanel parent;

    public AddressDetailsPanel(AddressesPanel parent) {
        super(parent, SWT.NONE);

        this.parent = parent;

        GridLayoutFactory.fillDefaults().numColumns(3).applyTo(this);

        new CLabel(this, SWT.NONE).setText("Полный адрес:");
        fullName = new Text(this, SWT.BORDER | SWT.SINGLE);
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(fullName);

        new CLabel(this, SWT.NONE).setText("В базе MPK:");
        status = new Text(this, SWT.BORDER | SWT.SINGLE);
        GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(status);
        dbButton = new Button(this, SWT.PUSH);
        dbButton.setImage(DImages.instance().img_Db());
        dbButton.setText("Сохранить в базу MPK");

        CLabel clientsTitle = new CLabel(this, SWT.NONE);
        clientsTitle.setText("Клиенты");
        GridDataFactory.fillDefaults().span(3, 1).grab(true, false)
                .align(SWT.CENTER, SWT.CENTER).applyTo(clientsTitle);

        Button backButton = new Button(this, SWT.PUSH);
        backButton.setText("Назад");
        backButton.setImage(DImages.instance().img_Back());
        GridDataFactory.swtDefaults().span(3, 1).align(SWT.RIGHT, SWT.BOTTOM).applyTo(backButton);
        backButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                parent.back();
            }
        });
    }

    public void show(FiasAddressDto dto) {
        fullName.setText(dto.getFullName());
        status.setText(dto.getMpkUid() != null ? "Сохранён в базу" : "Нет в базе");
        dbButton.setEnabled(dto.getMpkUid() == null);
    }
}
