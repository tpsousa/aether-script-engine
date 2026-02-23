package aether.parser;
import aether.lexer.*;
import aether.ast.*;

import java.util.List;
import java.util.ArrayList;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // === ENTRADA PRINCIPAL ===
  public List<Stmt> parse() {
    List<Stmt> statements = new ArrayList<>();

    while (!isAtEnd()) {
        if (match(TokenType.NEWLINE)) continue;
        statements.add(statement());
    }

    return statements;
}
    //comandos : 

    //if
    private Stmt ifStatement() {
    Expr condition = expression();
    consume(TokenType.NEWLINE, "Esperado nova linha após condição");

    List<Stmt> thenBranch = new ArrayList<>();

    while (!check(TokenType.ELSE) && !check(TokenType.END)) {
        thenBranch.add(statement());
    }

    List<Stmt> elseBranch = null;

    if (match(TokenType.ELSE)) {
        consume(TokenType.NEWLINE, "Esperado nova linha após else");
        elseBranch = new ArrayList<>();

        while (!check(TokenType.END)) {
            elseBranch.add(statement());
        }
    }

    consume(TokenType.END, "Esperado 'end'");
    consume(TokenType.NEWLINE, "Esperado nova linha após end");

    return new Stmt.If(
        condition,
        new Stmt.Block(thenBranch),
        elseBranch == null ? null : new Stmt.Block(elseBranch)
    );
}

    //while:

    private Stmt whileStatement() {
    Expr condition = expression();
    consume(TokenType.NEWLINE, "Esperado nova linha após condição");

    List<Stmt> body = new ArrayList<>();

    while (!check(TokenType.END)) {
        body.add(statement());
    }

    consume(TokenType.END, "Esperado 'end'");
    consume(TokenType.NEWLINE, "Esperado nova linha após end");

    return new Stmt.While(condition, new Stmt.Block(body));
}


    // =========================
    //   STATEMENTS
    // =========================
    private Stmt statement() {
     if (match(TokenType.NEWLINE)) return statement(); // pula linhas vazias
     if (match(TokenType.PRINT)) return printStatement();
     if (match(TokenType.IF)) return ifStatement();
     if (match(TokenType.WHILE)) return whileStatement();
     if (check(TokenType.IDENT)) return assignStatement();
     throw new RuntimeException("Comando inválido em: " + peek());
}

    private Stmt printStatement() {
        consume(TokenType.LPAREN, "Esperado '('");
        Expr value = expression();
        consume(TokenType.RPAREN, "Esperado ')'");
        consume(TokenType.NEWLINE, "Esperado nova linha");
        return new Stmt.Print(value);
    }

    private Stmt assignStatement() {
        Token name = consume(TokenType.IDENT, "Esperado variável");
        consume(TokenType.EQ, "=");
        Expr value = expression();
        consume(TokenType.NEWLINE, "Esperado nova linha");
        return new Stmt.Assign(name.getLexeme(), value);
    }

    // =========================
    //   EXPRESSIONS
    // =========================

    private Expr expression() {
        return equality();
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(TokenType.EQEQ, TokenType.NEQ)) {
            String op = previous().getLexeme();
            Expr right = comparison();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = term();

        while (match(TokenType.GT, TokenType.LT, TokenType.GE, TokenType.LE)) {
            String op = previous().getLexeme();
            Expr right = term();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    private Expr term() {
        Expr expr = factor();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            String op = previous().getLexeme();
            Expr right = factor();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = unary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            String op = previous().getLexeme();
            Expr right = unary();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match(TokenType.MINUS)) {
            return new Expr.Unary("-", unary());
        }
        return primary();
    }

    private Expr primary() {
        if (match(TokenType.NUMBER)) {
            return new Expr.Literal(Integer.parseInt(previous().getLexeme()));
        }
        if (match(TokenType.STRING)) {
            return new Expr.Literal(previous().getLexeme());
        }
        if (match(TokenType.IDENT)) {
            return new Expr.Variable(previous().getLexeme());
        }
        if (match(TokenType.LPAREN)) {
            Expr expr = expression();
            consume(TokenType.RPAREN, "Esperado ')'");
            return expr;
        }

        throw new RuntimeException("Expressão inválida");
    }

    // =========================
    //   FERRAMENTAS DO PARSER
    // =========================

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) pos++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token previous() {
        return tokens.get(pos - 1);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message);
    }
}
