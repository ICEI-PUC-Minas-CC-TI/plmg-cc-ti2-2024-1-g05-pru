package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Especialidade;
import model.Vinculo;

public class EspecialidadeDAO extends DAO{
  public EspecialidadeDAO() {
    super();
  }

  public List<Especialidade> getAll(int medicoId) {
    List<Especialidade> especialidades = new ArrayList<>();

    try {
      String sql = "SELECT * FROM vinculo WHERE medico_id = ?";
      PreparedStatement st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
      st.setInt(1, medicoId);

      ResultSet rs = st.executeQuery(); // Declare and initialize the ResultSet variable rs

      while(rs.next()) {
        Especialidade esp = new Especialidade(
          rs.getInt("medico_id"),
          rs.getString("especialidade")
        );

        especialidades.add(esp);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return especialidades;
	}
  
  // SO EXISTE O INSERT UNITARIO, NAO EXISTE O INSERT EM LOTE
  public boolean insert(Especialidade especialidade) {
    boolean status = false;

    // Verifica se o vinculo é nulo
    if (especialidade == null) {
      return status;
    }

    try {
      String sql = "INSERT INTO especialidade (medico_id, especialidade) VALUES (?, ?)";
     
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, especialidade.getMedicoId());
      st.setString(2, especialidade.getEspecialidade());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar relacao entre medico e especialidade, nenhuma linha alterada.");
      }
      return true;

    } catch (SQLException u) {
      throw new RuntimeException("Falha ao criar relacao entre medico e especialidade, nenhuma linha alterada.", u);
    }
  }

  public boolean update(Especialidade especialidade) {
		boolean status = false;

		if (especialidade == null) {
			return status;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE especialidade SET especialidade = ? WHERE medico_id = ?");
      st.setString(1, especialidade.getEspecialidade());
      st.setInt(2, especialidade.getMedicoId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao atualizar a especialidade do medico, nenhuma linha alterada.");
			}

			status = true;
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao atualizar a especialidade do medico.", u);
		}

		return status;
	}

  public boolean delete(int medicoId) {
		boolean status = false;

		try {
			String sql = "DELETE FROM especialidade WHERE medico_id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, medicoId);

			if (st.executeUpdate() > 0) {
				status = true;
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao deletar a relacao medico-especialidade, nenhuma linha alterada.", u);
		}

		return status;
	}
}
