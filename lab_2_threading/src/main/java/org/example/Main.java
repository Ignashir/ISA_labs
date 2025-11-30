package org.example;

import org.example.Calculator;
import org.example.Inserter;
import org.example.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int getElem(Object[] array, Object key) {
        for  (int i = 0; i < array.length; i++) {
            if (key.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws InterruptedException {
        int position = getElem(args, "--threads");
        if (position == -1) {
            System.out.println("Please provide number of threads to be used in the calculations --threads X");
            return;
        }

        int numberOfThreads = Integer.parseInt(args[position + 1]);
        System.out.println("Starting with: " + numberOfThreads + " calculating threads");
        int MAX_QUEUE_CAPACITY = 20;
        ResourceQueue resourceQueue = new ResourceQueue(MAX_QUEUE_CAPACITY);
        ResourceQueue resultQueue = new ResourceQueue(MAX_QUEUE_CAPACITY * numberOfThreads);

        // Create Calculator threads
        List<Calculator> calculators = new ArrayList<>();
        for(int i = 0; i < numberOfThreads; i++) {
            Calculator calculator = new Calculator(resourceQueue, resultQueue, "Calculator nr. " + (i + 1));
            Thread calculatorThread = new Thread(calculator);
            calculatorThread.start();
            calculators.add(calculator);
        }

        Inserter inserter = new Inserter(resourceQueue);
        Thread inserterThread = new Thread(inserter);
        inserterThread.start();

        inserterThread.join();

        // Stop threads
        for (Resource resource : resultQueue.getQueue()){
            System.out.println(resource.getValue());
        }
        calculators.forEach(Calculator::stop);
    }
}