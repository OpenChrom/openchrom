/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.base.model.Suspect;
import net.openchrom.xxd.base.model.SuspectEvaluation;
import net.openchrom.xxd.base.ui.swt.SuspectsListUI;

public class SuspectListEditingSupport extends EditingSupport {

	private final SuspectsListUI tableViewer;
	private final String column;
	private CellEditor cellEditor;
	//
	private DecimalFormat decimalFormatRT = ValueFormat.getDecimalFormatEnglish("0.000");
	private DecimalFormat decimalFormatRI = ValueFormat.getDecimalFormatEnglish("0");

	public SuspectListEditingSupport(SuspectsListUI tableViewer, String column) {

		super(tableViewer);
		this.column = column;
		this.cellEditor = new TextCellEditor(tableViewer.getTable());
		this.tableViewer = tableViewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {

		if(tableViewer.isEditEnabled()) {
			if(element instanceof SuspectEvaluation) {
				if(column.equals(SuspectListLabelProvider.NAME)) {
					return true;
				} else if(column.equals(SuspectListLabelProvider.RETENTION_TIME_TARGET)) {
					return true;
				} else if(column.equals(SuspectListLabelProvider.RETENTION_TIME_DELTA)) {
					return true;
				} else if(column.equals(SuspectListLabelProvider.RETENTION_INDEX_TARGET)) {
					return true;
				} else if(column.equals(SuspectListLabelProvider.RETENTION_INDEX_DELTA)) {
					return true;
				}
			}
		}
		//
		return false;
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof SuspectEvaluation suspectEvaluation) {
			Suspect suspect = suspectEvaluation.getSuspect();
			switch(column) {
				case SuspectListLabelProvider.NAME:
					return suspect.getName();
				case SuspectListLabelProvider.RETENTION_TIME_TARGET:
					return decimalFormatRT.format(suspect.getRetentionTimeMinutesTarget());
				case SuspectListLabelProvider.RETENTION_TIME_DELTA:
					return decimalFormatRT.format(suspect.getRetentionTimeMinutesDelta());
				case SuspectListLabelProvider.RETENTION_INDEX_TARGET:
					return decimalFormatRI.format(suspect.getRetentionIndexTarget());
				case SuspectListLabelProvider.RETENTION_INDEX_DELTA:
					return decimalFormatRI.format(suspect.getRetentionIndexDelta());
				default:
					break;
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof SuspectEvaluation suspectEvaluation) {
			Suspect suspect = suspectEvaluation.getSuspect();
			switch(column) {
				case SuspectListLabelProvider.NAME:
					suspect.setName(value.toString());
					break;
				case SuspectListLabelProvider.RETENTION_TIME_TARGET:
					suspect.setRetentionTimeMinutesTarget(getRetentionTime(value));
					break;
				case SuspectListLabelProvider.RETENTION_TIME_DELTA:
					suspect.setRetentionTimeMinutesDelta(getRetentionTime(value));
					break;
				case SuspectListLabelProvider.RETENTION_INDEX_TARGET:
					suspect.setRetentionIndexTarget(getRetentionIndex(value));
					break;
				case SuspectListLabelProvider.RETENTION_INDEX_DELTA:
					suspect.setRetentionIndexDelta(getRetentionIndex(value));
					break;
				default:
					break;
			}
			//
			tableViewer.refresh(element);
			tableViewer.updateContent();
		}
	}

	private double getRetentionTime(Object value) {

		try {
			return Double.parseDouble(value.toString().trim());
		} catch(NumberFormatException e) {
			return 0;
		}
	}

	private float getRetentionIndex(Object value) {

		try {
			return Float.parseFloat(value.toString().trim());
		} catch(NumberFormatException e) {
			return 0;
		}
	}
}