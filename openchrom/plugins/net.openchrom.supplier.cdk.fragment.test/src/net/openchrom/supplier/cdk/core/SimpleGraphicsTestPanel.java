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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.*;

public class SimpleGraphicsTestPanel extends JPanel{
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		animate((Graphics2D)g);
	}
	
	public void animate(Graphics2D g)
	{
		Line2D.Double b1 = new Line2D.Double(200,200,210,210);
		Ellipse2D.Double c1 = new Ellipse2D.Double(180,180,10,10);
		g.setStroke(new BasicStroke(2.0f));
		
		g.draw(b1);
		g.setStroke(new BasicStroke(0.1f));
		g.setColor(Color.BLUE);
		g.fill(c1);
	}
}
