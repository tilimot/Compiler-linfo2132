package compiler.Lexer;
import java.io.Reader;
import java.io.StringReader;

public class Lexer {
    
    public Lexer(Reader input) {
    }
    
    public Symbol getNextSymbol() {
        return null;
    }

    public static void main(String[] args) {
        String test= "int a = \"variance\" 1235.9 + 3.14 = int a";
        Reader test2 = new StringReader(test);
        Tokenizer tokenizer = new Tokenizer(test2);
        System.out.println(tokenizer.getTokens());
    }
}
