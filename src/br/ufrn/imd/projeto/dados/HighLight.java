/*
 * Classe que pega uma imagem seguimentada e gera o highlight dela
 */
package br.ufrn.imd.projeto.dados;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Patricia & Fagner
 */
public class HighLight {
    private ArrayList<Integer> valores;
    private BufferedImage highLight;
    private Segmentacao seg;
    
    /**
     * Contrutor da classe HighLight
     * @param seg Segmentacao
     */
    public HighLight(Segmentacao seg){
        this.seg = seg;
        valores = new ArrayList<Integer>();
    }
    
    /**
     * retorna a imagem com a região escolhida em HighLight
     *
     * @param verifica int
     * @param posiçaoX int 
     * @param posiçaoY int
     * @return BufferedImage
     */
    public BufferedImage getHighlight(int verifica, int posiçaoX, int posiçaoY) {
        int[] d = seg.getRegionMarkedPixels();
        int[] v = seg.getSegmentedImageMap();
        int y = seg.getHeight();
        int x = seg.getWidth();
        int count = 0;
        int green;
        int blue;
        int red;

        //transformando o vetor v com o rgb do mapa de rotulos em uma matriz
        int[][] matriz_mapa;
        matriz_mapa = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (v[count] >= 0 && v[count] <= 255) {
                    matriz_mapa[i][j] = v[count];
                }
                count++;
            }
        }
        //pega o valor da matriz selecionado pelo mouse e coloca num array
        int pixel = matriz_mapa[posiçaoY][posiçaoX];

        //se foi a primeira vez que esse método foi chamado 
        if (verifica == 0) {
            valores.add(pixel);
            for (int k = 0; k < v.length; k++) {
                if (pixel != v[k]) {
                    Color cor = new Color(d[k]);
                    red = cor.getRed() / 2;
                    green = cor.getGreen() / 2;
                    blue = cor.getBlue() / 2;
                    Color novacor = new Color(red, green, blue);
                    d[k] = novacor.getRGB();
                }
            }
        } else {
            //se essa região já havia sido selecionada a escurece e exclui do array
            if (valores.contains((Integer) pixel)) {
                for (int k = 0; k < v.length; k++) {
                    if (pixel == v[k]) {
                        Color cor = new Color(d[k]);
                        red = cor.getRed() / 2;
                        green = cor.getGreen() / 2;
                        blue = cor.getBlue() / 2;
                        Color novacor = new Color(red, green, blue);
                        d[k] = novacor.getRGB();
                    }
                }
                valores.remove((Integer) pixel);
            } else {
                for (int k = 0; k < v.length; k++) {
                    if (pixel == v[k]) {
                        Color cor = new Color(d[k]);
                        red = cor.getRed() * 2;
                        green = cor.getGreen() * 2;
                        blue = cor.getBlue() * 2;
                        Color novacor = new Color(red, green, blue);
                        d[k] = novacor.getRGB();
                    }
                }
                valores.add((Integer) pixel);
            }
        }
        highLight = seg.getImagemSegmentada();
        highLight.setRGB(0, 0, seg.getWidth(), seg.getHeight(), d, 0, seg.getWidth());
        return highLight;
    }
}
