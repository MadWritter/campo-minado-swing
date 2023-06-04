package git.jeanvictor.cm.modelo;

public class ResultadoEvento {
	/*
	 * Essa classe é somente para resolver
	 * o resultado se ganhou ou perdeu o jogo
	 * somente por questão de clareza de código
	 */
	private final boolean ganhou;

	public boolean isGanhou() {
		return ganhou;
	}

	public ResultadoEvento(boolean ganhou) {
		this.ganhou = ganhou;
	}
}
