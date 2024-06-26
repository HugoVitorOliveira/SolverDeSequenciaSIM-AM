package solver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    	    @Override
    	    public void run() {
    	        createAndShowGUI();
    	    }
    	});
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Ajuste de Sequencia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField pathField = new JTextField(20);
        JTextField campoField = new JTextField(5);
        JTextField sequenciaField = new JTextField(5);
        JButton browseButton = new JButton("Procurar");
        JButton solveButton = new JButton("Ajuste");

        pathField.setBackground(Color.LIGHT_GRAY);
        campoField.setBackground(Color.LIGHT_GRAY);
        sequenciaField.setBackground(Color.LIGHT_GRAY);
        browseButton.setBackground(Color.DARK_GRAY);
        browseButton.setForeground(Color.WHITE);
        solveButton.setBackground(Color.DARK_GRAY);
        solveButton.setForeground(Color.WHITE);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    pathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = pathField.getText();
                String campo = campoField.getText();
                String sequenciaCorreta = sequenciaField.getText();
                ArrayList<String> linhasCorretas = new ArrayList<String>();
                
                try (BufferedReader bf = new BufferedReader(new FileReader(path))) {

                    String line = bf.readLine();

                    while (line != null) {
                        String linhaCorreta = "";
                        String[] splitLine = line.split("\\|");
                        splitLine[Integer.valueOf(campo) - 1] = sequenciaCorreta;

                        for (int i = 0; i < splitLine.length; i++) {
                            splitLine[i] += "|";
                        }
                        for (String t : splitLine) {
                            linhaCorreta += t;
                        }
                        
                        linhasCorretas.add(linhaCorreta);

                        sequenciaCorreta = String.valueOf(Integer.valueOf(sequenciaCorreta) + 1);

                        line = bf.readLine();
                    }
                    
                    Write(linhasCorretas, path, frame);

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Arquivo não encontrado: " + ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro de E/S: " + ex.getMessage());
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(frame, "Seu arquivo possui linhas vazias, verifique!");
                }
            }
        });

        panel.add(createPanel("Caminho do Arquivo:  ", pathField, browseButton));
        panel.add(createPanel("Campo a ser alterado:  ", campoField));
        panel.add(createPanel("Sequência correta do campo:  ", sequenciaField));
        panel.add(solveButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void Write(ArrayList<String> linhasCorretas, String path, JFrame frame) {
    	StringBuilder sb = new StringBuilder(path);
    	sb.delete(path.length() - 4, path.length());
    	sb.append("Ajustado.txt");
    	path = sb.toString();
    	try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
    		for(String linha: linhasCorretas) {
    			bw.write(linha);
    			bw.newLine();
    		}
    		JOptionPane.showMessageDialog(frame, "Arquivo Ajustado criado no caminho: " + path);
    	}catch (IOException ex) {
    		JOptionPane.showMessageDialog(frame, "Erro de E/S: " + ex.getMessage());
    	}
    }

    private static JPanel createPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.DARK_GRAY);
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return panel;
    }

    private static JPanel createPanel(String labelText, JTextField textField, JButton button) {
        JPanel panel = createPanel(labelText, textField);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
}