package service;

import model.Medico;
import model.Vinculo;
import dao.MedicoDAO;
import dao.VinculoDAO;
import util.GsonUtil;

import java.sql.SQLException;
import java.util.List;

import spark.Request;
import spark.Response;


public class MedicoService {
  private MedicoDAO medicoDAO;
  private VinculoDAO vinculoDAO;

  public MedicoService() {
    medicoDAO = new MedicoDAO();
    vinculoDAO = new VinculoDAO();
  }

  public Object read(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Medico medico = medicoDAO.get(id);

    if (medico == null) {
      response.status(404); // 404 Not found
      response.body("Medico ID #" + id + " não encontrado.");

      return response.body();
    }

    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(medico);
  }

  public Object create(Request request, Response response) {
    response.header("Content-Type", "application/json");

    try {
      Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);
      medico = medicoDAO.insert(medico);

      response.status(201); // 201 Created
      return GsonUtil.GSON.toJson(medico);
    } catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      response.body("Erro ao criar medico: " + e.getMessage());

      return response.body();
    } catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar medico: " + e.getMessage());

      return response.body();
    } catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar medico: " + e.getCause().getMessage());

      return response.body();
    }
  }

  public Object update(Request request, Response response) {
    try {
      int id = Integer.parseInt(request.params(":id"));
      Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);

      if (medico.getId() != id) {
        response.status(400); // 400 Bad request
        return "ID do medico diferente do ID da URL!";
      }

      if (!medicoDAO.update(medico)) {
        response.status(500); // 500 Internal Server Error
        return "Erro ao atualizar medico.";
      }

      response.status(200); // 200 OK
      return "Medico atualizado com sucesso.";
    } catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    } catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (medicoDAO.delete(id)) {
      response.status(200); // 200 OK
      return "Medico (" + id + ") excluído!";
    } else {
      response.status(404); // 404 Not found
      return "Medico (" + id + ") não encontrado!";
    }
  }

  // Ver todas as especialidades de um médico

  // Adicionar especialidade a um médico

  // Ver todos os pacientes de um médico
  public Object readAllPacientes(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Vinculo> vinculos = vinculoDAO.getAllPacientes(id);
    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(vinculos);
  }
}
