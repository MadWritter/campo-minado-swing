package git.jeanvictor.cm.modelo;

@FunctionalInterface
public interface CampoObservador {
	// interface funcional observadora dos eventos de campo
	public void eventoOcorreu(Campo c, CampoEvento evento);
}
