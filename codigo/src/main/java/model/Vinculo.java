package model;

public class Vinculo {
  private int id;
  private int pacienteId;
  private int medicoId;
  private String status;

  public Vinculo() {
  }

  public Vinculo(int id, int idPaciente, int idMedico, String status) {
    setId(id);
    setPacienteId(idPaciente);
    setMedicoId(idMedico);
    setStatus(status);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getPacienteId() {
    return pacienteId;
  }

  public void setPacienteId(int pacienteId) {
    this.pacienteId = pacienteId;
  }

  public int getMedicoId() {
    return medicoId;
  }

  public void setMedicoId(int medicoId) {
    this.medicoId = medicoId;
  }

  @Override
  public String toString() {
    return "Vinculo{" +
        "id=" + id +
        ", Paciente='" + pacienteId + '\'' +
        ", Medico='" + medicoId + '\'' +
        ", status=" + status +
        '}';
  }
}
