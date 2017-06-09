/*******************************************************************************
 * Copyright 2013 Universidad Politécnica de Madrid
 * Copyright 2013 Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.samples.ctxtbus;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

/**
 * @author amedrano
 *
 */
public class SparQLQueryPanel extends JPanel implements Runnable {

	private JTextArea query;
	private JTextArea result;

	/**
	 * Create the panel.
	 */
	public SparQLQueryPanel() {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		query = new JTextArea();
		scrollPane.setViewportView(query);
		query.getDocument().addUndoableEditListener(new UndoManager());

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		result = new JTextArea();
		result.setEditable(false);
		scrollPane_1.setViewportView(result);

	}

	public void query() {
		new Thread(this).start();
	}

	/** {@ inheritDoc} */
	public void run() {
		String text = this.query.getText();
		if (!text.isEmpty()) {
			String res = Activator.hcaller.callDoSPARQL(text);
			this.result.setText(res);
		}
	}

}
