package br.ufrn.imd.projeto.trie;

/*
 * Classe que trata dos nós da trie
 */
import java.util.*;

/**
 *
 * @author Patrícia
 */
class TrieNode 
{
    char content; 
    boolean isEnd; 
    int count;  
    LinkedList<TrieNode> childList; 
 
   /* 
     *Construtor da classe TrieNode
     * @Param char c
     */
    public TrieNode(char c)
    {
        childList = new LinkedList<TrieNode>();
        isEnd = false;
        content = c;
        count = 0;
    }  
    
    /* 
     *Inicia os nós filhos
     * @Param char c
     */
    public TrieNode subNode(char c)
    {
        if (childList != null)
            for (TrieNode eachChild : childList)
                if (eachChild.content == c)
                    return eachChild;
        return null;
    }
    
}
