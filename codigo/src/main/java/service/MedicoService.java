package service;

import model.Medico;
import model.Vinculo;
import model.Especialidade;
import dao.MedicoDAO;
import dao.VinculoDAO;
import dao.EspecialidadeDAO;
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
      return "Medico ID #" + id + " não encontrado!";
    }

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(medico);
  }

  public Object create(Request request, Response response) {
    try {
      Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);
      medico = medicoDAO.insert(medico);

      response.status(201); // 201 Created
      response.header("Content-Type", "application/json");
      return GsonUtil.GSON.toJson(medico);
    }
    catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      return "Erro ao criar medico: " + e.getMessage();
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar medico: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar medico: " + e.getCause().getMessage();
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

      if (medicoDAO.update(medico)) {
        response.status(204); // 204 No content
        return response;
      } else {
        response.status(404); // 404 Not found
				return "Médico ID #" + medico.getId() + " não encontrado!";
      }
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (medicoDAO.delete(id)) {
      response.status(204); // 204 No content
      return response;
    } else {
      response.status(404); // 404 Not found
      return "Médico ID #" + id + " não encontrado!";
    }
  }

  // Ver todas as especialidades de um médico
  public Object readAllEspecialidades(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Especialidade> especialidades = new EspecialidadeDAO().getAll(id);

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(especialidades);
  }

  // Ver todos os pacientes de um médico
  public Object readAllPacientes(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Vinculo> vinculos = vinculoDAO.getAllPacientes(id);

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(vinculos);
  }
}
