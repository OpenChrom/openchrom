/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

public class TraceDataSettings {

	private boolean enableRangeSelector = false;
	private boolean showRangeSelectorInitially = false;
	private boolean showAxisTitle = false;
	private boolean enableHorizontalSlider = false;
	private boolean createMenu = false;

	public boolean isEnableRangeSelector() {

		return enableRangeSelector;
	}

	public void setEnableRangeSelector(boolean enableRangeSelector) {

		this.enableRangeSelector = enableRangeSelector;
	}

	public boolean isShowRangeSelectorInitially() {

		return showRangeSelectorInitially;
	}

	public void setShowRangeSelectorInitially(boolean showRangeSelectorInitially) {

		this.showRangeSelectorInitially = showRangeSelectorInitially;
	}

	public boolean isShowAxisTitle() {

		return showAxisTitle;
	}

	public void setShowAxisTitle(boolean showAxisTitle) {

		this.showAxisTitle = showAxisTitle;
	}

	public boolean isEnableHorizontalSlider() {

		return enableHorizontalSlider;
	}

	public void setEnableHorizontalSlider(boolean enableHorizontalSlider) {

		this.enableHorizontalSlider = enableHorizontalSlider;
	}

	public boolean isCreateMenu() {

		return createMenu;
	}

	public void setCreateMenu(boolean createMenu) {

		this.createMenu = createMenu;
	}
}
