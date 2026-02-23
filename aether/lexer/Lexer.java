/*  ===== THIAGO SOUSA ====
    ===== lP2 =====
*/

package aether.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String source;
    private int pos = 0;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = advance();

            switch (c) {
                case ' ':
                case '\t':
                    break;

                case '\n':
                    tokens.add(new Token(TokenType.NEWLINE, "\\n"));
                    break;

                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+"));
                    break;

                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-"));
                    break;

                case '*':
                    tokens.add(new Token(TokenType.STAR, "*"));
                    break;

                case '/':
                    tokens.add(new Token(TokenType.SLASH, "/"));
                    break;

                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "("));
                    break;

                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")"));
                    break;

                case '=':
                    tokens.add(match('=') ?
                            new Token(TokenType.EQEQ, "==") :
                            new Token(TokenType.EQ, "="));
                    break;

                case '!':
                    if (match('=')) {
                        tokens.add(new Token(TokenType.NEQ, "!="));
                    }
                    break;

                case '>':
                    tokens.add(match('=') ?
                            new Token(TokenType.GE, ">=") :
                            new Token(TokenType.GT, ">"));
                    break;

                case '<':
                    tokens.add(match('=') ?
                            new Token(TokenType.LE, "<=") :
                            new Token(TokenType.LT, "<"));
                    break;

                case '"':
                    tokens.add(readString());
                    break;

                default:
                    if (isDigit(c)) {
                        tokens.add(readNumber(c));
                    } else if (isAlpha(c)) {
                        tokens.add(readIdentifier(c));
                    }
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    // ============================
    // Auxiliares
    // ============================

    private char advance() {
        return source.charAt(pos++);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(pos) != expected) return false;
        pos++;
        return true;
    }

    private boolean isAtEnd() {
        return pos >= source.length();
    }

    // ============================
    // Leitores
    // ============================

    private Token readNumber(char first) {
        StringBuilder sb = new StringBuilder();
        sb.append(first);

        while (!isAtEnd() && Character.isDigit(source.charAt(pos))) {
            sb.append(advance());
        }

        return new Token(TokenType.NUMBER, sb.toString());
    }

    private Token readIdentifier(char first) {
        StringBuilder sb = new StringBuilder();
        sb.append(first);

        while (!isAtEnd() && isAlphaNumeric(source.charAt(pos))) {
            sb.append(advance());
        }

        String text = sb.toString();

        switch (text) {
            case "if": return new Token(TokenType.IF, text);
            case "else": return new Token(TokenType.ELSE, text);
            case "while": return new Token(TokenType.WHILE, text);
            case "end": return new Token(TokenType.END, text);
            case "print": return new Token(TokenType.PRINT, text);
            default: return new Token(TokenType.IDENT, text);
        }
    }

    private Token readString() {
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && source.charAt(pos) != '"') {
            sb.append(advance());
        }

        advance(); // fecha aspas

        return new Token(TokenType.STRING, sb.toString());
    }

    // ============================
    // Helpers
    // ============================

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
