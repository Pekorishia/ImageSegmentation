package br.ufrn.imd.projeto.IO;


import java.io.File;
import javax.swing.JFileChooser;

/*
 * Classe que abre e retorna o arquivo selecionado pelo JFileChooser
 */

/**
 *
 * @author Patricia & Fagner
 */
public class FileChooser {
    private String filename  = "";

    /**
     *Construtor do FileChooser
     */
    public FileChooser(){
    JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);

        if (chooser.getSelectedFile() != null) {
            File file = chooser.getSelectedFile();
            filename = file.getAbsolutePath();
        }   
    }

    /**
     * Retorna o path do arquivo selecionado
     * @return String
     */
    public String getFilename() {
        return filename;
    }
    
}
