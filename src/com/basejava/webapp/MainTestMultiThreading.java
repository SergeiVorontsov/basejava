package com.basejava.webapp;

public class MainTestMultiThreading {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread thread1 = new Thread(runner::method1);
        Thread thread2 = new Thread(runner::method2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("VM stop");
    }
}

class Runner {
    private final Monitor monitor1 = new Monitor();
    private final Monitor monitor2 = new Monitor();

    public void method1() {
        for (int i = 0; i < 10000; i++) {
            synchronized (monitor1) {
                synchronized (monitor2) {
                    Monitor.action(monitor1,monitor2);
                }
            }
        }
        System.out.println("Thirst thread done");

    }

    public void method2() {
        for (int i = 0; i < 10000; i++) {
            synchronized (monitor2) {
                synchronized (monitor1) {
                    Monitor.action(monitor1,monitor2);
                }
            }
        }
        System.out.println("Second thread done");
    }
}

class Monitor {
    int amount = 100;

    public void add(int amount) {
        this.amount += amount;
    }

    public void remove(int amount) {
        this.amount -= amount;
    }
    public static void action (Monitor mon1, Monitor mon2){
        mon1.add(5);
        mon2.remove(5);
    }
}



