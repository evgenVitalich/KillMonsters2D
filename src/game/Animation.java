/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Image;

//КЛАСС АНИМАЦИИ
//ПОДСМОТРЕНО НА АНГЛИЙСКОМ ЮТУБЧИКЕ

public class Animation {

    private int speed;
    private int frames;

    private int index = 0;
    private int count = 0;

    private Image[] images; //МАССИВ ТЕКСТУР
    private Image currentIMG; //ТЕКУЩАЯ ТЕКСТУРА

    public Animation(int speed, Image... args /*ПЕРЕДАЁМ СЮДА МАССИВ КАРТИНОК И ЖЕЛАЕМУЮ СКОРОСТЬ ИХ ИЗМЕНЕНИЯ*/) {
        this.speed = speed;
        images = new Image[args.length];
        for (int i = 0; i < args.length; i++) {

            images[i] = args[i];

        }
        frames = args.length;
    }
    //ЗАПУСК АНИМАЦИИ
    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextFrame();

        }

    }
    //ОБНОВЛЕНИЕ ТЕКУЩЕЙ ТЕКСТУРКИ
    private void nextFrame() {
        for (int i = 0; i < frames; i++) {
            if (count == i) {
                currentIMG = images[i];
            }
        }
        count++;
        if (count > frames) {
            count = 0;
        }

    }
    //ДВА МЕТОДА ДЛЯ ОТРИСОВКИ АНИМАЦИИ
    //ОДИН ПРИНИМАЕТ ПРОСТО КООРДИНАТЫ
    //ДРУГОЙ ПОЗВОЛЯЕТ УВЕЛИЧИТЬ/СЖАТЬ ТЕКСТУРКУ
    public void drawAnimation(Graphics g, int x, int y) {
        g.drawImage(currentIMG, x, y, null);
    }

    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
        g.drawImage(currentIMG, x, y, scaleX, scaleY, null);
    }
}
