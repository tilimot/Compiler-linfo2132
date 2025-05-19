package compiler.CodeGeneration;

import java.util.HashMap;

public class FieldTable {
    private HashMap<String, String> table;

    public FieldTable() {
        this.table = new HashMap<>();
    }

    public void addIdentifier(String identifier, String tokenDescriptor) throws Exception {
        table.put(identifier, tokenDescriptor);
    }

    public String getTypeDescriptor(String identifier) throws Exception {
            return table.get(identifier);
    }

}

