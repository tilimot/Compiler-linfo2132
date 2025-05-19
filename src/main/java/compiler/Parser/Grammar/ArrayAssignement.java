package compiler.Parser.Grammar;

import compiler.Parser.Parser;
import compiler.Semantic.Semantic;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ArrayAssignement extends Statement{
    public String identifier;
    public ArrayList<Type> type;
    public String equal;
    public String array;
    public ArrayList<Expression> size;
    public String typeOf;


    public ArrayAssignement(String identifier, ArrayList<Type> type,String equal,String array,ArrayList<Expression> size,String typeOf, int tabIndex){
        super(tabIndex);
        this.identifier = identifier;
        this.type = type;
        this.equal = equal;
        this.array = array;
        this.size = size;
        this.typeOf = typeOf;
    }
    @Override
    public void semanticAnalysis(SymbolTable symbolTable) throws Exception {
        LinkedHashMap<String, Type> st = symbolTable.getTable();
        if (!st.containsKey(identifier)) {
            st.put(identifier, type.getFirst());
        }

        Semantic.checkReturn(type.getFirst(),size);

    }
}
