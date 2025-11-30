package org.example;

public class Calculator implements Runnable {
    private final ResourceQueue resourceQueue;
    private boolean running = false;

    public Calculator(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
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
        Thread.sleep(5000);
    }

    public void stop() {
        running = false;
        resourceQueue.notifyIsNotEmpty();
    }
}
