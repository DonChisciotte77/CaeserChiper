package Classes;

import java.util.ArrayList;

public class DecryptThread implements Runnable {
    ArrayList<String> cipherText;
    StringBuilder plainText;
    int key;

    public DecryptThread(ArrayList<String> cipherText, int key) {
        this.cipherText = cipherText;
        this.plainText = new StringBuilder();
        this.key = key;
    }

    @Override
    public void run() {
        String firstLine = this.cipherText.get(0);

        for(int i = 0; i < firstLine.length(); i++){
            char ch = firstLine.charAt(i);

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

        System.out.println("Key: " + key + "; First Line: " + plainText);
    }
}
