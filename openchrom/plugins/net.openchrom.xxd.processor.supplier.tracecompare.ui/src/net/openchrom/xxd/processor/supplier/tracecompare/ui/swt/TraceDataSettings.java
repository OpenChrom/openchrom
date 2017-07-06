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

	private boolean enableRangeInfo = false;
	private boolean showAxisTitle = false;
	private boolean enableHorizontalSlider = false;
	private boolean createMenu = false;

	public boolean isEnableRangeInfo() {

		return enableRangeInfo;
	}

	public void setEnableRangeInfo(boolean enableRangeInfo) {

		this.enableRangeInfo = enableRangeInfo;
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
