package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Exames;

public class ExamesDAO extends DAO {
  public ExamesDAO() {
    super();
  }

  public boolean insert(Exame exame) {
    boolean status = false;

    // verifica0 se o usuario é nulo
    if (exame == null) {
      return status;
    }

    try {
      // no medicoDAO.java, de onde ele está pegando usuario? o equivalente é Exames?
      String sql = "INSERT INTO exame (id, consultaId, titulo, data, urlArquivo, status) VALUES (?,?,?,?,?,?)";

      PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      st.setSting(1, exame.getId());
      st.setSting(2, exame.getConsultaId());
      st.setSting(3, exame.getTitulo());
      st.setSting(4, exame.getData());
      st.setSting(5, exame.getUrlArquivo());
      st.setSting(6, exame.getStatus());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar exame, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao criar exame.", u);
    }
  }

  public Exame get(int id) {
    Exame exame = null;

    try {
      String sql = "SELECT * FROM exame WHERE id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, id);

      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        exame = new Exame(
            rs.getId("id"),
            rs.getConsultaId("consultaId"),
            rs.getTitulo("titulo"),
            rs.getData("data"),
            rs.getUrlArquivo("urlArquivo"),
            rs.getStatus("status"));
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter exame.", u);
    }

    return exame;
  }

  public boolean update(Consulta consulta) {
    boolean status = false;

    if (consulta == null) {
      return status;
    }

    try {
      PreparedStatement st = conexao.prepareStatement(
          "UPDATE exame SET id = ?, consultaId = ?, titulo = ?, data = ?, urlArquivo = ?, status = ? WHERE id = ?");

      st.setSting(1, exame.getId());
      st.setSting(2, exame.getConsultaId());
      st.setSting(3, exame.getTitulo());
      st.setSting(4, exame.getData());
      st.setSting(5, exame.getUrlArquivo());
      st.setSting(6, exame.getStatus());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao atualizar consulta, nenhuma linha alterada.");
      }

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao atualizar consulta.", u);
    }

    return status;
  }

  public boolean delete(int id) {
		boolean status = false;

		try {
			String sql = "DELETE FROM consulta WHERE id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, id);

			if (st.executeUpdate() > 0) {
				status = true;
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao deletar exame, nenhuma linha alterada.", u);
		}

		return status;
	}
}
