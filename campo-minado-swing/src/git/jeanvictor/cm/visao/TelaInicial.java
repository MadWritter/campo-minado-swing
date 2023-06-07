package git.jeanvictor.cm.visao;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class TelaInicial extends JFrame implements ActionListener{
	
	private int dificuldade;
	JRadioButton trinta;
	JRadioButton cinquenta;
	JRadioButton setenta;
	JLabel label = new JLabel();
	
	public TelaInicial() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		this.setTitle("Bombas");
		
		label.setText("Quantas bombas deseja?");
		trinta = new JRadioButton("30");
		cinquenta = new JRadioButton("50");
		setenta = new JRadioButton("70");
		
		ButtonGroup botoes = new ButtonGroup();
		botoes.add(trinta);
		botoes.add(cinquenta);
		botoes.add(setenta);
		
		trinta.addActionListener(this);
		cinquenta.addActionListener(this);
		setenta.addActionListener(this);
		
		add(label);
		add(trinta);
		add(cinquenta);
		add(setenta);
		this.setSize(220, 90);
		this.setVisible(true);		
	}
	
	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == trinta) {
			this.setVisible(false);
			setDificuldade(30);
			new TelaPrincipal(getDificuldade()).setVisible(true);;
		} else if (evento.getSource() == cinquenta) {
			this.setVisible(false);
			setDificuldade(50);
			new TelaPrincipal(getDificuldade()).setVisible(true);;
		} else if (evento.getSource() == setenta) {
			this.setVisible(false);
			setDificuldade(70);
			new TelaPrincipal(getDificuldade()).setVisible(true);;
		}
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}
	
	public static void main(String[] args) {
		new TelaInicial();
	}
}
