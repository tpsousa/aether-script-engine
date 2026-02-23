package aether.interpreter;

import aether.ast.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {

    private final StringBuilder output = new StringBuilder();

    private final Map<String, Object> environment = new HashMap<>();

    public void interpret(List<Stmt> statements) {
        for (Stmt stmt : statements) {
            stmt.accept(this);
        }
    }

    @Override
    public Object visitLiteral(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitVariable(Expr.Variable expr) {
        if (!environment.containsKey(expr.name)) {
            throw new RuntimeException("Variável não definida: " + expr.name);
        }
        return environment.get(expr.name);
    }
@Override
public Object visitBinary(Expr.Binary expr) {
    Object left = expr.left.accept(this);
    Object right = expr.right.accept(this);

    switch (expr.operator) {
        case "+":
            return (int) left + (int) right;
        case "-":
            return (int) left - (int) right;
        case "*":
            return (int) left * (int) right;
        case "/":
            return (int) left / (int) right;

        case "<":
            return (int) left < (int) right;
        case ">":
            return (int) left > (int) right;
        case "<=":
            return (int) left <= (int) right;
        case ">=":
            return (int) left >= (int) right;
        case "==":
            return left.equals(right);
        case "!=":
            return !left.equals(right);
    }

    throw new RuntimeException("Operador desconhecido: " + expr.operator);
}

    @Override
    public Object visitUnary(Expr.Unary expr) {
        Object value = expr.expr.accept(this);
        switch (expr.operator) {
            case "-": return -(int)value;
        }
        return null;
    }
    @Override
    public Void visitPrint(Stmt.Print stmt) {
    Object value = stmt.expression.accept(this);
    output.append(value).append("\n");
    return null;
}


    @Override
    public Void visitAssign(Stmt.Assign stmt) {
        Object value = stmt.value.accept(this);
        environment.put(stmt.name, value);
        return null;
    }

    @Override
    public Void visitIf(Stmt.If stmt) {
        Boolean condition = (Boolean) stmt.condition.accept(this);
        if (condition) {
            stmt.thenBranch.accept(this);
        } else if (stmt.elseBranch != null) {
            stmt.elseBranch.accept(this);
        }
        return null;
    }

    @Override
    public Void visitWhile(Stmt.While stmt) {
        while ((Boolean) stmt.condition.accept(this)) {
            stmt.body.accept(this);
        }
        return null;
    }

    @Override
    public Void visitBlock(Stmt.Block stmt) {
        for (Stmt s : stmt.statements) {
            s.accept(this);
        }
        return null;
    }

    public String getOutput() {
    return output.toString();
}

}
