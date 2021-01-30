package ru.mpk.client.ui;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mpk.client.DImages;
import ru.mpk.client.service.GreetingService;
import ru.mpk.client.service.dto.ParameterDto;
import ru.mpk.client.ui.dialog.AbstractDialog;
import ru.mpk.client.ui.dialog.ElementDialogListener;
import ru.mpk.client.ui.dialog.FindParameterDialog;
import ru.mpk.client.ui.dialog.ModifyParameterDialog;

import javax.annotation.Nullable;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class ParametersTable extends Composite {

    private final Table table;
    private final ToolBar toolBar;

    @Autowired
    private GreetingService boService;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private final ElementDialogListener modifyDialogListener = new ElementDialogListener() {
        @Override
        public boolean okAction(AbstractDialog dialog) {
            ModifyParameterDialog mDialog = (ModifyParameterDialog) dialog;
            try {
                String name = mDialog.getName();
                String inputValue = mDialog.getInputValue();
                log.info("Send for modify {}={}", name, inputValue);
//                boService.modifyParameter(name, inputValue);
//                ParameterDto parameterDto = boService.findParametersByNames(new String[]{name}).get(0);
//                log.info("Requested and received {}}", parameterDto);
//                refreshParameterWithNewValue(parameterDto);
            } catch (Exception e) {
                log.error("Error while modify", e);
                dialog.setErrorMessage(e.getMessage());
                return false;
            }
            return true;
        }
    };

    public ParametersTable(Composite parent) {
        super(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(this);

        toolBar = createToolBar();
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).grab(true, false).applyTo(toolBar);

        table = createTable();
        GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

        ToolItem itemAdd = new ToolItem(toolBar, SWT.PUSH);
        itemAdd.setText("Найти");
        itemAdd.setImage(DImages.instance().img_Find());
        itemAdd.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FindParameterDialog dialog = createFindParameterDialog(ParametersTable.this, toolBar.getShell());
                dialog.setListener(new ElementDialogListener() {
                    @Override
                    public boolean okAction(AbstractDialog dialog) {
                        try {
                            String[] inputNames = ((FindParameterDialog) dialog).getInputNames();
                            log.info("Sent for find {}", (Object) inputNames);
//                            List<ParameterDto> dtoList = boService.findParametersByNames(inputNames);
//                            log.info("Receive response by find request: " + dtoList);
//                            refreshParameterWithNewValue(dtoList);
                        } catch (Exception e) {
                            log.error("Error while find request", e);
                            dialog.setErrorMessage(e.getMessage());
                            return false;
                        }
                        return true;
                    }
                });
                dialog.open();
            }
        });

        ToolItem itemModify = new ToolItem(toolBar, SWT.PUSH);
        itemModify.setText("Изменить");
        itemModify.setImage(DImages.instance().img_Modify());
        itemModify.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Pair<String, String> currentRow = getCurrentRow();
                if (currentRow == null) {
                    MessageDialog.openInformation(
                            toolBar.getShell(), "Ой...", "Вы не выбрали строку для редактирования.\n" +
                                    "Выберите и попробуйте снова!");
                    return;
                }
                ModifyParameterDialog dialog = createModifyParameterDialog(ParametersTable.this, currentRow.getLeft(), currentRow.getRight(), toolBar.getShell());
                dialog.setListener(modifyDialogListener);
                dialog.open();
            }
        });
        return toolBar;
    }

    private Table createTable() {
        Table table = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        String[] titles = { " ", "Название параметра", "Значение", "Обновлено"};
        for (int i = 0; i < titles.length; i++) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
            switch (i) {
                case 1:
                    column.setWidth(300);
                    break;
                case 2:
                    column.setWidth(200);
                    break;
                case 3:
                    column.setWidth(175);
            }
        }
        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                Pair<String, String> currentRow = getCurrentRow();
                ModifyParameterDialog dialog = createModifyParameterDialog(ParametersTable.this, currentRow.getLeft(), currentRow.getRight(), toolBar.getShell());
                dialog.setListener(modifyDialogListener);
                dialog.open();
            }
        });
        return table;
    }

    @Lookup
    public ModifyParameterDialog createModifyParameterDialog(ParametersTable parametersTable, String name, String initValue, Shell shell) {
        return null;
    }

    @Lookup
    public FindParameterDialog createFindParameterDialog(ParametersTable parametersTable, Shell shell) {
        return null;
    }

    @Nullable
    public Pair<String, String> getCurrentRow() {
        int index = table.getSelectionIndex();
        if (index >= 0) {
            TableItem item = table.getItem(index);
            return new ImmutablePair<>(item.getText(1), item.getText(2));
        }
        return null;
    }

    public void refreshParameterWithNewValue(ParameterDto dto) {
        Optional<TableItem> itemOp = Stream.of(table.getItems()).filter(i -> i.getText(1).equals(dto.getName())).findFirst();
        if (itemOp.isPresent()) {
            TableItem item = itemOp.get();
            item.setText(2, dto.getValue());
            item.setText(3, dto.getDateTime().format(dateTimeFormatter));
        } else {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setImage(0, DImages.instance().img_Wheel());
            item.setText(1, dto.getName());
            item.setText(2, dto.getValue());
            item.setText(3, dto.getDateTime().format(dateTimeFormatter));
        }
    }

    public void refreshParameterWithNewValue(List<ParameterDto> dtoList) {
        dtoList.forEach(this::refreshParameterWithNewValue);
    }

    @Override
    public boolean setFocus() {
        return table.setFocus();
    }
}
