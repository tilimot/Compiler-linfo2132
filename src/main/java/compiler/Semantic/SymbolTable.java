package compiler.Semantic;

import compiler.Parser.Grammar.Type;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parentTable = null;
    private static HashMap<String, Type> table;
    private HashMap<String, Type> constTable;


    public SymbolTable(SymbolTable parentTable) {
        this.parentTable = parentTable;
        this.table = new HashMap<>();
        this.constTable = new HashMap<>();
    }

    public void addSymbol(String name, Type type) throws Exception {
        table.put(name, type);
    }

    public void addConst(String name, Type type) throws Exception {
        constTable.put(name, type);
    }

    public Type getSymbol(String name) throws Exception {
        SymbolTable currentTable = this;
        while (currentTable != null && !currentTable.table.containsKey(name)) {
            currentTable = currentTable.parentTable;
        }
        if (currentTable != null) {
            return currentTable.table.get(name);
        } else {
            throw new Exception("VariableError, " + name + " is not defined.");
        }
    }

    public boolean containsSymbol(String name) throws Exception {
        SymbolTable currentTable = this;
        while ( currentTable != null && !currentTable.table.containsKey(name)){
            System.out.println("currentTable: " + currentTable.table + " name: " + name);
            currentTable = currentTable.parentTable;
        }
        return currentTable != null;
    }

    public Type getConst(String name) throws Exception {
        SymbolTable currentTable = this;
        while (currentTable != null && !currentTable.constTable.containsKey(name)) {
            currentTable = currentTable.parentTable;
        }
        if (currentTable != null) {
            return currentTable.constTable.get(name);
        } else {
            throw new Exception("VariableError, " + name + " is not defined.");
        }
    }

    public boolean containsConstant(String name) throws Exception {
        SymbolTable currentTable = this;
        while ( currentTable != null && !currentTable.constTable.containsKey(name)){
            currentTable = currentTable.parentTable;
        }
        if (currentTable != null) {
            return true;
        }
        return false;
    }

    public void removeSymbol(String name) throws Exception {
        SymbolTable currentTable = this;
        while (currentTable != null && !currentTable.table.containsKey(name)) {
            currentTable = currentTable.parentTable;
        }
        if (currentTable != null) {
            currentTable.table.remove(name);
        } else {
            throw new Exception("VariableError, " + name + " is not defined.");
        }
    }

    public static HashMap<String, Type> getTable() {
        return table;
    }
}