/* ===== THIAGO SOUSA ====
   ===== lP2 ======
*/

package aether;
import aether.lexer.*;
import java.util.List;

import aether.parser.*;
import aether.ast.*;
import aether.interpreter.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println(java.nio.file.Path.of(".").toAbsolutePath());

        String codigo = Files.readString(Path.of("programa.aether"));

        Lexer lexer = new Lexer(codigo);
        List<Token> tokens = lexer.tokenize();


        for (Token t : tokens) {
        System.out.println(t);
         }
         
        System.out.println("---- PARSER ----");
        Parser parser = new Parser(tokens);


        List<Stmt> ast = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(ast);

    }
}
