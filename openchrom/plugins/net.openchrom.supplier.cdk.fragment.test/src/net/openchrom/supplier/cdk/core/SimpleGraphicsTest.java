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

/**
 * Class for testing how a molecule could conveniently be rendered.
 * @author administrator_marwin
 *
 */

public class SimpleGraphicsTest {
	
	
	
	
	public static void main(String[]  args)
	{
		JFrame frame = new JFrame();
		frame.setContentPane(new SimpleGraphicsTestPanel());
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
