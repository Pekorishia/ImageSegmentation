package br.ufrn.imd.projeto.buscas;


import java.util.ArrayList;

/*
 *Classe que checa se uma palavra está contida em um arraylist ou em uma lista de Strings separadas por '\n'
 */
/**
 *
 * @author Patricia & Fagner
 */
public class ContemPalavra {

    private ArrayList<String> tabela;
    private String anotacao;

    /**
     * Contrutor de ContemPalavra que recebe uma String com as palavras separadas por '\n' como parametro e a anotação
     * @param tabelas
     * @param anotacao
     */
    public ContemPalavra(String tabelas, String anotacao) {
        this.anotacao = anotacao;
        tabela = new ArrayList<String>();

        String word = "";
        for (char ch : tabelas.toCharArray()) {
            if (ch != '\n') {
                word += ch;
            } else {
                tabela.add(word);
                word = "";
            }
        }
    }

    /**
     *  Contrutor de ContemPalavra que recebe um ArryList de Strings como
     * parametro e a anotação
     * @param ArrayList tabelas 
     * @param String anotacao
     */
    public ContemPalavra(ArrayList<String> tabelas, String anotacao) {
        this.anotacao = anotacao;
        tabela = tabelas;
    }
    
    /**
     * verifica se a palavra estava contida na lista
     * @return boolean
     */
    public boolean check() {
        boolean contido = false;

        for (String tab : tabela) {
            if (tab.equals(anotacao)) {
                contido = true;
            }
        }
        return contido;
    }
}
