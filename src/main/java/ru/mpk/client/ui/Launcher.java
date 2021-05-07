package ru.mpk.client.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mpk.client.DImages;
import ru.mpk.client.conf.SpringConfiguration;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) {
        Display display = new Display();
        final Image image = new Image(display, 300, 300);
        GC gc = new GC(image);
        gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        gc.fillRectangle(image.getBounds());
        gc.drawText("MPK Client", 10, 10);
        gc.drawImage(DImages.instance().img_BO128(), (300-128)/2, (300-128)/2);
        gc.dispose();
        final Shell splash = new Shell(SWT.ON_TOP);
        final ProgressBar bar = new ProgressBar(splash, SWT.INDETERMINATE);
        Label label = new Label(splash, SWT.NONE);
        label.setImage(image);
        FormLayout layout = new FormLayout();
        splash.setLayout(layout);
        FormData labelData = new FormData();
        labelData.right = new FormAttachment(100, 0);
        labelData.bottom = new FormAttachment(100, 0);
        label.setLayoutData(labelData);
        FormData progressData = new FormData();
        progressData.left = new FormAttachment(0, 5);
        progressData.right = new FormAttachment(100, -5);
        progressData.bottom = new FormAttachment(100, -5);
        bar.setLayoutData(progressData);
        splash.pack();
        Rectangle splashRect = splash.getBounds();
        Rectangle displayRect = display.getBounds();
        int x = (displayRect.width - splashRect.width) / 2;
        int y = (displayRect.height - splashRect.height) / 2;
        splash.setLocation(x, y);
        splash.open();

        Shell[] shells = new Shell[] {splash};

        SpringStarter springStarter = new SpringStarter(args, display, shells, splash, image);
        springStarter.start();

        while (!shells[0].isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private static class SpringStarter extends Thread {
        private final String[] args;
        private final Display display;
        private final Shell[] shells;
        private final Shell splash;
        private final Image image;
        private ConfigurableApplicationContext springContext;
        public SpringStarter(String[] args, Display display, Shell[] shells, Shell splash, Image image) {
            this.args = args;
            this.display = display;
            this.shells = shells;
            this.splash = splash;
            this.image = image;
        }
        @Override
        public void run() {
            SpringApplication app = new SpringApplication(SpringConfiguration.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            ConfigurableApplicationContext springContext = app.run(args);

            display.asyncExec(new Runnable() {
                public void run() {
                    MainWindow window = springContext.getBean(MainWindow.class);

                    window.open();
                    shells[0] = window.getShell();

                    splash.close();
                    image.dispose();
                }
            });
        }
    }
}
