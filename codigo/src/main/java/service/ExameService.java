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

        if (exame != null) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");

            return GsonUtil.GSON.toJson(exame);
        } else {
            response.status(404); // 404 Not found
            return "Exame ID #" + id + " não encontrado.";
        }
    }

    public Object create(Request request, Response response) {
        Exame exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);

        // pegando o id da consulta
        int consultaId = Integer.parseInt(request.params(":id"));

        // inserindo o exame com o id da consulta como parâmetro
        if (exameDAO.insert(exame, consultaId)) {
            response.status(201); // 201 Created
            return "Exame (" + exame.getTitulo() + ") inserido!";
        } else {
            response.status(404); // 404 Not found
            return "Exame (" + exame.getTitulo() + ") não inserido!";
        }
    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Exame exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);

        if (exame.getId() != id) {
            response.status(400); // 400 Bad request
            return "ID do Exame diferente do ID na URL!";
        }

        if (exameDAO.update(exame)) {
            response.status(200); // 200 OK
            return "Exame (ID #" + exame.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            return "Exame (ID #" + exame.getId() + ") não encontrado!";
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
