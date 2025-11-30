package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class ResourceQueue {
    private final Queue<Resource> queue = new LinkedList<>();
    private final int maxSize;
    private final Object IS_NOT_FULL = new Object();
    private final Object IS_NOT_EMPTY = new Object();

    ResourceQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void waitIsNotFull() throws InterruptedException {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.wait();
        }
    }

    public void notifyIsNotFull() {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.notify();
        }
    }

    public void waitIsNotEmpty() throws InterruptedException {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.wait();
        }
    }

    public void notifyIsNotEmpty() {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.notify();
        }
    }

    public void add(Resource resource) {
        queue.add(resource);
        notifyIsNotEmpty();
    }

    public Resource remove() {
        Resource resource = queue.poll();
        notifyIsNotFull();
        return resource;
    }

    public boolean isFull() {
        return queue.size() >= maxSize;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
