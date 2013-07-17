// ===================================================================
//  The Game of Life
//
//  Written by Eron Hennessey
// ===================================================================

package abstrys.games;
import abstrys.games.Life.*;

import javax.swing.JApplet;
import java.awt.Container;

/**
 * A "driver" for the Life Panel in LifePanel.java
 */
public class LifeApplet extends JApplet
{
	/**
	 * Life application constructor
	 */

	public void init()
	{
		int gridpixx = 400;
		int gridpixy = 400;
		int griddimx = 40;
		int griddimy = 40;

		try
		{
			gridpixx = Integer.parseInt(getParameter("gridpixx"));
			gridpixy = Integer.parseInt(getParameter("gridpixy"));
			griddimx = Integer.parseInt(getParameter("griddimx"));
			griddimy = Integer.parseInt(getParameter("griddimy"));
		}
		catch(NumberFormatException exc)
		{
		}

		Container cp = getContentPane();
		cp.add(new GamePanel(gridpixx, gridpixy, griddimx, griddimy));
	}
}

