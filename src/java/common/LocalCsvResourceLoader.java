package src.java.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalCsvResourceLoader implements InputLoader {

    private final String filePath;

    public LocalCsvResourceLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> loadInputs() {
        try {
            return loadFromFilePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> loadFromFilePath() throws IOException {
        if ( ! fileExists() ) throw  new FileNotFoundException("File not found: " + filePath);
        try (InputStream is = getStreamFromFile()) {
            return loadFrom(is);
        }
    }

    private InputStream getStreamFromFile() throws FileNotFoundException {
        return new FileInputStream("src/resources/" + filePath);
    }

    private boolean fileExists() {
        return new File("src/resources/" + filePath).exists();
    }

    private List<String> loadFrom(InputStream is) throws IOException {
        return loadFrom(new InputStreamReader(is));
    }

    private List<String> loadFrom(InputStreamReader inputStreamReader) throws IOException {
        return loadFrom(new BufferedReader(inputStreamReader));
    }

    private List<String> loadFrom(BufferedReader reader) throws IOException {
        List<String> ids = new ArrayList<>();
        StringBuilder idPair = new StringBuilder();
        while (true) {
            int character = reader.read();
            switch (character) {
                case -1:
                    ids.add(idPair.toString());
                    return ids;
                case ',':
                    ids.add(idPair.toString());
                    idPair = new StringBuilder();
                    continue;
                case '\r':
                    continue;
                case '\n':
                    continue;
                default:
                    idPair.append((char) character);
            }
        }
    }
}