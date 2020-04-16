/*
 * Classe gerenciadora da interface ProjetoGUI
 */
package br.ufrn.imd.projeto.GUI;

import br.ufrn.imd.projeto.buscas.ContemPalavra;
import br.ufrn.imd.projeto.dados.Segmentacao;
import br.ufrn.imd.projeto.buscas.BuscaNote;
import br.ufrn.imd.projeto.dados.ImageName;
import br.ufrn.imd.projeto.IO.FileChooser;
import br.ufrn.imd.projeto.IO.Anotaçoes;
import br.ufrn.imd.projeto.IO.LoadImage;
import br.ufrn.imd.projeto.IO.SaveImage;
import br.ufrn.imd.projeto.dados.HighLight;

import java.awt.image.BufferedImage;
import javax.swing.DefaultListModel;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import java.io.File;

/**
 *
 * @author Patricia & Fagner
 */
public class ProjetoGUIHandler {

    private ArrayList<String> notas = new ArrayList<String>();
    private BufferedImage highLight = null;
    private boolean comeFromBusca = false;
    private Segmentacao segmentation2;
    private Segmentacao segmentation;
    private HighLight imagemHighSeg1;
    private HighLight imagemHighSeg2;
    private String filename = "";
    private String word = "";
    private String name = "";
    private int verifica = 0;
    private int posiçaoX;
    private int posiçaoY;

    /**
     * Construtor padrão da classe ProjetoGUIHandler
     */
    public ProjetoGUIHandler() {
    }

    /**
     * Adiciona uma anotação na tabela de notas
     *
     * @param tabela JTextPane
     * @param anotacao JTextField
     */
    public void addAnotacao(JTextPane tabela, JTextField anotacao) {
        if (!anotacao.getText().equals("") && anotacao.getText() != null && !anotacao.getText().contains(";")) {
            ContemPalavra contem = new ContemPalavra(tabela.getText(), anotacao.getText());
            if (!contem.check()) {
                tabela.setText(tabela.getText() + anotacao.getText().toLowerCase() + "\n");
                notas.add(anotacao.getText().toLowerCase());
                anotacao.setText("");
            }
        }
    }

    /**
     * Limpa a tabela de notas
     *
     * @param tabela JTextPane
     */
    public void clear(JTextPane tabela) {
        tabela.setText("");
        notas.clear();
    }

    /**
     * Faz a segmentação da imagem e a coloca nos labels da segmentação e
     * anotação
     *
     * @param blurLevelSpinner JSpinner
     * @param colorRadiusSpinner JSpinner
     * @param minSizeSpinner JSpinner
     * @param quadroSegmentar JLabel
     * @param quadroAnotar JLabel
     * @param regioes JLabel
     * @return boolean
     */
    public boolean segmentar(JSpinner blurLevelSpinner, JSpinner colorRadiusSpinner, JSpinner minSizeSpinner, JLabel quadroSegmentar, JLabel quadroAnotar, JLabel regioes) {
        boolean test = false;
        if ((filename.matches(".*jpg$") || filename.matches(".*png$")) && (!filename.equals(""))) {
            segmentation = new Segmentacao(filename, (double) blurLevelSpinner.getValue(), (int) colorRadiusSpinner.getValue(), (int) minSizeSpinner.getValue());
            imagemHighSeg1 = new HighLight(segmentation);
            if (segmentation != null) {
                LoadImage load = new LoadImage(segmentation.getImagemSegmentada(), quadroSegmentar);
                LoadImage load2 = new LoadImage(segmentation.getImagemSegmentada(), quadroAnotar);
                load.load();
                load2.load();

                regioes.setText(segmentation.getTotalRegioes().toString());
                verifica = 0;

                test = true;
            }
            comeFromBusca = false;
        }
        return test;
    }

    /**
     * Coloca a imagem do mapa de rótulos em um label
     *
     * @param quadroSegmentar
     */
    public void mapaRotulos(JLabel quadroSegmentar) {
        if (segmentation != null) {
            LoadImage load = new LoadImage(segmentation.getMapaRegioes(), quadroSegmentar);
            load.load();
        }
    }

    /**
     * Procura uma imagem no disco e a coloca num label
     *
     * @param quadroSegmentar JLabel
     * @param regioes JLabel
     * @return boolean
     */
    public boolean addImagem(JLabel quadroSegmentar, JLabel regioes) {
        boolean test = false;
        FileChooser chooser = new FileChooser();
        if (!chooser.getFilename().equals("")) {
            filename = chooser.getFilename();
            if (filename.matches(".*jpg$") || filename.matches(".*png$")) {
                LoadImage load = new LoadImage(filename, quadroSegmentar);
                load.load();

                regioes.setText("0");
                segmentation = null;
                test = true;
            }
        }
        return test;
    }

