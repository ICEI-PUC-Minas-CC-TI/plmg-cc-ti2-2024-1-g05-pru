package service;

import model.Medico;
import model.Especialidade;
import dao.MedicoDAO;
import dao.EspecialidadeDAO;
import util.GsonUtil;

import java.sql.Connection;
import java.util.List;
import spark.Request;
import spark.Response;

public class EspecialidadeService {
  private MedicoDAO medicoDAO;
  private EspecialidadeDAO especialidadeDAO;

  public EspecialidadeService() {
    medicoDAO = new MedicoDAO();
    especialidadeDAO = new EspecialidadeDAO();
  }

  public EspecialidadeService(Connection conexao) {
    medicoDAO = new MedicoDAO(conexao);
    especialidadeDAO = new EspecialidadeDAO(conexao);
  }

  public Object create(Request request, Response response) {
    try {
			Especialidade especialidade = GsonUtil.GSON.fromJson(request.body(), Especialidade.class);

      // Verifica se existe medico com o ID informado
      Medico medico = medicoDAO.get(especialidade.getMedicoId());
      if (medico == null) {
        response.status(404); // 404 Not found
        return "Nao existe médico com esse ID!";
      }

      // Verifica se o médico não tem mais de 2 especialidades
      List<Especialidade> especialidades = especialidadeDAO.getAll(especialidade.getMedicoId());
      if (especialidades.size() >= 2) {
        response.status(409); // 409 Conflict
        return "Médico já tem 2 especialidades!";
      }

      especialidade = especialidadeDAO.insert(especialidade);
      response.status(201); // 201 Created
			response.header("Content-Type", "application/json");

      return GsonUtil.GSON.toJson(especialidade);
    }
		catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      return "Erro ao criar especialidade: " + e.getMessage();
    }
		catch (Exception e) {
      response.status(500); // 500 Internal Server Error
			return "Erro ao criar especialidade: " + e.getMessage();
    }
  }

  public Object update(Request request, Response response) {
    try {
      int id = Integer.parseInt(request.params(":id"));
      Especialidade especialidade = GsonUtil.GSON.fromJson(request.body(), Especialidade.class);

      if (especialidade.getId() != id) {
				response.status(400); // 400 Bad Request
				return "ID do medico diferente do ID na URL!";
			}

      if (especialidadeDAO.update(especialidade)) {
        response.status(204); // 204 No content
        return response;
      } else {
        response.status(404); // 404 Not found
        return " Erro ao atualizar especialdiade!";
      }
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar especialidade: " + e.getMessage();
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (especialidadeDAO.delete(id)) {
      response.status(204); // 204 No content
      return response;
    } else {
      response.status(404); // 404 Not found
      return "Especialidade não encontrada!";
    }
  }
}
