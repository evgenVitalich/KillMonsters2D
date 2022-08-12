/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
public class Enemy implements ActionListener  {
    
    boolean isDead = false; //СОСТОЯНИЕ МОНСТРА
    int x = 2000;
    int y = 729;
    int damage;
    int HP;
    int attackSPD = 3000;
    int speed = 1;
    int maxSpeed = 4;
    Main back;
    boolean M_ANIM = false;
        //ТЕКСТУРЫ
        Image IC = new ImageIcon("./src/resources/monster_st.png").getImage();
        Image Mwalk1 = new ImageIcon("./src/resources/anim/monster_walk1.png").getImage();
        Image Mwalk2 = new ImageIcon("./src/resources/anim/monster_walk2.png").getImage();
        Image Mwalk3 = new ImageIcon("./src/resources/anim/monster_walk3.png").getImage();
        Image Mwalk4 = new ImageIcon("./src/resources/anim/monster_walk4.png").getImage();
        Image Mwalk5 = new ImageIcon("./src/resources/anim/monster_walk5.png").getImage();
        Image Mwalk6 = new ImageIcon("./src/resources/anim/monster_walk6.png").getImage();
    public Enemy(int X, int HP, int damage, int maxSpd, Main backgr){
        this.x = x;
        this.HP = HP;
        this.damage = damage;
        this.back = backgr;
        this.maxSpeed = maxSpd;     
    }
    //HitBox
    public Rectangle getRect(){
        
        return new Rectangle(x - 60, y, 60, 130);
        
        
    }
    public void move(){
        
        x = x - back.player.getSpd() - speed;
        
        
    }
    public void isDead(){
        
        if(HP <= 0){
            //С НЕКОТОРЫМ ШАНСОМ (СЕЙЧАС - 10%) ПОСЛЕ СМЕТРИ МОНСТРА
            //ИГРОКУ В ИНВЕНТАРЬ ДОБАВЛЯЕТСЯ ЗЕЛЬЕ ЛЕЧЕНИЯ
            back.MonsterCount--;
            Random rnd = new Random();
            int prob = rnd.nextInt(100);
            isDead = true;
            if(prob < 10){
                back.player.HealthCount++;
            }
            HP = 0;
        }
        
    }
    
    public void isAnim(){
        //СОСТОЯНИЕ АНИМАЦИИ
        //СДЕЛАНО КРИВОВАТО, Т.К. ОРИЕНТИРУЕТСЯ НА СКОРОСТЬ МОНСТРА
        //НО РАБОТАЕТ
        if(speed != 0){
            M_ANIM = true;
        }
        else{
            M_ANIM = false;
        }
        
    }
    
    public int attack(){
        
        return damage;
        
    }
    
     
    
    
    
    @Override
    public void actionPerformed(ActionEvent e){
       
    }

}

