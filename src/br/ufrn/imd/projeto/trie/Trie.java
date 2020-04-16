package br.ufrn.imd.projeto.trie;


import java.util.ArrayList;

/*
 * Classe que contém as operações das Tries
 */
/**
 *
 * @author Patrícia & Fagner
 */
public class Trie {

    private TrieNode root;
    private ArrayList<String> list;
    private String test = "";

    /* 
     *Construtor da Trie
     */
    public Trie() {
        root = new TrieNode(' ');
    }

    /* 
     *Insere uma palavra na Trie
     * @Param String word
     */
    public void insertWord(String word) {
        if (searchWord(word) == true) {
            return;
        }
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode child = current.subNode(ch);
            if (child != null) {
                current = child;
            } else {
                current.childList.add(new TrieNode(ch));
                current = current.subNode(ch);
            }
            current.count++;
        }
        current.isEnd = true;
    }

    /* 
     *Procura uma palavra na Trie
     * @Param String word
     */
    public boolean searchWord(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.subNode(ch) == null) {
                return false;
            } else {
                current = current.subNode(ch);
            }
        }
        if (current.isEnd == true) {
            return true;
        }
        return false;
    }

    /* 
     *Remove uma palavra na Trie
     * @Param String word
     */
    public void removeWord(String word) {
        if (searchWord(word) == false) {
            System.out.println(word + " does not exist in trie\n");
            return;
        }
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode child = current.subNode(ch);
            if (child.count == 1) {
                current.childList.remove(child);
                return;
            } else {
                child.count--;
                current = child;
            }
        }
        current.isEnd = false;
    }

    /* 
     *Retorna o número de palavras que apresentam o mesmo prefixo 
     * @Param String preffix
     * @Return int
     */
    public int getWordCountFromPrefix(String prefix) {
        TrieNode current = root;
        int count = 0;

        for (char ch : prefix.toCharArray()) {
            if (current.subNode(ch) == null) {
                System.out.println(prefix + " does not exist in trie.");
                return -1;
            } else {
                current = current.subNode(ch);
            }
        }
        return current.count;
    }

    /* 
     *Retorna a lista de palavras que apresentam o mesmo prefixo 
     * @Param String preffix
     * @Return List
     */
    public void getWordFromPrefix(String prefix) {
        TrieNode current = this.root;
        list = new ArrayList<String>();
        
        for (char ch : prefix.toCharArray()) {
            if (current.subNode(ch) == null) {
                return;
            } else {
                current = current.subNode(ch);
            }
        }

        prefix = prefix.substring(0, prefix.length() - 1);
        getWord(current, prefix);
    }
    
    /* 
     *método auxiliar ao de busca de palavras que apresentam o mesmo prefixo 
     # @Param TrieNode node
     * @Param String path
     * @Return List
     */
    private void getWord(TrieNode node, String path) {
        if (this.root.childList.isEmpty()) {
            System.out.println("Árvore vazia!");
            return;
        }

        if (node.content != ' ') {
            path += node.content;
        }
        if (node.isEnd) {
            list.add(path);
        }
        for (TrieNode childList : node.childList) {
            getWord(childList, path);
            path = path.substring(0, path.length());
        }
    }
        
    /* 
     * método coloca todas as palavras da trie na lista
     */
    public void PrintTrie() {
        TrieNode current = this.root;
        String path = "";        
        list = new ArrayList<String>();
        
        PrintTrie(current, path);
        
    }
    
    /* 
     *método auxiliar ao de salvar as palavras da trie na lista
     # @Param TrieNode node
     * @Param String path
     * @Return List
     */
    private void PrintTrie(TrieNode node, String path) {
        
        if (node.content != ' ') {
            path += node.content;
            System.out.println(node.content);
        }
        if (node.isEnd) {
            list.add(path);
        }
        for (TrieNode childList : node.childList) {
            getWord(childList, path);
            path = path.substring(0, path.length());
        }
    }
    
    public TrieNode getRoot() {
        return this.root;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }   
}