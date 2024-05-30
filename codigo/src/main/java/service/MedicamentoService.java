package service;

import model.Medicamento;
import dao.MedicamentoDAO;
import util.GsonUtil;

import java.sql.Connection;
import java.sql.SQLException;
import spark.Request;
import spark.Response;

public class MedicamentoService {
  private MedicamentoDAO medicamentoDAO;

  public MedicamentoService() {
    medicamentoDAO = new MedicamentoDAO();
  }

	public MedicamentoService(Connection conexao) {
		medicamentoDAO = new MedicamentoDAO(conexao);
	}

  public Object create(Request request, Response response) {
    try {
      Medicamento medicamento = GsonUtil.GSON.fromJson(request.body(), Medicamento.class);
      medicamento = medicamentoDAO.insert(medicamento);

      response.status(201); // 201 Created
      response.header("Content-Type", "application/json");
      return GsonUtil.GSON.toJson(medicamento);
    }
    catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      return "Erro ao criar medicamento: " + e.getMessage();
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar medicamento: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar medicamento: " + e.getCause().getMessage();
    }
  }

  public Object update(Request request, Response response) {
		try {
			int id = Integer.parseInt(request.params(":id"));
			Medicamento medicamento = GsonUtil.GSON.fromJson(request.body(), Medicamento.class);

			if (medicamento.getId() != id) {
				response.status(400); // 400 Bad request
				return "ID da medicamento diferente do ID na URL!";
			}

			if (medicamentoDAO.update(medicamento)) {
				response.status(204); // 204 No content
				return response;
			} else {
				response.status(404); // 404 Not found
				return "Medicamento ID #" + medicamento.getId() + " não encontrado!";
			}
		}
		catch (Exception e) {
			response.status(500); // 500 Internal Server Error
			return "Erro ao atualizar medicamento: " + e.getMessage();
		}
	}

  public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		if (medicamentoDAO.delete(id)) {
			response.status(204); // 204 No content
			return response;
		} else {
			response.status(404); // 404 Not found
			return "Medicamento ID #" + id + " não encontrado!";
		}
	}
}
