package service;

import model.Exame;
import dao.ExameDAO;
import util.GsonUtil;

import spark.Request;
import spark.Response;

public class ExameService {
    private ExameDAO exameDAO;

    public ExameService() {
        exameDAO = new ExameDAO();
    }

    public Object read(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Exame exame = exameDAO.get(id);

        if (exame == null) {
            response.status(404); // 404 Not found
            response.body("Exame ID #" + id + " não encontrado.");

            return response.body();
        }

        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");

        return GsonUtil.GSON.toJson(exame);
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
				response.status(200); // 200 OK
				return "Exame (ID #" + exame.getId() + ") atualizada!";
			} else {
				response.status(404); // 404 Not found
				return "Exame (ID #" + exame.getId() + ") não encontrada!";
			}
		} catch (Exception e) {
			response.status(500); // 500 Internal Server Error
			return "Erro ao atualizar exame: " + e.getMessage();
		}
    }

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        if (exameDAO.delete(id)) {
            response.status(200); // 200 OK
            return "Exame (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            return "Exame (" + id + ") não encontrado!";
        }
    }
}
