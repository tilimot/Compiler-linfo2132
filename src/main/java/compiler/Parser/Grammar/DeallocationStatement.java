package compiler.Parser.Grammar;

public class DeallocationStatement extends Statement {
    String free_;
    String identifier;
    String eol;


    public DeallocationStatement(String free_, String identifier, int tabIndex){
        super(tabIndex);
        this.free_ = free_;
        this.identifier = identifier;


    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t +free_ + "\n" + t + identifier + "\n" +t + eol + "\n";
    }
}
