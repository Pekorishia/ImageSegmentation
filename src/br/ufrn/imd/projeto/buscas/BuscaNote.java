package br.ufrn.imd.projeto.buscas;


import br.ufrn.imd.projeto.IO.Anotaçoes;
import br.ufrn.imd.projeto.trie.Trie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Classe que busca palavras e nome de imagens
 */

/**
 *
 * @author Patricia & Fagner
 */
public class BuscaNote {
    private Anotaçoes notas;
    private Trie node;
    private ArrayList<String> lista;
    
    /**
     *Construtor da classe BuscaNote
     */
    public BuscaNote() {
        notas = new Anotaçoes();
        node = new Trie();
        try {
            lista = notas.getNotas();
        } catch (IOException ex) {
            Logger.getLogger(BuscaNote.class.getName()).log(Level.SEVERE, null, ex);
        }        
        for(String list: lista){
            node.insertWord(list);
        }         
        try {
            lista = notas.getNomes();
        } catch (IOException ex) {
            Logger.getLogger(BuscaNote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retorna a lista de anotações feitas que contém o mesmo prefixo (word)
     * @param word
     * @return ArrayList
     */
    public ArrayList<String> getPalavras(String word){
        node.getWordFromPrefix(word);
        return node.getList();
    }
        
    /**
     * Retorna a lista de imagens  que contém o mesmo prefixo (word)
     * @param word
     * @return ArrayList
     */
    public ArrayList<String> getListaImagens(String word){
        ArrayList<String> auto;
        ArrayList<String> imagens = new ArrayList<String>();
        
        auto =  getPalavras(word);
                if (auto != null) {
                    for (String aut : auto) {
                        imagens.addAll(getImagens(aut));
                    }
                }
        return imagens;
    }
    
    /**
     * Retorna a lista de imagens  que contém o mesmo prefixo (word)
     * @param word
     * @return ArrayList
     */
    private ArrayList<String> getImagens(String word){
        node.getWordFromPrefix(word);
        
        ArrayList<String> nomes = new ArrayList<String>();
        for(String list: lista){
            if(list.contains(word)){
                nomes.add(list);
            }
        }        
        return nomes;
    }
    
}
