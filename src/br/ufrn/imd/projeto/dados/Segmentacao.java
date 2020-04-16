package br.ufrn.imd.projeto.dados;


import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import br.ufrn.imd.lp2.imagesegmentation.ImageSegmentation;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * Classe que segmenta imagens e gera seu mapa de rótulos
 */
/**
 *
 * @author Patricia & Fagner
 */
public class Segmentacao {
    private ImageInformation seg;
    private BufferedImage mapaRotulo;

    /**
     * Constructor for segmentacao
     *
     * @param fileName
     * @param blurLevel
     * @param colorRadius
     * @param minSize
     */
    public Segmentacao(String fileName, double blurLevel, int colorRadius, int minSize) {
        seg = ImageSegmentation.performSegmentation(fileName, blurLevel, colorRadius, minSize);
    }

    /**
     * Retorna a imagem segmentada.
     *
     * @return BufferedImage
     */
    public BufferedImage getImagemSegmentada() {
        if (seg.getRegionMarkedImage() != null) {
            return seg.getRegionMarkedImage();
        }
        return null;
    }

    /**
     * retorna a imagem do mapa de regiões
     *
     * @return BufferedImage
     */
    public BufferedImage getMapaRegioes() {
        int[] aux = seg.getSegmentedImageMap(); ;
        int[] v = new int[aux.length];
        
        for(int r = 0; r < v.length; r++){
            v[r] = aux[r];
        }

        // red e green estão zero, somente o blue que está variando.
        //então o mapa de rótulos sairia em escala de azul
        //passar para escala de cinza
        for (int i = 0; i < v.length; i++) {
            //checagem para os pixels com valores absurdos (ex: -15658735)
            if (v[i] >= 0 && v[i] <= 255) {
                int pixels = v[i];

                //transformando para escala de cinza
                Color cor = new Color(pixels, pixels, pixels);
                //deixar mais claro
                cor = cor.brighter();
                cor = cor.brighter();
                cor = cor.brighter();

                pixels = cor.getRGB();
                v[i] = pixels;
            }
        }
        mapaRotulo = seg.getOriginalImage();
        mapaRotulo.setRGB(0, 0, seg.getWidth(), seg.getHeight(), v, 0, seg.getWidth());
        return mapaRotulo;
    }
    
    /**
     * Retorna o vetor de inteiros da imagem segmentada
     * @return int[]
     */
    public int[] getSegmentedImageMap(){
        return seg.getSegmentedImageMap();
    }
    /**
     * Retorna o vetor de inteiros do mapa de regiões
     * @return int[]
     */
    public int[] getRegionMarkedPixels(){
        return seg.getRegionMarkedPixels();
    }
    
    /**
     * Retorna um inteiro contendo a quantidade de regiões segmentadas
     *
     * @return Integer
     */
    public Integer getTotalRegioes() {
        return (Integer) seg.getTotalRegions();
    }

    /**
     * Retorna a largura da imagem
     *
     * @return int
     */
    public int getWidth() {
        return seg.getWidth();
    }

    /**
     * Retorna a altura da imagem
     *
     * @return int
     */
    public int getHeight() {
        return seg.getHeight();
    }
}
