package br.ufrn.imd.projeto.IO;


import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 *Classe que recebe uma buffered image ou um filepath e carrega a imagem em um JLabel
 */

/**
 *
 * @author Patricia & Fagner
 */
public class LoadImage {
    private ImageIcon image;
    private JLabel name;
    
    /**
     * Construtor da classe LoadImage que recebe um bufferedImagem para carregar no JLabel
     * @param buff
     * @param name
     */
    public LoadImage(BufferedImage buff, JLabel name){
        image = new ImageIcon(buff);
        this.name = name;
    }

    /**
     * Construtor da classe LoadImage que recebe um filepath de uma imagem para carregar no JLabel
     * @param filePath
     * @param name
     */
    public LoadImage(String filePath, JLabel name){
        image = new ImageIcon(filePath);
        this.name = name;
    }
    
    /**
     *Carrega a imagem no JLabel
     */
    public void load(){
        name.setIcon(image);
    }
    
}
