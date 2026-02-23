package aether.lexer;

public enum TokenType {

    // Palavras-chave
    IF, ELSE, WHILE, END, PRINT,

    // Literais
    IDENT, NUMBER, STRING,

    // Operadores
    PLUS, MINUS, STAR, SLASH,
    EQ, EQEQ, NEQ,
    GT, LT, GE, LE,

    // Símbolos
    LPAREN, RPAREN,

    // Estrutura
    NEWLINE, EOF
}
