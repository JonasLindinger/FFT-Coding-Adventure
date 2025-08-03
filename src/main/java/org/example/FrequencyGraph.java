package org.example;

import org.example.Graphics.PointD;

import java.util.ArrayList;
import java.util.List;

public class FrequencyGraph {
    private List<PointD> points = new ArrayList<>();

    public FrequencyGraph(Wave wave, float xMin, float xMax) {
        PointD center = new PointD(0, 0);

        // Go through every x position of the graph, we want to create
        // x-coordinate for the center of mass
        for (float x = xMin; x < xMax; x += Wave.step) {
            PointD centerOfMass = wave.GetCenterOfMass(x);
            double y = Math.abs(getDistance(center, centerOfMass));

            points.add(new PointD(x, y));
            System.out.println(x + " | " + y);
        }
    }

    private static double getDistance(PointD p1, PointD p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    public List<PointD> GetPoints() {
        return points;
    }
}
