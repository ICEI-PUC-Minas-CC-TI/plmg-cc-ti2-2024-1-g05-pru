package dao;

abstract class UsuarioDAO extends DAO {
	protected UsuarioDAO() {
		super();
		conectar();
	}

	protected void finalize() {
		close();
	}
}