package org.example;

import org.example.Graphics.Frame;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Wave wave2 = new Wave(2, 0.5f);
        Wave wave3 = new Wave(3, -0.25f);

        wave3.Normalize().GetCircularPoints(0.5f);

        // Einzelne Wellen anzeigen
        //Frame frame1 = new Frame("Welle 1 (f=2)", wave2.GetPoints());
        Frame frame2 = new Frame("Welle 2 (f=3)", wave3.Normalize().GetCircularPoints(3f));
        //Frame frame4 = new Frame("Welle 2 (f=3) N", wave3.Normalize().GetPoints());

        // Kombinierte Welle
        List<Wave> waves = new ArrayList<>();
        waves.add(wave2);
        waves.add(wave3);
        Wave combinedWave = Wave.Combine(waves);
        //Frame frame3 = new Frame("Kombinierte Welle", combinedWave.GetPoints());
    }
}