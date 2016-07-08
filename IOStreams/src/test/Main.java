package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public Main() {
//        copyFileBytes("samples/hello.txt", "samples/copied.txt");
        copyFileChars("samples/hello.txt", "samples/copied.txt");
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    public void copyFileBytes(String in, String out) {
        final int BUFSIZE = 5;
        FileInputStream inFile = null;
        FileOutputStream outFile = null;
        byte[] buffer = new byte[BUFSIZE];
        try {
            inFile = new FileInputStream(in);
            outFile = new FileOutputStream(out);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        BufferedInputStream bis = new BufferedInputStream(inFile);
        BufferedOutputStream bos = new BufferedOutputStream(outFile);
        int bytesRead = 0;
        while (bytesRead != -1) {
            try {
                bytesRead = bis.read(buffer, 0, BUFSIZE);
                if (bytesRead != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            bis.close();
            bos.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void copyFileChars(String in, String out) {
        final int BUFSIZE = 1024;
        FileReader inFile = null;
        FileWriter outFile = null;
        char[] buffer = new char[BUFSIZE];
        try {
            inFile = new FileReader(in);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            outFile = new FileWriter(out);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        BufferedReader bis = new BufferedReader(inFile);
        BufferedWriter bos = new BufferedWriter(outFile);
        int charsRead = 0;
        while (charsRead != -1) {
            try {
                charsRead = bis.read(buffer, 0, BUFSIZE);
                if (charsRead != -1) {
                    bos.write(buffer, 0, charsRead);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            bis.close();
            bos.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
