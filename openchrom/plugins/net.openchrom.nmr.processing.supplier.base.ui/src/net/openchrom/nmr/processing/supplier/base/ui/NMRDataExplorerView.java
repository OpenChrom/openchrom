package net.openchrom.nmr.processing.supplier.base.ui;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.ux.extension.xxd.ui.editors.EditorSupportFactory;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.DataExplorerUI;
import org.eclipse.swt.widgets.Composite;

public class NMRDataExplorerView {

	@Inject
	public NMRDataExplorerView() {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {

		DataExplorerUI dataExplorerUI = new DataExplorerUI(parent);
		dataExplorerUI.setSupplierFileEditorSupportList(Collections.singletonList(new EditorSupportFactory(DataType.NMR).getInstanceEditorSupport()));
	}
}