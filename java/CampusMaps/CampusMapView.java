package sample;

import javax.swing.*;
import java.awt.*;

/**
 * CampusMapView is a JPanel that displays a region map image
 * with a given path drawn on it.
 *
 */
public class CampusMapView extends JPanel {

	private Image i;
	private CampusPath path;

	/**
	 * 
	 * @param i The campus map image to display
	 */
	public CampusMapView(Image i) {
		path = null;
		this.i = i;
		setPreferredSize(new Dimension(1024, 728));
	}

	/**
	 * Clears the path and resets the view
	 */
	public void reset() {
		path = null;
		repaint();
	}

	/**
	 * Changes the path and redraws the new path
	 * @param p new path to be drawn
	 */
	public void setPath(CampusPath p) {
		path = p;
		repaint();
	}

	/**
	 * Most of the work done here. Draws the map image and the path on top of it.
	 * If the path is currently set, only the region around the path will be drawn.
	 * If no path is set, the whole map will be drawn.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int currentWidth = getWidth();
		int currentHeight = getHeight();
		double currentAsp = (double) currentWidth / currentHeight;
		Graphics2D g2 = (Graphics2D) g;
		if (path != null) {
			CampusBuilding start = path.getStartBuilding();
			CampusBuilding end = path.getDestinationBuilding();
			double distx = Math.abs(start.getLocation().getX()
					- end.getLocation().getX());
			double disty = Math.abs(start.getLocation().getY()
					- end.getLocation().getY());
			double pathAsp = distx / disty;
			int sx1, sx2, sy1, sy2;
			double scale;
			if (pathAsp > currentAsp) { //Path is wide compared to window
				sx1 = (int) (Math.min(start.getLocation().getX(), end
						.getLocation().getX()) - 100); //find minimum x location of start and destination add 100px padding
				sx2 = (int) (Math.max(start.getLocation().getX(), end
						.getLocation().getX()) + 100); //find maximum x location of start and destination add 100px padding
				int newDisty = (int) ((distx + 200) / currentAsp); //Distance between sy1 and sy2 needed to maintain aspect ratio
				sy1 = (int) (Math.min(start.getLocation().getY(), end
						.getLocation().getY()) - (100 / currentAsp));
				sy2 = (int) (Math.max(start.getLocation().getY(), end
						.getLocation().getY()) + (100 / currentAsp));
				int diff = (sy2 - sy1);
				sy1 -= (newDisty - diff) / 2; //offset to make (sy2 - sy1) = newDisty
				sy2 += (newDisty - diff) / 2;
				scale = (distx + 200) / currentWidth;
			} else { //Path is tall compared to window
				sy1 = (int) (Math.min(start.getLocation().getY(), end
						.getLocation().getY()) - 100);//find minimum y location of start and destination add 100px padding
				sy2 = (int) (Math.max(start.getLocation().getY(), end
						.getLocation().getY()) + 100);//find maximum y location of start and destination add 100px padding
				int newDistx = (int) ((disty + 200) * currentAsp); //Distance between sy1 and sy2 needed to maintain aspect ratio
				sx1 = (int) (Math.min(start.getLocation().getX(), end
						.getLocation().getX()) - (100 * currentAsp));
				sx2 = (int) (Math.max(start.getLocation().getX(), end
						.getLocation().getX()) + (100 * currentAsp));
				int diff = (sx2 - sx1);
				sx1 -= (newDistx - diff) / 2; //offset to make (sx2 - sx1) = newDistx
				sx2 += (newDistx - diff) / 2;
				scale = (disty + 200) / currentHeight; 
			}
			// sx1,sy1,sx2,sy2 mark a rectangular region of the image around the selected path that has the same
			// aspect ratio as the current window
			g2.drawImage(i, 0, 0, currentWidth, currentHeight, sx1, sy1,
					sx2, sy2, null); //draw map
			g2.setColor(Color.RED);
			// Draw Paths
			g2.setStroke(new BasicStroke(3));
			int prevX = (int) ((start.getLocation().getX() - sx1) / scale);
			int prevY = (int) ((start.getLocation().getY() - sy1) / scale);
			for (CampusLocation l : path.getPathLocations()) {
				int nextX = (int) ((l.getX() - sx1) / scale);
				int nextY = (int) ((l.getY() - sy1) / scale);
				g2.drawLine(prevX, prevY, nextX, nextY);
				prevX = nextX;
				prevY = nextY;
			}

			// Mark Buildings
			g2.fillOval((int) ((start.getLocation().getX() - sx1) / scale) - (int) (5 / (scale / 2)),
					    (int) ((start.getLocation().getY() - sy1) / scale) - (int) (5 / (scale / 2)),
					    (int) (10 / (scale / 2)), (int) (10 / (scale / 2)));
			g2.fillOval((int) ((end.getLocation().getX() - sx1) / scale) - (int) (5 / (scale / 2)),
				     	(int) ((end.getLocation().getY() - sy1) / scale) - (int) (5 / (scale / 2)),
					    (int) (10 / (scale / 2)), (int) (10 / (scale / 2)));
			
		} else {
			g2.drawImage(i, 0, 0, currentWidth, currentHeight, 0, 0, 4330, (int) (4330 / currentAsp), null);
		}
	}
}
