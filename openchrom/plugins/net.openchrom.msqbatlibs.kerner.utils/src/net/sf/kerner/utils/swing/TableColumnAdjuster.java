/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/*
 *  Class to manage the widths of colunmns in a table.
 *
 *  Various properties control how the width of the column is calculated.
 *  Another property controls whether column width calculation should be dynamic.
 *  Finally, various Actions will be added to the table to allow the user
 *  to customize the functionality.
 *
 *  This class was designed to be used with tables that use an auto resize mode
 *  of AUTO_RESIZE_OFF. With all other modes you are constrained as the width
 *  of the columns must fit inside the table. So if you increase one column, one
 *  or more of the other columns must decrease. Because of this the resize mode
 *  of RESIZE_ALL_COLUMNS will work the best.
 */
public class TableColumnAdjuster implements PropertyChangeListener, TableModelListener {
    /*
     * Action to adjust or restore the width of a single column or all columns
     */
    class ColumnAction extends AbstractAction {
        private final boolean isAdjust;
        private final boolean isSelectedColumn;

        public ColumnAction(final boolean isSelectedColumn, final boolean isAdjust) {
            this.isSelectedColumn = isSelectedColumn;
            this.isAdjust = isAdjust;
        }

        public void actionPerformed(final ActionEvent e) {
            // Handle selected column(s) width change actions

            if (isSelectedColumn) {
                final int[] columns = table.getSelectedColumns();

                for (int i = 0; i < columns.length; i++) {
                    if (isAdjust)
                        adjustColumn(columns[i]);
                    else
                        restoreColumn(columns[i]);
                }
            } else {
                if (isAdjust)
                    adjustColumns();
                else
                    restoreColumns();
            }
        }
    }

    /*
     * Toggle properties of the TableColumnAdjuster so the user can customize
     * the functionality to their preferences
     */
    class ToggleAction extends AbstractAction {
        private final boolean isToggleDynamic;
        private final boolean isToggleLarger;

        public ToggleAction(final boolean isToggleDynamic, final boolean isToggleLarger) {
            this.isToggleDynamic = isToggleDynamic;
            this.isToggleLarger = isToggleLarger;
        }

        public void actionPerformed(final ActionEvent e) {
            if (isToggleDynamic) {
                setDynamicAdjustment(!isDynamicAdjustment);
                return;
            }

            if (isToggleLarger) {
                setOnlyAdjustLarger(!isOnlyAdjustLarger);
                return;
            }
        }
    }

    private final Map<TableColumn, Integer> columnSizes = new HashMap<TableColumn, Integer>();
    private boolean isColumnDataIncluded;
    private boolean isColumnHeaderIncluded;
    private boolean isDynamicAdjustment;
    private boolean isOnlyAdjustLarger;

    private final int spacing;

    private final JTable table;

    /*
     * Specify the table and use default spacing
     */
    public TableColumnAdjuster(final JTable table) {
        this(table, 6);
    }

    /*
     * Specify the table and spacing
     */
    public TableColumnAdjuster(final JTable table, final int spacing) {
        this.table = table;
        this.spacing = spacing;
        setColumnHeaderIncluded(true);
        setColumnDataIncluded(true);
        setOnlyAdjustLarger(true);
        setDynamicAdjustment(false);
        installActions();
    }

    /*
     * Adjust the width of the specified column in the table
     */
    public void adjustColumn(final int column) {
        final TableColumn tableColumn = table.getColumnModel().getColumn(column);

        if (!tableColumn.getResizable())
            return;

        final int columnHeaderWidth = getColumnHeaderWidth(column);
        final int columnDataWidth = getColumnDataWidth(column);
        final int preferredWidth = Math.max(columnHeaderWidth, columnDataWidth);

        updateTableColumn(column, preferredWidth);
    }

    /*
     * Adjust the widths of all the columns in the table
     */
    public void adjustColumns() {
        final TableColumnModel tcm = table.getColumnModel();

        for (int i = 0; i < tcm.getColumnCount(); i++) {
            adjustColumn(i);
        }
    }

    /*
     * Get the preferred width for the specified cell
     */
    private int getCellDataWidth(final int row, final int column) {
        // Inovke the renderer for the cell to calculate the preferred width

        final TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
        final Component c = table.prepareRenderer(cellRenderer, row, column);
        final int width = c.getPreferredSize().width + table.getIntercellSpacing().width;

        return width;
    }

    /*
     * Calculate the width based on the widest cell renderer for the given
     * column.
     */
    private int getColumnDataWidth(final int column) {
        if (!isColumnDataIncluded)
            return 0;

        int preferredWidth = 0;
        final int maxWidth = table.getColumnModel().getColumn(column).getMaxWidth();

        for (int row = 0; row < table.getRowCount(); row++) {
            preferredWidth = Math.max(preferredWidth, getCellDataWidth(row, column));

            // We've exceeded the maximum width, no need to check other rows

            if (preferredWidth >= maxWidth)
                break;
        }

        return preferredWidth;
    }

