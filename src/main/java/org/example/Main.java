package org.example;

import org.example.Graphics.Frame;
import org.example.Graphics.PointD;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Wave wave2 = new Wave("2 Beats/Second", 2, 0.125f);
        Wave wave3 = new Wave("3 Beats/Second", 3, -0.25f);

        List<Wave> waves = new ArrayList<>();
        waves.add(wave2);
        waves.add(wave3);

        Wave wave = Wave.Combine(waves);
        //Wave wave = wave3;

        //ShowWave(wave2);
        //ShowWave(wave3);
        ShowWave(wave);
        ShowWaveAroundOrigin(wave, 2);
        //ShowWaveAroundOrigin(wave, 98);
        ShowFrequencyGraph(wave, 0, 1000);
    }

    private static void ShowWave(Wave wave) {
        // W => Wave
        Frame frame = new Frame("W: " + wave.GetWaveName(), wave.Normalize().GetPoints());
    }

    private static void ShowWaveAroundOrigin(Wave wave, float cyclesPerSecond) {
        // OW => Origin Wave
        Frame frame = new Frame("OW: " + wave.GetWaveName(), wave.Normalize().GetCircularPoints(cyclesPerSecond));
        frame.AddPoint(wave.Normalize().GetCenterOfMass(cyclesPerSecond)); // Show center of mass
        frame.AddPoint(new PointD(0f, 0)); // Show center
    }

    private static void ShowFrequencyGraph(Wave wave, float xMin, float xMax) {
        // FG => Frequency Graph Wave
        FrequencyGraph graph = new FrequencyGraph(wave, xMin, xMax);
        Frame frame = new Frame("FGW: " + wave.GetWaveName(), graph.GetPoints());
    }
}