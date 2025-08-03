package org.example.Graphics;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PointDrawer extends JPanel {
    private final List<PointD> points;

    public PointDrawer(List<PointD> points) {
        this.points = points;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);

        // Fenstermaße abrufen
        int width = getWidth();
        int height = getHeight();

        // Mittelpunkt des Fensters
        int centerX = width / 2;
        int centerY = height / 2;

        // Punkte mit Verschiebung zum Zentrum zeichnen
        for (int i = 0; i < points.size() - 1; i++) {
            PointD p1 = points.get(i);
            PointD p2 = points.get(i + 1);

            // Punkte als Integer konvertieren und zum Zentrum verschieben
            int x1 = (int) (p1.x * 100) + centerX;
            int y1 = (int) (p1.y * 100) + centerY;
            int x2 = (int) (p2.x * 100) + centerX;
            int y2 = (int) (p2.y * 100) + centerY;

            // Linie zwischen den Punkten zeichnen
            g.drawLine(x1, y1, x2, y2);
        }
    }
}