package br.ufrn.imd.projeto.IO;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * Classe que salva uma imagem no file path indicado
 */

/**
 *
 * @author Patricia & Fagner
 */
public class SaveImage {
    private BufferedImage buff;
    private File path;
    
    /**
     * Construtor da classe SaveImage
     * @param buff
     * @param path
     */
    public SaveImage(BufferedImage buff, File path){
        this.buff = buff;
        this.path = path;
    }
    
    /**
     * Método que salva a imagem 
     * @throws IOException
     */
    public void salvar() throws IOException{
        ImageIO.write(buff, "jpg", path);
                    
    }
}
