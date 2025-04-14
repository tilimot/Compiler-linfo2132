package compiler.Parser.Grammar;

public abstract class Statement {
    int tabIndex;
    public Statement(int tabIndex){
        this.tabIndex = tabIndex;
    }

    public abstract void semanticAnalysis() throws Exception;


}
