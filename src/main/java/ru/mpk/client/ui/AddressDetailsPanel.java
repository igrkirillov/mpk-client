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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.service.abonents.AbonentClient;
import ru.mpk.client.service.abonents.AbonentDto;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;
import ru.mpk.client.service.mpkaddress.MpkAddressClient;
import ru.mpk.client.service.mpkaddress.MpkAddressCreationParameters;
import ru.mpk.client.service.mpkaddress.MpkAddressDto;
import ru.mpk.client.service.nebula.NebulaUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AddressDetailsPanel extends Composite {

    private final Text fullName;
    private final Text status;
    private final Button dbButton;
    private final AddressesPanel parent;
    private final Table table;

    @Autowired
    private MpkAddressClient mpkAddressClient;
    @Autowired
    private AbonentClient abonentClient;

    private FiasAddressDto value;

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
        dbButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                saveToDB();
            }
        });

        CLabel abonentsTitle = new CLabel(this, SWT.NONE);
        abonentsTitle.setText("Абоненты, прикреплённые к адресу");
        GridDataFactory.fillDefaults().span(3, 1).grab(true, false)
                .align(SWT.CENTER, SWT.CENTER).applyTo(abonentsTitle);

        table = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
        table.setHeaderVisible(false);
        String[] titles = { " ", "ФИО"};
        for (int i = 0; i < titles.length; i++) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
            column.setResizable(true);
            switch (i) {
                case 0:
                    column.setWidth(30);
                    break;
                case 1:
                    column.setWidth(450);
                    break;
            }
        }
        GridDataFactory.fillDefaults().span(3, 1).grab(true, true).applyTo(table);

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
        value = dto;
        fullName.setText(dto.getFullName());
        status.setText(dto.getMpkUid() != null ? "Сохранён в базу" : "Нет в базе");
        dbButton.setEnabled(dto.getMpkUid() == null);

        if (dto.getMpkUid() != null) {
            loadAbonents();
        }
    }

    private void loadAbonents() {
        Arrays.stream(table.getItems()).forEach(Widget::dispose);
        List<AbonentDto> list = abonentClient.getList();
        list.forEach(dto -> {
            TableItem ti = new TableItem(table, SWT.NONE);
            decorateTableItem(ti, dto);
        });
    }

    private void decorateTableItem(TableItem ti, AbonentDto dto) {
        ti.setImage(0, DImages.instance().img_Abonent());
        ti.setText(1, dto.getFullName());
        ti.setData(dto);
    }

    private void saveToDB() {
        MpkAddressCreationParameters parameters = new MpkAddressCreationParameters();
        parameters.setFiasUid(value.getFiasUid());
        parameters.setFullName(value.getFullName());
        parameters.setZip(value.getZip());
        MpkAddressDto dto = mpkAddressClient.create(parameters);
        value.setMpkUid(dto.getUid());
        show(value);
        parent.refreshOnLookupPanel(value);
        NebulaUtils.messageInfo(parent.getShell(), "Адрес сохранён в базу успешно!");
    }
}
