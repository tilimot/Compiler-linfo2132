package compiler.Lexer;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class Lexer {
    Reader input;
    List<String> tokenList;
    int currentIndex;
    public Lexer(Reader input) {
        this.input = input;
        Tokenizer tokenizer = new Tokenizer(input);
        this.tokenList = tokenizer.getTokens();
        this.currentIndex=0;
    }

    public boolean hasNextSymbol(){
        return currentIndex < tokenList.size();
    }
    
    public Symbol getNextSymbol() {
        if (hasNextSymbol()){
            String token = tokenList.get(currentIndex);
            Symbol symbol = new Symbol(TokenClassifier.classifyToken(token),token);
            this.currentIndex +=1;
            return symbol;
        }
        else {
            throw new IllegalStateException("No more symbols available");
        }
    }




}
