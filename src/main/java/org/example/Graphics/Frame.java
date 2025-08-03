package org.example.Graphics;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    private JFrame frame;
    private List<PointD> points;
    private PointDrawer pointDrawer;

    public Frame(String windowTitle, List<PointD> points) {
        this.points = new ArrayList<>(points);
        this.pointDrawer = new PointDrawer(this.points);
        
        frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.add(pointDrawer);
        frame.setVisible(true);
    }

    public void AddPoint(PointD point) {
        pointDrawer.addSinglePoint(point); // Verwendet neue Methode für einzelne Punkte
    }
}