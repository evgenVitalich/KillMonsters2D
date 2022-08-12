/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;




public class Player {
    
    //ИГРОК
    Image ic = new ImageIcon("./src/resources/knight_st2.png").getImage();
    //ИГРОК
    
    
    //ТЕКСТУРЫ АНИМАЦИИ ХОДЬБЫ
    Image walk1 = new ImageIcon("./src/resources/anim/walk1.png").getImage();
    Image walk2 = new ImageIcon("./src/resources/anim/walk2.png").getImage();
    Image walk3 = new ImageIcon("./src/resources/anim/walk3.png").getImage();
    Image walk4 = new ImageIcon("./src/resources/anim/walk4.png").getImage();
    Image walk5 = new ImageIcon("./src/resources/anim/walk5.png").getImage();
    Image walk6 = new ImageIcon("./src/resources/anim/walk6.png").getImage();
    Image walk7 = new ImageIcon("./src/resources/anim/walk7.png").getImage();
    Image walk8 = new ImageIcon("./src/resources/anim/walk8.png").getImage();
    Image backDIR = new ImageIcon("./src/resources/anim/back_st.png").getImage();
    //ТЕКСТУРЫ АНИМАЦИИ АТАКИ
    Image hitting1 = new ImageIcon("./src/resources/anim/hitting1.png").getImage();
    Image hitting2 = new ImageIcon("./src/resources/anim/hitting2.png").getImage();
    Image hitting3 = new ImageIcon("./src/resources/anim/hitting3.png").getImage();
    Image hitting4 = new ImageIcon("./src/resources/anim/hitting4.png").getImage();
    Image hitting5 = new ImageIcon("./src/resources/anim/hitting5.png").getImage();
    Image hitting6 = new ImageIcon("./src/resources/anim/hitting6.png").getImage();
    Image hitting7 = new ImageIcon("./src/resources/anim/hitting7.png").getImage();
    





   
    
    
    
    
    
    //ПЕРЕМЕННЫЕ
    boolean ATTACK = false; //"РАЗРЕШЕНИЕ" НА АТАКУ
    boolean ATC_KEY = false; //СОСТОЯНИЕ КНОПКИ АТАКИ
    boolean ANIM = false; //"РАЗРЕШЕНИЕ" НА АНИМАЦИЮ
    boolean DIR = true; //НАПРАВЛЕНИЕ ХОДЬБЫ ("ТРУ" - НАПРАВО)
    private int damage = 20;
    private int x = 400;
    private int y = 715;
    private int speed = 0;
    private int dv = 0;
    private int mX = 0;
    private int mY = 0;
    private int S = 0;
    private int L1 = 0; //КООРДИНАТЫ ФОНА ОТНОСИТЕЛЬНО ТЕКСТУРКИ ПЕРСОНАЖА
    private int L2 = 1080; //КООРДИНАТЫ ФОНА ОТНОСИТЕЛЬНО ТЕКСТУРКИ ПЕРСОНАЖА (1100)
    int L3 = -1920;
    public int anim = 0;
    private int HP = 100;
    private int maxHP = 100;
    public int money = 30;
    public int attackspeed = 600;
    public int HealthCount = 1; //КОЛИЧЕСТВО ЗЕЛИЙ ЛЕЧЕНИЯ (НОРМАЛЬНЫЙ ИНВЕНТАРЬ НЕ ПОЛУЧИЛСЯ) :(( 
    Main back; //ОСНОВНОЙ ПРОЦЕСС ИГРЫ
    public int regenSpeed = 0; //ТЕКУЩАЯ СКОРОСТЬ РЕГЕНЕРАЦИИ ЗДОРОВЬЯ
    public int maxRegenSpd = 1; //МАКСИМАЛЬНАЯ СКОРОСТЬ РЕГЕНЕРАЦИИ ХП
    //ПЕРЕМЕННЫЕ
    
    //ОБЪЕКТ КЛАССА "ANIMATION"
    private Animation playerWalk;
    
    public Player(){
    
    Listener lst = new Listener();
    Timer attack = new Timer(attackspeed, lst); //ТАЙМЕР СКОРОСТИ АТАКИ
    
    attack.start(); //ТАЙМЕР СКОРОСТИ АТАКИ
    
    attack.setRepeats(true);
    attack.setDelay(attackspeed);
    }
    private class Listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
                   
