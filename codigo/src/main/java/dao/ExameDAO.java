package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Exame;

public class ExameDAO extends DAO {
  public ExameDAO() {
    super();
  }

  public boolean insert(Exame exame) {
    boolean status = false;

    // verifica se o exame é nulo
    if (exame == null) {
      return status;
    }

    try {
      String sql = "INSERT INTO exame (titulo, data, url_arquivo, status, consulta_id) VALUES (?, ?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      st.setString(1, exame.getTitulo());
      st.setDate(2, java.sql.Date.valueOf(exame.getData()));
      st.setString(3, exame.getUrlArquivo());
      st.setString(4, exame.getStatus());
      st.setInt(5, exame.getConsultaId());

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
          rs.getInt("id"),
          rs.getString("titulo"),
          rs.getDate("data").toLocalDate(),
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

  public boolean update(Exame exame) {
    boolean status = false;

    if (exame == null) {
      return status;
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

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao atualizar exame.", u);
    }

    return status;
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
      String sql = "SELECT * FROM exame WHERE consulta_id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, consultaId);

      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        exames.add(new Exame(
          rs.getInt("id"),
          rs.getString("titulo"),
          rs.getDate("data").toLocalDate(),
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
}
