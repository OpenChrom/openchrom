/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - search bar
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.ui.swt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.IChangeListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.part.support.DataUpdateSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;
import net.openchrom.chromatogram.xxd.report.supplier.csv.ui.Activator;

public class ReportColumnEditor extends Composite implements IChangeListener {

	private Table tableAvailableColumns;
	private Table tableReportColumns;
	private List<ToolItem> toolItems = new ArrayList<>();
	//
	private ReportColumns reportColumns = new ReportColumns();
	private List<String> availableColumns = new ArrayList<>();
	private Set<String> headerColumns = new HashSet<>();

	public ReportColumnEditor(Composite parent, int style) {

		super(parent, style);
		createControl();
		checkUpdates(IChemClipseEvents.TOPIC_CHROMATOGRAM_XXD_UPDATE_SELECTION);
	}

	void checkUpdates(String event) {

		DataUpdateSupport dataUpdateSupport = Activator.getDefault().getDataUpdateSupport();
		List<Object> overviews = dataUpdateSupport.getUpdates(event);
		if(!overviews.isEmpty()) {
			Object first = overviews.get(0);
			if(first instanceof IChromatogramSelection<?, ?> overview) {
				headerColumns = overview.getChromatogram().getHeaderDataMap().keySet();
			}
		}
	}

	@Override
	public void setEnabled(boolean enabled) {

		tableAvailableColumns.setEnabled(enabled);
		tableReportColumns.setEnabled(enabled);
		for(ToolItem toolItem : toolItems) {
			toolItem.setEnabled(enabled);
		}
	}

	public ReportColumns getReportColumns() {

		return reportColumns;
	}

	public void load(String entries) {

		reportColumns.load(entries);
		resetTables();
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
		GridData searchGridData = new GridData(GridData.FILL_HORIZONTAL);
		searchGridData.horizontalSpan = 3;
		searchGridData.grabExcessHorizontalSpace = true;
		Text text = createTextSearch(this);
		text.setLayoutData(searchGridData);
		//
		GridData availableColumnsGridData = new GridData(GridData.FILL_BOTH);
		availableColumnsGridData.grabExcessHorizontalSpace = true;
		availableColumnsGridData.grabExcessVerticalSpace = true;
		tableAvailableColumns = createTableAvailableColumns(this);
		tableAvailableColumns.setLayoutData(availableColumnsGridData);
		//
		createToolbarSelectItems(this);
		//
		GridData reportColumnsGridData = new GridData(GridData.FILL_BOTH);
		reportColumnsGridData.grabExcessHorizontalSpace = true;
		reportColumnsGridData.grabExcessVerticalSpace = true;
		tableReportColumns = createTableReportColumns(this);
		tableReportColumns.setLayoutData(reportColumnsGridData);
		//
		initialize();
	}

	private void initialize() {

		resetTables();
	}

