package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
import ru.mpk.client.service.fiasaddress.FiasAddressClient;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AddressLookupPanel extends Composite {

    public static final String MPK = "MPK";
    private final Button button;
    private final LoaderInfoPanel lp;
    private final Text text;
    private final Table table;

    @Autowired
    private FiasAddressClient fiasAddressClient;

    public AddressLookupPanel(AddressesPanel parent) {
        super(parent, SWT.NONE);

        GridLayoutFactory.fillDefaults().numColumns(3).applyTo(this);

        CLabel label = new CLabel(this, SWT.NONE);
        label.setText("Запрос:");
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(label);

        text = new Text(this, SWT.SINGLE | SWT.BORDER);
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
                    findPressed();
                    text.setFocus();
                }
            }
        });
        GridDataFactory.fillDefaults().grab(true, false).applyTo(text);

        button = new Button(this, SWT.PUSH);
        button.setText("Найти");
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                findPressed();
                text.setFocus();
            }
        });

        lp = new LoaderInfoPanel(this);
        GridDataFactory.fillDefaults().span(3, 1).grab(true, false).applyTo(lp);
        lp.setText("Найдено 0 адресов");

        table = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
        table.setHeaderVisible(false);
        String[] titles = { " ", "Полный адрес"};
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
        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                FiasAddressDto dto = (FiasAddressDto) table.getSelection()[0].getData();
                parent.open(dto);
            }
        });
    }

    private void clear() {
        Arrays.stream(table.getItems()).forEach(Widget::dispose);
        table.layout();
    }


    private void findPressed() {
        clear();
        lp.setProgress();;
        String query = text.getText();
        table.clearAll();
        Display.getDefault().asyncExec(() -> {
            List<FiasAddressDto> dataList = null;
            try {
                dataList = fiasAddressClient.getList(query);
                dataList.forEach(dto -> {
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setImage(0, dto.getMpkUid() != null ? DImages.instance().img_Db() : DImages.instance().img_Fias());
                    item.setText(1, dto.getFullName());
                    item.setData(dto);
                });
            } finally {
                lp.setText(String.format("Найдено %d адресов", dataList != null ? dataList.size() : 0));
            }
        });
    }

    public void setFocusToText() {
        text.setFocus();
    }
}