    /*
     * Calculated the width based on the column name
     */
    private int getColumnHeaderWidth(final int column) {
        if (!isColumnHeaderIncluded)
            return 0;

        final TableColumn tableColumn = table.getColumnModel().getColumn(column);
        final Object value = tableColumn.getHeaderValue();
        TableCellRenderer renderer = tableColumn.getHeaderRenderer();

        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        final Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1,
                column);
        return c.getPreferredSize().width;
    }

    /*
     * Install Actions to give user control of certain functionality.
     */
    private void installActions() {
        installColumnAction(true, true, "adjustColumn", "control ADD");
        installColumnAction(false, true, "adjustColumns", "control shift ADD");
        installColumnAction(true, false, "restoreColumn", "control SUBTRACT");
        installColumnAction(false, false, "restoreColumns", "control shift SUBTRACT");

        installToggleAction(true, false, "toggleDynamic", "control MULTIPLY");
        installToggleAction(false, true, "toggleLarger", "control DIVIDE");
    }

    /*
     * Update the input and action maps with a new ColumnAction
     */
    private void installColumnAction(final boolean isSelectedColumn, final boolean isAdjust,
            final String key, final String keyStroke) {
        final Action action = new ColumnAction(isSelectedColumn, isAdjust);
        final KeyStroke ks = KeyStroke.getKeyStroke(keyStroke);
        table.getInputMap().put(ks, key);
        table.getActionMap().put(key, action);
    }

    /*
     * Update the input and action maps with new ToggleAction
     */
    private void installToggleAction(final boolean isToggleDynamic, final boolean isToggleLarger,
            final String key, final String keyStroke) {
        final Action action = new ToggleAction(isToggleDynamic, isToggleLarger);
        final KeyStroke ks = KeyStroke.getKeyStroke(keyStroke);
        table.getInputMap().put(ks, key);
        table.getActionMap().put(key, action);
    }

    //
    // Implement the PropertyChangeListener
    //
    public void propertyChange(final PropertyChangeEvent e) {
        // When the TableModel changes we need to update the listeners
        // and column widths

        if ("model".equals(e.getPropertyName())) {
            TableModel model = (TableModel) e.getOldValue();
            model.removeTableModelListener(this);

            model = (TableModel) e.getNewValue();
            model.addTableModelListener(this);
            adjustColumns();
        }
    }

    /*
     * Restore the width of the specified column to its previous width
     */
    private void restoreColumn(final int column) {
        final TableColumn tableColumn = table.getColumnModel().getColumn(column);
        final Integer width = columnSizes.get(tableColumn);

        if (width != null) {
            table.getTableHeader().setResizingColumn(tableColumn);
            tableColumn.setWidth(width.intValue());
        }
    }

    /*
     * Restore the widths of the columns in the table to its previous width
     */
    public void restoreColumns() {
        final TableColumnModel tcm = table.getColumnModel();

        for (int i = 0; i < tcm.getColumnCount(); i++) {
            restoreColumn(i);
        }
    }

    /*
     * Indicates whether to include the model data in the width calculation
     */
    public void setColumnDataIncluded(final boolean isColumnDataIncluded) {
        this.isColumnDataIncluded = isColumnDataIncluded;
    }

    /*
     * Indicates whether to include the header in the width calculation
     */
    public void setColumnHeaderIncluded(final boolean isColumnHeaderIncluded) {
        this.isColumnHeaderIncluded = isColumnHeaderIncluded;
    }

    /*
     * Indicate whether changes to the model should cause the width to be
     * dynamically recalculated.
     */
    public void setDynamicAdjustment(final boolean isDynamicAdjustment) {
        // May need to add or remove the TableModelListener when changed

        if (this.isDynamicAdjustment != isDynamicAdjustment) {
            if (isDynamicAdjustment) {
                table.addPropertyChangeListener(this);
                table.getModel().addTableModelListener(this);
            } else {
                table.removePropertyChangeListener(this);
                table.getModel().removeTableModelListener(this);
            }
        }

        this.isDynamicAdjustment = isDynamicAdjustment;
    }

    /*
     * Indicates whether columns can only be increased in size
     */
    public void setOnlyAdjustLarger(final boolean isOnlyAdjustLarger) {
        this.isOnlyAdjustLarger = isOnlyAdjustLarger;
    }

    //
    // Implement the TableModelListener
    //
    public void tableChanged(final TableModelEvent e) {
        if (!isColumnDataIncluded)
            return;

        // A cell has been updated

        if (e.getType() == TableModelEvent.UPDATE) {
            final int column = table.convertColumnIndexToView(e.getColumn());

            // Only need to worry about an increase in width for this cell

            if (isOnlyAdjustLarger) {
                final int row = e.getFirstRow();
                final TableColumn tableColumn = table.getColumnModel().getColumn(column);

                if (tableColumn.getResizable()) {
                    final int width = getCellDataWidth(row, column);
                    updateTableColumn(column, width);
                }
            }

            // Could be an increase of decrease so check all rows

            else {
                adjustColumn(column);
            }
        }

        // The update affected more than one column so adjust all columns

        else {
            adjustColumns();
        }
    }

    /*
     * Update the TableColumn with the newly calculated width
     */
    private void updateTableColumn(final int column, int width) {
        final TableColumn tableColumn = table.getColumnModel().getColumn(column);

        if (!tableColumn.getResizable())
            return;

        width += spacing;

        // Don't shrink the column width

        if (isOnlyAdjustLarger) {
            width = Math.max(width, tableColumn.getPreferredWidth());
        }

        columnSizes.put(tableColumn, new Integer(tableColumn.getWidth()));
        table.getTableHeader().setResizingColumn(tableColumn);
        tableColumn.setWidth(width);
    }
}
