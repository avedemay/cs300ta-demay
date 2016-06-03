package com.company;

import GUI.LandingFrame;
import GUI.MainFrame;

import javax.swing.*;
/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Main class
 * This class will kick start the landing page for the Task Master program
 */


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LandingFrame();
            }
        });

    }
}
