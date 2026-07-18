<h1 align="center">Aether</h1>
<p align="center">
  <strong>IDE e Interpretador para uma Linguagem de Script</strong>
</p>

<p align="center">
  <img src="https://skillicons.dev/icons?i=java&theme=light" />
</p>

---

## Sobre o Projeto

**Aether** é uma IDE com interpretador embutido para uma linguagem de script simplificada, desenvolvida em **Java** como projeto da disciplina de Linguagem de Programação II (UFMA).

O sistema realiza leitura de arquivos de texto, análise léxica e sintática, e execução dos comandos definidos pela linguagem — com suporte a:

- Variáveis
- Expressões aritméticas
- Estruturas condicionais
- Laços de repetição
- Comandos de saída

## Arquitetura

O projeto foi construído com foco em **separação de responsabilidades** e **baixo acoplamento**, isolando cada etapa da execução em um módulo específico para facilitar manutenção e extensibilidade.

### Padrões de projeto aplicados

| Padrão | Aplicação |
|---|---|
| **Visitor** | Utilizado na AST para permitir execução de comandos e expressões sem acoplar a lógica de execução às classes da árvore sintática |
| **Facade** | A classe `Aether` centraliza e simplifica o fluxo de execução (Lexer → Parser → Interpreter), servindo como ponto único de acesso para a IDE |
| **MVC** | A interface gráfica em Swing representa a camada de visualização, enquanto o interpretador constitui o modelo, garantindo independência entre interface e lógica de negócio |

### Estrutura de módulos

```
AetherIDE (GUI)
      │
      ▼
   Aether (Facade)
      │
   ┌──┼──────────┐
   ▼  ▼          ▼
 Lexer Parser Interpreter
             │
             ▼
        AST (Expr / Stmt)
```

## Tecnologias

- **Java**
- **Swing** (interface gráfica)

## Conclusão

O projeto atende integralmente aos requisitos propostos, apresentando uma arquitetura organizada, orientada a objetos e baseada em padrões consagrados. A separação clara entre as camadas do sistema resulta em um interpretador funcional, extensível e de fácil compreensão.

---

<p align="center">
  Desenvolvido por <strong>Thiago Sousa</strong>
</p>
