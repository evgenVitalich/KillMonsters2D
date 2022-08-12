/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;



import java.awt.*;
import java.time.*;

import javax.swing.*;
        
public class GUI {
    public static void main(String[] args) {
        
        
    
    JFrame MainFrame = new JFrame("Road to Pirncess");
    MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    MainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    MainFrame.setUndecorated(true);
    MainFrame.add(new Main(MainFrame));
    MainFrame.setVisible(true);
    
    
    
    }
}
