package com.zianedu.api.utils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Robot2 {

    public static void main(String[] args) {
        try {
            jump1();
            //keyboard1();
            //spacebar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    public static void mouse1() throws Exception {
//        Robot robot = new Robot();
//        robot.mouseMove(2000, 250);
//        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK
//    }
    public static void loginNaver() throws Exception {
        Robot robot = new Robot();
        robot.mouseMove(2350, 470);
        robot.delay(1000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        //로그인버튼
        robot.delay(1000);
        robot.mouseMove(1900, 500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

//    public static void keyboard1() throws Exception {
//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_A);
//        robot.keyPress(KeyEvent.VK_B);
//        robot.keyPress(KeyEvent.VK_C);
//    }

    public static void spacebar() throws Exception {
        Robot robot = new Robot();
        int duration = 3000;
        robot.delay(5000);
//        long start = System.currentTimeMillis();
//        while (System.currentTimeMillis() - start < duration) {
//            robot.keyPress(KeyEvent.VK_W);
//        }
//        robot.keyRelease(KeyEvent.VK_W);
//        robot.keyPress(KeyEvent.VK_3);
//        robot.keyRelease(KeyEvent.VK_3);1
        //10번동안
        for (int i=0; i<10; i++) {
            //robot.delay(1000);
            //1번 4번클릭(물)
            for (int j=0; j<6; j++) {
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                robot.delay(3500);
            }
            robot.delay(1000);
            //3번 4번클릭(빵)
            //robot.keyPress(KeyEvent.VK_3);
            //robot.keyRelease(KeyEvent.VK_3);

            robot.delay(3500);
            //2번클릭(물먹기)
            robot.keyPress(KeyEvent.VK_2);
            robot.keyRelease(KeyEvent.VK_2);
            robot.delay(23000);
            //스페이스 클릭(일어서기)
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
            robot.delay(1000);
        }
    }

    public static void jump1() throws Exception {
        Robot robot = new Robot();
        //10번동안
        for (int i=0; i<60; i++) {
            robot.delay(60000);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
        }
    }
}
