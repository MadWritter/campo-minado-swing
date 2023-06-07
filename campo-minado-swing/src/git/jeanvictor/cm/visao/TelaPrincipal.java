package git.jeanvictor.cm.visao;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import git.jeanvictor.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	/*
	 * Configurações visuais do tabuleiro
	 */
	public TelaPrincipal(int dificuldade) {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, dificuldade);
		add(new PainelTabuleiro(tabuleiro));
		setTitle("Campo Minado");
		setSize(670, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
