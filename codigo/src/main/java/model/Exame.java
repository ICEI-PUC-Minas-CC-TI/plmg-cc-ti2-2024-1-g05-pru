package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Exame implements Serializable {
    private int id;
    private String titulo;
    private LocalDate data;
    private String urlArquivo;
    private String status;
    private int consultaId;

    public Exame() { }

    public Exame (int id, String titulo, LocalDate data, String urlArquivo, String status, int consultaId){
        setId(id);
        setTitulo(titulo);
        setData(data);
        setUrlArquivo(urlArquivo);
        setStatus(status);
        setConsultaId(consultaId);
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

    // data
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // urlArquivo
    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String url) {
        this.urlArquivo = url;
    }

    // status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "Exame #" + getId() +
                " - ID Consulta: " + getConsultaId() +
                " - Titulo: " + getTitulo() +
                " - Data: " + getData() +
                " - Arquivo (url): " + getUrlArquivo() +
                " - Status: " + getStatus();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Exame exame = (Exame) obj;
        return this.id == exame.id;
    }
}
