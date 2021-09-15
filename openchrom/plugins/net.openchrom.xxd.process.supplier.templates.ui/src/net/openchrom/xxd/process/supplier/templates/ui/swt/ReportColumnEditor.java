/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.IChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.openchrom.xxd.process.supplier.templates.model.ReportColumns;

public class ReportColumnEditor extends Composite implements IChangeListener {

	private Table tableAvailableColumns;
	private Table tableReportColumns;
	private List<ToolItem> toolItems = new ArrayList<>();
	//
	private ReportColumns reportColumns = new ReportColumns();
	private List<String> availableColumns = new ArrayList<>();

	public ReportColumnEditor(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public ReportColumns getReportColumns() {

		return reportColumns;
	}

	public void load(String entries) {

		reportColumns.load(entries);
		updateTables();
	}

	public String save() {

		return reportColumns.save();
	}

	@Override
	public void addChangeListener(Listener listener) {

		/*
		 * Listen to changes in the table.
		 */
		addListener(tableAvailableColumns, listener);
		addListener(tableReportColumns, listener);
		/*
		 * Listen to selection of the buttons.
		 */
		for(ToolItem toolItem : toolItems) {
			toolItem.addListener(SWT.Selection, listener);
			toolItem.addListener(SWT.KeyUp, listener);
			toolItem.addListener(SWT.MouseUp, listener);
			toolItem.addListener(SWT.MouseDoubleClick, listener);
		}
	}

	private void addListener(Table table, Listener listener) {

		Control control = table;
		control.addListener(SWT.Selection, listener);
		control.addListener(SWT.KeyUp, listener);
		control.addListener(SWT.MouseUp, listener);
		control.addListener(SWT.MouseDoubleClick, listener);
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		//
		createDataSection(this);
		//
		initialize();
	}

	private void initialize() {

		updateTables();
	}

	private void createDataSection(Composite parent) {

		tableAvailableColumns = createTableAvailableColumns(parent);
		createToolbarSelectItems(parent);
		tableReportColumns = createTableReportColumns(parent);
	}

	private Table createTableAvailableColumns(Composite parent) {

		Table table = createTable(parent);
		table.setToolTipText("The following columns are available.");
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {

				addColumn();
			}
		});
		//
		return table;
	}

	private Table createTableReportColumns(Composite parent) {

		Table table = createTable(parent);
		table.setToolTipText("If no column is listed, all columns are reported.");
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {

				removeColumn();
			}
		});
		//
		return table;
	}

	private Table createTable(Composite parent) {

		Table table = new Table(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		addTableColumn(table, "Column", 500);
		//
		return table;
	}

	private void addTableColumn(Table table, String column, int width) {

		TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
		tableColumn.setText(column);
		tableColumn.setWidth(width);
	}

	private void createToolbarSelectItems(Composite parent) {

		ToolBar toolBar = new ToolBar(parent, SWT.VERTICAL | SWT.FLAT);
		//
		toolItems.add(createToolItemAdd(toolBar));
		toolItems.add(createToolItemRemove(toolBar));
		toolItems.add(createToolItemMoveUp(toolBar));
		toolItems.add(createToolItemMoveDown(toolBar));
	}

	private ToolItem createToolItemAdd(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_FORWARD, IApplicationImage.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				addColumn();
			}
		});
		//
		return toolItem;
	}

	private ToolItem createToolItemRemove(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImage.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				removeColumn();
			}
		});
		//
		return toolItem;
	}

	private ToolItem createToolItemMoveUp(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_UP_2, IApplicationImage.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				switchColumns(true);
				updateTables();
			}
		});
		//
		return toolItem;
	}

	private ToolItem createToolItemMoveDown(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_DOWN_2, IApplicationImage.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				switchColumns(false);
				updateTables();
			}
		});
		//
		return toolItem;
	}

	private void addColumn() {

		int index = tableAvailableColumns.getSelectionIndex();
		if(index >= 0 && index < availableColumns.size()) {
			reportColumns.add(availableColumns.get(index));
			updateTables();
		}
	}

	private void removeColumn() {

		int index = tableReportColumns.getSelectionIndex();
		if(index >= 0 && index < reportColumns.size()) {
			reportColumns.remove(index);
			updateTables();
		}
	}

	private void switchColumns(boolean moveUp) {

		int index = tableReportColumns.getSelectionIndex();
		int size = reportColumns.size();
		if(index >= 0 && index < size) {
			int indexSwitch = moveUp ? index - 1 : index + 1;
			if(indexSwitch >= 0 && indexSwitch < size) {
				Collections.swap(reportColumns, index, indexSwitch);
				tableReportColumns.select(indexSwitch);
			}
		}
	}

	private void updateTables() {

		/*
		 * Clear the current tables.
		 */
		disposeTableItems(tableAvailableColumns);
		tableAvailableColumns.clearAll();
		disposeTableItems(tableReportColumns);
		tableReportColumns.clearAll();
		/*
		 * Reset the available columns.
		 */
		availableColumns.clear();
		availableColumns.addAll(ReportColumns.getDefault());
		//
		for(String reportColumn : reportColumns) {
			availableColumns.remove(reportColumn);
			addTableRow(tableReportColumns, reportColumn);
		}
		//
		for(String availableColumn : availableColumns) {
			addTableRow(tableAvailableColumns, availableColumn);
		}
	}

	private void addTableRow(Table table, String text) {

		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(text);
	}

	private void disposeTableItems(Table table) {

		for(TableItem tableItem : table.getItems()) {
			tableItem.dispose();
		}
	}
}