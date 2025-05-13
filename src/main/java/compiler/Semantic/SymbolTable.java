package compiler.Semantic;

import compiler.Parser.Grammar.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SymbolTable {
    private SymbolTable parentTable;
    private String name;
    private ArrayList<SymbolTable> childTable = new ArrayList<>();
    private final LinkedHashMap<String, Type> table;
    private LinkedHashMap<String, Type> constTable;


    public SymbolTable(SymbolTable parentTable, String name) {
        this.parentTable = parentTable;
        this.name = name;
        table = new LinkedHashMap<>();
        this.constTable = new LinkedHashMap<>();
    }

    public void addSymbol(String name, Type type) throws Exception {
        table.put(name, type);
    }

    public void addConst(String name, Type type) throws Exception {
        constTable.put(name, type);
    }

    public SymbolTable getParentTable() {
        return parentTable;
    }
    public ArrayList<SymbolTable> getChildTable() {
        return childTable;
    }
    public String getName() {
        return name;
    }

    public Type getSymbol(String name, SymbolTable currentTable) throws Exception {
        while (currentTable != null && !currentTable.getTable().containsKey(name)) {
            currentTable = currentTable.parentTable;
        }
        if (currentTable != null) {
            return currentTable.getTable().get(name);
        } else {
            throw new Exception("VariableError, " + name + " is not defined.");
        }
    }

    public boolean containsSymbol(String name) throws Exception {
        SymbolTable currentTable = this;
        while ( currentTable != null && !currentTable.getTable().containsKey(name)){
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

    public LinkedHashMap<String, Type> getTable() {
        return table;
    }

    public void setChildTable(SymbolTable childTable) {
        this.childTable.add(childTable);
    }
}