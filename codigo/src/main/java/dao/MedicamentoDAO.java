package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Medicamento;

public class MedicamentoDAO extends DAO {
  public MedicamentoDAO(Connection conexao) {
    this.conexao = conexao;
  }

  public Medicamento insert(Medicamento medicamento) throws SQLException {
    // verifica se o medicamento é nulo
    if (medicamento == null) {
      return null;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("INSERT INTO medicamento (nome, dias, consulta_id) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
      st.setString(1, medicamento.getNome());
      st.setInt(2, medicamento.getDias());
      st.setInt(3, medicamento.getConsultaId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao adicionar medicamento, nenhuma linha alterada.");
      }

      return medicamento;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao adicionar medicamento.", u);
    }
  }

  public boolean update(Medicamento medicamento) throws SQLException {
    // verifica se o medicamento é nulo
    if (medicamento == null) {
      return false;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("UPDATE medicamento SET nome = ?, dias = ? WHERE id = ?");
      st.setString(1, medicamento.getNome());
      st.setInt(2, medicamento.getDias());
      st.setInt(3, medicamento.getId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao atualizar medicamento, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao atualizar medicamento.", u);
    }
  }

  public boolean delete(int id) {
    try {
      PreparedStatement st = conexao.prepareStatement("DELETE FROM medicamento WHERE id = ?");
      st.setInt(1, id);

      return st.executeUpdate() != 0;
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao deletar medicamento.", u);
    }
  }

  // Obter todos os medicamentos de uma consulta
  public List<Medicamento> getAllMedicamentosConsulta(int consultaId) {
    List<Medicamento> medicamentos = new ArrayList<Medicamento>();

    try {
      PreparedStatement st = conexao.prepareStatement("SELECT * FROM medicamento WHERE consulta_id = ?");
      st.setInt(1, consultaId);

      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        medicamentos.add(new Medicamento(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getInt("dias"),
          rs.getInt("consulta_id")
        ));
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter medicamentos.", u);
    }

    return medicamentos;
  }
}
