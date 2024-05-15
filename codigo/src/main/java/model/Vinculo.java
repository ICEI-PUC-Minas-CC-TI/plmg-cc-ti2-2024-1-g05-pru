package model;

public class Vinculo {
  private int id;
  private String status;
  private String paciente;
  private int pacienteId;
  private String medico;
  private int medicoId;

  public Vinculo() { }

  public Vinculo(int id, String status, int pacienteId, int medicoId) {
    setId(id);
    setStatus(status);
    setPacienteId(pacienteId);
    setMedicoId(medicoId);
  }

  public Vinculo(int id, String status, String paciente, int pacienteId, String medico, int medicoId) {
    setId(id);
    setStatus(status);
    setPaciente(paciente);
    setPacienteId(pacienteId);
    setMedico(medico);
    setMedicoId(medicoId);
  }

  // id
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // status
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  // paciente
  public String getPaciente() {
    return paciente;
  }

  public void setPaciente(String paciente) {
    this.paciente = paciente;
  }

  // pacienteId
  public int getPacienteId() {
    return pacienteId;
  }

  public void setPacienteId(int pacienteId) {
    this.pacienteId = pacienteId;
  }

  // medico
  public String getMedico() {
    return medico;
  }

  public void setMedico(String medico) {
    this.medico = medico;
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
    return "Vinculo #" + getId() +
      " - Paciente: " + getPacienteId() +
      " - Médico: " + getMedicoId() +
      " - Status: " + getStatus();
  }
}
