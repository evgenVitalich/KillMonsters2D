/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//КЛАСС "БОЯ" МЕЖДУ ИГРОКОМ И МОНСТРОМ
public class Battle implements ActionListener, Runnable{
    
    Main back;
    Enemy E;
    Thread BattleSpeed = new Thread(this);
    public Battle(Main BK){
        this.back = BK;
        
        BattleSpeed.start();
        
    }
    
     @Override 
    public void run(){
         while(true){
            try{
            
            back.isNear();
            Thread.sleep(1000);
            
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
                
                
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}  

