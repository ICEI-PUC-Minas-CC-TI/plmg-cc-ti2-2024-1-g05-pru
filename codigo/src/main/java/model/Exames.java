package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Exames implements Serializable {
    private int id;
    private int consultaId;
    private String titulo;
    private LocalDate data;
    private String urlArquivo;
    private String status;

    public Exame() {};

    public Exame (int id, int consultaId, String titulo, LocalDate data, String urlArquivo, String status){
        setId(id);
        setConsultaId(consultaId);
        setTitulo(titulo);
        setData(data);
        setUrlArquivo(urlArquivo);
        setStatus(status);
    }

    // id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        // 18.04 | TODO ---- FAZER VALIDAÇÃO DOS PARAMS
        this.id = id;
    }

    // consultaId
    public int getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(int consultaId) {
        // 18.04 | TODO ---- FAZER VALIDAÇÃO DOS PARAMS
        this.consultaId = consultaId;
    }

    // titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        // 18.04 | TODO ---- FAZER VALIDAÇÃO DOS PARAMS
        this.titulo = titulo;
    }

    // id
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        // 18.04 | TODO ---- FAZER VALIDAÇÃO DOS PARAMS
        this.data = id;
    }

    // titulo
    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String url) {
        // 18.04 | TODO ---- FAZER VALIDAÇÃO DOS PARAMS
        this.urlArquivo = url;
    }

    // titulo
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        // 18.04 | TODO ---- FAZER VALIDAÇÃO DOS PARAMS
        this.status = status;
    }

    @Override
    public String toString() {
        return "Exame #" + setId() +
                " - ID Consulta: " + setConsultaId() +
                " - Titulo: " + setTitulo() +
                " - Data: " + setData() +
                " - Arquivo (url): " + setUrlArquivo() +
                " - Status: " + setStatus();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Exame exame = (Exame) obj;
        return id == examexame.id;
    }
}
