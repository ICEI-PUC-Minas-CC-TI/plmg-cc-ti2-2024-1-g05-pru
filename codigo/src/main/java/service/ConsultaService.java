package service;

import model.Consulta;
import dao.ConsultaDAO;
import util.GsonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class ConsultaService {
    private ConsultaDAO consultaDAO;

    public ConsultaService() {
        consultaDAO = new ConsultaDAO();
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Consulta consulta = consultaDAO.get(id);

        if (consulta != null) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");

            return GsonUtil.GSON.toJson(consulta);
        } else {
            response.status(404); // 404 Not found
            return "Consulta ID #" + id + " não encontrada.";
        }
    }

    public Object create(Request request, Response response) {
        JsonObject jsonBody = GsonUtil.GSON.fromJson(request.body(), JsonObject.class);

        String titulo = jsonBody.get("titulo").getAsString();
        String diagnostico = jsonBody.get("titulo").getAsString();
        LocalDateTime dataHora = LocalDateTime.parse(jsonBody
            .get("dataHora").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        String urlAnexo = jsonBody.get("urlAnexo").getAsString();
        int idPaciente = jsonBody.get("idPaciente").getAsInt();
        int idMedico = jsonBody.get("idMedico").getAsInt();

        Consulta consulta = new Consulta();
        consulta.setTitulo(titulo);
        consulta.setDiagnostico(diagnostico);
        consulta.setDataHora(dataHora);
        consulta.setUrlAnexo(urlAnexo);
        consulta.setIdPaciente(idPaciente);
        consulta.setIdMedico(idMedico);

        if (consultaDAO.insert(consulta)) {
            response.status(201); // 201 Created
            return "Consulta (" + consulta.getTitulo() + ") inserida!";
        } else {
            response.status(404); // 404 Not found
            return "Consulta (" + consulta.getTitulo() + ") não inserida!";
        }
    }

    public Object put(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Consulta consulta = GsonUtil.GSON.fromJson(request.body(), Consulta.class);

        if (consulta.getId() != id) {
            response.status(400); // 400 Bad request
            return "ID da consulta diferente do ID na URL!";
        }

        if (consultaDAO.update(consulta)) {
            response.status(200); // 200 OK
            return "Consulta (ID #" + consulta.getId() + ") atualizada!";
        } else {
            response.status(404); // 404 Not found
            return "Consulta (ID #" + consulta.getId() + ") não encontrada!";
        }
    }

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        if (consultaDAO.delete(id)) {
            response.status(200); // 200 OK
            return "Consulta (" + id + ") excluída!";
        } else {
            response.status(404); // 404 Not found
            return "Consulta (" + id + ") não encontrada!";
        }
    }
}