	private Table createTableAvailableColumns(Composite parent) {

		Table table = createTable(parent);
		table.setToolTipText("The following columns are available.");
		addTableColumn(table, "Available", 280);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {

				addColumn();
			}
		});
		//
		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				switch(e.keyCode) {
					case SWT.LF, SWT.CR, SWT.KEYPAD_CR, SWT.ARROW_RIGHT:
						addColumn();
						break;
					default:
						// No action
						break;
				}
			}
		});
		//
		return table;
	}

	private Text createTextSearch(Composite parent) {

		Text text = new Text(parent, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		text.setText("");
		text.setToolTipText("Type to filter available columns.");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				filterColumn(text.getText());
			}
		});
		text.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

				if(e.detail == SWT.ICON_CANCEL) {
					text.setText("");
					filterColumn(text.getText());
				} else if(e.detail == SWT.ICON_SEARCH) {
					filterColumn(text.getText());
				}
			}
		});
		return text;
	}

	private Table createTableReportColumns(Composite parent) {

		Table table = createTable(parent);
		table.setToolTipText("If no column is listed, all columns are reported.");
		addTableColumn(table, "Reported", 280);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {

				removeColumn();
			}
		});
		//
		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				switch(e.keyCode) {
					case SWT.LF, SWT.CR, SWT.KEYPAD_CR, SWT.ARROW_LEFT:
						removeColumn();
						break;
					case SWT.ARROW_UP:
						if(e.stateMask == SWT.MOD1) {
							moveColumnUp();
						}
						break;
					case SWT.ARROW_DOWN:
						if(e.stateMask == SWT.MOD1) {
							moveColumnDown();
						}
						break;
					default:
						// No action
						break;
				}
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
		toolItems.add(createToolItemRemoveAll(toolBar));
		toolItems.add(createToolItemMoveUp(toolBar));
		toolItems.add(createToolItemMoveDown(toolBar));
	}

	private ToolItem createToolItemAdd(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setToolTipText("Add Column");
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_FORWARD, IApplicationImageProvider.SIZE_16x16));
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
		toolItem.setToolTipText("Remove Column");
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImageProvider.SIZE_16x16));
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

	private ToolItem createToolItemRemoveAll(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setToolTipText("Clear Columns");
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CLEAR, IApplicationImageProvider.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				reportColumns.clear();
				resetTables();
			}
		});
		//
		return toolItem;
	}

	private ToolItem createToolItemMoveUp(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setToolTipText("Move Column Up");
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_UP_2, IApplicationImageProvider.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				moveColumnUp();
			}
		});
		//
		return toolItem;
	}

	private ToolItem createToolItemMoveDown(ToolBar toolBar) {

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setToolTipText("Move Column Down");
		toolItem.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_DOWN_2, IApplicationImageProvider.SIZE_16x16));
		//
		toolItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				moveColumnDown();
			}
		});
		//
		return toolItem;
	}

	private void addColumn() {

		int index = tableAvailableColumns.getSelectionIndex();
		if(index >= 0 && index < availableColumns.size()) {
			reportColumns.add(availableColumns.get(index));
			resetTables();
		}
	}

	private void removeColumn() {

		int index = tableReportColumns.getSelectionIndex();
		if(index >= 0 && index < reportColumns.size()) {
			reportColumns.remove(index);
			resetTables();
		}
	}

	private void moveColumnUp() {

		int indexNew = switchColumns(true);
		resetTables();
		if(indexNew != -1) {
			tableReportColumns.select(indexNew);
		}
	}

	private void moveColumnDown() {

		int indexNew = switchColumns(false);
		resetTables();
		if(indexNew != -1) {
			tableReportColumns.select(indexNew);
		}
	}

	private int switchColumns(boolean moveUp) {

		int indexNew = -1;
		//
		int index = tableReportColumns.getSelectionIndex();
		int size = reportColumns.size();
		if(index >= 0 && index < size) {
			int indexSwitch = moveUp ? index - 1 : index + 1;
			if(indexSwitch >= 0 && indexSwitch < size) {
				Collections.swap(reportColumns, index, indexSwitch);
				indexNew = indexSwitch;
			}
		}
		//
		return indexNew;
	}

	private void clearTables() {

		disposeTableItems(tableAvailableColumns);
		tableAvailableColumns.clearAll();
		disposeTableItems(tableReportColumns);
		tableReportColumns.clearAll();
	}

	private void resetAvailable() {

		availableColumns.clear();
		availableColumns.addAll(ReportColumns.getDefault());
		availableColumns.addAll(headerColumns);
	}

	private void updateTables() {

		for(String reportColumn : reportColumns) {
			availableColumns.remove(reportColumn);
			addTableRow(tableReportColumns, reportColumn);
		}
		for(String availableColumn : availableColumns) {
			addTableRow(tableAvailableColumns, availableColumn);
		}
	}

	private void resetTables() {

		clearTables();
		resetAvailable();
		updateTables();
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

	private void filterColumn(String searchText) {

		clearTables();
		resetAvailable();
		if(searchText == null || searchText.isEmpty() || searchText.isBlank()) {
			updateTables();
			return;
		}
		List<String> toRemove = new ArrayList<>();
		for(String columnText : availableColumns) {
			if(!StringUtils.containsAnyIgnoreCase(columnText, searchText)) {
				toRemove.add(columnText);
			}
		}
		for(String column : toRemove) {
			availableColumns.remove(column);
		}
		updateTables();
	}
}
