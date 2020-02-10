/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import static org.eclipse.chemclipse.support.ui.swt.ControlBuilder.createColumn;
import static org.eclipse.chemclipse.support.ui.swt.ControlBuilder.createTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.msd.model.detector.TemplatePeakDetector;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.swt.columns.SimpleColumnDefinition;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class DetectorConfigurationDialog extends Dialog {

	private Map<PeakType, List<TemplatePeakDetector<?>>> detectorMap = new TreeMap<>();
	private Map<PeakType, TemplatePeakDetector<?>> selectedMap = new HashMap<>();

	public DetectorConfigurationDialog(Shell parentShell, Collection<TemplatePeakDetector<?>> detectors) {

		super(parentShell);
		for(PeakType type : PeakDetectorValidator.USEFULL_TYPES) {
			detectorMap.put(type, new ArrayList<>());
		}
		for(TemplatePeakDetector<?> templatePeakDetector : detectors) {
			PeakType[] supportedPeakTypes = templatePeakDetector.getSupportedPeakTypes();
			for(PeakType type : supportedPeakTypes) {
				List<TemplatePeakDetector<?>> list = detectorMap.get(type);
				if(list != null) {
					list.add(templatePeakDetector);
					if(templatePeakDetector.isDefaultFor(type)) {
						selectedMap.put(type, templatePeakDetector);
					}
				}
			}
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite composite = (Composite)super.createDialogArea(parent);
		TableViewer tableViewer = createTable(composite, false);
		createColumn(tableViewer, new SimpleColumnDefinition<PeakType, String>("Type", 100, t -> t.name()));
		createColumn(tableViewer, new SimpleColumnDefinition<PeakType, TemplatePeakDetector<?>>("Detector", 300, peakType -> {
			TemplatePeakDetector<?> detector = selectedMap.get(peakType);
			if(detector != null) {
				return detector.getName();
			}
			return "-";
		}).withEditingSupport(v -> new EditingSupport(v) {

			@Override
			protected void setValue(Object element, Object value) {

				List<TemplatePeakDetector<?>> list = detectorMap.get(element);
				if(list != null) {
					selectedMap.put((PeakType)element, list.get((Integer)value));
					getViewer().update(element, null);
				}
			}

			@Override
			protected Object getValue(Object element) {

				TemplatePeakDetector<?> detector = selectedMap.get(element);
				if(detector != null) {
					List<TemplatePeakDetector<?>> list = detectorMap.get(element);
					if(list != null) {
						return list.indexOf(detector);
					}
				}
				return -1;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {

				List<String> names = new ArrayList<>();
				List<TemplatePeakDetector<?>> list = detectorMap.get(element);
				if(list != null) {
					for(TemplatePeakDetector<?> templatePeakDetector : list) {
						names.add(templatePeakDetector.getName());
					}
				}
				return new ComboBoxCellEditor((Composite)v.getControl(), names.toArray(new String[0]));
			}

			@Override
			protected boolean canEdit(Object element) {

				List<TemplatePeakDetector<?>> list = detectorMap.get(element);
				return list != null && list.size() > 1;
			}
		}));
		createColumn(tableViewer, new SimpleColumnDefinition<PeakType, String>("Config", 75, new ColumnLabelProvider() {

			@Override
			public Image getImage(Object element) {

				TemplatePeakDetector<?> detector = selectedMap.get(element);
				if(detector != null && detector.getConfigClass() != null) {
					return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16);
				}
				return null;
			}

			@Override
			public String getText(Object element) {

				return "";
			}
		}));
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(detectorMap.keySet());
		return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {

		super.configureShell(newShell);
		newShell.setText("Template Detector Configuration");
	}

	@Override
	protected Point getInitialSize() {

		return new Point(450, 300);
	}

	@Override
	protected boolean isResizable() {

		return false;
	}
}
