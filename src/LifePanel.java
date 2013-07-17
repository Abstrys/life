// ===================================================================
//  The Game of Life
//
//  Written by Eron Hennessey
// ===================================================================

package abstrys.games.Life;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * A JPanel that plays the game of life.
 */
public class LifePanel extends JPanel
{
	Point field_dimensions;
	private Grid grid;
	private long cur_generation;
	private Color gen_color;
	private boolean mouse_down;
	private boolean show_grid = true;
	private Random random = new Random();

	/**
	 * LifePanel constructor
	 * @param xsize the x size (in pixels) of the panel
	 * @param ysize the y size (in pixels) of the panel
	 * @param xdim the x dimension of the grid
	 * @param ydim the y dimension of the grid
	 */
	public LifePanel(int xsize, int ysize, int xdim, int ydim)
	{
		field_dimensions = new Point(xdim, ydim);
		grid = new Grid(ydim, xdim);
		cur_generation = 0;
		gen_color = getRandomColor();
		setupMouseInput();
		mouse_down = false;
		setPreferredSize(new Dimension(xsize, ysize));
	}

	/**
	 * To show, or not to show the grid?  That is the question!
	 */
	public void showGrid(boolean show)
	{
		show_grid = show;
	}

	private void setupMouseInput()
	{
		// create the MouseAdapter that will be used to handle input on the
		// panel.
		MouseAdapter mouse_adapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				// find the cell that was clicked on.
				Point grid_pos = getGridFromMouse(e.getPoint());
				if((grid_pos.x < 0) || (grid_pos.y < 0))
				{
					// out of bounds.
					return;
				}

				// toggle the cell on or off.
				if(grid.getCellColor(grid_pos.x, grid_pos.y) == null)
				{
					grid.setCellColor(grid_pos.x, grid_pos.y, gen_color);
				}
				else
				{
					grid.setCellColor(grid_pos.x, grid_pos.y, null);
				}

				repaint();
			}};

        MouseMotionAdapter mouse_motion_adapter = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e)
			{
				// find the cell that was clicked on.
				Point grid_pos = getGridFromMouse(e.getPoint());
				if((grid_pos.x < 0) || (grid_pos.y < 0))
				{
					// out of bounds.
					return;
				}

				// paint cells using the current gen_color
				grid.setCellColor(grid_pos.x, grid_pos.y, gen_color);
				repaint();
			}};

		addMouseListener(mouse_adapter);
		addMouseMotionListener(mouse_motion_adapter);
	}

	/**
	 * Paints the panel.
	 * @param g the graphics context
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		int cell_width = getWidth() / field_dimensions.x;
		int cell_height = getHeight() / field_dimensions.y;

		for(int y = 0; y < field_dimensions.y; y++)
		{
			for(int x = 0; x < field_dimensions.x; x++)
			{
				int xpos = x * cell_width;
				int ypos = y * cell_height;

				// if there's a cell at this position, draw a little rect with
				// the color
				Color cell_color = grid.getCellColor(x,y);
				if(cell_color != null)
				{
					g2.setColor(cell_color);
					g2.fillRect(xpos, ypos, cell_width, cell_height);
				}

				if(show_grid)
				{
					g2.setColor(Color.LIGHT_GRAY);
					g2.drawRect(xpos, ypos, cell_width, cell_height);
				}
			}
		}
	}

	/**
	 * Calculates the next generation
	 * @return the new generation number
	 */
	public long nextGeneration()
	{
		// All cells in this generation have a particular (random) color.
		Color gen_color = getRandomColor();

		// make a new grid to replace the old one.
		Grid new_grid = new Grid(grid.getXDim(), grid.getYDim());

		Point grid_pos = new Point(0,0);

		for(grid_pos.x = 0; grid_pos.x < field_dimensions.x; grid_pos.x++)
		{
			for(grid_pos.y = 0; grid_pos.y < field_dimensions.y; grid_pos.y++)
			{
				int n = grid.getNeighborCount(grid_pos.x, grid_pos.y);

				if((grid.getCellColor(grid_pos.x, grid_pos.y) == null) && (n == 3))
				{
					// Check for cell birth.
					// A new cell is born on an empty square if it is surrounded by
					// 3 neighbors.
					new_grid.setCellColor(grid_pos.x, grid_pos.y, gen_color);
				}
				else if((grid.getCellColor(grid_pos.x, grid_pos.y) != null) && ((n > 3) || (n < 2)))
				{
					// Check for cell death.
					// A cell dies of overcrowding if it is surrounded by four or
					// more neighbors, and it dies of loneliness if it is surrounded
					// by zero or one neighbors.
					new_grid.setCellColor(grid_pos.x, grid_pos.y, null);
				}
				else
				{
					// the current cell has not changed.  Simply copy the cell.
					new_grid.setCellColor(
							grid_pos.x, grid_pos.y,
							grid.getCellColor(grid_pos.x, grid_pos.y));
				}
			}
		}

		// replace the "old" grid with the new one.
		grid = new_grid;

		return ++cur_generation;
	}

	/**
	 * translates mouse coordinates into grid coordinates
	 * @param mousex the mouse x position.
	 * @param mousey the mouse y position.
	 * @return the grid position.
	 */
	public Point getGridFromMouse(Point mouse_coordinates)
	{
		Point grid_pos = new Point(mouse_coordinates);
		grid_pos.x /= (getWidth() / field_dimensions.x);
		grid_pos.y /= (getHeight() / field_dimensions.y);

		if((grid_pos.x < 0) || (grid_pos.x >= field_dimensions.x))
		{
			grid_pos.x = -1;
		}

		if((grid_pos.y < 0) || (grid_pos.y >= field_dimensions.y))
		{
			grid_pos.y = -1;
		}

		return grid_pos;
	}


	/**
	 * Clears the grid and resets the generation to zero
	 */
	public void clear()
	{
		grid.clear();
		cur_generation = 0;
	}

	/**
	 *
	 * returns a random color suitable for display on a light gray background.
	 * @return the color generated.
	 */
	private Color getRandomColor()
	{
		return new Color(
			random.nextInt(128), random.nextInt(128), random.nextInt(128));
	}

}
