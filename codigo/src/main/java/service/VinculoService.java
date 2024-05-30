package service;

import model.Vinculo;

import java.sql.Connection;

import dao.VinculoDAO;
import util.GsonUtil;

import spark.Request;
import spark.Response;

public class VinculoService {
  private VinculoDAO vinculoDAO;

  public VinculoService() {
    vinculoDAO = new VinculoDAO();
  }

  public VinculoService(Connection conexao) {
    vinculoDAO = new VinculoDAO(conexao);
  }

  public Object read(Request request, Response response) {
    response.header("Content-Type", "application/json");

    int id = Integer.parseInt(request.params(":id"));
    Vinculo vinculo = vinculoDAO.get(id);

    if (vinculo == null) {
      response.status(404); // 404 Not found

      return "Vinculo ID #" + id + " não encontrado.";
    }

    response.header("Content-Encoding", "UTF-8");
    return GsonUtil.GSON.toJson(vinculo);
  }

  public Object create(Request request, Response response) {
    Vinculo vinculo = GsonUtil.GSON.fromJson(request.body(), Vinculo.class);

    // Garante que o status do novo vinculo seja "Pendente"
    vinculo.setStatus("Pendente");

    // Verifica se o vinculo já existe
    if (vinculoDAO.exists(vinculo)) {
      response.status(409); // 409 Conflict
      response.body("Vinculo entre Medico: (" + vinculo.getMedicoId() + ") e Paciente :("  + vinculo.getPacienteId() + ") já existe!");

      return response;
    }

    if (vinculoDAO.insert(vinculo)) {
      response.status(201); // 201 Created
      return "Vinculo entre Medico: (" + vinculo.getMedicoId() + ") e Paciente :("  + vinculo.getPacienteId() + ") inserido!";
    } else {
      response.status(404); // 404 Not found
      return "Vinculo não inserido!";
    }
  }

  public Object update(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Vinculo vinculo = GsonUtil.GSON.fromJson(request.body(), Vinculo.class);

    if (vinculo.getId() != id) {
      response.status(400); // 400 Bad request
      return "ID do vinculo diferente do ID da URL!";
    }

    if (vinculoDAO.update(vinculo)) {
      response.status(204); // 204 No content
      return response;
    } else {
      response.status(404); // 404 Not found
      return "Vinculo (ID #" + vinculo.getId() + ") não encontrado!";
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    response.header("Content-Type", "application/json");

    if (vinculoDAO.delete(id)) {
      response.status(204); // 204 No content
      return response;
    } else {
      response.status(404); // 404 Not found
      return "Vinculo (" + id + ") não encontrado!";
    }
  }
}
