package service;

import model.Medico;
import model.Vinculo;
import dao.MedicoDAO;
import dao.VinculoDAO;
import util.GsonUtil;

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

  public Object readAll(Request request, Response response) {
    List<Medico> medicos = medicoDAO.getAll();
    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(medicos);
  }

   public Object readAllPacientes(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Vinculo> vinculos = vinculoDAO.getAllPacientes(id);
    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(vinculos);
  }


  public Object read(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Medico medico = medicoDAO.get(id);

    if (medico != null) {
      response.header("Content-Type", "application/json");
      response.header("Content-Encoding", "UTF-8");

      return GsonUtil.GSON.toJson(medico);
    } else {
      response.status(404); // 404 Not found
      return "Medico ID #" + id + " não encontrado.";
    }
  }

  public Object create(Request request, Response response) {
    Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);

    if (medicoDAO.insert(medico)) {
      response.status(201); // 201 Created
      return "Medico (" + medico.getNome() + ") inserido!";
    } else {
      response.status(404); // 404 Not found
      return "Medico (" + medico.getNome() + ") não inserido!";
    }
  }

  public Object update(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);

    if (medico.getId() != id) {
      response.status(400); // 400 Bad request
      return "ID do medico diferente do ID da URL!";
    }

    if (medicoDAO.update(medico)) {
      response.status(200); // 200 OK
      return "Medico (ID #" + medico.getId() + ") atualizado!";
    } else {
      response.status(404); // 404 Not found
      return "Medico (ID #" + medico.getId() + ") não encontrado!";
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (medicoDAO.delete(id)) {
      response.status(200); // 200 OK
      return "Medico (" + id + ") excluído!";
    } else {
      response.status(404); // 404 Not found
      return "Medico (" + id + ") não encontrado!";
    }
  }
}
