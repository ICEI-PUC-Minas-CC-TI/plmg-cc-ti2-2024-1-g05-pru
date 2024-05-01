package service;

import model.Vinculo;
import dao.VinculoDAO;
import util.GsonUtil;

import java.util.List;
import spark.Request;
import spark.Response;


public class VinculoService {
  private VinculoDAO vinculoDAO;

  public VinculoService() {
    vinculoDAO = new VinculoDAO();
  }

  public Object readAll(Request request, Response response) {
    List<Vinculo> vinculos = vinculoDAO.getAll();
    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(vinculos);
  }

  public Object read(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Vinculo vinculo = vinculoDAO.get(id);

    if (vinculo != null) {
      response.header("Content-Type", "application/json");
      response.header("Content-Encoding", "UTF-8");

      return GsonUtil.GSON.toJson(vinculo);
    } else {
      response.status(404); // 404 Not found
      return "Vinculo ID #" + id + " não encontrado.";
    }
  }

  public Object create(Request request, Response response) {
    Vinculo vinculo = GsonUtil.GSON.fromJson(request.body(), Vinculo.class);

    if (vinculoDAO.insert(vinculo)) {
      response.status(201); // 201 Created
      return "Vinculo entre Medico: (" + vinculo.getMedicoId() + ") e Paciente :("  + vinculo.getPacienteId() + ") inserido!";
    } else {
      response.status(404); // 404 Not found
      return "Vinculo não inserido!";
    }
  }

  //
  public Object update(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Vinculo vinculo = GsonUtil.GSON.fromJson(request.body(), Vinculo.class);

    if (vinculo.getId() != id) {
      response.status(400); // 400 Bad request
      return "ID do vinculo diferente do ID da URL!";
    }

    if (vinculoDAO.update(vinculo)) {
      response.status(200); // 200 OK
      return "Vinculo (ID #" + vinculo.getId() + ") atualizado!";
    } else {
      response.status(404); // 404 Not found
      return "Vinculo (ID #" + vinculo.getId() + ") não encontrado!";
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (vinculoDAO.delete(id)) {
      response.status(200); // 200 OK
      return "Vinculo (" + id + ") excluído!";
    } else {
      response.status(404); // 404 Not found
      return "Vinculo (" + id + ") não encontrado!";
    }
  }
}
