package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.service.abonents.AbonentDto;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AbonentsPanel extends Composite {

    private final StackLayout stackLayout;
    private final AbonentsTablePanel abonentsTablePanel;
    private final AbonentDetailsPanel detailsPanel;

    public AbonentsPanel(Composite parent, @Autowired BeanFactory beanFactory) {
        super(parent, SWT.NONE);

        stackLayout = new StackLayout();
        setLayout(stackLayout);

        abonentsTablePanel = beanFactory.getBean(AbonentsTablePanel.class, this);
        detailsPanel = beanFactory.getBean(AbonentDetailsPanel.class, this);

        stackLayout.topControl = abonentsTablePanel;
        layout();
    }

    public void open(AbonentDto dto) {
        stackLayout.topControl = detailsPanel;
        layout();
        detailsPanel.show(dto);
    }

    public void back() {
        stackLayout.topControl = abonentsTablePanel;
        layout();
    }

    public void refreshOnTablePanel(AbonentDto dto) {
        abonentsTablePanel.refresh(dto);
    }

    public void setFocusToTable() {
        abonentsTablePanel.setFocusToTable();
    }
}
