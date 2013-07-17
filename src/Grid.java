package abstrys.games.Life;
import java.awt.Color;

/**
 * Represents the Grid in the game of life;
 */
public class Grid
{
	private Color[][] cells;

	/**
	 * Creates a new grid of a particular size
	 * @param the number of cells in the x dimension.
	 * @param the number of cells in the y dimension.
	 */
	public Grid(int xdim, int ydim)
	{
		// allocate a new cell array.
		cells = new Color[ydim][xdim];
	}

	/**
	 * Clears the entire grid
	 */
	public void clear()
	{
		for(int x = 0; x < getXDim(); x++)
		{
			for(int y = 0; y < getYDim(); y++)
			{
				cells[y][x] = null;
			}
		}
	}


	/**
	 * gets the x dimension of the cell array
	 */
	public int getXDim()
	{
		return cells[0].length;
	}

	/**
	 * gets the y dimension of the cell array
	 */
	public int getYDim()
	{
		return cells.length;
	}

	/**
	 * sets the cell color at the given location
	 * @param x the x-position of the cell to set.
	 * @param y the y-position of the cell to set.
	 * @param color the color to set the cell.
	 */
	public void setCellColor(int x, int y, Color color)
	{
		cells[y][x] = color;
	}

	public Color getCellColor(int x, int y)
	{
		return cells[y][x];
	}

	/**
	 * counts all the neighbors around a given cell position
	 * @param x the x-position of the reference cell
	 * @param y the y-position of the reference cell
	 * @return the neighbor count
	 */
	public int getNeighborCount(int x, int y)
	{
		final int xdim = getXDim();
		final int ydim = getYDim();

		//
		// constrain the for loop if necessary
		//
		int xstart = -1;
		int xend = 1;
		if(x == 0)
		{
			xstart = 0;
		}
		else if((x+1) == xdim)
		{
			xend = 0;
		}

		int ystart = -1;
		int yend = 1;
		if(y == 0)
		{
			ystart = 0;
		}
		else if((y+1) == ydim)
		{
			yend = 0;
		}

		int neighbors = 0;

		for(int xn = xstart; xn <= xend; xn++)
		{
			for(int yn = ystart; yn <= yend; yn++)
			{
				if((xn == 0) && (yn == 0))
				{
					// don't count the reference cell as a neighbor!
					continue;
				}

				// now see if there's a neighbor at this position
				if(cells[y+yn][x+xn] != null)
				{
					neighbors++;
				}
			}
		}

		return neighbors;
	}
}
