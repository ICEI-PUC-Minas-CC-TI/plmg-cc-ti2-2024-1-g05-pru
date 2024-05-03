// package dao;

// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// import model.DicionarioEspecialidade;
// import model.Vinculo;

// public class DicionarioEspecialidadeDAO extends DAO{
//   public DicionarioEspecialidadeDAO() {
//     super();
//   }

//   public DicionarioEspecialidade get(int id) {
//     DicionarioEspecialidade dicionarioEspecialidade = null;

//     try {
//       String sql = "SELECT * FROM dicionario_especialidade WHERE id = ?";
//       PreparedStatement st = conexao.prepareStatement(sql);
//       st.setInt(1, id);

//       ResultSet rs = st.executeQuery();
//       if (rs.next()) {
//         dicionarioEspecialidade = new DicionarioEspecialidade(
//             rs.getInt("id"),
//             rs.getString("nome")
//         );
//       }
//     } catch (SQLException u) {
//       throw new RuntimeException("Falha ao obter nome da especialidade.", u);
//     }

//     return dicionarioEspecialidade;
// 	}

//   public List<DicionarioEspecialidade> getAllDicionariosEspecialidades() {
//     List<DicionarioEspecialidade> dicionarios = new ArrayList<DicionarioEspecialidade>();
//     PreparedStatement st = null;
//     ResultSet rs = null;

//     try {
//       String sql = "SELECT * FROM dicionario_especialidade";
//       st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);

//       rs = st.executeQuery();
//       while(rs.next()) {
//         DicionarioEspecialidade dicionarioEspecialidade = new DicionarioEspecialidade(
//           rs.getInt("id"),
//           rs.getString("nome")
//         );

//         dicionarios.add(dicionarioEspecialidade);
//       }
//     } catch (SQLException u) {
//       throw new RuntimeException(u);
//     }

//     return dicionarios;
//   }

//   public boolean insert(DicionarioEspecialidade dicionarioEspecialidade) {
//     boolean status = false;

//     // Verifica se o dicionarioEspecialidade é nulo
//     if (dicionarioEspecialidade == null) {
//       return status;
//     }

//     try {
//       String sql = "INSERT INTO dicionario_especialidade (id, nome) VALUES (?, ?)";
     
//       PreparedStatement st = conexao.prepareStatement(sql);
//       st.setInt(1, dicionarioEspecialidade.getId());
//       st.setString(2, dicionarioEspecialidade.getNome());

//       if (st.executeUpdate() == 0) {
//         throw new SQLException("Falha ao criar especialidade no dicionario, nenhuma linha alterada.");
//       }
//       return true;

//     } catch (SQLException u) {
//       throw new RuntimeException("Falha ao criar especialidade no dicionario, nenhuma linha alterada.", u);
//     }
//   }

//   public boolean update(DicionarioEspecialidade dicionarioEspecialidade) {
// 		boolean status = false;

// 		if (dicionarioEspecialidade == null) {
// 			return status;
// 		}

// 		try {
// 			PreparedStatement st = conexao.prepareStatement("UPDATE dicionario_especialidade SET nome = ? WHERE id = ?");
//       st.setString(1, dicionarioEspecialidade.getNome());
//       st.setInt(2, dicionarioEspecialidade.getId());

// 			if (st.executeUpdate() == 0) {
// 				throw new SQLException("Falha ao atualizar a DicionarioEspecialidade, nenhuma linha alterada.");
// 			}

// 			status = true;
// 		} catch (SQLException u) {
// 			throw new RuntimeException("Falha ao atualizar a DicionarioEspecialidade, nenhuma linha alterada.", u);
// 		}

// 		return status;
// 	}

//   public boolean delete(int id) {
// 		boolean status = false;

// 		try {
// 			String sql = "DELETE FROM dicionario_especialidade WHERE id = ?";
// 			PreparedStatement st = conexao.prepareStatement(sql);
// 			st.setInt(1, id);

// 			if (st.executeUpdate() > 0) {
// 				status = true;
// 			}
// 		} catch (SQLException u) {
// 			throw new RuntimeException("Falha ao deletar a especialidade, nenhuma linha alterada.", u);
// 		}

// 		return status;
// 	}
// }
