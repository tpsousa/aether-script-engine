package aether;

import aether.lexer.*;
import aether.parser.*;
import aether.ast.*;
import aether.interpreter.*;

import java.util.List;

public class Aether {

    public static String run(String codigo) {
        try {
            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.tokenize();

            Parser parser = new Parser(tokens);
            List<Stmt> program = parser.parse();

            Interpreter interpreter = new Interpreter();
            interpreter.interpret(program);

            return interpreter.getOutput();
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}