    /**
     * Pega as notas feitas e gera uma imagem para cada anotação, além de
     * adiciona-las num .txt
     *
     * @param tabela JTextPane
     */
    public void anotar(JTextPane tabela) {
        if (highLight != null && !notas.isEmpty()) {
            Anotaçoes note = new Anotaçoes();
            String nome = "";

            if (segmentation != null && !comeFromBusca) {
                ImageName name = new ImageName(filename);
                nome = name.getNome();
            } else if (segmentation2 != null && comeFromBusca) {
                nome = name;
            }
            if (!nome.equals("")) {
                for (String nota : notas) {
                    try {
                        int count = note.add(nota + ";" + nome + "_" + nota);
                        note.escrita();

                        if (count == 0) {
                            SaveImage salvaimagem = new SaveImage(highLight, new File("imagens/" + nome + "_" + nota + ".jpg"));
                            salvaimagem.salvar();
                        } else {
                            SaveImage salvaimagem = new SaveImage(highLight, new File("imagens/" + nome + "_" + nota + "(" + count + "" + ".jpg"));
                            salvaimagem.salvar();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ProjetoGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        notas.clear();
        tabela.setText("");
    }

    /**
     * Gera o highlight da imagem clicada
     *
     * @param posX int
     * @param posY int
     * @param quadroAnotar JLabel
     * @return boolean
     */
    public boolean putSegTOHighLight(int posX, int posY, JLabel quadroAnotar) {
        boolean test = false;
        posiçaoX = posX;
        posiçaoY = posY;
        if (segmentation != null && !comeFromBusca) {
            if (posiçaoX <= segmentation.getWidth() && posiçaoY <= segmentation.getHeight()) {
                highLight = imagemHighSeg1.getHighlight(verifica, posiçaoX, posiçaoY);
                LoadImage load = new LoadImage(highLight, quadroAnotar);
                load.load();
                test = true;
                verifica++;
            }
        } else if (comeFromBusca) {
            if (posiçaoX <= segmentation2.getWidth() && posiçaoY <= segmentation2.getHeight()) {
                highLight = imagemHighSeg2.getHighlight(verifica, posiçaoX, posiçaoY);
                LoadImage load = new LoadImage(highLight, quadroAnotar);
                load.load();
                test = true;
                verifica++;
            }
        }
        return test;
    }

    /**
     * Pega as letras digitadas e busca a string formada na trie. Se houvererem
     * anotações com o mesmo prefixo coloca a lista de imagens com a mesma
     * anotação num JList
     *
     * @param key int
     * @param listaNomes JList
     */
    public void buscaKeyPressed(int key, JList listaNomes) {
        DefaultListModel dlm = new DefaultListModel();
        ArrayList<String> auto = new ArrayList<String>();
        BuscaNote busca = new BuscaNote();

        if (key <= 90 && key >= 65) {
            word += (char) (key + 32);
            auto = busca.getListaImagens(word);
        } else if (key == 8) {
            if (word.length() > 1) {
                String aux = "";
                for (int i = 0; i < word.length() - 1; i++) {
                    aux += word.charAt(i);
                }
                word = aux;
                auto = busca.getListaImagens(word);
            } else {
                word = "";
            }
        } else if (key != 59) {
            word += (char) (key);
            auto = busca.getListaImagens(word);
        }
        if (auto != null) {
            for (String nom : auto) {
                dlm.addElement(nom);
            }
            listaNomes.setModel(dlm);
        }

    }

    /**
     * Pega o item selecionado e coloca a imagem com o mesmo nome do item na aba
     * seguinte
     *
     * @param listaNomes JList
     * @param quadroBusca JLabel
     * @param jTabbedPane1 JTabbedPane
     * @return boolean
     */
    public boolean itemListSelected(JList listaNomes, JLabel quadroBusca, JTabbedPane jTabbedPane1) {
        boolean test = false;
        name = (String) listaNomes.getSelectedValue();
        if (!name.equals("") && name != null) {
            LoadImage load = new LoadImage("imagens/" + name + ".jpg", quadroBusca);
            load.load();
            jTabbedPane1.setSelectedIndex(1);
            test = true;
        }
        return test;
    }

    /**
     * Segmenta e carrega a imagem selecionada para o label de anotações 
     * @param blurLevelSpinner JSpinner
     * @param colorRadiusSpinner JSpinner
     * @param minSizeSpinner JSpinner
     * @param quadroAnotar JLabel
     * @return boolean
     */
    public boolean imageSelClicked(JSpinner blurLevelSpinner, JSpinner colorRadiusSpinner, JSpinner minSizeSpinner, JLabel quadroAnotar) {
        boolean test = false;
        if (!name.equals("")) {
            segmentation2 = new Segmentacao("imagens/" + name + ".jpg", (double) blurLevelSpinner.getValue(), (int) colorRadiusSpinner.getValue(), (int) minSizeSpinner.getValue());
            imagemHighSeg2 = new HighLight(segmentation2);
            highLight = segmentation2.getImagemSegmentada();
            LoadImage load2 = new LoadImage(highLight, quadroAnotar);
            load2.load();
            verifica = 0;
            comeFromBusca = true;
            test = true;
        }
        return test;
    }

    /**
     * Coloca a imagem original da anotação no label de busca
     * @param quadroBusca JLabel
     * @return boolean
     */
    public boolean imagemOriginal(JLabel quadroBusca) {
        boolean test = false;
        String nomeOriginal = "";
        for (char ch : name.toCharArray()) {
            if (ch == '_') {
                break;
            }
            nomeOriginal += ch;
        }
        if (!nomeOriginal.equals("")) {
            LoadImage load = new LoadImage("imagens/" + nomeOriginal + ".jpg", quadroBusca);
            load.load();
            test = true;
        }
        return test;
    }

    /**
     * Coloca a imagem anotada no label de busca
     * @param quadroBusca JLabel
     * @return boolean
     */
    public boolean imagemSelecionada(JLabel quadroBusca) {
        boolean test = false;
        if (!name.equals("") && name != null) {
            LoadImage load = new LoadImage("imagens/" + name + ".jpg", quadroBusca);
            load.load();
            test = true;
        }
        return test;
    }
}
