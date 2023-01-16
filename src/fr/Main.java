package fr;

import java.awt.Dimension;

public class Main {
    public static void main (String[] args) {
        Grid grid = new Grid(new Dimension(100, 100/16*9));
        MainMenu frame = new MainMenu();
    }
}