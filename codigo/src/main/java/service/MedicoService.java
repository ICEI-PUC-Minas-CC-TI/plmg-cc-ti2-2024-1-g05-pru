package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.EspecialidadeDAO;
import dao.MedicoDAO;
import dao.VinculoDAO;
import model.Especialidade;
import model.Medico;
import model.Vinculo;
import spark.Request;
import spark.Response;
import util.CompareFacesUtil;
import util.GsonUtil;
import util.ScrapperUtil;


public class MedicoService {
  private MedicoDAO medicoDAO;
  private VinculoDAO vinculoDAO;
  private EspecialidadeDAO especialidadeDAO;

  public MedicoService(Connection conexao) {
    medicoDAO = new MedicoDAO(conexao);
    vinculoDAO = new VinculoDAO(conexao);
    especialidadeDAO = new EspecialidadeDAO(conexao);
  }

  public Object read(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Medico medico = medicoDAO.get(id);

    if (medico == null) {
      response.status(404); // 404 Not found
      return "Medico ID #" + id + " não encontrado!";
    }

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(medico);
  }

  public Object create(Request request, Response response) {
    try {
      Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);
      medico.setValidado(false);
      medico = medicoDAO.insert(medico);

      response.status(201); // 201 Created
      response.header("Content-Type", "application/json");
      return GsonUtil.GSON.toJson(medico);
    }
    catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      return "Erro ao criar medico: " + e.getMessage();
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar medico: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar medico: " + e;
    }
  }

  public Object update(Request request, Response response) {
    try {
      int id = Integer.parseInt(request.params(":id"));
      Medico medico = GsonUtil.GSON.fromJson(request.body(), Medico.class);

      if (medico.getId() != id) {
        response.status(400); // 400 Bad request
        return "ID do medico diferente do ID da URL!";
      }

      if (medicoDAO.update(medico)) {
        response.status(204); // 204 No content
        return response;
      } else {
        response.status(404); // 404 Not found
				return "Médico ID #" + medico.getId() + " não encontrado!";
      }
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (medicoDAO.delete(id)) {
      response.status(204); // 204 No content
      return response;
    } else {
      response.status(404); // 404 Not found
      return "Médico ID #" + id + " não encontrado!";
    }
  }

  // Ver todas as especialidades de um médico
  public Object readAllEspecialidades(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Especialidade> especialidades = especialidadeDAO.getAll(id);

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(especialidades);
  }

  // Ver todos os pacientes de um médico
  public Object readAllPacientes(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Vinculo> vinculos = vinculoDAO.getAllPacientes(id);

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(vinculos);
  }

  // Validar um médico
  public Object validate(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Medico medico = medicoDAO.get(id);

    if (medico == null) {
      response.status(404); // 404 Not found
      return "Medico ID #" + id + " não encontrado!";
    }

    // Valida o médico com IA
    if (medico.getUrlFoto() == null) {
      response.status(400); // 400 Bad request
      return "Médico ID #" + id + " não possui URL da foto!";
    }

    if (medico.getValidado()) {
      response.status(400); // 400 Bad request
      return "Médico ID #" + id + " já validado!";
    }

    // Busca a foto do médico no "CFM Busca Médico"
    String driverURI = "http://172.25.16.1:9516";
    String cfmImage =
      new ScrapperUtil(driverURI)
        .scrape(medico.getNome());

    boolean valido =
      CompareFacesUtil
        .compareTwoFaces(medico.getUrlFoto(), cfmImage);

    if (valido) {
      medico.setValidado(true);
      medicoDAO.validar(medico.getId());

      response.status(200); // 200 OK
      return "Médico ID #" + id + " validado com sucesso!";
    } else {
      response.status(400); // 400 Bad request
      return "Médico ID #" + id + " não validado!";
    }
  }
}
