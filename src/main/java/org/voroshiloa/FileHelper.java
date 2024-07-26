package org.voroshiloa;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHelper {
    protected static List<String> readWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                Pattern pattern = Pattern.compile("#[\\w_\\d]+");
                Matcher matcher = pattern.matcher(data);
                while (matcher.find()) {
                    words.add(matcher.group());
                }
            }
            reader.close();
            return words;
        } catch (FileNotFoundException e) {
            System.out.println("input.txt file is supposed to be present at the same folder as launching file");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected static List<String> selectRandomWords(List<String> words, int count) {
        if (count > words.size()) {
            throw new IllegalArgumentException("Not enough words in the list.");
        }

        List<String> shuffledWords = new ArrayList<>(words);
        Collections.shuffle(shuffledWords);

        return shuffledWords.subList(0, count);
    }

    protected static void writeWordsToFile(List<String> words, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String word : words) {
                writer.print(word + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
