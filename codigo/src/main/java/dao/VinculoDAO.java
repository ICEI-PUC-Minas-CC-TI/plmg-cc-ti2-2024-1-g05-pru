package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Vinculo;

public class VinculoDAO extends DAO{
  public VinculoDAO() {
    super();
  }

  public Vinculo get(int id) {
    Vinculo vinculo = null;

    try {
      String sql = "SELECT * FROM vinculo WHERE id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, id);

      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        vinculo = new Vinculo(
            rs.getInt("id"),
            rs.getInt("paciente_id"),
            rs.getInt("medico_id"),
            rs.getString("status")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter consulta.", u);
    }

    return vinculo;
	}

  public List<Vinculo> getAll() {
    List<Vinculo> vinculos = new ArrayList<Vinculo>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT * FROM vinculo";
      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
      rs = st.executeQuery();
      while(rs.next()) {
        Vinculo vinculo = new Vinculo(
          rs.getInt("id"),
          rs.getInt("paciente_id"),
          rs.getInt("medico_id"),
          rs.getString("status")
        );

        vinculos.add(vinculo);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return vinculos;
  }

  public List<Vinculo> getAllMedicos(int pacienteId) {
    List<Vinculo> vinculos = new ArrayList<Vinculo>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT * FROM vinculo WHERE paciente_id = ?";
      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
      st.setInt(1, pacienteId);

      rs = st.executeQuery();
      while(rs.next()) {
        Vinculo vinculo = new Vinculo(
          rs.getInt("id"),
          rs.getInt("paciente_id"),
          rs.getInt("medico_id"),
          rs.getString("status")
        );

        vinculos.add(vinculo);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return vinculos;
  }

  public List<Vinculo> getAllPacientes(int medicoId) {
    List<Vinculo> vinculos = new ArrayList<Vinculo>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT * FROM vinculo WHERE medico_id = ?";
      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
      st.setInt(1, medicoId);

      rs = st.executeQuery();
      while(rs.next()) {
        Vinculo vinculo = new Vinculo(
          rs.getInt("id"),
          rs.getInt("paciente_id"),
          rs.getInt("medico_id"),
          rs.getString("status")
        );

        vinculos.add(vinculo);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return vinculos;
  }

  //TODO DEVO CHECAR SE O PACIENTE E O MEDICO EXISTEM ANTES DE INSERIR O VINCULO ?
  public boolean insert(Vinculo vinculo) {
    boolean status = false;

    // Verifica se o vinculo é nulo
    if (vinculo == null) {
      return status;
    }

    try {
      String sql = "INSERT INTO vinculo (paciente_id, medico_id, status) VALUES (?, ?, ?)";
     
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, vinculo.getPacienteId()); // Change setString to setInt
      st.setInt(2, vinculo.getMedicoId()); // Change setString to setInt
      st.setString(3, vinculo.getStatus());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar vinculo entre usuario e medico, nenhuma linha alterada.");
      }
      return true;

    } catch (SQLException u) {
      throw new RuntimeException("Falha ao criar vinculo entre usuario e medico/", u);
    }
  }

  public boolean update(Vinculo vinculo) {
		boolean status = false;

		if (vinculo == null) {
			return status;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE vinculo SET paciente_id = ?, medico_id = ?, status = ? WHERE id = ?");
			st.setInt(1, vinculo.getPacienteId());
			st.setInt(2, vinculo.getMedicoId());
			st.setString(3, vinculo.getStatus());
      st.setInt(4, vinculo.getId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao atualizar o vinculo, nenhuma linha alterada.");
			}

			status = true;
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao atualizar vinculo.", u);
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
			throw new RuntimeException("Falha ao deletar consulta, nenhuma linha alterada.", u);
		}

		return status;
	}
}
