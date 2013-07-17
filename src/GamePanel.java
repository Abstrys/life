// ===================================================================
//  The Game of Life
//
//  Written by Eron Hennessey
// ===================================================================

package abstrys.games.Life;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A "driver" for the Life Panel in LifePanel.java
 */
public class GamePanel extends JPanel
{
	private Color gen_color;
	private LifePanel life_panel;

	JTextField gen_field;

	private boolean is_animating;
	private int animation_gps = 10;
	private Timer animation_timer;


	/**
	 * Life application constructor
	 */
	public GamePanel(int xsize, int ysize, int grid_xdim, int grid_ydim)
	{
		life_panel = new LifePanel(xsize, ysize, grid_xdim, grid_ydim);

		setLayout(new BorderLayout());
		add(createInfoPanel(), BorderLayout.NORTH);
		add(life_panel, BorderLayout.CENTER);
		add(createControlPanel(), BorderLayout.SOUTH);
	}

	private JPanel createInfoPanel()
	{
		JPanel panel = new JPanel();

		gen_field = new JTextField("0", 10);
		gen_field.setEditable(false);

		panel.add(new JLabel("Generation:"));
		panel.add(gen_field);

		return panel;
	}

	/**
	 * creates the control panel.
	 * @return the panel.
	 */
	private JPanel createControlPanel()
	{
		JPanel panel = new JPanel();

		final JButton next_button = new JButton("Next");
		next_button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						long gen = life_panel.nextGeneration();
						gen_field.setText("" + gen);
						repaint();
						next_button.requestFocus();
					}
				});

		final JButton clear_button = new JButton("Clear");
		clear_button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						life_panel.clear();
						gen_field.setText("0");
						repaint();
						next_button.requestFocus();
					}
				});
		
		final JButton animate_button = new JButton("Animate");
		animate_button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						if(is_animating)
						{
							stopAnimation();
							animate_button.setText("Animate");
							next_button.setEnabled(true);
							clear_button.setEnabled(true);
						}
						else
						{
							startAnimation();
							animate_button.setText("Stop");
							next_button.setEnabled(false);
							clear_button.setEnabled(false);
						}
					}
				});

		panel.add(next_button);
		panel.add(clear_button);
		panel.add(animate_button);
		return panel;
	}

	/**
	 * Starts animating the generations
	 */
	public void startAnimation()
	{
		TimerTask task = new TimerTask()
			{
				public void run()
				{
					long gen = life_panel.nextGeneration();
					gen_field.setText("" + gen);
					repaint();
				}
			};
		animation_timer = new Timer();
		animation_timer.schedule(task, 0, (1000 / animation_gps));

		is_animating = true;
	}

	/**
	 * Stops the currently running animation
	 */
	public void stopAnimation()
	{
		animation_timer.cancel();
		is_animating = false;
	}

	/**
	 * Sets the animation rate
	 * @param gps the animation rate, in generations per second.
	 */
	public void setAnimationRate(int gps)
	{
		animation_gps = gps;
	}
}

