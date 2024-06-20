package service;

import java.sql.Connection;

import com.google.gson.JsonObject;

import dao.UsuarioDAO;
import spark.Request;
import spark.Response;
import util.GsonUtil;

public class UsuarioService {
  private UsuarioDAO usuarioDAO;

  public UsuarioService(Connection conexao) {
    usuarioDAO = new UsuarioDAO(conexao);
  }

  public Object updatePhoto(Request request, Response response) {
    try {
      int id = Integer.parseInt(request.params(":id"));
      JsonObject jsonBody = GsonUtil.GSON.fromJson(request.body(), JsonObject.class);

      String urlFoto = jsonBody.get("urlFoto").getAsString();

      usuarioDAO.updatePhoto(id, urlFoto);

      response.status(200); // 200 OK
      return "Foto atualizada com sucesso!";
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar medico: " + e.getMessage();
    }
  }
}
