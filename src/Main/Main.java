package Main;

import Caesar.CaesarCipher;
import Classes.Read;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

public class Main {
    private Read input = new Read();

    public static void main(String[] args) {
        Main main = new Main();
        char choice = main.menu();

        String pathFile = main.chooseFile();
        String newFileName = main.readNewFileName();

        if (choice == 'e' || choice == 'E') {
            try {
                int key = main.readKey();
                new CaesarCipher(pathFile, newFileName).encrypt(key);
                System.out.println("::::: DONE ::::");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new CaesarCipher(pathFile, newFileName).decrypt();
                System.out.println("::::: DONE ::::");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readNewFileName() {
        System.out.print("New File Name: ");
        String fileName = input.readString();

        if (fileName.length() <= 0)
            readNewFileName();

        return  fileName;
    }

    private int readKey() {
        System.out.print("Key: ");
        int key = input.readInt();

        if (key <= 0 || key >= 26)
            readKey();

        return key;
    }

    @Nullable
    private String chooseFile() {
        System.out.print("Choose File: ");
        FileDialog fileDialog = openFileDialog();

        if (fileDialog.getFile() != null) {
            System.out.println(fileDialog.getDirectory()  + fileDialog.getFile());
            return fileDialog.getDirectory()  + fileDialog.getFile();
        }

        return null;
    }

    @NotNull
    private FileDialog openFileDialog() {
        FileDialog fileDialog = new FileDialog(new Frame(), "File to Use", FileDialog.LOAD);
        fileDialog.setFile("*.txt;*.cod");
        fileDialog.setModal(true);
        fileDialog.setVisible(true);

        return fileDialog;
    }

    private char menu() {
        System.out.println("::::::::::::::: Caesar Program :::::::::::::::");

        while (true) {
            System.out.print("Encrypt or Decrypt? [E/D]: ");
            char choice = input.readChar();
            if (choice == 'D' || choice == 'd' || choice == 'E' || choice == 'e')
                return choice;
        }
    }
}
