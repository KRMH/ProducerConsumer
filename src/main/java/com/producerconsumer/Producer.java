/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.producerconsumer;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kristel
 */
public class Producer implements Runnable {
    private final Vector sharedQueue;
    private final int SIZE;

    public Producer(Vector sharedQueue, int SIZE) {
        this.sharedQueue = sharedQueue;
        this.SIZE = SIZE;
    }

    /**
    */
    @Override
    public void run(){
        for (int i = 0; i < 7; i++){
            System.out.println("Produced: " + i);
            try{
                produce (i);
            }
            catch(InterruptedException ie){
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ie);
            }
        }
    }

    /**
    */
    private void produce (int i) throws InterruptedException{
        while (sharedQueue.size() == SIZE){
            synchronized(sharedQueue){
                System.out.println("The queue is full " + Thread.currentThread().getName()
                                    + " is waiting , size: " + sharedQueue.size());

                sharedQueue.wait();
            }
        }

        synchronized(sharedQueue){
            sharedQueue.add(i);
            sharedQueue.notifyAll();
        }
    }

}
