package org.example;

import java.util.Scanner;

public class Inserter implements Runnable{
    private final ResourceQueue resourceQueue;
    private boolean running = false;
    public Inserter(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
    }

    @Override
    public void run() {
        running = true;
        gatherInputs();
    }

    private void insert(Resource resource) {
        if(resourceQueue.isFull()) {
            try {
                resourceQueue.waitIsNotFull();
            } catch (InterruptedException e) {
                System.out.println("Error while waiting to Produce messages.");
                return;
            }
        }
        resourceQueue.add(resource);
    }

    public void gatherInputs(){
        Scanner inputBuffer = new Scanner(System.in);
        String input = "";

        while(running) {
            System.out.println("Input number to perform calculations / stop for terminating program.");
            input = inputBuffer.nextLine();

            if (input.equals("stop")) {
                stop();
                break;
            }
            // Check if input is a Number
            if (!input.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Invalid input");
                continue;
            }

            // Insert number into queue
            insert(new Resource(input));
        }
    }

    public void stop() {
        running = false;
        resourceQueue.notifyIsNotFull();
    }
}
