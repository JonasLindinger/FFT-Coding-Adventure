package org.example;

import org.example.Graphics.PointD;

import java.util.*;

public class Wave {
    // Configuration
    private static final double amplitude = 1;  // Changed to 0.5 to prevent combined wave from going out of bounds
    private static final double step = 0.01;      // Made smaller for smoother curve
    private static final double end = 4.5f;  // 4.5 seconds (multiplied by 2π)

    private List<PointD> points = new ArrayList<>();
    private Map<Double, Double> pointMap = new HashMap<>();

    public Wave(float frequency, float xOffset) {
        for (double x = 0; x <= end; x += step) {
            // x represents time in seconds
            double y = amplitude * Math.sin(2 * Math.PI * frequency * (x + xOffset));  // Corrected frequency calculation
            points.add(new PointD(x, y));
            pointMap.put(x, y);
        }
    }

    public Wave(List<PointD> points) {
        this.points = points;

        // Create the point Map from Point list
        for (PointD point: points) {
            pointMap.put(point.x, point.y);
        }
    }

    public static Wave Combine(List<Wave> waves) {
        if (waves == null || waves.isEmpty()) {
            System.out.println("Waves has a length of 0, which is not valid!");
            return null;
        }
        if (waves.size() == 1) {
            return waves.get(0);
        }

        List<PointD> points = new ArrayList<>();
        Wave firstWave = waves.get(0);
        
        // Use the x-coordinates from the first wave as reference
        for (PointD point : firstWave.GetPoints()) {
            double x = point.x;
            double combinedY = 0;
            
            // Sum up y values from all waves at this x coordinate
            for (Wave wave : waves) {
                Double y = wave.GetPointMap().get(x);
                if (y != null) {
                    combinedY += y;
                }
            }
            
            points.add(new PointD(x, combinedY));
        }

        return new Wave(points);
    }

    public List<PointD> GetPoints() {
        return points;
    }

    public Map<Double, Double> GetPointMap() {
        return pointMap;
    }

    public List<PointD> GetCircularPoints(float cyclesPerSecond) {
        var points = GetPoints();
        int length = points.size();

        List<PointD> newPoints = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointD point = points.get(i);
            
            // Berechne den Winkel basierend auf der Zeit (x-Koordinate) und cyclesPerSecond
            double angle = 2 * Math.PI * cyclesPerSecond * point.x;
            
            // Berechne die Kreiskoordinaten (Trigonometry -> point.y is the hypotenuse)
            double x = Math.cos(angle) * point.y;
            double y = Math.sin(angle) * point.y;
            
            newPoints.add(new PointD(x, y));
        }

        return newPoints;
    }

    private static double clampDegrees(double degrees) {
        // Zuerst den Winkel auf positiv normalisieren
        degrees = degrees % 360.0;

        // Wenn der Wert negativ ist, addieren wir 360°
        if (degrees < 0) {
            degrees += 360.0;
        }

        return degrees;
    }

    public Wave Normalize() {
        // Finden der min und max y-Werte
        double minY = points.stream().mapToDouble(p -> p.y).min().orElse(0);
        double maxY = points.stream().mapToDouble(p -> p.y).max().orElse(1);
        double range = maxY - minY;

        // Neue normalisierte Punkte erstellen
        List<PointD> normalizedPoints = new ArrayList<>();
        for (PointD point : points) {
            double normalizedY = (point.y - minY) / range;
            normalizedPoints.add(new PointD(point.x, normalizedY));
        }

        return new Wave(normalizedPoints);
    }
}