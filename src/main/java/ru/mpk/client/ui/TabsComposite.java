package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class TabsComposite extends Composite {

    private final CTabFolder tf;
    private final ToolBar toolBar;

    @Autowired
    private BeanFactory beanFactory;

    private static final String HELP_TITLE = "Помощь";
    private static final String ADDRESS_TITLE = "Адреса";
    private static final String CLIENTS_TITLE = "Абоненты";

    public TabsComposite(Composite parent) {
        super(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(this);

        toolBar = createToolBar();
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).grab(true, false).applyTo(toolBar);

        tf = createTabFolder();
        openHelpTab();
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tf);
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

        ToolItem itemAddress = new ToolItem(toolBar, SWT.PUSH);
        itemAddress.setText("Адреса");
        itemAddress.setImage(DImages.instance().img_Address());
        itemAddress.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openAddressTab();
            }
        });

        ToolItem itemClients = new ToolItem(toolBar, SWT.PUSH);
        itemClients.setText("Абоненты");
        itemClients.setImage(DImages.instance().img_Abonents());
        itemClients.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openAbonentsTab();
            }
        });

        return toolBar;
    }

    private CTabFolder createTabFolder() {
        CTabFolder tf = new CTabFolder(this, SWT.HORIZONTAL | SWT.BORDER);
        return tf;
    }

    private void openHelpTab() {
        CTabItem ti = new CTabItem(tf, SWT.NULL);
        ti.setText(HELP_TITLE);
        ti.setImage(DImages.instance().img_Help());
        Composite panel = new Composite(tf, SWT.NULL);
        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(panel);
        Text text = new Text(panel, SWT.READ_ONLY | SWT.SINGLE);
        text.setBackground(tf.getBackground());
        text.setText("Чтобы открыть новую вкладку, нажмите на одну из кнопок на верхней панели");
        GridDataFactory.swtDefaults()
                .grab(true, true)
                .align(SWT.CENTER, SWT.CENTER).applyTo(text);
        ti.setControl(panel);
        tf.setSelection(ti);
    }

    public void openAddressTab() {
        CTabItem ti = new CTabItem(tf, SWT.CLOSE);
        ti.setText(ADDRESS_TITLE);
        ti.setImage(DImages.instance().img_Address());
        Composite panel = new Composite(tf, SWT.NULL);
        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(panel);
        AddressesPanel addressesPanel = beanFactory.getBean(AddressesPanel.class, panel, beanFactory);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(addressesPanel);
        ti.setControl(panel);
        tf.setSelection(ti);
        addressesPanel.setFocusToLookup();
    }

    public void openAbonentsTab() {
        CTabItem ti = new CTabItem(tf, SWT.CLOSE);
        ti.setText(CLIENTS_TITLE);
        ti.setImage(DImages.instance().img_Abonents());
        Composite panel = new Composite(tf, SWT.NULL);
        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(panel);
        AbonentsPanel clientsPanel = beanFactory.getBean(AbonentsPanel.class, panel, beanFactory);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(clientsPanel);
        ti.setControl(panel);
        tf.setSelection(ti);
        clientsPanel.setFocusToTable();
    }
}
