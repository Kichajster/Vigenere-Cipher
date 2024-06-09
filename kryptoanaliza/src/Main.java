import java.util.*;

public class Main {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHABET_LENGTH = ALPHABET.length();

    public static void main(String[] args) {
        String ciphertext = "YZT WO UKFWJC ISSDXSZY YHDO";
        String knownPlaintext = "OLA MA BARDZO PIEKNEGO KOTA"; // Przykład znanego tekstu jawnego

        // Krok 1: Złamanie klucza za pomocą znanego tekstu jawnego
        String brokenKey = breakKeyUsingKnownPlaintext(ciphertext, knownPlaintext);
        System.out.println("Złamany klucz: " + brokenKey);

        // Krok 2: Odszyfrowanie wiadomości przy użyciu złamanego klucza
        String decryptedText = decrypt(ciphertext, brokenKey);
        System.out.println("Odszyfrowany tekst: " + decryptedText);
    }

    // Funkcja do łamania klucza przy użyciu znanego tekstu jawnego
    public static String breakKeyUsingKnownPlaintext(String ciphertext, String knownPlaintext) {
        StringBuilder brokenKey = new StringBuilder();
        ciphertext = ciphertext.toUpperCase();
        knownPlaintext = knownPlaintext.toUpperCase();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char plainChar = knownPlaintext.charAt(i % knownPlaintext.length());

            // Jeśli znak jest spacją, dodaj spację do złamanego klucza
            if (cipherChar == ' ') {
                brokenKey.append(' ');
            } else {
                int cipherIndex = ALPHABET.indexOf(cipherChar);
                int plainIndex = ALPHABET.indexOf(plainChar);

                int keyIndex = (cipherIndex - plainIndex + ALPHABET_LENGTH) % ALPHABET_LENGTH;
                brokenKey.append(ALPHABET.charAt(keyIndex));
            }
        }

        return brokenKey.toString();
    }


    // Funkcja do odszyfrowania tekstu przy użyciu klucza
    public static String decrypt(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        StringBuilder decryptedText = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char textSign = text.charAt(i);

            // Jeśli znak jest spacją, dodaj go bez zmian
            if (textSign == ' ') {
                decryptedText.append(' ');
            } else {
                // Upewnij się, że klucz jest przesuwany tylko dla liter
                while (keyIndex < key.length() && key.charAt(keyIndex) == ' ') {
                    keyIndex++;
                }

                char keySign = key.charAt(keyIndex % key.length());
                int textSignIndex = ALPHABET.indexOf(textSign);
                int keySignIndex = ALPHABET.indexOf(keySign);

                // Obliczanie indeksu odszyfrowanej litery
                int decryptedSignIndex = (textSignIndex - keySignIndex + ALPHABET_LENGTH) % ALPHABET_LENGTH;
                decryptedText.append(ALPHABET.charAt(decryptedSignIndex));

                // Przesunięcie klucza tylko po odszyfrowaniu litery
                keyIndex++;
            }
        }

        return decryptedText.toString();
    }

}
