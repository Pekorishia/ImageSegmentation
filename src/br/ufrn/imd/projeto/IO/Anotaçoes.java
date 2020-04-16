package br.ufrn.imd.projeto.IO;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Classe para ler e escrever as anotações 
 */
/**
 *
 * @author Patricia & Fagner
 */
public class Anotaçoes {

    private ArrayList<String> linhas;
    private ArrayList<String> notas;
    private BufferedReader in;

    /**
     * Construtor da classe Anotações
     */
    public Anotaçoes() {
        notas = new ArrayList<>();
    }

    /**
     * Método que lê as anotações e as grava no array linhas
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void leitura() throws FileNotFoundException, IOException {
        linhas = new ArrayList<>();
        String line;

        in = new BufferedReader(new FileReader("imagens/anotaçoes.txt"));
        line = in.readLine();

        while (line != null) {
            linhas.add(line);
            line = in.readLine();
        }
        in.close();
    }

    /**
     * Método que escreve as anotações que foram armazenadas em notas
     *
     * @throws IOException
     */
    public void escrita() throws IOException {
        leitura();

        for (String nota : notas) {
            linhas.add(nota);
        }

        FileWriter fileWriter = new FileWriter("imagens/anotaçoes.txt");

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String linha : linhas) {
            bufferedWriter.write(linha);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        notas.clear();
    }

    /**
     * Adiciona uma anotação no array de notas e retona um inteiro referente a
     * quantidade de imagens que teriam o mesmo nome
     *
     * @param nota
     * @return int
     */
    public int add(String nota) throws IOException {
        ArrayList<String> nomes = getNomes();
        String nome = getNome(nota);
        int count = 0;

        for (String nom : nomes) {
            if (nom.contains(nome)) {
                count++;
            }
        }
        if (count == 0) {
            notas.add(nota);
        } else {
            notas.add(nota + "(" + count + ")");
        }
        return count;
    }

    /**
     * Retorna a lista com todas as anotações
     *
     * @return ArrayList
     * @throws IOException
     */
    public ArrayList<String> getAnotacao() throws IOException {
        leitura();
        return linhas;
    }

    /**
     * Retorna um array com apenas as notas feitas.
     *
     * @return ArrayList
     * @throws IOException
     */
    public ArrayList<String> getNotas() throws IOException {
        ArrayList<String> notes = getAnotacao();
        ArrayList<String> name = new ArrayList<String>();
        String aux;

        for (String note : notes) {
            aux = "";
            for (char ch : note.toCharArray()) {
                if (ch == ';') {
                    break;
                }
                aux += ch;

            }
            name.add(aux);
        }
        return name;
    }

    /**
     * Retorna um array com o nome das imagens em highlight .
     *
     * @return ArrayList
     * @throws IOException
     */
    public ArrayList<String> getNomes() throws IOException {
        ArrayList<String> notes = getAnotacao();
        ArrayList<String> nomes = new ArrayList<String>();

        for (String note : notes) {
            nomes.add(getNome(note));
        }
        return nomes;
    }

    /**
     * Recebe uma anotação e retorna apenas o nome da imagem
     *
     * @param word
     * @return String
     * @throws IOException
     */
    public String getNome(String word) throws IOException {
        String aux = "";

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ';') {
                aux = "";
            } else {
                aux += word.charAt(i);
            }
        }
        return aux;
    }
}
