package org.example.Graphics;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PointDrawer extends JPanel {
    private final List<PointD> points;
    private final List<PointD> singlePoints;
    private static final int POINT_SIZE = 4;
    private static final int PADDING = 50;

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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Finde die Grenzen der Datenpunkte
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (PointD point : points) {
            minX = Math.min(minX, point.x);
            maxX = Math.max(maxX, point.x);
            minY = Math.min(minY, point.y);
            maxY = Math.max(maxY, point.y);
        }

        // Füge einen kleinen Puffer hinzu
        double xBuffer = (maxX - minX) * 0.1;
        double yBuffer = (maxY - minY) * 0.1;
        minX -= xBuffer;
        maxX += xBuffer;
        minY -= yBuffer;
        maxY += yBuffer;

        // Berechne einheitliche Skalierung
        int availableWidth = getWidth() - 2 * PADDING;
        int availableHeight = getHeight() - 2 * PADDING;
        
        double xRange = maxX - minX;
        double yRange = maxY - minY;
        
        // Berechne die einheitliche Skalierung basierend auf dem kleineren verfügbaren Platz
        double scale = Math.min(availableWidth / xRange, availableHeight / yRange);
        
        // Zentriere die Zeichnung
        int usedWidth = (int) (xRange * scale);
        int usedHeight = (int) (yRange * scale);
        int startX = (getWidth() - usedWidth) / 2;
        int startY = (getHeight() - usedHeight) / 2;

        // Bestimme Ursprung des Koordinatensystems
        int originX = (int) (-minX * scale) + startX;
        int originY = (int) (maxY * scale) + startY;

        // Zeichne Achsen
        g2d.setColor(Color.BLACK);
        
        // X-Achse
        g2d.drawLine(startX, originY, startX + usedWidth, originY);
        drawArrow(g2d, startX + usedWidth, originY, 0);

        // Y-Achse
        g2d.drawLine(originX, startY + usedHeight, originX, startY);
        drawArrow(g2d, originX, startY, 90);

        // Bestimme Schrittgröße für Markierungen
        double xStep = calculateStepSize(xRange);
        double yStep = calculateStepSize(yRange);

        // Zeichne Skala und Markierungen
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        // X-Achsen Markierungen
        for (double x = Math.ceil(minX/xStep)*xStep; x <= maxX; x += xStep) {
            int xPos = (int) (originX + x * scale);
            g2d.drawLine(xPos, originY - 5, xPos, originY + 5);
            g2d.drawString(String.format("%.1f", x), xPos - 15, originY + 20);
        }

        // Y-Achsen Markierungen
        for (double y = Math.ceil(minY/yStep)*yStep; y <= maxY; y += yStep) {
            int yPos = (int) (originY - y * scale);
            g2d.drawLine(originX - 5, yPos, originX + 5, yPos);
            g2d.drawString(String.format("%.2f", y), originX - 40, yPos + 5);
        }

        // Zeichne die Datenpunkte und Linien
        g2d.setColor(Color.RED);
        if (points.size() > 1) {
            for (int i = 0; i < points.size() - 1; i++) {
                PointD p1 = points.get(i);
                PointD p2 = points.get(i + 1);

                int x1 = (int) (originX + p1.x * scale);
                int y1 = (int) (originY - p1.y * scale);
                int x2 = (int) (originX + p2.x * scale);
                int y2 = (int) (originY - p2.y * scale);

                g2d.drawLine(x1, y1, x2, y2);
                g2d.fillOval(x1 - POINT_SIZE/2, y1 - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
            }

            // Letzten Punkt zeichnen
            if (!points.isEmpty()) {
                PointD last = points.get(points.size() - 1);
                int x = (int) (originX + last.x * scale);
                int y = (int) (originY - last.y * scale);
                g2d.fillOval(x - POINT_SIZE/2, y - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
            }
        }

        // Zeichne einzelne Punkte
        g2d.setColor(Color.BLUE);
        for (PointD point : singlePoints) {
            int x = (int) (originX + point.x * scale);
            int y = (int) (originY - point.y * scale);
            g2d.fillOval(x - POINT_SIZE/2, y - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        }
    }

    private double calculateStepSize(double range) {
        double roughStep = range / 10.0;
        double[] steps = {0.1, 0.2, 0.5, 1.0, 2.0, 5.0};
        double power = Math.pow(10, Math.floor(Math.log10(roughStep)));
        
        for (double step : steps) {
            if (step * power >= roughStep) {
                return step * power;
            }
        }
        return steps[steps.length - 1] * power;
    }

    private void drawArrow(Graphics2D g2d, int x, int y, int angle) {
        int[] xPoints = {x, x-5, x+5};
        int[] yPoints = {y-8, y, y};
        
        Graphics2D g2dRotated = (Graphics2D) g2d.create();
        g2dRotated.translate(x, y);
        g2dRotated.rotate(Math.toRadians(angle));
        g2dRotated.translate(-x, -y);
        g2dRotated.fillPolygon(xPoints, yPoints, 3);
        g2dRotated.dispose();
    }
}