package service;

import model.Exame;
import dao.ExameDAO;
import util.GsonUtil;

import java.sql.Connection;
import java.sql.SQLException;
import spark.Request;
import spark.Response;

public class ExameService {
	private ExameDAO exameDAO;

	public ExameService() {
		exameDAO = new ExameDAO();
	}

	public ExameService(Connection conexao) {
		exameDAO = new ExameDAO(conexao);
	}

	public Object read(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Exame exame = exameDAO.get(id);

		if (exame == null) {
			response.status(404); // 404 Not found
			return "Exame ID #" + id + " não encontrado.";
		}

		response.header("Content-Type", "application/json");
		return GsonUtil.GSON.toJson(exame);
	}

	public Object create(Request request, Response response) {
		try {
			Exame exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);
			exame = exameDAO.insert(exame);

			response.status(201); // 201 Created
			response.header("Content-Type", "application/json");
			return GsonUtil.GSON.toJson(exame);
		}
		catch (IllegalArgumentException e) {
			response.status(400); // 400 Bad request
			return "Erro ao criar exame: " + e.getMessage();
		}
		catch (SQLException e) {
			response.status(500); // 500 Internal Server Error
			return "Erro ao criar exame: " + e.getMessage();
		}
		catch (Exception e) {
			response.status(500); // 500 Internal Server Error
			return "Erro ao criar exame: " + e.getCause().getMessage();
		}
	}

	public Object update(Request request, Response response) {
		try {
			int id = Integer.parseInt(request.params(":id"));
			Exame exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);

			if (exame.getId() != id) {
				response.status(400); // 400 Bad request
				return "ID da exame diferente do ID na URL!";
			}

			if (exameDAO.update(exame)) {
				response.status(204); // 204 No content
				return response;
			} else {
				response.status(404); // 404 Not found
				return "Exame ID #" + exame.getId() + " não encontrado!";
			}
		}
		catch (Exception e) {
			response.status(500); // 500 Internal Server Error
			return "Erro ao atualizar exame: " + e.getMessage();
		}
	}

	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		if (exameDAO.delete(id)) {
			response.status(204); // 204 No content
			return response;
		} else {
			response.status(404); // 404 Not found
			return "Exame ID #" + id + " não encontrado!";
		}
	}
}
