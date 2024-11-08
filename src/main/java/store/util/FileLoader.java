package store.util;

import store.dto.PromotionDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static List<String> load(String fileName) {
        List<String> contents = new ArrayList<>();
        try {
            InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new IOException("Resource file not found.");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}
