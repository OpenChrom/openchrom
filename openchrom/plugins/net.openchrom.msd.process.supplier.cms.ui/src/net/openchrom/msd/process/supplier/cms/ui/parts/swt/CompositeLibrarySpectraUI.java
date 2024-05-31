/*******************************************************************************
 * Copyright (c) 2017, 2023 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;

public class CompositeLibrarySpectraUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private static final String SHOW_SELECTED = "  *     ";
	private static final String SHOW_NOT_SELECTED = "        ";
	private DecimalFormat decimalFormatsScaleFactor = ValueFormat.getDecimalFormatEnglish("0.0#####");
	//
	private static IMassSpectra cmsLibSpectra;
	private Text textCmsLibraryFilePath;
	private Button buttonLibFileSelect;
	private List listCmsComponents;
	private Table tableCmsComponents;
	private TableEditor tableEditor;

	public CompositeLibrarySpectraUI(Composite parent, int style) {

		super(parent, style);
		this.initialize();
	}

	/*
	 * A static method is not optimal, but works in this case.
	 */
	public static IMassSpectra getLibSpectra() {

		if(null == cmsLibSpectra) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a CMS library file");
			return (null);
		}
		boolean selectionOK = false;
		for(int i = 0; i < cmsLibSpectra.getList().size(); i++) {
			IScanMSD libSpec = cmsLibSpectra.getList().get(i);
			if(libSpec instanceof ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum) {
				if(calibratedVendorLibraryMassSpectrum.isSelected()) {
					selectionOK = true;
				}
			} else {
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "This is not a CMS library file");
				return (null);
			}
		}
		if(!selectionOK) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select library components");
			return (null);
		}
		return cmsLibSpectra; // whw testing
	}

	private void initialize() {

		GridLayout thisGridLayout = new GridLayout(2, false);
		thisGridLayout.marginHeight = 0;
		thisGridLayout.marginWidth = 0;
		this.setLayout(thisGridLayout);
		GridData thisGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		// thisGridData.horizontalSpan = 2;
		// thisGridData.heightHint = 300;
		this.setLayoutData(thisGridData);
		// CMS library path
		textCmsLibraryFilePath = new Text(this, SWT.BORDER);
		textCmsLibraryFilePath.setText("");
		GridData textCmsLibraryFilePathGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textCmsLibraryFilePath.setLayoutData(textCmsLibraryFilePathGridData);
		// load library file button
		addButtonSelect(this);
		// component selection table
		addComponentTable(this);
	}

	// Component Table
	private void addComponentTable(Composite comp) {

		final int COLUMN_0 = 0;
		final int COLUMN_1 = 1;
		tableCmsComponents = new Table(comp, SWT.FULL_SELECTION | SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData tableCmsComponentsGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableCmsComponentsGridData.horizontalSpan = 2;
		tableCmsComponents.setLayoutData(tableCmsComponentsGridData);
		tableCmsComponents.setHeaderVisible(true);
		tableCmsComponents.setLinesVisible(true);
		for(TableColumn tableColumn : tableCmsComponents.getColumns()) {
			tableColumn.dispose();
		}
		//
		TableColumn tableColumnScale = new TableColumn(tableCmsComponents, SWT.LEFT);
		tableColumnScale.setText("Scale");
		tableColumnScale.setWidth(50);
		//
		TableColumn tableColumnName = new TableColumn(tableCmsComponents, SWT.LEFT);
		tableColumnName.setText("Component");
		tableColumnName.setWidth(200);
		//
		tableEditor = new TableEditor(tableCmsComponents);
		// The editor must have the same size as the cell and must not be any smaller than 50 pixels.
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		tableEditor.minimumWidth = 50;
		//
		tableCmsComponents.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event event) {

				Point pt = new Point(event.x, event.y);
				// Clean up any previous editor control
				Control oldEditor = tableEditor.getEditor();
				if(oldEditor != null) {
					oldEditor.dispose();
				}
				TableItem item = tableCmsComponents.getItem(pt);
				if(item != null) {
					/* Iterate over all columns and check if event is contained */
					for(int col = 0; col < tableCmsComponents.getColumnCount(); col++) {
						Rectangle rect = item.getBounds(col);
						int rowIndex;
						if(rect.contains(pt) && (null != cmsLibSpectra) && (0 <= (rowIndex = tableCmsComponents.indexOf(item)))) {
							ICalibratedVendorLibraryMassSpectrum libSpectrum = (ICalibratedVendorLibraryMassSpectrum)cmsLibSpectra.getList().get(rowIndex);
							if(COLUMN_1 == col) {
								libSpectrum.setSelected(!libSpectrum.isSelected());
								item.setText(makeTableStrings(libSpectrum, libSpectrum.isSelected()));
							} else if(COLUMN_0 == col) {
								// The control that will be the editor must be a child of the Table
								Text newEditor = new Text(tableCmsComponents, SWT.NONE);
								newEditor.setText(item.getText(COLUMN_0));
								newEditor.addModifyListener(new ModifyListener() {

									@Override
									public void modifyText(ModifyEvent e) {

										Text text = (Text)tableEditor.getEditor();
										if(isValidDoubleString(text.getText())) {
											tableEditor.getItem().setText(COLUMN_0, text.getText());
											libSpectrum.setScaleFactor(Double.parseDouble(text.getText()));
											item.setText(makeTableStrings(libSpectrum, libSpectrum.isSelected()));
										}
									}
								});
								newEditor.selectAll();
								newEditor.setFocus();
								tableEditor.setEditor(newEditor, item, COLUMN_0);
							}
						}
					}
				}
			}
		});
		// resize component name column(1) to take all width not used by scale column(0)
		tableCmsComponents.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				Rectangle area = tableCmsComponents.getClientArea();
				int width = area.width;
				tableCmsComponents.getColumn(COLUMN_1).setWidth(width - tableCmsComponents.getColumn(COLUMN_0).getWidth());
			}
		});
	}

	private boolean isValidDoubleString(String myString) {

		final String Digits = "(\\p{Digit}+)";
		final String HexDigits = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally
		// signed decimal integer.
		final String Exp = "[eE][+-]?" + Digits;
		final String fpRegex = ("[\\x00-\\x20]*" + // Optional leading "whitespace"
				"[+-]?(" + // Optional sign character
				"NaN|" + // "NaN" string
				"Infinity|" + // "Infinity" string
				// A decimal floating-point string representing a finite positive
				// number without a leading sign has at most five basic pieces:
				// Digits . Digits ExponentPart FloatTypeSuffix
				//
				// Since this method allows integer-only strings as input
				// in addition to strings of floating-point literals, the
				// two sub-patterns below are simplifications of the grammar
				// productions from section 3.10.2 of
				// The Java™ Language Specification.
				// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
				"(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +
				// . Digits ExponentPart_opt FloatTypeSuffix_opt
				"(\\.(" + Digits + ")(" + Exp + ")?)|" +
				// Hexadecimal strings
				"((" +
				// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "(\\.)?)|" +
				// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits + "))" + "[fFdD]?))" + "[\\x00-\\x20]*" + // Optional trailing "whitespace"
				"$"); // and that's all
		if(Pattern.matches(fpRegex, myString)) {
			return true; // Will not throw NumberFormatException
		} else {
			return false;
		}
	}

	private void addTableRow(Table table, String[] row) {

		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(row);
	}

	private void addButtonSelect(Composite parent) {

		buttonLibFileSelect = new Button(parent, SWT.NONE);
		buttonLibFileSelect.setText("");
		buttonLibFileSelect.setToolTipText("Select the *.cms library file.");
		buttonLibFileSelect.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FILE, IApplicationImageProvider.SIZE_16x16));
		buttonLibFileSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String pathCmsLibrarySpectra = PreferenceSupplier.getPathCmsLibrarySpectra();
				FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.READ_ONLY);
				fileDialog.setText("Select the CMS library file.");
				fileDialog.setFilterExtensions(new String[]{"*.cms", "*.CMS"});
				fileDialog.setFilterNames(new String[]{"Calibrated Spectra (*.cms)", "Calibrated Spectra (*.CMS)"});
				fileDialog.setFilterPath(pathCmsLibrarySpectra);
				String pathname = fileDialog.open();
				if(pathname != null) {
					/*
					 * Remember the path.
					 */
					pathCmsLibrarySpectra = fileDialog.getFilterPath();
					PreferenceSupplier.setPathCmsLibrarySpectra(pathCmsLibrarySpectra);
					textCmsLibraryFilePath.setText(pathname);
					readAndLoadCMSlibraryFile();
				}
			}
		});
	}

	private String makeListLine(ICalibratedVendorLibraryMassSpectrum libSpectrum, boolean isSelected) {

		StringBuilder strLine = new StringBuilder("(");
		String strings[] = makeTableStrings(libSpectrum, isSelected);
		strLine.append(strings[0]);
		strLine.append(") ");
		strLine.append(strings[1]);
		return strLine.toString();
	}

	private String[] makeTableStrings(ICalibratedVendorLibraryMassSpectrum libSpectrum, boolean isSelected) {

		String strings[] = new String[2];
		StringBuilder strName = new StringBuilder();
		strings[0] = decimalFormatsScaleFactor.format(libSpectrum.getScaleFactor());
		if(isSelected) {
			strName.append(SHOW_SELECTED);
		} else {
			strName.append(SHOW_NOT_SELECTED);
		}
		strName.append(libSpectrum.makeNameString());
		strName.append(", ");
		strName.append(libSpectrum.getLibraryInformation().getFormula());
		strings[1] = strName.toString();
		return strings;
	}

	private void readAndLoadCMSlibraryFile() {

		try {
			File file = new File(textCmsLibraryFilePath.getText().trim());
			if(file.exists()) {
				MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
				cmsLibSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
				if(null != cmsLibSpectra) {
					if(listCmsComponents instanceof List) {
						listCmsComponents.removeAll();
					}
					if(tableCmsComponents instanceof Table) {
						tableCmsComponents.removeAll();
					}
					new StringBuilder();
					for(IScanMSD spectrum : cmsLibSpectra.getList()) {
						if((null != spectrum) && (spectrum instanceof ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum) && !(spectrum instanceof ICalibratedVendorMassSpectrum)) {
							if(listCmsComponents instanceof List) {
								listCmsComponents.add(makeListLine(calibratedVendorLibraryMassSpectrum, false));
							}
							if(tableCmsComponents instanceof Table) {
								addTableRow(tableCmsComponents, makeTableStrings(calibratedVendorLibraryMassSpectrum, false));
							}
						} else {
							String fileName = textCmsLibraryFilePath.getText().trim();
							int index = fileName.lastIndexOf(File.separator);
							if(0 < index) {
								fileName = fileName.substring(1 + index);
							}
							if(listCmsComponents instanceof List) {
								listCmsComponents.removeAll();
							}
							if(tableCmsComponents instanceof Table) {
								tableCmsComponents.removeAll();
							}
							cmsLibSpectra = null;
							textCmsLibraryFilePath.setText("");
							MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "\"" + fileName + "\" is not a CMS library file\nPlease select a CMS library file");
							break; // for
						}
					} // for
				} else {
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a *.cms library file first.");
				}
			} // if(file.exists())
		} catch(Exception e1) {
			logger.warn(e1);
		}
	}
}
