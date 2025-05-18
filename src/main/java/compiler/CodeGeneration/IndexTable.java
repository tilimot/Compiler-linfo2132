package compiler.CodeGeneration;

import compiler.Parser.Grammar.Type;

import java.util.HashMap;

public class IndexTable {
    private IndexTable parentTable = null;
    private HashMap<String, Integer> table;
    private int current_index; //current number of identifier stored

    public IndexTable(IndexTable parentTable) {
        this.parentTable = parentTable;
        this.table = new HashMap<>();
        this.current_index = 0;
    }

    public void addIdentifier(String identifier) throws Exception {
        this.current_index+=1;
        int index = current_index;
        table.put(identifier, index);
    }

    public int getCurrent_index(){
        return this.current_index;
    }

    public Integer getIndexIdentifier(String identifier) throws Exception {
        /*
        * Return the index associated to the given identifier
        * */
        IndexTable currentTable = this;
        while (currentTable != null && !currentTable.table.containsKey(identifier)) {
            currentTable = currentTable.parentTable;
        }
        if (currentTable != null) {
            return currentTable.table.get(identifier);
        } else {
            System.out.println("currentTable: " + currentTable.table + " identifier: " + identifier);
            throw new Exception("VariableError, " + identifier + " is not defined.");
        }
    }

}
