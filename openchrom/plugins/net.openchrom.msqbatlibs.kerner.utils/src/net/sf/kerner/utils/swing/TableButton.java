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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TableButton extends JButton implements TableCellRenderer, TableCellEditor {

    public static interface Listener extends EventListener {
        public void tableButtonClicked(int row, int col);
    }

    private static final long serialVersionUID = 8772175735048815470L;

    private final List<Listener> listener = new ArrayList<Listener>();

    private int selectedColumn;
    private int selectedRow;

    public TableButton(final String text) {
        super(text);
        addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                for (final Listener l : listener) {
                    l.tableButtonClicked(selectedRow, selectedColumn);
                }
            }
        });
    }

    public void addCellEditorListener(final CellEditorListener arg0) {
    }

    public void addTableButtonListener(final Listener l) {
        listener.add(l);
    }

    public void cancelCellEditing() {
    }

    public Object getCellEditorValue() {
        return "";
    }

    public Component getTableCellEditorComponent(final JTable table, final Object value,
            final boolean isSelected, final int row, final int col) {
        selectedRow = row;
        selectedColumn = col;
        return this;
    }

    public Component getTableCellRendererComponent(final JTable table, final Object value,
            final boolean isSelected, final boolean hasFocus, final int row, final int col) {
        return this;
    }

    public boolean isCellEditable(final EventObject arg0) {
        return true;
    }

    public void removeCellEditorListener(final CellEditorListener arg0) {
    }

    public void removeTableButtonListener(final Listener l) {
        listener.remove(l);
    }

    public boolean shouldSelectCell(final EventObject arg0) {
        return true;
    }

    public boolean stopCellEditing() {
        return true;
    }
}
