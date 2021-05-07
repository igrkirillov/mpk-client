package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.service.ServerAddress;
import ru.mpk.client.ui.ParametersTable;

import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class MainWindow extends ApplicationWindow {

    private TrayItem trayItem;
    private Menu trayMenu;
    private MenuItem miShow;
    private TabsComposite tabs;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    @Autowired
    private ServerAddress serverAddress;

    private boolean healthyInitialized;
    private boolean boIsHealthy;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    public MainWindow() {
        super(null);
        addMenuBar();
        addStatusLine();
        setBlockOnOpen(false);
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.TOP);
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(root);
        GridDataFactory.swtDefaults()
                .align(SWT.FILL, SWT.TOP)
                .grab(true, true)
                .applyTo(root);

        tabs = createTabs(root);
        GridDataFactory.swtDefaults()
                .align(SWT.FILL, SWT.FILL)
                .grab(true, true)
                .applyTo(tabs);

        createMenuBar();
        createTrayItem();

        Rectangle bounds = getShell().getDisplay().getPrimaryMonitor().getBounds();
        getShell().setBounds((bounds.width - 1024) / 2, (bounds.height - 768) / 2, 1024, 768);

        return root;
    }

    private void createMenuBar() {
        addMenuBar();
        Menu menuBar = getMenuBarManager().getMenu();

        MenuItem menuItemFile = new MenuItem(menuBar, SWT.CASCADE);
        menuItemFile.setText("File");
        Menu menuFile = new Menu(menuBar);
        menuItemFile.setMenu(menuFile);

        MenuItem menuItemQuit = new MenuItem(menuFile, SWT.NONE);
        menuItemQuit.setText("Выйти");
        menuItemQuit.setImage(DImages.instance().img_Quit());
        menuItemQuit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                exitGracefully();
            }
        });
    }

    private void createTrayItem() {
        trayItem = new TrayItem(getShell().getDisplay().getSystemTray(), SWT.NONE);
        trayItem.setImage(DImages.instance().img_BO_Online());
        trayItem.setToolTipText("MPK Client");
        trayItem.addListener(SWT.DefaultSelection, event -> show());
        trayItem.addListener(SWT.MenuDetect, event -> trayMenu.setVisible(true));
        trayItem.addListener(SWT.Selection, event -> trayMenu.setVisible(true));

        trayMenu = new Menu(getShell(), SWT.POP_UP);
        miShow = new MenuItem(trayMenu, SWT.PUSH);
        miShow.setText("Показать");
        miShow.setImage(DImages.instance().img_BO_Online());
        miShow.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                show();
            }
        });

        new MenuItem(trayMenu, SWT.SEPARATOR);

        MenuItem miClose = new MenuItem(trayMenu, SWT.PUSH);
        miClose.setText("Выйти");
        miClose.setImage(DImages.instance().img_Quit());
        miClose.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                exitGracefully();
            }
        });
    }

    @Override
    public void create() {
        super.create();
        getShell().setText("MPK Client " + serverAddress.getHost() + ":" + serverAddress.getPort());
        tabs.setFocus();

        Runnable healthyUpdater = () -> {
            boolean newValue = isBoAvailable();
            if (!healthyInitialized || boIsHealthy != newValue) {
                boIsHealthy = newValue;
                healthyInitialized = true;
                updateBOStatusIndicators();
            }
        };
        healthyUpdater.run();
        scheduler.scheduleWithFixedDelay(healthyUpdater, 5000);
    }

    public void show() {
        Shell shell = getShell();
        shell.setVisible(true);
        shell.setActive();
        shell.setFocus();
        shell.setMinimized(false);
    }

    private void exitGracefully() {
        if (canHandleShellCloseEvent()) handleShellCloseEvent();
    }

    @Override
    protected void handleShellCloseEvent() {
        trayMenu.dispose();
        trayItem.dispose();
        scheduler.shutdown();
        close();
    }

    @Lookup
    public TabsComposite createTabs(Composite parent) {
        return null;
    }

    private void updateBOStatusIndicators() {
        Display.getDefault().asyncExec(() -> {
            StatusLineManager statusLineManager = getStatusLineManager();
            if (statusLineManager == null || trayItem.isDisposed()) {
                return;
            }
            if (boIsHealthy) {
                String text = "MPK Client Online";
                statusLineManager.setMessage(DImages.instance().img_GreenCircle(), text);
                Image image = DImages.instance().img_BO_Online();
                trayItem.setImage(image);
                miShow.setImage(image);
                getShell().setImage(image);
            } else {
                statusLineManager.setMessage(DImages.instance().img_RedCircle(), "BO Offline");
                Image image = DImages.instance().img_BO_OfflineImage();
                trayItem.setImage(image);
                miShow.setImage(image);
                getShell().setImage(image);
            }
        });
    }

    private boolean isBoAvailable() {
        try {
            final URL url = new URL(serverAddress.getServerUrl() + "/greeting");
            final URLConnection conn = url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
