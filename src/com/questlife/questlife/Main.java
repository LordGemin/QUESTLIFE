package com.questlife.src;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void printVN() throws InterruptedException {
        System.out.println("Unknown: Hello");
        Thread.sleep(1000);
        System.out.println("Unknown: What's your name?");
        Scanner input = new Scanner(System.in);
        System.out.print("You: ");
        String name = input.next();
        System.out.println("Unknown: Nice to meet you " + name);
        Thread.sleep(1000);
        System.out.println("Unknown: Let me introduce myself");
        Thread.sleep(1000);
        System.out.println("Unknown: I'm awesome.");
        Thread.sleep(1000);
        System.out.println("Unknown: There is nothing else you need to know.");

    }

    public static void main(String[] args) {
        try {
            printVN();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
