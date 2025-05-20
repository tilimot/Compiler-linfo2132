package compiler.Parser.Grammar;

import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class ElseStatement extends Statement {
    String else_;
    Block block;

    //TODO implement Blocks block;

    public ElseStatement(String else_, Block block, int tabIndex){
        super(tabIndex);
        this.else_ = else_;
        this.block = block;

    }

  
    public Block getBlock() {
        return block;
    }


    @Override
    public String toString() {
        return else_ + "\n" + block;
    }

  
    @Override
    public void semanticAnalysis(SymbolTable symbolTable) throws Exception {
        block.semanticAnalysis(symbolTable);

    }
}
