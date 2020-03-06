package Caesar;

import Classes.DecryptThread;
import Classes.Read;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;

@SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
public class CaesarCipher {
    private String pathFile;
    private String pathDirectory;
    private String newFileName;

    public CaesarCipher(String pathFile, String newFileName) {
        this.pathFile = pathFile;
        this.pathDirectory = new File(this.pathFile).getParent();
        this.newFileName = newFileName;
    }

    public void encrypt(int key) throws IOException {
        String plainText = new String(Files.readAllBytes(Paths.get(pathFile)));

        StringBuilder cipheredText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            char ch = plainText.charAt(i);

            if(ch >= 'a' && ch <= 'z'){
                ch = (char) (ch + key);
                if(ch > 'z'){
                    ch = (char) (ch - 'z' + 'a' - 1);
                }
                cipheredText.append(ch);
            } else if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch + key);

                if (ch > 'Z') {
                    ch = (char) (ch - 'Z' + 'A' - 1);
                }
                cipheredText.append(ch);
            } else {
                cipheredText.append(ch);
            }
        }

        String finalPath = pathDirectory + "\\" + newFileName + ".cod";
        Files.write(Paths.get(finalPath), Collections.singleton(cipheredText), StandardCharsets.UTF_8);
    }

    public void decrypt() throws IOException {
        ArrayList<String> cipherText = (ArrayList<String>) Files.readAllLines(Paths.get(pathFile));

        ExecutorService executor= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 25; i++) {
            int key = i + 1;
            executor.execute(new DecryptThread(cipherText, key));
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Correct Key: ");
        int key = new Read().readInt();

        StringBuilder plainText = new StringBuilder();
        for (String line : cipherText) {
            for(int i = 0; i < line.length(); i++){
                char ch = line.charAt(i);

                if (ch >= 'a' && ch <= 'z') {
                    ch = (char) (ch - key);

                    if (ch < 'a') {
                        ch = (char) (ch + 'z' - 'a' + 1);
                    }
                    plainText.append(ch);
                } else if (ch >= 'A' && ch <= 'Z') {
                    ch = (char) (ch - key);

                    if(ch < 'A'){
                        ch = (char)(ch + 'Z' - 'A' + 1);
                    }
                    plainText.append(ch);
                } else {
                    plainText.append(ch);
                }
            }
        }

        String finalPath = pathDirectory + "\\" + newFileName + ".txt";
        Files.write(Paths.get(finalPath), Collections.singleton(plainText), StandardCharsets.UTF_8);
    }
}
