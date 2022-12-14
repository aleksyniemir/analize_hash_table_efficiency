package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class HashListChainingTest {

    @Test
    public void removeTest() {
        HashListChaining<String> hashListChaining = new HashListChaining<>(3);
        hashListChaining.add("element");
        hashListChaining.remove("element");
        assertEquals(null, hashListChaining.get("element"));
    }

    @Test
    public void getTest() {
        HashListChaining<String> hashListChaining = new HashListChaining<>(5);
        hashListChaining.add("element");
        assertEquals("element", hashListChaining.get("element"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentTest() {
        HashListChaining<String> hashListChaining = new HashListChaining<>(5);
        hashListChaining.add(null);
    }

    @Test
    public void duplicateTest() {
        HashListChaining<String> hashListChaining = new HashListChaining<>(5);
        hashListChaining.add("element");
        hashListChaining.add("element");
        hashListChaining.remove("element");
        assertEquals(null, hashListChaining.get("element"));
    }

    @Test
    public void TimeTest(){
        HashListChaining<String> hashListChaining = new HashListChaining<>(32768);
        String[] words = new String[100000];
        long start, end;
        int i, j;
        for (j = 0; j < 30; j++) {
            i = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("dane.txt"))) {
                String line;

                while ((line = br.readLine()) != null) {
                    words[i] = line;
                    hashListChaining.add(line);
                    i++;
                }

            } catch (IOException e) {
                System.out.println("Problem with opening or reading from a file with data");
            }
            start = System.nanoTime();

            for (i = 0; i < 100000; i++) {
                hashListChaining.get(words[i]);
            }

            end = System.nanoTime();
            File wynik = new File("32768.txt");
            try {
                PrintWriter out = new PrintWriter(new FileWriter(wynik, true));
                out.append(end - start + "\n");
                out.close();
            } catch (IOException e) {
                System.out.println("Problem with opening or writing to a file with results");
            }

            for(i = 0; i < 100000; i++) {
                words[i] = null;
            }
        }
    }
}
