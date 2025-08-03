package org.example.Graphics;

import javax.swing.*;
import java.util.List;

public class Frame {
    public Frame(String windowTitle, List<PointD> points) {
        // Create frame
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);  // Larger window to better show the wave
        frame.setLocationRelativeTo(null); // Center on screen
        frame.add(new PointDrawer(points));
        frame.setVisible(true);
    }
}