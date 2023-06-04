package git.jeanvictor.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{
	/*
	 * Atributos do campo:
	 * Quantidade de linhas
	 * Quantidade de colunas
	 * Quantidade de minas
	 * 
	 * e uma lista de Campos
	 */
	private final int linhas;
	private final int colunas;
	private int minas;
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
	
	// o construtor do tabuleiro, junto com a criação da estrutura do jogo
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	// registrar os observadores dos resultados dos eventos
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	// notificaros observadores dos resultados
	private void notificarObservadores(boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	// filtra o campo com a linha e a coluna passada e abre
	public void abrir(int linha, int coluna) {
			campos.parallelStream()
				  .filter(c -> c.getLinha() == linha &&
						  c.getColuna() == coluna)
				  .findFirst() // pegue o primeiro
				  .ifPresent(c -> c.abrir()); // se estiver presente, abra
	}
	
	
	// filtra o campo com a linha e a coluna passada e marca
	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha &&
		c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	
	// gera os campos com as linhas e colunas informadas no construtor
	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}
	// esse método fará com que todos os campos tentem se fazer vizinhos
	// as regras que determinam vizinhos está no método adicionarVizinho
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	/*
	 * Método que sorteia as minas aleatoriamente
	 * baseado na quantidade que foi informada no construtor
	 */
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while(minasArmadas < minas);
	}
	
	// se todos os campos tem o objetivo alcançado, o jogador vence
	public boolean objetivoAlcancado() {
		// pra isso, todos os campos tem que passar true para o predicado
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	// reinicia o todos os campos e sorteia as minas novamente
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	@Override
	public void eventoOcorreu(Campo c, CampoEvento evento) {
		if (evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		} else if (objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	// exibe todas as minas ao perder o jogo
	private void mostrarMinas() {
		campos.stream()
			  .filter(c -> c.isMinado())
			  .filter(c -> !c.isMarcado())
			  .forEach(c -> c.setAberto(true));		
	}
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}	
	// um método forEach para os campos, usados o JButton
	public void forEach(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
}
