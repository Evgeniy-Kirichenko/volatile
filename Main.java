import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean palindrome(String str) {
        String rev = new StringBuilder(str).reverse().toString();
        return rev.equals(str);
    }

    public static boolean monoStr(String str) {
        char[] char1 = str.toCharArray();
        HashSet<Character> set = new HashSet<>();
        for (char c : char1) {
            set.add(c);
        }
        if (set.size() > 1)
            return false;
        return true;
    }

    public static boolean lettersAscending(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i - 1) > str.charAt(i)) return false;
        }
        return true;
    }

    public static void incr(String str, AtomicInteger a3, AtomicInteger a4, AtomicInteger a5) {
        if (str.length() == 3) {
            a3.incrementAndGet();
        } else if (str.length() == 4) {
            a4.incrementAndGet();
        } else {
            a5.incrementAndGet();
        }

    }


    public static void main(String[] args) throws InterruptedException {
        AtomicInteger a3 = new AtomicInteger(0);
        AtomicInteger a4 = new AtomicInteger(0);
        AtomicInteger a5 = new AtomicInteger(0);


        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (lettersAscending(text)) {
                    incr(text, a3, a4, a5);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (palindrome(text)) {
                    incr(text, a3, a4, a5);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (monoStr(text)) {
                    incr(text, a3, a4, a5);
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длиной 3: " + a3 + "шт");
        System.out.println("Красивых слов с длиной 4: " + a4 + "шт");
        System.out.println("Красивых слов с длиной 5: " + a5 + "шт");

    }
}
