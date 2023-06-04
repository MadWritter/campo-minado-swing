package git.jeanvictor.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	/*
	 * atributos do campo
	 */
	private final int linha;
	private final int coluna;
	
	// propriedades do campo
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	// lista que irá armazenas os campos vizinhos do campo atual
	private List<Campo> vizinhos = new ArrayList<>();
	// lista dos observers
	private List<CampoObservador> observadores = new ArrayList<>();
	
	// o construtor diz em qual linha e coluna esse campo está
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	// método que vai registrar os observers dos eventos de campo
	public void registrarObservador(CampoObservador c) {
		observadores.add(c);
	}
	// notificar os observadores dos eventos
	private void notificarObservadores(CampoEvento e) {
		observadores.stream().forEach(o -> o.eventoOcorreu(this, e));
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		// critérios para diagonal
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(this.linha - vizinho.linha);
		int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
		// se o resultado der 1, significa que está na mesma LINHA, e 1 de distância
		// se der dois, está na DIAGONAL, e a 1 de distância
		// esses são os únicos resultados possíveis para um vizinho
		int deltaGeral = deltaColuna + deltaLinha;
		
		// lógica para adicionar o campo vizinho
		
		// caso delta 1 e não estiver na diagonal, é  vizinho
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} 
		// caso delta 2 e estiver na diagonal, é vizinho
			else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	public void alternarMarcacao() {
		// só podemos alternar a marcação de um campo fechado
		if (aberto == false) {
			// se estiver marcado, ele desmarca
			// se estiver desmarcado, ele marca.
			marcado = !marcado;
			
			// notificação de eventos
			if (marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}
	
	// método para abrir um campo
	public boolean abrir() {
		// se não estiver aberto e não estiver marcado
		if (!aberto && !marcado) {
			
			// se estiver minado
			if (minado) {
				// notifique o observador
				notificarObservadores(CampoEvento.EXPLODIR);
				// e retorne true, para informar que o campo foi aberto
				return true;
			}
			// se foi somente aberto sem mina, informe no setAberto
			setAberto(true);
			// e notique os observadores
			notificarObservadores(CampoEvento.ABRIR);
			
			// se a vizinhança do campo aberto for segura
			if (vizinhancaSegura()) {
				// abra todos os vizinhos com chamada recursiva
				// até que vizinhancaSegura seja falso
				vizinhos.forEach(v -> v.abrir());
			}
			// e retorne true para informar que o campo foi aberto
			return true;
		} else {
		// caso não obedeça o primeiro if, retorne false
		return false;
		}
	}
	// método para determinar se os vizinhos do campo são seguros
	public boolean vizinhancaSegura() {
		// simplesmente nenhum campo dentro dos vizinhos pode estar minado
		return vizinhos.stream()
				.noneMatch(v -> v.minado);
	}
	
	boolean isMarcado() {
		return marcado;
	}
	
	boolean isAberto() {
		return aberto;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	boolean isFechado() {
		return !isAberto();
	}
	void setAberto(boolean valor) {
		aberto = valor;
		
		// se foi aberto, notifique os observadores
		if (aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}
	
	void minar() {
		minado = true;
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	/*
	 * O objetivo é alcançado quando o campo minado é marcado
	 * ou o campo sem mina é aberto
	 */
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	/*
	 * Vai retornar o número com a quantidade de bombas
	 * em volta daquele campo
	 */
	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}
	// reiniciando os campos para default
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
}
