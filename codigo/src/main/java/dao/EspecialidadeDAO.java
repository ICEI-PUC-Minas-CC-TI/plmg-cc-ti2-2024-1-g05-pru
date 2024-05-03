package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Especialidade;

public class EspecialidadeDAO extends DAO {
  public EspecialidadeDAO() {
    super();
  }

  // Obter todas as especialidades de um médico
  public List<Especialidade> getAll(int medicoId) {
    List<Especialidade> especialidades = new ArrayList<Especialidade>();

    try {
      PreparedStatement st = conexao.prepareStatement("SELECT * FROM especialidade WHERE medico_id = ?");
      st.setInt(1, medicoId);

      ResultSet rs = st.executeQuery();

      while(rs.next()) {
        especialidades.add(new Especialidade(
          rs.getInt("id"),
          rs.getString("titulo"),
          rs.getInt("medico_id")
        ));
      }

      return especialidades;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
	}

  public Especialidade insert(Especialidade especialidade) {
    // Verifica se a especialidade é nulo
    if (especialidade == null) {
      return null;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("INSERT INTO especialidade (titulo, medico_id) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

      st.setString(1, especialidade.getTitulo());
      st.setInt(2, especialidade.getMedicoId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao adicionar especialidade, nenhuma linha alterada.");
      }

      // retorna o id gerado
      ResultSet generatedKeys = st.getGeneratedKeys();
      if (generatedKeys.next())
        especialidade.setId(generatedKeys.getInt(1));

      return especialidade;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao adicionar especialidade.", u);
    }
  }

  public boolean update(Especialidade especialidade) {
		if (especialidade == null) {
			return false;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE especialidade SET titulo = ? WHERE id = ?");
      st.setString(1, especialidade.getTitulo());
      st.setInt(2, especialidade.getId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao atualizar a especialidade do medico, nenhuma linha alterada.");
			}

			return true;
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao atualizar a especialidade do medico.", u);
		}
	}

  public boolean delete(int id) {
    try {
      PreparedStatement st = conexao.prepareStatement("DELETE FROM especialidade WHERE id = ?");
      st.setInt(1, id);

      return st.executeUpdate() > 0;
    }
    catch (SQLException u) {
      throw new RuntimeException("Falha ao deletar a especialidade, nenhuma linha alterada.", u);
    }
  }
}
