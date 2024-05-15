package model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private String titulo;
    private String diagnostico;
    private LocalDateTime dataHora;
    private String paciente;
    private int pacienteId;
    private String medico;
    private int medicoId;

    public Consulta() { }

    public Consulta(int id, String titulo, String diagnostico, LocalDateTime dataHora, int pacienteId, int medicoId) {
        setId(id);
        setTitulo(titulo);
        setDiagnostico(diagnostico);
        setDataHora(dataHora);
        setPacienteId(pacienteId);
        setMedicoId(medicoId);
    }

    public Consulta(int id, String titulo, String diagnostico, LocalDateTime dataHora, String paciente, int pacienteId, String medico, int medicoId) {
        setId(id);
        setTitulo(titulo);
        setDiagnostico(diagnostico);
        setDataHora(dataHora);
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

    // titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // diagnostico / resumo
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    // dataHora
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    // pacienteId
    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    // paciente (nome)
    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    // medicoId
    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    // medico (nome)
    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    // utils
    @Override
    public String toString() {
        return "Consulta #" + getId() +
            " - Título: " + getTitulo() +
            " - Diagnóstico: " + getDiagnostico() +
            " - Data e Hora: " + getDataHora() +
            " - Paciente: " + getPacienteId() +
            " - Médico: " + getMedicoId();
    }
}
