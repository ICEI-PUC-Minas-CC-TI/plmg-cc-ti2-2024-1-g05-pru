package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.Exame;

public class ExameDAO extends DAO {
  public ExameDAO() {
    super();
  }

	// Inserir registro de exame associado a consulta
  public Exame insert(Exame exame) throws SQLException {
    // verifica se o exame é nulo
    if (exame == null) {
      return null;
    }

    try {
      String sql = "INSERT INTO exame (titulo, data, url_arquivo, status, consulta_id) VALUES (?, ?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      st.setString(1, exame.getTitulo());

      if (exame.getData() != null)
        st.setDate(2, java.sql.Date.valueOf(exame.getData()));
      else
        st.setNull(2, java.sql.Types.DATE);

      st.setString(3, exame.getUrlArquivo());
      st.setString(4, exame.getStatus());
      st.setInt(5, exame.getConsultaId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar exame, nenhuma linha alterada.");
      }

      return exame;
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
        Date date = rs.getDate("data");
        LocalDate localDate = date != null ? date.toLocalDate() : null;

        exame = new Exame(
          rs.getInt("id"),
          rs.getString("titulo"),
          localDate,
          rs.getString("url_arquivo"),
          rs.getString("status"),
          rs.getInt("consulta_id")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter exame.", u);
    }

    return exame;
  }

  public boolean update(Exame exame) throws SQLException {
    if (exame == null) {
      return false;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("UPDATE exame SET titulo = ?, data = ?, url_arquivo = ?, status = ? WHERE id = ?");

      st.setString(1, exame.getTitulo());
      st.setDate(2, java.sql.Date.valueOf(exame.getData()));
      st.setString(3, exame.getUrlArquivo());
      st.setString(4, exame.getStatus());
      st.setInt(5, exame.getId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao atualizar exame, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao atualizar exame.", u);
    }
  }

  public boolean delete(int id) {
		boolean status = false;

		try {
			String sql = "DELETE FROM exame WHERE id = ?";
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

  // Obter todos os exames de uma consulta
  public List<Exame> getAllExamesConsulta(int consultaId) {
    List<Exame> exames = new ArrayList<Exame>();

    try {
      PreparedStatement st = conexao.prepareStatement("SELECT * FROM exame WHERE consulta_id = ?");
      st.setInt(1, consultaId);

      ResultSet rs = st.executeQuery();
      while (rs.next()) {

        Date date = rs.getDate("data");
        LocalDate localDate = date != null ? date.toLocalDate() : null;

        exames.add(new Exame(
          rs.getInt("id"),
          rs.getString("titulo"),
          localDate,
          rs.getString("url_arquivo"),
          rs.getString("status"),
          rs.getInt("consulta_id")
        ));
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter exames da consulta.", u);
    }

    return exames;
  }

  public List<Exame> getLastExames(int pacienteId, int qtde) {
    List<Exame> exames = new ArrayList<Exame>();

    try {
      PreparedStatement st = conexao.prepareStatement("SELECT e.* FROM exame e INNER JOIN consulta c ON c.id = e.consulta_id WHERE c.paciente_id = ? ORDER BY e.data LIMIT ?");
      st.setInt(1, pacienteId);
      st.setInt(2, qtde);

      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        Date date = rs.getDate("data");
        LocalDate localDate = date != null ? date.toLocalDate() : null;

        Exame exame = new Exame(
          rs.getInt("id"),
          rs.getString("titulo"),
          localDate,
          rs.getString("url_arquivo"),
          rs.getString("status"),
          rs.getInt("consulta_id")
        );
        exames.add(exame);
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter os últimos exames do paciente.", u);
    }

    return exames;
  }
}