                    if(ATC_KEY){
                       ATTACK = true; //ЕСЛИ НАЖАТА КНОПКА АТАКИ - АТАКУЙ
                       
                   } 
                   else{
                        
                        ATTACK = false; 
                   }
		}
 
    }    
    
    
    public Rectangle getRect(){
        
        return new Rectangle(x, y, 50, 120); //"ХИТБОКС" ПЕРСОНАЖА
        
        
    }
    
    
    
    public int getHP(){
        
        return HP;
        
    }
    
    public int getDamage(){
        
        return damage;
        
    }
    
    public void TakeDamage(int dmg){
        //МЕТОД ПОЛУЧЕНИЯ УРОНА
        HP -= dmg;
        
    }
    
    public void setMoney(int M){
        
        this.money += M;
        
    }
    
    public int getMoney(){
        
        return money;
        
    }
    
    public void setSpeed(int speed){
        
        this.speed = speed;
        
    }
    public void resetHP(){
        
        HP = maxHP;
        
    }
    
    public int getS(){
        
        return S;
        
    }

    public int getX(){
        
        return x;
        
    }
    
    public int getY(){
        
        return y;
        
    }
    
    public int getSpd(){
        
        return speed;
        
    }
    
    //ЗАПУСК АНИМАЦИИ
    
    
    
    public int getL1(){
        //КООРДИНАТЫ ФОНА ОТНОСИТЕЛЬНО ТЕКСТУРКИ ПЕРСОНАЖА
        return L1;
        
    }
    public int getL2(){
        //КООРДИНАТЫ ФОНА ОТНОСИТЕЛЬНО ТЕКСТУРКИ ПЕРСОНАЖА
        return L2;
        
    }
    public int getmY(){
        
        return mY;
        
    }
    
    public void move(){
        //ДВИЖЕНИЕ
        if(DIR){
            
            if(L2 - speed <= 0){
            //ЕСЛИ ВТОРАЯ КАРТИНКА "ЗАКАНЧИВАЕТСЯ", ПЕРВАЯ "МЕНЯЕТСЯ" МЕСТАМИ СО ВТОРОЙ
            
            
            
            
            L1 = 0;
            L2 = 1920;
            L3 = -1920;
            /*if(!DIR && L3 - speed >= 0){
 
               L1 = -1920;
               L3 = 0;
               
            }
            */
        }
        else{          
            //ПЕРЕМЕЩАЕМ КАРТИНКИ НА ФОНЕ СО СКОРОСТЬЮ ПЕРСОНАЖА
            L1 -= speed;
            L2 -= speed; 
            L3 -= speed;
        }
        
        
     }
        else{
            if(L3 - speed >= 0){       
            L3 = -1920;
            L2 = 0;
            L1 = 1920;
            }
             else{          
            //ПЕРЕМЕЩАЕМ КАРТИНКИ НА ФОНЕ СО СКОРОСТЬЮ ПЕРСОНАЖА
            L1 -= speed;
            L2 -= speed; 
            L3 -= speed;
        }
        
        
     }
}
    
    
    public void Heal(){
        //МЕТОД ДЛЯ ИСПОЛЬЗОВАНИЯ ЗЕЛЬЯ ЛЕЧЕНИЯ
        if(HealthCount > 0){
            
            HP += 70;
            HealthCount--;
            if(HP > maxHP){HP = maxHP;}
        }
        
    }
    public void increaseDamege(int Incr){
        //МЕТОД ДЛЯ УВЕЛИЧЕНИЯ УРОНА (ИЗ МАГАЗИНА)
        damage += Incr;
        
    }
     public void increaseHP(int Incr){
        //МЕТОД ДЛЯ УВЕЛИЧЕНИЯ МАКСИМАЛЬНОГО ЗДОРОВЬЯ (ИЗ МАГАЗИНА)
        maxHP += Incr;
        
    }
     public void increaseRegen(){
         //МЕТОД ДЛЯ УВЕЛИЧЕНИЯ СКОРОСТИ РЕГЕНЕРАЦИИ(ИЗ МАГАЗИНА)
         maxRegenSpd++;
         
     }
    public int attack(){     
        
        return damage;
    }
    public void Regeneration(boolean BT, boolean shop, boolean pause){
        //МЕТОД РЕГЕНЕРАЦИИ ЗДОРОВЬЯ
        //РАБОТАЕТ ВСЕГДА КРОМЕ СОСТОЯНИЯ
        //КОГДА ИГРОК В МАГАЗИНЕ, НА ПАУЗЕ, ИЛИ В БОЮ
        
        
        regenSpeed = maxRegenSpd;
        if(BT || shop || pause){
        regenSpeed = 0;
        }
        else if(HP < maxHP){
        HP += regenSpeed;
        if(HP > maxHP){
            HP = maxHP;
        }
        }
        else{
            regenSpeed = maxRegenSpd;
        }
    }
    
    //ОТРАБОТКА СОБЫТИЙ НАЖАТИЯ НА КЛАВИАТУРУ
    public void KeyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_D){
            DIR = true;
            ANIM = true;
            speed = 7;
            
        }
        if(key == KeyEvent.VK_A){           
            speed = -4;
            DIR = false;
        }    
                
        if(key == KeyEvent.VK_E){
            ATC_KEY = true;     
        }
        
              
    }
    public void KeyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_D){
            
            ANIM = false;
            speed = 0;
           
        }
        if(key == KeyEvent.VK_A){
            DIR = true;
            speed = 0;
           
        } 
        if(key == KeyEvent.VK_E){
            
           ATC_KEY = false;
        }  
    }
    
}