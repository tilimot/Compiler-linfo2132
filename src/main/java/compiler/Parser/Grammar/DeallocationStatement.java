package compiler.Parser.Grammar;

public class DeallocationStatement extends Statement {
    String free_;
    String identifier;
    String eol;

    public DeallocationStatement(String free_, String identifier, String eol){
        this.free_ = free_;
        this.identifier = identifier;
        this.eol = eol;
    }
}
