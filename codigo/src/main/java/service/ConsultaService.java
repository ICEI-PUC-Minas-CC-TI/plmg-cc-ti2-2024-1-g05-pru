package service;

import model.Consulta;
import model.Exame;
import dao.ConsultaDAO;
import dao.ExameDAO;
import util.GsonUtil;

import java.sql.SQLException;
import java.util.List;
import spark.Request;
import spark.Response;

public class ConsultaService {
	private ConsultaDAO consultaDAO;
	private ExameDAO exameDAO;

	public ConsultaService() {
		consultaDAO = new ConsultaDAO();
		exameDAO = new ExameDAO();
	}

	public Object read(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Consulta consulta = consultaDAO.get(id);

		if (consulta == null) {
			response.status(404); // 404 Not found
			response.body("Consulta ID #" + id + " não encontrada.");

			return response.body();
		}

		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");

		return GsonUtil.GSON.toJson(consulta);
	}

	public Object create(Request request, Response response) {
    response.header("Content-Type", "application/json");

    try {
      Consulta consulta = GsonUtil.GSON.fromJson(request.body(), Consulta.class);
      consulta = consultaDAO.insert(consulta);

      response.status(201); // 201 Created
      return GsonUtil.GSON.toJson(consulta);
    } catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      response.body("Erro ao criar consulta: " + e.getMessage());

      return response.body();
    } catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar consulta: " + e.getMessage());

      return response.body();
    } catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar consulta: " + e.getCause().getMessage());

      return response.body();
    }
  }

	public Object update(Request request, Response response) {
		try {
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
		} catch (Exception e) {
			response.status(500); // 500 Internal Server Error
			return "Erro ao atualizar consulta: " + e.getMessage();
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

	// Ver todos os exames de uma consulta
	public Object readAllExames(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		List<Exame> exames = exameDAO.getAllExamesConsulta(id);

		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");

		return GsonUtil.GSON.toJson(exames);
	}

	// Cria um exame associado à uma consulta
	public Object createExame(Request request, Response response) {
		response.header("Content-Type", "application/json");

    try {
      Exame exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);
      exame = exameDAO.insert(exame);

      response.status(201); // 201 Created
      return GsonUtil.GSON.toJson(exame);
    } catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      response.body("Erro ao criar exame: " + e.getMessage());

      return response.body();
    } catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar exame: " + e.getMessage());

      return response.body();
    } catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      response.body("Erro ao criar exame: " + e.getCause().getMessage());

      return response.body();
    }
	}
}
