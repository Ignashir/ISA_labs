package org.example;

public class Calculator implements Runnable {
    private final ResourceQueue resourceQueue;
    private final ResourceQueue resultQueue;
    private boolean running = false;
    private final String name;

    public Calculator(ResourceQueue resourceQueue, ResourceQueue resultQueue, String name) {
        this.resourceQueue = resourceQueue;
        this.resultQueue = resultQueue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            running = true;
            consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void consume() throws InterruptedException {
        while (running) {
            if(resourceQueue.isEmpty()) {
                try {
                    System.out.println("Waiting for calculation");
                    resourceQueue.waitIsNotEmpty();
                } catch (InterruptedException e) {
                    System.out.println("Error while waiting to Consume messages.");
                    break;
                }
            }
            if (!running) {
                break;
            }
            Resource resource = resourceQueue.remove();
            calculate(resource);
        }
        System.out.println("Calculator finished.");
    }

    private void calculate(Resource resource) throws InterruptedException {
        System.out.println("Calculating " + resource.getValue());
        int result = Integer.parseInt(resource.getValue()) * 10;
        resultQueue.add(new Resource("[" + this.name + "]" + "Result of " + resource.getValue() + " is " + result));
        Thread.sleep(5000);
    }

    public void stop() {
        System.out.println("Stopping Calculator : " + this.name);
        running = false;
        resourceQueue.notifyIsNotEmpty();
    }
}
