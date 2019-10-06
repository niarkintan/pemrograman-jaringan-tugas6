package tugas;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PushbackReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Asus
 */
public class ReadStreamPushBackReader {
    private final tugas6 view;

    public ReadStreamPushBackReader(tugas6 view) {
        this.view = view;

        this.view.getBtBaca().addActionListener((ActionEvent e) -> {
            try {
                proses();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReadStreamPushBackReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException | IOException ex) {
                Logger.getLogger(ReadStreamPushBackReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.view.getBtSimpan().addActionListener((ActionEvent e) -> {
            simpan();
        });
    }

    private void proses() throws FileNotFoundException, BadLocationException, IOException {
        JFileChooser loadFile = view.getLoadFile();
        StyledDocument doc = view.getTxtPane().getStyledDocument();
        if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
            PushbackReader  reader = new PushbackReader(new FileReader(loadFile.getSelectedFile()));
            LineNumberReader re = new LineNumberReader(new FileReader(loadFile.getSelectedFile()));
                
            char[] words = new char[(int) loadFile.getSelectedFile().length()];
            
                try {
                    reader.read(words);
                    
                    String data = null;
                    String data1 = null;
                    doc.insertString(0, "", null);
                    
                    int kar = 0;
                    int word = 0;
           
                    if((data = new String(words)) != null) {
                        String[] wordlist = data.split("\\s+");
                      
                        kar += words.length;
                        word += wordlist.length;
                       
                        doc.insertString(doc.getLength(), "" + data + "\n", null);
                     
                    }
                    while((data1 = re.readLine()) != null){
                       
                    }
                     int lines = re.getLineNumber();
                    
                JOptionPane.showMessageDialog(null, "File Berhasil dibaca." + "\n"
                        + "Jumlah baris = " + lines + "\n"
                        + "Jumlah kata = " + word + "\n"
                        + "Jumlah karakter = " + kar);
                    
                } catch (IOException ex) {
                    Logger.getLogger(ReadStreamPushBackReader.class.getName()).log(Level.SEVERE, null, ex);
                }
         
        }
    }

    private void simpan() {
        JFileChooser loadFile = view.getLoadFile();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
            BufferedWriter writer = null;
            try {
                String contents = view.getTxtPane().getText();
                if (contents != null && !contents.isEmpty()) {
                    writer = new BufferedWriter(new FileWriter(loadFile.getSelectedFile()));
                    writer.write(contents);
                }
                JOptionPane.showMessageDialog(null, "File Berhasil disimpan.");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReadStreamPushBackReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReadStreamPushBackReader.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        view.getTxtPane().setText("");
                    } catch (IOException ex) {
                        Logger.getLogger(ReadStreamPushBackReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
