package org.weebee;

import org.weebee.services.FileProcessor;

public class Main {
    public static void main(String[] args) {
        // System.out.println(args[0]);
        FileProcessor a = new FileProcessor("logs");
        try{
            a.process();
        }
        catch(Exception e){
            System.err.println(e);
        }

        System.out.println("Done");
    }
}