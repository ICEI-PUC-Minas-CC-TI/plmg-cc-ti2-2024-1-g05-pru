package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO extends DAO {
  public UsuarioDAO(Connection conexao) {
    this.conexao = conexao;
  }

  public boolean updatePhoto(int id, String urlFoto) {
    try {
      System.out.println("Atualizando foto do usuário com id " + id + " para " + urlFoto);

      PreparedStatement st = conexao.prepareStatement("UPDATE usuario SET url_foto = ? WHERE id = ?");
      st.setString(1, urlFoto);
      st.setInt(2, id);

      int affectedRows = st.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Falha ao atualizar foto do usuário, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }
}
