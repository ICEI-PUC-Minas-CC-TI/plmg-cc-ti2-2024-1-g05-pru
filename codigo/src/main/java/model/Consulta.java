package model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private String titulo;
    private String diagnostico;
    private LocalDateTime dataHora;
    private String urlAnexo;
    private int pacienteId;
    private int medicoId;

    public Consulta() {
    }

    public Consulta(int id, String titulo, String diagnostico, LocalDateTime dataHora, String urlAnexo,
            int idPaciente, int idMedico) {
        setId(id);
        setTitulo(titulo);
        setDiagnostico(diagnostico);
        setDataHora(dataHora);
        setUrlAnexo(urlAnexo);
        setPacienteId(idPaciente);
        setMedicoId(idMedico);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getUrlAnexo() {
        return urlAnexo;
    }

    public void setUrlAnexo(String urlAnexo) {
        this.urlAnexo = urlAnexo;
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
        return "Consulta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", data e hora=" + dataHora +
                ", urlAnexo='" + urlAnexo + '\'' +
                ", idPaciente=" + pacienteId +
                ", idMedico=" + medicoId +
                '}';
    }
}
