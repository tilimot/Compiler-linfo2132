package compiler.Parser.Grammar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Condition {
    private String operator;
    private ArrayList<Expression> leftPart;
    private ArrayList<Expression> rightPart;

    // Liste des opérateurs de comparaison pris en charge
    private static final List<String> COMPARISON_OPERATORS = List.of("<", "<=", ">", ">=", "==", "!=", "=", "!");

    public Condition(ArrayList<Expression> expression) {
        parseCondition(expression);
    }

    private void parseCondition(ArrayList<Expression> expressions) {
        leftPart = new ArrayList<>();
        rightPart = new ArrayList<>();
        boolean operatorFound = false;
        boolean maybeComplexComp = false;

        for (Expression expression : expressions) {
            String token = expression.value;
            if (COMPARISON_OPERATORS.contains(token)) {
                operator = token;
                operatorFound = true;
                maybeComplexComp = true;
            }
            else if(maybeComplexComp){
                if(COMPARISON_OPERATORS.contains(token)){
                    operator += token;
                }
                maybeComplexComp=false;
            }
            else if (!operatorFound) {
                leftPart.add(expression);
            } else {
                rightPart.add(expression);
            }
        }

    }

    public String getOperator() {
        return operator;
    }

    public ArrayList<Expression> getLeftPart() {
        return leftPart;
    }

    public ArrayList<Expression> getRightPart() {
        return rightPart;
    }

}

