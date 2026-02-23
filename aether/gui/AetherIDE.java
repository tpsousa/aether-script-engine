/* ===== THIAGO SOUSA ====== 
   ===== LP2 ======
*/

package aether.gui;
import aether.Aether;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class AetherIDE extends JFrame {

    private JTextArea editor;
    private JTextArea console;

    public AetherIDE() {
        setTitle("Aether IDE");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout());

        // Editor de código
        editor = new JTextArea();
        JScrollPane editorScroll = new JScrollPane(editor);

        // Console de saída
        console = new JTextArea();
        console.setEditable(false);
        console.setBackground(Color.BLACK);
        console.setForeground(Color.GREEN);
        JScrollPane consoleScroll = new JScrollPane(console);

        // Painel de botões
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnOpen = new JButton("Abrir");
        JButton btnSave = new JButton("Salvar");
        JButton btnRun  = new JButton("Run");

        topPanel.add(btnOpen);
        topPanel.add(btnSave);
        topPanel.add(btnRun);

        // Split vertical (editor em cima, console embaixo)
        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                editorScroll,
                consoleScroll
        );
        split.setDividerLocation(400);

        add(topPanel, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        // Eventos
        btnOpen.addActionListener(e -> abrirArquivo());
        btnSave.addActionListener(e -> salvarArquivo());
        btnRun.addActionListener(e -> executar());

        setVisible(true);
    }

    private void abrirArquivo() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                String content = Files.readString(file.toPath());
                editor.setText(content);
                console.setText("Arquivo carregado: " + file.getName());
            } catch (IOException ex) {
                console.setText("Erro ao abrir arquivo: " + ex.getMessage());
            }
        }
    }

    private void salvarArquivo() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                Files.writeString(file.toPath(), editor.getText());
                console.setText("Arquivo salvo: " + file.getName());
            } catch (IOException ex) {
                console.setText("Erro ao salvar arquivo: " + ex.getMessage());
            }
        }
    }
private void executar() {
    String codigo = editor.getText();
    String saida = aether.Aether.run(codigo);
    console.setText(saida);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AetherIDE::new);
    }
}
