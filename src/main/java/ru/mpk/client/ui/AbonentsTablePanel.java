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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.service.abonents.AbonentClient;
import ru.mpk.client.service.abonents.AbonentDto;
import ru.mpk.client.service.fiasaddress.FiasAddressClient;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AbonentsTablePanel extends Composite implements InitializingBean {

    public static final String MPK = "MPK";
    private final Table table;
    private final ToolItem addItem, editItem, removeItem;
    private final AbonentsPanel parent;

    @Autowired
    private AbonentClient abonentClient;

    public AbonentsTablePanel(AbonentsPanel parent) {
        super(parent, SWT.NONE);

        this.parent = parent;

        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(this);

        ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

        addItem = new ToolItem(toolBar, SWT.PUSH);
        addItem.setText("Добавить");
        addItem.setImage(DImages.instance().img_Address());
        addItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                processAddPressed();
            }
        });

        editItem = new ToolItem(toolBar, SWT.PUSH);
        editItem.setText("Редактировать");
        editItem.setImage(DImages.instance().img_Edit());
        editItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                processEditPressed();
            }
        });

        removeItem = new ToolItem(toolBar, SWT.PUSH);
        removeItem.setText("Удалить");
        removeItem.setImage(DImages.instance().img_Remove());
        removeItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                processDeletePressed();
            }
        });

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
        GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                AbonentDto dto = (AbonentDto) table.getSelection()[0].getData();
                parent.open(dto);
            }
        });

        updateEnabledSigns();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        load();
        updateEnabledSigns();
    }

    private void clear() {
        Arrays.stream(table.getItems()).forEach(Widget::dispose);
        table.layout();
    }

    private void load() {
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

    public void refresh(AbonentDto dto) {
        for (TableItem ti : table.getItems()) {
            AbonentDto data = (AbonentDto) ti.getData();
            if (data.getUid().equals(dto.getUid())) {
                ti.setData(dto);
                decorateTableItem(ti, dto);
                break;
            }
        }
        table.redraw();
    }

    public void setFocusToTable() {
        table.setFocus();
        updateEnabledSigns();
    }

    private void updateEnabledSigns() {
        boolean empty = table.getItemCount() == 0;
        AbonentDto currentRow = !empty
                && table.getSelection() != null
                && table.getSelection().length != 0 ? (AbonentDto) table.getSelection()[0].getData() : null;
        addItem.setEnabled(true);
        editItem.setEnabled(currentRow != null);
        removeItem.setEnabled(currentRow != null);
    }

    private void processAddPressed() {
        parent.open(null);
    }

    private void processEditPressed() {
        parent.open((AbonentDto) table.getSelection()[0].getData());
    }

    private void processDeletePressed() {

    }
}
