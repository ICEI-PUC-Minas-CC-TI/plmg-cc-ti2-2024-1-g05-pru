package model;

public class Especialidade {
	private int id;
	private String titulo;
	private int medicoId;

	public Especialidade() { }

	public Especialidade(int id, String titulo, int medicoId) {
		setId(id);
		setTitulo(titulo);
		setMedicoId(medicoId);
	}

	// id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// titulo
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	// medicoId
	public int getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(int medicoId) {
		this.medicoId = medicoId;
	}

	// utils
	@Override
	public String toString() {
		return "Especialidade #" + getId() +
			" - Título: " + getTitulo() +
			" - Médico: " + getMedicoId();
	}
}
