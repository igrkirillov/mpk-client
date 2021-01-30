package ru.mpk.client.ui.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import ru.mpk.client.utils.Lazy;

public abstract class AbstractDialog extends TitleAreaDialog {

	private ElementDialogListener listener;

	public AbstractDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	protected Control createButtonBar(Composite parent) {
    	Composite composite = new Composite(parent, SWT.NONE);
    	GridLayout layout = new GridLayout(2, false);
    	layout.marginWidth = 0;
    	layout.marginHeight = 0;
    	layout.horizontalSpacing = 0;
    	composite.setLayout(layout);
    	composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    	composite.setFont(parent.getFont());

		// create help control if needed
        if (isHelpAvailable()) {
        	Control helpControl = createHelpControl(composite);
        	((GridData) helpControl.getLayoutData()).horizontalIndent = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		}

        Composite buttonSection = createButtonBar(composite, GridData.HORIZONTAL_ALIGN_BEGINNING);
		createButtonsForButtonBarLeft(buttonSection);

		buttonSection = createButtonBar(composite, GridData.HORIZONTAL_ALIGN_END);
		createButtonsForButtonBar(buttonSection);
        ((GridData) buttonSection.getLayoutData()).grabExcessHorizontalSpace = true;

        return composite;
	}
	private Composite createButtonBar(Composite parent, int horizAlign) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font
		// size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 0; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(horizAlign | GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
		
		return composite;
	}

	protected void createButtonsForButtonBarLeft(Composite parent) {}
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstantsLocal.OK_ID,
				IDialogConstantsLocal.OK_LABEL, true);
		createButton(parent, IDialogConstantsLocal.CANCEL_ID,
				IDialogConstantsLocal.CANCEL_LABEL, false);
	}

	/**
	 * Выполняется после конструирования формы перед самым открытием.
	 * Использовать для инициализации контролов необходимыми значениями.
	 */
	protected abstract void initProperties();

	/**
	 * Преопределение данного метода позволит формировать область диалога, расположенную между
	 * шапкой и панелью кнопок.
	 * @param parent
	 * @return Должен возвращать контрол, который содержит сформированную область.
	 */
	protected abstract Control createContentDialogArea(Composite parent);

    protected final Control createDialogArea(Composite parent) {
        return createContentDialogArea((Composite) super.createDialogArea(parent));
    }

    /**
	 * Предоставляет точку для входа после создания окна и перед открытием и запуском цикла обработки
	 * событий.
	 */
	protected void constrainShellSize() {
		super.constrainShellSize();
		initProperties();
	}

	/**
	 * Вызывается при нажатии кнопки Ок перед уведомлением слушателей событий диалога
	 */
	protected abstract void applyChanges();

	protected void okPressed() {
		applyChanges();
		if (listener != null) {
			if (listener.okAction(this)) {
				super.okPressed();
			}
		} else {
			super.okPressed();
		}
	}

	public ElementDialogListener getListener() {
		return listener;
	}
	public void setListener(ElementDialogListener listener) {
		this.listener = listener;
	}

	private Lazy<Listener> lFilter =
			new Lazy(() -> new Listener() {
				public void handleEvent(Event event) {
					if (event.character == SWT.CR && (event.stateMask & SWT.CTRL) == SWT.CTRL) {
						if (event.widget instanceof Control) {
							Control control = (Control)event.widget;
							do {
								if (control instanceof Shell) {
									if (getShell() == control) {
										Button defaultButton = getShell().getDefaultButton();
										if (defaultButton != null) {
											defaultButton.notifyListeners(
													SWT.Selection, event);
										}
									}
									break;
								} else {
									control = control.getParent();
								}
							} while (control != null);
						}
					}
				}
			});

	public void create() {
		super.create();
		getShell().getDisplay().addFilter(SWT.KeyDown, lFilter.get());
		getShell().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getShell().getDisplay().removeFilter(SWT.KeyDown, lFilter.get());
				dispose();
                if (listener != null) {
                    listener.closed();
                }
			}
		});
	}

	/**
	 * Если переопределить этот метод в подклассах, то будут сохранятся параметры диалога.
	 * @return Глобальный узел настроек.
	 */
	protected IDialogSettings getDialogSettings() {
	    return null;
	}
    protected IDialogSettings getDialogBoundsSettings() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
            IDialogSettings localSettings = settings.getSection(this.getClass().getSimpleName());
            if (localSettings == null) localSettings = settings.addNewSection(this.getClass().getSimpleName());
            return localSettings;
        } else {
            return null;
        }
    }

	protected void dispose() {}

    protected static Composite createTop(Composite parent, int columnCount) {
        Composite top = new Composite(parent, SWT.NONE);
        top.setLayout(new GridLayout(columnCount, false));
        GridDataFactory.fillDefaults().grab(true, true).applyTo(top);
        return top;
    }
}