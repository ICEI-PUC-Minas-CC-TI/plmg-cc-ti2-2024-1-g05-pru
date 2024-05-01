package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Especialidade;

public class EspecialidadeDAO extends DAO{
  public EspecialidadeDAO() {
    super();
  }

  public Especialidade get(int medicoId) {
    Especialidade especialidade = null;

    try {
      String sql = "SELECT * FROM vinculo WHERE medico_id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, medicoId);

      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        especialidade = new Especialidade(
            rs.getInt("medico_id"),
            rs.getInt("especialidade_id")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter especialidade do Medico.", u);
    }

    return especialidade;
	}

  //TODO DEVO CHECAR SE O PACIENTE E O MEDICO EXISTEM ANTES DE INSERIR O VINCULO ?
  public boolean insert(Especialidade especialidade) {
    boolean status = false;

    // Verifica se o vinculo é nulo
    if (especialidade == null) {
      return status;
    }

    try {
      String sql = "INSERT INTO especialidade (medico_id, especialidade_id) VALUES (?, ?)";
     
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, especialidade.getMedicoId());
      st.setInt(2, especialidade.getEspecialidadeId());

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
			PreparedStatement st = conexao.prepareStatement("UPDATE especialidade SET especialidade_id = ? WHERE medico_id = ?");
      st.setInt(1, especialidade.getEspecialidadeId());
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
