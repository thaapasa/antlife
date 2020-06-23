package fi.tuska.antlife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import fi.tuska.antlife.simulator.Cell;
import fi.tuska.antlife.util.Grid2D;

public class Renderer extends JComponent {

    private static final Logger log = Logger.getLogger(Renderer.class);
    private static final long serialVersionUID = 2511507400948423556L;

    private BufferedImage buffer;

    private static final Color COLOR_BACKGROUND = Color.BLACK;
    private static final Color COLOR_GRID = Color.BLUE;
    private static final Color COLOR_ACTIVE_CELL = Color.WHITE;
    private static final Color COLOR_SPECIAL_CELL = Color.YELLOW;

    private int cellSize = 5;
    private static final int GRID_SIZE = 1;

    public Renderer(int width, int height) {
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension size = new Dimension(width, height);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public void zoom(boolean in) {
        if (in) {
            cellSize++;
        } else {
            cellSize = Math.max(1, cellSize - 1);
        }
        log.debug("Cell size is " + cellSize);
    }

    public synchronized void render(Grid2D<Cell> grid, boolean phase, int cx, int cy) {
        Dimension size = getSize();
        final int width = (int) size.getWidth();
        final int height = (int) size.getHeight();

        Graphics2D g = buffer.createGraphics();
        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, width, height);
        g.setClip(0, 0, width, height);

        int hCells = (width + cellSize + GRID_SIZE) / (cellSize + GRID_SIZE);
        int vCells = (height + cellSize + GRID_SIZE) / (cellSize + GRID_SIZE);

        int y = cy + vCells / 2;
        for (int yp = 0; yp < height; yp += cellSize + GRID_SIZE) {
            int x = cx - hCells / 2;
            // Draw horizontal line
            g.setColor(COLOR_GRID);
            g.drawLine(0, yp, width, yp);
            for (int xp = 0; xp < width; xp += cellSize + GRID_SIZE) {
                // Draw vertical line
                if (yp == 0) {
                    g.setColor(COLOR_GRID);
                    g.drawLine(xp, 0, xp, height);
                }
                // x, y are logical Grid coordinates
                // xp, yp are corresponding screen coordinates for the
                // drawn cell
                Cell c = grid.get(x, y);

                if (c != null) {
                    if (c.isSpecial())
                        g.setColor(COLOR_SPECIAL_CELL);
                    else if (c.get(phase))
                        g.setColor(COLOR_ACTIVE_CELL);
                    else
                        g.setColor(COLOR_BACKGROUND);
                    g.fillRect(xp + 1, yp + 1, cellSize, cellSize);
                }
                x++;
            }
            y--;
        }
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public synchronized void update(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

}
