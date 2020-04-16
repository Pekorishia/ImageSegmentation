package br.ufrn.imd.projeto.dados;

/*
 * Classe que quebra o file path e pega o nome do arquivo
 */

/**
 *
 * @author Patricia & Fagner
 */
public class ImageName {
    private String filename;
    
    /**
     * Construtor da classe ImageName
     * @param filename String
     */
    public ImageName(String filename){
        this.filename = filename;
    }
    
    /**
     *Retorna o nome do arquivo selecionado
     * @return String
     */
    public String getNome(){
        String newfilename = "";
            if (filename.matches(".*jpg$") || filename.matches(".*png$")) {
                for (int i = 0; i < filename.length(); i++) {
                    if (filename.charAt(i) == '\\') {
                        newfilename = "";
                    } else {
                        newfilename = newfilename + filename.charAt(i);
                    }
                }
            }
            String nome = "";

            for (int j = 0; j < newfilename.length(); j++) {
                if (newfilename.charAt(j) == '.') {
                    break;
                } else {
                    nome = nome + newfilename.charAt(j);
                }
            }
            return nome;
    }
}
