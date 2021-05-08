package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.service.fiasaddress.FiasAddressDto;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class AddressesPanel extends Composite {

    private final StackLayout stackLayout;
    private final AddressLookupPanel lookupPanel;
    private final AddressDetailsPanel detailsPanel;

    public AddressesPanel(Composite parent, @Autowired BeanFactory beanFactory) {
        super(parent, SWT.NONE);

        stackLayout = new StackLayout();
        setLayout(stackLayout);

        lookupPanel = beanFactory.getBean(AddressLookupPanel.class, this);
        detailsPanel = beanFactory.getBean(AddressDetailsPanel.class, this);

        stackLayout.topControl = lookupPanel;
        layout();
    }

    public void open(FiasAddressDto dto) {
        stackLayout.topControl = detailsPanel;
        layout();
        detailsPanel.show(dto);
    }

    public void back() {
        stackLayout.topControl = lookupPanel;
        layout();
    }
}
