package compiler.Lexer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileToReader {

    public static Reader getReaderFromFile(String filePath) {
        try {
            File file = new File(filePath);
            return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}