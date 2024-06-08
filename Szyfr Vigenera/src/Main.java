import java.io.*;
import java.util.Scanner;

public class Main {
    static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Provide a file name (including the extension): ");
        String fileName = scanner.nextLine();

        System.out.println("Choose option: ");
        System.out.println("1. Encryption");
        System.out.println("2. Decryption");
        String option = scanner.nextLine();

        System.out.print("Provide key: ");
        String key = scanner.nextLine();


        File file = new File(fileName);

        try (FileReader reader = new FileReader(file);


             FileWriter writer = new FileWriter(option + ".txt")) {

            char[] data = new char[(int) file.length()];
            reader.read(data); // read data

            String text = new String(data); // conversion


            if (option.equalsIgnoreCase("1")) {
                String encryptedText = encrypt(text, key);
                writer.write(encryptedText);
                System.out.println("The file has been encrypted using '" + key + "' as a key.");
            } else if (option.equalsIgnoreCase("2")) {
                String decryptedText = decrypt(text, key);
                writer.write(decryptedText);
                System.out.println("The file has been decrypted using '" + key + "' as a key.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // encryption
    public static String encrypt(String text, String key) {
        key = key.toUpperCase();
        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char textSign = text.charAt(i);

            if (textSign == ' ') {
                encryptedText.append(' ');
            } else {
                char keySign = key.charAt(keyIndex % key.length());
                int textSignIndex = alphabet.indexOf(textSign);
                int keySignIndex = alphabet.indexOf(keySign);

                int encryptedSignIndex = (textSignIndex + keySignIndex) % alphabet.length();
                encryptedText.append(alphabet.charAt(encryptedSignIndex));

                keyIndex++;
            }
        }

        return encryptedText.toString();
    }

    // decryption
    public static String decrypt(String text, String key) {
        key = key.toUpperCase();
        StringBuilder decryptedText = new StringBuilder();

        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char textSign = text.charAt(i);

            if (textSign == ' ') {
                decryptedText.append(' ');
            } else {
                char keySign = key.charAt(keyIndex % key.length());
                int textSignIndex = alphabet.indexOf(textSign);
                int keySignIndex = alphabet.indexOf(keySign);

                int decryptedSignIndex = (textSignIndex - keySignIndex + alphabet.length()) % alphabet.length();
                decryptedText.append(alphabet.charAt(decryptedSignIndex));

                keyIndex++;
            }
        }

        return decryptedText.toString();
    }

}
