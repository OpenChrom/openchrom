/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
*******************************************************************************/
package net.openchrom.supplier.cdk.core;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Checks the Class CDKGraphView.
 * @author administrator_marwin
 *
 */

public class CDKGraphViewTest {
	
	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		CDKGraphViewTestPanel cont = new CDKGraphViewTestPanel();
		frame.setContentPane(cont);
		
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
