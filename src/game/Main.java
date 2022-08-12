/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;



import java.awt.*;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends JPanel implements ActionListener, Runnable {
    
    int MonsterCount = 50;
    
    Timer time = new Timer(13, this); //ОСНОВНОЙ ТАЙМЕР ОБНОВЛЕНИЯ КАРТИНКИ
    
    JFrame frame;
    
    Player player = new Player();
    
    Thread MonstersSpawner = new Thread(this); //ПОТОК ДЛЯ СОЗДАНИЯ МОНСТРОВ КАЖДЫЕ "N" СЕКУНД
    
    List<Enemy> enemies = new ArrayList<Enemy>(); //МАССИВ МОНСТРОВ
    
    public Animation playerWalk; //ОБЪЕКТ АНИМАЦИИ ХОДЬБЫ
    
    public Animation playerAttack; //ОБЪЕКТ АНИМАЦИИ АТАКИ ПЕРСОНАЖА
    
    public Animation monsterWalk; //ОБЪЕКТ АНИМАЦИИ ХОДЬБЫ МОНСТРА
    
    Enemy CurrentMonster; //ТУТ ХРАНИТСЯ ТЕКУЩИЙ МОНСТР (ПОСЛЕДНИЙ ПОЯВИВШИЙСЯ)
    
    boolean PAUSE = false; //СОСТОЯНИЕ ПАУЗЫ
    boolean SHOP = false; //СОСТОЯНИЕ МАГАЗИНА
    public Main(JFrame frame){
        this.frame = frame;
        time.start(); //ЗАПУСК ТАЙМЕРА ОБНОВЛЕНИЯ ОТРИСОВКИ
        enemies.add(new Enemy(1000, 59, 10, 3, this));
        CurrentMonster = enemies.get(0);
        MonstersSpawner.start(); //СТАРТ ПОТОКА СОЗДАНИЯ МОНСТРОВ
         //ТЕКСТУРЫ АНИМАЦИИ ХОДЬБЫ ИГРОКА
         Image[] tex = new Image[8];
         tex[0] = player.walk1;
         tex[1] = player.walk2;
         tex[2] = player.walk3;
         tex[3] = player.walk4;
         tex[4] = player.walk5;
         tex[5] = player.walk6;
         tex[6] = player.walk7;
         tex[7] = player.walk8;
         //ТЕКСТУРЫ АНИМАЦИИ АТАКИ ИГРОКА
         Image[] swordHitting = new  Image[7];
         swordHitting[0] = player.hitting1;
         swordHitting[1] = player.hitting2;
         swordHitting[2] = player.hitting3;
         swordHitting[3] = player.hitting4;
         swordHitting[4] = player.hitting5;
         swordHitting[5] = player.hitting6;
         swordHitting[6] = player.hitting7;
         //ТЕКСТУРЫ АНИМАЦИИ ХОДЬБЫ МОНСТРА
         Image[] Monster_walk = new  Image[6];
         Monster_walk[0] = CurrentMonster.Mwalk1;
         Monster_walk[1] = CurrentMonster.Mwalk2;
         Monster_walk[2] = CurrentMonster.Mwalk3;
         Monster_walk[3] = CurrentMonster.Mwalk4;
         Monster_walk[4] = CurrentMonster.Mwalk5;
         Monster_walk[5] = CurrentMonster.Mwalk6;
         //ANIM SPEED = 3
         playerWalk = new Animation(3, tex[0], tex[1], tex[2], tex[3], tex[4], tex[5], tex[6], tex[7]);
         //ANIM SPEED = 5
         playerAttack = new Animation(6, swordHitting[0], swordHitting[1], swordHitting[2], swordHitting[3], swordHitting[4], swordHitting[5], swordHitting[6]);
         monsterWalk = new Animation(5, Monster_walk[0], Monster_walk[1], Monster_walk[2], Monster_walk[3], Monster_walk[4], Monster_walk[5]);
         frame.addKeyListener(new KeyAdapter(){
        
        


        @Override
        public void keyPressed(KeyEvent e){
            
            player.KeyPressed(e);
            int key = e.getKeyCode();
            //ВЫЗОВ ПАУЗЫ
            if(key == KeyEvent.VK_P){
            PAUSE = true;
            time.stop();
            repaint();
        }
            
            if(key == KeyEvent.VK_ENTER){
                //ВЫХОД ИЗ ПАУЗЫ
                PAUSE = false;
                time.restart();
            
        }
            if(key == KeyEvent.VK_H){
            //ИЧПОЛЬЗОВАТЬ ЗЕЛЬЕ ЛЕЧЕНИЯ
            player.Heal();
            
        }
            if(key == KeyEvent.VK_F3){
                //ЗАЙТИ В МАГАЗИН
                time.stop();
                SHOP = true;
                CurrentMonster.damage = 0;
                repaint();
        }
            if(key == KeyEvent.VK_F4){
            //ВЫХОД ИЗ МАГАЗА
            SHOP = false;
            repaint();
            
            if(!PAUSE){
                time.restart();
                CurrentMonster.damage = 8;
            }
        }
            
            if(key == KeyEvent.VK_ESCAPE){
                
                time.stop();
                System.exit(0);
            
        }
            if(key == KeyEvent.VK_R){
                
                if(!AreYouAlive(player.getHP()) || MonsterCount == 0){
                    
                    player.resetHP();
                    AreYouAlive(player.getHP());
                    CurrentMonster.HP = 0;
                    MonsterCount = 50;
                    player.HealthCount = 1;
                    player.move();
                    repaint();
                    time.restart();
                    MonsterCount++;
                    
                }
        }
            //МЕТОДЫ ДЛЯ МАГАЗИНА
            
            if(key == KeyEvent.VK_1){
            if(SHOP == true && player.getMoney() >= 35){
                player.HealthCount++;
                player.money -= 35;
                repaint();
            }
            
            
        }if(key == KeyEvent.VK_2){
            if(SHOP == true && player.getMoney() >= 60){
                player.increaseDamege(10);
                player.money -= 60;
                repaint();
            }
            
        }if(key == KeyEvent.VK_3){
            if(SHOP == true && player.getMoney() >= 60){
                player.increaseHP(40);
                player.money -= 60;
                repaint();
            }
            
        }if(key == KeyEvent.VK_4){
            if(SHOP == true && player.getMoney() >= 70){
                player.increaseRegen();
                player.money -= 70;
                repaint();
            }
            
        }    
        }
        @Override
        public void keyReleased(KeyEvent e){
            
            player.KeyReleased(e);
            

        }
            
});
    frame.setFocusable(true);    
    Listener list = new Listener();
    Timer atc = new Timer(800, list);
   
    isNear();
    atc.start();

    
    }
    private class Listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
                    player.Regeneration(isNear(), SHOP, PAUSE);
                    //isNear();
                    Battle(CurrentMonster, isNear());	
		}
 
    }    
    
        
    
    
    public boolean AreYouAlive(int PlayerHP){
        //ПРОВЕРКА НА ТО, ЧТО ТЫ ЖИВ
        boolean alive = true;
        if(PlayerHP <= 0){
            
            alive = false;
            
        }
        else{
            alive = true;
        }
        return alive;
    }
    
    
    //ОТРИСОВКА
    
    public void paint(Graphics g){;
        Image youLose = new ImageIcon("./resources/youlose.jpg").getImage();
        Image youWin = new ImageIcon("./resources/WINNER.jpg").getImage();
        /*if(MonsterCount == 0){   
            time.stop();
            JOptionPane.showMessageDialog(frame, "YOU WON!!");    
        }
        */
        g = (Graphics2D) g;
        Image backgr = new ImageIcon("./resources/backgroundNEW.jpg").getImage();
        Image sun = new ImageIcon("./src/resources/sun.png").getImage();
        Image shop = new ImageIcon("./src/resources/shop_back.jpg").getImage();
        Image backshop = new ImageIcon("./src/resources/shop-layer.png").getImage();
        AreYouAlive(player.getHP());
        
        g.drawImage(backgr, player.getL1(), 0, 1920, 1080, null);
        g.drawImage(backgr, player.getL2(), 0, 1920, 1080, null); 
        //!!!!!!!!!!!!!
        g.drawImage(backgr, player.L3, 0, 1920, 1080, null); 
        //!!!!!!!!!!!!!
        g.drawImage(sun, 100, 36, null);
        
        double MyHP = player.getHP();
        g.setColor(Color.WHITE);
        Font font = new Font("Calibri", Font.BOLD, 22);
        g.setFont(font);
        g.drawString("Победи монстров", 820, 40);
        g.drawString("Осталось убить: " + (int)MonsterCount, 820, 70);
        g.drawString("Здоровье: " + (int)MyHP, 100, 40);
        g.drawString("Деньги: " + player.getMoney(), 100, 65);
        g.drawString("Зелья лечения " + player.HealthCount, 100, 90);
        g.drawString("Здоровье врага " + CurrentMonster.HP, 1600, 40);
        if(!AreYouAlive(player.getHP())){
            g.drawImage(youLose, 0, 0, null);
            g.drawString("RESTART - <R>", 845, 750);
            g.drawString("ВЫХОД - <Esc>", 845, 800);
            time.stop(); 
        }
        if(MonsterCount == 0){   
            g.drawImage(youWin, 0, 0, null);
            g.drawString("RESTART - <R>", 845, 700);
            g.drawString("ВЫХОД - <Esc>", 845, 750);
            time.stop();
                
        }
        
        if(!PAUSE && AreYouAlive(player.getHP()) && MonsterCount !=0){
        g.drawString("Пауза: <P>", 100, 1050);
        g.drawString("Магазин: <F3>", 230, 1050);
        g.drawString("ХОДИТЬ: <A, D>", 420, 1050);
        g.drawString("АТАКА <E>", 590, 1050);
        g.drawString("ЛЕЧЕНИЕ <Н>", 720, 1050);
        g.drawString("Выход из игры <Esc>", 1700, 1050);
        }
        
        if(PAUSE){
            Font fontP = new Font("Calibri", Font.BOLD, 70);
            g.drawString("Магазин: <F3>", 100, 1050);
            g.drawString("PAUSED", 910, 540);
            g.drawString("Вернуться к игре: <ENTER>", 845, 720);
            g.drawString("Выход из игры <Esc>", 1700, 1050);
        }
        if(SHOP){
            
            g.drawImage(shop, 0, 0, null);
            g.drawImage(backshop, 670, 440, null);
            Font fontP = new Font("Calibri", Font.BOLD, 70);
            g.drawString("Выйти из магазина: <F4>", 100, 1050);
            g.drawString("Деньги: " + player.getMoney(), 100, 65);
            g.drawString("Здоровье: " + (int)MyHP, 100, 40);
            g.drawString("Урон: " + player.getDamage(), 100, 115);
            g.drawString("Регенерация: " + player.maxRegenSpd, 100, 140);
            g.drawString("Зелья лечения " + player.HealthCount, 100, 90);
            g.drawString("Зелье лечения - 1  : 35 монет", 845, 540);
            g.drawString("Увеличение урона - 2  : 60 монет", 845, 570);
            g.drawString("Увеличение здоровья - 3  : 60 монет", 845, 600);
            g.drawString("Увеличение скорости регена - 4  : 70 монет", 845, 630);
            
        }
        if(!SHOP){    
        
        
        Iterator<Enemy> i = enemies.iterator();
       
        
        
        while(i.hasNext()){
            Enemy en = i.next();
            en.isDead();
            if(en.isDead){
                player.setMoney((int) (Math.random()*(15-25) + 1)+15);
            }
            if(en.x <= -2000 || en.isDead){
                i.remove();
                repaint();
            }else{ 
                en.move();               
                isNear();
                
            }          
    }
         
}
        
 CurrentMonster.isAnim();      
 if(CurrentMonster.M_ANIM && !CurrentMonster.isDead && !SHOP){
        monsterWalk.runAnimation();
        monsterWalk.drawAnimation(g, CurrentMonster.x, CurrentMonster.y);
    }
 else if(!CurrentMonster.M_ANIM && !CurrentMonster.isDead && AreYouAlive(player.getHP())){
     g.drawImage(CurrentMonster.IC, CurrentMonster.x, CurrentMonster.y, null);
 }
    if(player.ANIM && MonsterCount != 0){
            
            playerWalk.runAnimation();
            playerWalk.drawAnimation(g, player.getX(), player.getY());
        }
    else if(!player.DIR){
        g.drawImage(player.backDIR, player.getX(), player.getY() + 40, null);
    }
    else if(player.ATC_KEY && MonsterCount !=0){
        playerAttack.runAnimation();
        playerAttack.drawAnimation(g, player.getX(), player.getY());
    } 
    else if(!SHOP && AreYouAlive(player.getHP())){
        if(MonsterCount !=0){
        g.drawImage(player.ic, player.getX(), player.getY(), null);
        
        }
        
    }
    
    
    }      
    
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        player.move();
        repaint();

        
    }
    
    
    public void Battle(Enemy en, boolean near){
        isNear();
        if(near && player.ATTACK){
            en.HP -= player.attack();
        }
        if(near){
        player.TakeDamage(en.attack());
        }
    }
    

    //МЕТОД, ПРОВЕРЯЮЩИ РАССТОЯНИЕ МЕЖДУ ИГРОКОМ И МОНСТРОМ 
    public boolean isNear(){
        boolean near = false;
        Iterator<Enemy> i = enemies.iterator();
        while(i.hasNext()){
            Enemy e = i.next();
            //ДЕМО ВЕРСИЯ ИСКУСТВЕННОГО ИНТЕЛЛЕКТА
            //ЕСЛИ ИГРОК ОТОШЁЛ НА БОЛЕЕ ЧЕМ "N" - БЕГИ ЗА НИМ
            //ИНАЧЕ - ОСТАНОВИСЬ
            if(player.getRect().intersects(e.getRect())){         
                near = true;
                e.speed = 0;
                player.setSpeed(0);
                if(CurrentMonster.x <= player.getX()){
                    
                    near = false;
                    
                }                   
                
            }
            else if(player.getX() > (e.x)){
                near = false;
                e.speed = -e.maxSpeed;  
                
            }
            
            else{
                    near = false;
                    e.speed = e.maxSpeed;
                    
            }
            
           
        }
       return near; 
        
    }
    
    @Override 
    
    //ПЕРЕОПРЕДЕЛЕНИЕ МЕТОДА ДЛЯ ПОТОКА МОНСТРОВ
    
    public void run(){
        while(true){
            try{
                //ЕСЛИ ПРЕДЫДУЩИЙ МОНСТР УМЕР
                //СОЗДАЙ СЛЕДУЮЩЕГО
            if(enemies.isEmpty()){
                //РАНДОМИЗАЦИЯ ХАРАКТЕРИСТИК МОНСТРА
                int MonsHp = (int) (Math.random()*(70-50) + 1)+50;
                int MonDMG = (int) (Math.random()*(13-9) + 1)+9;
                enemies.add(new Enemy(1000, MonsHp, MonDMG, 3, this));
                CurrentMonster = enemies.get(0);
            }
            else{
                //ЗАДЕРЖКА СОЗДАНИЯ МОНСТРА (4 СЕКУНДЫ)
                Thread.sleep(4000);
            }      
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        
        
    }
    
    
}
