package org.example.Graphics;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PointDrawer extends JPanel {
    private final List<PointD> points;
    private final List<PointD> singlePoints;
    private static final int POINT_SIZE = 8;

    public PointDrawer(List<PointD> points) {
        this.points = points;
        this.singlePoints = new ArrayList<>();
    }

    public void addSinglePoint(PointD point) {
        singlePoints.add(point);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        // Verbundene Punkte und Linien zeichnen
        g.setColor(Color.RED);
        if (points.size() > 1) {
            for (int i = 0; i < points.size() - 1; i++) {
                PointD p1 = points.get(i);
                PointD p2 = points.get(i + 1);

                int x1 = (int) (p1.x * 100) + centerX;
                int y1 = (int) (-p1.y * 100) + centerY;  // Beachten Sie das negative Vorzeichen
                int x2 = (int) (p2.x * 100) + centerX;
                int y2 = (int) (-p2.y * 100) + centerY;  // Beachten Sie das negative Vorzeichen

                g.drawLine(x1, y1, x2, y2);
                g.fillOval(x1 - POINT_SIZE/2, y1 - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
            }
            // Letzten Punkt zeichnen
            if (!points.isEmpty()) {
                PointD last = points.get(points.size() - 1);
                int x = (int) (last.x * 100) + centerX;
                int y = (int) (-last.y * 100) + centerY;  // Beachten Sie das negative Vorzeichen
                g.fillOval(x - POINT_SIZE/2, y - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
            }
        }

        // Einzelne Punkte in anderer Farbe zeichnen
        g.setColor(Color.BLUE);
        for (PointD point : singlePoints) {
            int x = (int) (point.x * 100) + centerX;
            int y = (int) (-point.y * 100) + centerY;  // Beachten Sie das negative Vorzeichen
            g.fillOval(x - POINT_SIZE/2, y - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        }
    }
}