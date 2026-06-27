package src.java.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LocalResourceLoader implements InputLoader {

    private final String filePath;

    public LocalResourceLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> loadInputs() {
        try {
            return loadFromFilepath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> loadFromFilepath() throws IOException {
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

    private List<String> loadFrom(InputStream is) {
        return loadFrom(new InputStreamReader(is));
    }

    private List<String> loadFrom(InputStreamReader inputStreamReader) {
        return loadFrom(new BufferedReader(inputStreamReader));
    }

    private List<String> loadFrom(BufferedReader reader) {
        List<String> lines = new ArrayList<>();
        reader.lines().forEach(lines::add);
        return lines;
    }
}