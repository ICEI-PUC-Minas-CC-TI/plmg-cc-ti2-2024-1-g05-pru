package service;

import model.Medico;
import model.Vinculo;
import model.Especialidade;

import dao.MedicoDAO;
import dao.EspecialidadeDAO;
import util.GsonUtil;

import java.sql.SQLException;
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

  public Object readAll(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Medico medico = medicoDAO.get(id);
    List<Especialidade> especialidades = especialidadeDAO.getAll(id);

    if (medico == null) {
      response.status(404); // 404 Not found
      return "Medico ID #" + id + " não encontrado!";
    }

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(especialidades);
  }

  public Object create(Request request, Response response) {
    try {
      Especialidade especialidade = GsonUtil.GSON.fromJson(request.body(), Especialidade.class);
        // Verifica se existe medico para onde estamos tentando adicionara especialidade
      Medico medico = medicoDAO.get(especialidade.getMedicoId());
      if (medico == null) {
        response.status(404); // 409 Conflict
        response.body("Nao existe medico com esse ID!");
        return response;
      }

      if (especialidadeDAO.insert(especialidade)) {
        response.status(201); // 201 Created
        response.body("Especialidade inserida");
        return response;
      } else {
        response.status(404); // 404 Not found
        response.body("Especialidade não inserida");
        return response;
      }
    } catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar especialidade: " + e.getMessage());
      return response;
    }
  }

  public Object update(Request request, Response response) {
    try {
      int id = Integer.parseInt(request.params(":id"));
      Especialidade especialidade = GsonUtil.GSON.fromJson(request.body(), Especialidade.class);

      Medico medico = medicoDAO.get(especialidade.getMedicoId());
      if (medico == null) {
        response.status(404); // 404 Not found
        response.body("Nao existe medico com esse ID!");
        return response;
      }

      if (especialidade.getMedicoId() != id) {
				response.status(400); // 400 Bad request
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

  // Ver todas as especialidades de um médico

  // Adicionar especialidade a um médico

  // Ver todos os pacientes de um médico

}
