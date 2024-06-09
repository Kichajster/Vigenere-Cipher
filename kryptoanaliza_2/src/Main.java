import java.util.*;

public class Main {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHABET_SIZE = ALPHABET.length();

    private static final double[] ENGLISH_FREQUENCIES = {
            0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 0.061, 0.070, 0.002,
            0.008, 0.040, 0.024, 0.067, 0.075, 0.019, 0.001, 0.060, 0.063, 0.091,
            0.028, 0.010, 0.023, 0.001, 0.020, 0.001
    };

    // Funkcja do określania długości klucza na podstawie indeksu korespondencji
    public static int findKeyLength(String text) {
        int maxKeyLength = 20; // Maksymalna sprawdzana długość klucza
        double bestIC = 0;
        int bestLength = 1;

        for (int keyLength = 1; keyLength <= maxKeyLength; keyLength++) {
            double averageIC = 0;

            for (int i = 0; i < keyLength; i++) {
                StringBuilder sequence = new StringBuilder();

                for (int j = i; j < text.length(); j += keyLength) {
                    sequence.append(text.charAt(j));
                }

                averageIC += calculateIndexOfCoincidence(sequence.toString());
            }

            averageIC /= keyLength;

            if (averageIC > bestIC) {
                bestIC = averageIC;
                bestLength = keyLength;
            }
        }

        return bestLength;
    }

    // Obliczanie indeksu korespondencji
    private static double calculateIndexOfCoincidence(String text) {
        int[] frequency = new int[ALPHABET_SIZE];
        int total = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                frequency[c - 'A']++;
                total++;
            }
        }

        double ic = 0.0;
        for (int freq : frequency) {
            ic += freq * (freq - 1);
        }

        ic /= (total * (total - 1));

        return ic;
    }

    // Funkcja do analizy częstości liter i odczytu klucza
    public static String findKey(String text, int keyLength) {
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < keyLength; i++) {
            int[] frequency = new int[ALPHABET_SIZE];
            int count = 0;

            for (int j = i; j < text.length(); j += keyLength) {
                char c = text.charAt(j);
                if (Character.isLetter(c)) {
                    frequency[c - 'A']++;
                    count++;
                }
            }

            double[] chiSquares = new double[ALPHABET_SIZE];
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                double chiSquare = 0.0;
                for (int k = 0; k < ALPHABET_SIZE; k++) {
                    int observed = frequency[(k + j) % ALPHABET_SIZE];
                    double expected = count * ENGLISH_FREQUENCIES[k];
                    chiSquare += Math.pow(observed - expected, 2) / expected;
                }
                chiSquares[j] = chiSquare;
            }

            int bestShift = 0;
            for (int j = 1; j < ALPHABET_SIZE; j++) {
                if (chiSquares[j] < chiSquares[bestShift]) {
                    bestShift = j;
                }
            }

            key.append(ALPHABET.charAt(bestShift));
        }

        return key.toString();
    }

    // Funkcja do odszyfrowania wiadomości
    public static String decrypt(String text, String key) {
        StringBuilder decryptedText = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                int decryptedChar = (c - key.charAt(keyIndex) + ALPHABET_SIZE) % ALPHABET_SIZE;
                decryptedText.append(ALPHABET.charAt(decryptedChar));
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                decryptedText.append(c); // Zachowaj odstępy
            }
        }

        return decryptedText.toString();
    }

    public static void main(String[] args) {
        // Przykładowy zaszyfrowany tekst
        String encryptedText = "LXF OPV EFR NHR";  // Przykładowy zaszyfrowany tekst

        encryptedText = encryptedText.toUpperCase().replaceAll("[^A-Z\\s]", "");

        // Określenie długości klucza
        int keyLength = findKeyLength(encryptedText.replaceAll("\\s", ""));
        System.out.println("Długość klucza: " + keyLength);

        // Wydobycie klucza
        String key = findKey(encryptedText.replaceAll("\\s", ""), keyLength);
        System.out.println("Klucz: " + key);

        // Odszyfrowanie wiadomości
        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Odszyfrowana wiadomość: " + decryptedText);
    }
}
