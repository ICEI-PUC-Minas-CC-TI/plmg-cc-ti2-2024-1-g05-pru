package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Medicamento;

public class MedicamentoDAO extends DAO {
  public MedicamentoDAO() {
    super();
  }

  public Medicamento insert(Medicamento medicamento) throws SQLException {
    // verifica se o medicamento é nulo
    if (medicamento == null) {
      return null;
    }

    try {
      String sql = "INSERT INTO medicamento (nome, dias, controlado, consulta_id) VALUES (?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      st.setString(1, medicamento.getNome());
      st.setInt(2, medicamento.getDias());
      st.setBoolean(3, medicamento.isControlado());
      st.setInt(4, medicamento.getConsultaId());

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
      String sql = "UPDATE medicamento SET nome = ?, dias = ?, controlado = ? WHERE id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, medicamento.getNome());
      st.setInt(2, medicamento.getDias());
      st.setBoolean(3, medicamento.isControlado());
      st.setInt(4, medicamento.getConsultaId());

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
      String sql = "DELETE FROM medicamento WHERE id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
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
      String sql = "SELECT * FROM medicamento WHERE consulta_id = ?";
      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, consultaId);

      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        medicamentos.add(new Medicamento(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getInt("dias"),
          rs.getBoolean("controlado"),
          rs.getInt("consulta_id")
        ));
      }
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao obter medicamentos.", u);
    }

    return medicamentos;
  }
}
