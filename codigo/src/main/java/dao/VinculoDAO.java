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
      String sql = "SELECT v.*, med.nome AS medico, pac.nome AS paciente FROM vinculo v INNER JOIN usuario med ON med.id = v.medico_id INNER JOIN usuario pac ON pac.id = v.paciente_id WHERE v.id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, id);

      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        vinculo = new Vinculo(
          rs.getInt("id"),
          rs.getString("status"),
          rs.getString("paciente"),
          rs.getInt("paciente_id"),
          rs.getString("medico"),
          rs.getInt("medico_id")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter vinculo.", u);
    }

    return vinculo;
	}

  // retorna todos os vinculos de um paciente
  public List<Vinculo> getAllMedicos(int pacienteId) {
    List<Vinculo> vinculos = new ArrayList<Vinculo>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT v.*, med.nome AS medico, pac.nome AS paciente FROM vinculo v INNER JOIN usuario med ON med.id = v.medico_id INNER JOIN usuario pac ON pac.id = v.paciente_id WHERE pac.id = ?";
      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
      st.setInt(1, pacienteId);

      rs = st.executeQuery();
      while(rs.next()) {
        Vinculo vinculo = new Vinculo(
          rs.getInt("id"),
          rs.getString("status"),
          rs.getString("paciente"),
          rs.getInt("paciente_id"),
          rs.getString("medico"),
          rs.getInt("medico_id")
        );

        vinculos.add(vinculo);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return vinculos;
  }

  // retorna todos os vinculos de um medico
  public List<Vinculo> getAllPacientes(int medicoId) {
    List<Vinculo> vinculos = new ArrayList<Vinculo>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT v.*, med.nome AS medico, pac.nome AS paciente FROM vinculo v INNER JOIN usuario med ON med.id = v.medico_id INNER JOIN usuario pac ON pac.id = v.paciente_id WHERE med.id = ?";
      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
      st.setInt(1, medicoId);

      rs = st.executeQuery();
      while(rs.next()) {
        Vinculo vinculo = new Vinculo(
          rs.getInt("id"),
          rs.getString("status"),
          rs.getString("paciente"),
          rs.getInt("paciente_id"),
          rs.getString("medico"),
          rs.getInt("medico_id")
        );

        vinculos.add(vinculo);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return vinculos;
  }

  public boolean insert(Vinculo vinculo) {
    boolean status = false;

    // Verifica se o vinculo é nulo
    if (vinculo == null) {
      return status;
    }

    try {
      String sql = "INSERT INTO vinculo (status, paciente_id, medico_id) VALUES (?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, vinculo.getStatus());
      st.setInt(2, vinculo.getPacienteId());
      st.setInt(3, vinculo.getMedicoId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar vinculo entre paciente e médico, nenhuma linha alterada.");
      }
      return true;

    } catch (SQLException u) {
      throw new RuntimeException("Falha ao criar vinculo entre paciente e médico.", u);
    }
  }

  public boolean update(Vinculo vinculo) {
		boolean status = false;

		if (vinculo == null) {
			return status;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE vinculo SET status = ? WHERE id = ?");
			st.setString(1, vinculo.getStatus());
      st.setInt(2, vinculo.getId());

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
			String sql = "DELETE FROM vinculo WHERE id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, id);

			if (st.executeUpdate() > 0) {
				status = true;
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao deletar vinculo, nenhuma linha alterada.", u);
		}

		return status;
	}

  // verifica se o vinculo existe
  public boolean exists(Vinculo vinculo) {
    boolean status = false;
    int medicoId = vinculo.getMedicoId();
    int pacienteId = vinculo.getPacienteId();

    try {
      String sql = "SELECT * FROM vinculo WHERE medico_id = ? AND paciente_id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, medicoId);
      st.setInt(2, pacienteId);

      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        status = true;
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao verificar se o vinculo existe.", u);
    }

    return status;
  }
}
