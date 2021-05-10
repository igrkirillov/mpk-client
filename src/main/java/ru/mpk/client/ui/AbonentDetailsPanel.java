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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.service.abonents.AbonentClient;
import ru.mpk.client.service.abonents.AbonentCreationParameters;
import ru.mpk.client.service.abonents.AbonentDto;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;
import ru.mpk.client.service.mpkaddress.MpkAddressClient;
import ru.mpk.client.service.mpkaddress.MpkAddressCreationParameters;
import ru.mpk.client.service.mpkaddress.MpkAddressDto;
import ru.mpk.client.service.nebula.NebulaUtils;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AbonentDetailsPanel extends Composite {

    private final Text fullName;
    private final AbonentsPanel parent;

    @Autowired
    private AbonentClient abonentClient;

    private AbonentDto value;

    public AbonentDetailsPanel(AbonentsPanel parent) {
        super(parent, SWT.NONE);

        this.parent = parent;

        GridLayoutFactory.fillDefaults().numColumns(3).applyTo(this);

        new CLabel(this, SWT.NONE).setText("ФИО:");
        fullName = new Text(this, SWT.BORDER | SWT.SINGLE);
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(fullName);

        Composite buttons = new Composite(this, SWT.PUSH);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(buttons);
        GridDataFactory.swtDefaults().span(3, 1).align(SWT.RIGHT, SWT.BOTTOM).applyTo(buttons);

        Button okButton = new Button(buttons, SWT.PUSH);
        okButton.setText("Сохранить");
        okButton.setImage(DImages.instance().img_Db());
        okButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                saveToDB();
                parent.back();
            }
        });
        Button backButton = new Button(buttons, SWT.PUSH);
        backButton.setText("Отмена");
        backButton.setImage(DImages.instance().img_Back());
        backButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                parent.back();
            }
        });
    }

    public void show(AbonentDto dto) {
        value = dto;
        fullName.setText(dto != null ? dto.getFullName() : "");
        fullName.setFocus();
    }

    private void saveToDB() {
        AbonentCreationParameters parameters = new AbonentCreationParameters();
        parameters.setFullName(fullName.getText());
        AbonentDto dto = abonentClient.create(parameters);
        NebulaUtils.messageInfo(getParent().getShell(), "Абонент сохранён в базу!");
        show(dto);
        parent.refreshOnTablePanel(dto);
    }
}
