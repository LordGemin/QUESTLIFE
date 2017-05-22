package main.java.com.questlife.questlife.util;

import javafx.scene.paint.Color;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 22.05.2017.
 */
public class Logger {
    private final static File file = new File("log.txt");
    private static List<String> stringWriter = new ArrayList<>();

    public static void log(String message, Color color) {

        String a = "["+getColor(color)+"] "+createTimeStamp()+": "+message+"\n";

        FileOutputStream fOs;
        if(stringWriter.size()==1000) {
            stringWriter.remove(0);
        }
        stringWriter.add(a);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fOs = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            for(String s : stringWriter)
                fOs.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(a);
        try {
            fOs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        log(message, null);
    }

    public static File getFile() {
        return file;
    }

    private static String createTimeStamp() {
        String timestamp = "";
        LocalDateTime ldt = LocalDateTime.now();
        timestamp += ldt.getHour()+":" + ldt.getMinute()+":"+ldt.getSecond()+"."+ldt.getNano()/10000000;
        return timestamp;
    }

    private static String getColor(Color color) {
        if(color == null) {
            return "0xffffffff";
        }
        return color.toString();
    }

    public static Color getColor(String color) {
        return Color.valueOf(color);
    }
}
