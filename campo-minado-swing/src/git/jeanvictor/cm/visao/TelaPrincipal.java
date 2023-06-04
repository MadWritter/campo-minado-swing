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
	
	public static void main(String[] args) {
		String resposta = JOptionPane.
				showInputDialog("Quantas Bombas deseja?\n30(easy) 50(mid) 70(hard)");
		int dificuldade = 0;
		try {
			dificuldade = Integer.parseInt(resposta);
			new TelaPrincipal(dificuldade);
		} catch (NumberFormatException e) {
			JOptionPane.showConfirmDialog(null, "Valor inserido incorreto\nEncerrando...", null, JOptionPane.DEFAULT_OPTION);
		}
	}
}
