package service;

import model.Consulta;
import model.Exame;

import java.util.List;

import dao.ConsultaDAO;
import dao.ExameDAO;
import util.GsonUtil;

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
		Consulta consulta = GsonUtil.GSON.fromJson(request.body(), Consulta.class);

		if (consultaDAO.insert(consulta)) {
			response.status(201); // 201 Created
			return "Consulta (" + consulta.getTitulo() + ") inserida!";
		} else {
			response.status(404); // 404 Not found
			return "Consulta (" + consulta.getTitulo() + ") não inserida!";
		}
	}

	public Object update(Request request, Response response) {
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

	// Ver todos os exames de uma consulta
	public Object readAllExames(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		List<Exame> exames = exameDAO.getAllExamesConsulta(id);

		if (exames == null) {
			response.status(404); // 404 Not found
			return "Exames da consulta ID #" + id + " não encontrados.";
		}

		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");

		return GsonUtil.GSON.toJson(exames);
	}
}
