package model;

public class Medicamento {
  private int id;
  private String nome;
  private int dias;
  private int consultaId;

  public Medicamento() { }

  public Medicamento(int id, String nome, int dias, int consultaId) {
    setId(id);
    setNome(nome);
    setDias(dias);
    setConsultaId(consultaId);
  }

  // id
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // nome
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  // dias
  public int getDias() {
    return dias;
  }

  public void setDias(int dias) {
    this.dias = dias;
  }

  // consultaId
  public int getConsultaId() {
    return consultaId;
  }

  public void setConsultaId(int consultaId) {
    this.consultaId = consultaId;
  }

  // utils
  @Override
  public String toString() {
    return "Medicamento #" + getId() +
      " - Nome: " + getNome() +
      " - Dias: " + getDias() +
      " - ConsultaId: " + getConsultaId();
  }
}
