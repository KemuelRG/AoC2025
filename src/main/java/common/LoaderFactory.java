package common;

public class LoaderFactory {

    public LoaderFactory() {}

    public static InputLoader getLoaderFor(String fileName) {
        if (fileName == null) throw new IllegalArgumentException("Filename cannot be null");
        return switch (getExtensionFrom(fileName)) {
            case "csv" -> new LocalCsvResourceLoader(fileName);
            case "txt" -> new LocalResourceLoader(fileName);
            default -> throw  new IllegalArgumentException("File with no extension: " + fileName);
        };
    }

    private static String getExtensionFrom(String fileName) {
        int indexOfSeparator = fileName.indexOf(".");
        if (indexOfSeparator != -1) {
            return fileName.substring(indexOfSeparator + 1);
        }
        throw  new IllegalArgumentException("File with no extension: " + fileName);
    }
}