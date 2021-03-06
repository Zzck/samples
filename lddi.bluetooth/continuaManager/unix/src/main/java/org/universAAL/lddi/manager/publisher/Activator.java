/*
    Copyright 2007-2014 TSB, http://www.tsbtecnologias.es
    Technologies for Health and Well-being - Valencia, Spain

    See the NOTICE file distributed with this work for additional
    information regarding copyright ownership

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
/**
 * x073 Continua agent publisher (agent events will be published over universAAL bus)
 *
 * @author Angel Martinez-Cavero
 * @version 0
 *
 * TSB Technologies for Health and Well-being
 */

// Package
package org.universAAL.lddi.manager.publisher;

// Imports
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.lddi.manager.gui.GUI;;

// Main class
public class Activator implements BundleActivator {

	// Attributes
	/** Main GUI object */
	private GUI gui = null;

	/** Bundle context object */
	private BundleContext ctx = null;

	// Methods
	/** Start */
	public void start(BundleContext context) throws Exception {
		ctx = context;
		// Create and show main GUI frame
		gui = new GUI(ctx);
		gui.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gui.stopGUI();
				try {
					ctx.getBundle().stop();
				} catch (BundleException e1) {
					e1.printStackTrace();
				}
			}
		});
		gui.setVisible(true);
	}

	/** Stop */
	public void stop(BundleContext arg0) throws Exception {
		gui.stopGUI();
		ctx = null;
	}
}