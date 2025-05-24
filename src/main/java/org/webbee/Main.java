package org.webbee;

import org.webbee.services.FileProcessor;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Needed one argument");
            return;
        }
        FileProcessor processor = new FileProcessor(args[0]);
        processor.process();
    }
}