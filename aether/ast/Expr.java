package aether.ast;
public abstract class Expr {

    public interface Visitor<R> {
        R visitLiteral(Literal expr);
        R visitVariable(Variable expr);
        R visitBinary(Binary expr);
        R visitUnary(Unary expr);
    }

    public abstract <R> R accept(Visitor<R> visitor);

    // =========================

    public static class Literal extends Expr {
        public final Object value;

        public Literal(Object value) {
            this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteral(this);
        }
    }

    public static class Variable extends Expr {
        public final String name;

        public Variable(String name) {
            this.name = name;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariable(this);
        }
    }

    public static class Binary extends Expr {
        public final Expr left;
        public final String operator;
        public final Expr right;

        public Binary(Expr left, String operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinary(this);
        }
    }

    public static class Unary extends Expr {
        public final String operator;
        public final Expr expr;

        public Unary(String operator, Expr expr) {
            this.operator = operator;
            this.expr = expr;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnary(this);
        }
    }
}