package org.example;

import org.example.Graphics.Frame;
import org.example.Graphics.PointD;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Wave wave2 = new Wave(2, 0.5f);
        Wave wave3 = new Wave(3, -0.25f);

        Wave wave = wave3.Normalize();

        // Einzelne Wellen anzeigen
        float cyclesPerSecond = 3f;
        Frame frame = new Frame("Welle 2 (f=3)", wave.GetCircularPoints(cyclesPerSecond));
        //frame.AddPoint(new PointD(0f, 0)); // Mark origin
        PointD centerOfMass = wave.GetCenterOfMass(cyclesPerSecond);
        frame.AddPoint(centerOfMass); // Mark origin
        System.out.println(centerOfMass.x + " | " + centerOfMass.y);

        // Kombinierte Welle
        // List<Wave> waves = new ArrayList<>();
        // waves.add(wave2);
        // waves.add(wave3);
        // Wave combinedWave = Wave.Combine(waves);
    }
}