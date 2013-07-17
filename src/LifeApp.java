// ===================================================================
//  The Game of Life
//
//  Written by Eron Hennessey
// ===================================================================

package abstrys.games;
import abstrys.games.Life.*;

import java.awt.*;
import javax.swing.*;

/**
 * A "driver" for the Life Panel in LifePanel.java
 */
public class LifeApp extends JFrame
{
	private static final int GRID_DIM = 50;
	private static final int PANEL_SIZE = 400;
	/**
	 * Life application constructor
	 */
	public LifeApp()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("The Game of Life...");
		Container pane = getContentPane();

		pane.add(new GamePanel(PANEL_SIZE, PANEL_SIZE, GRID_DIM, GRID_DIM), BorderLayout.CENTER);

		pack();
		setBounds(10, 10, 460, 480);
	}

	/**
	 * The main() for the application
	 * @param args command-line arguments.
	 */
	public static void main(String[] args)
	{
		LifeApp app = new LifeApp();
		app.setVisible(true);
	}
}

