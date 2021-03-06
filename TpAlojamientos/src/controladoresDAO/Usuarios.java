package controladoresDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.ValidacionException;
import extra.Conexion;
import extra.Constantes;
import extra.LOG;
import modelo.Usuario;

public class Usuarios implements Connectable<Usuario> {
	private static final _DAOConstantesNombreCampos cCampo = new _DAOConstantesNombreCampos();
	private final static String camposInsertIntoDB = " nombre=?, apellido=?, dni=?, mail=?, fechaNac=?"
			+ ", usuario=?, clave=?, sexo=?, rutaFotoPerfil=?, admin=?, puntaje=?, fechaAlta= "
			+ cCampo.sql_STR_TO_DATE_YmdHiS + ", fechaUltConexion = " + cCampo.sql_STR_TO_DATE_YmdHiS
			+ ", fechaUltModificado= " + cCampo.sql_STR_TO_DATE_YmdHiS + ", verificado=?, habilitado=?, nroTelefono=?";

	private final static String camposUpdateDB = "nombre=?, apellido=?, dni=?, mail=?, fechaNac=?"
			+ ", sexo=?, verificado=?, habilitado=?, nroTelefono=?";

	private static HashMap<String, String> queries = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1712263006671542535L;

		{
			put("all", "select * from usuarios");
			put("insert", String.format("INSERT into usuarios SET  %s , idUsuario=?", camposInsertIntoDB));
			put("count", "select count(*) as cantidad from usuarios");
			put("update", String.format("update usuarios SET  %s where idUsuario=?", camposInsertIntoDB));
			put("updateUsuario", String.format("update usuarios SET %s where idUsuario=?", camposUpdateDB));
			put("get", "select * from usuarios where idUsuario=?");
			put("like", "");
			put("updateFechaUltConexion", String.format("update usuarios set fechaUltConexion= %s  where idUsuario=?",
					cCampo.sql_STR_TO_DATE_YmdHiS));
			put("updateRutaFotoPerfil", "update usuarios set rutaFotoPerfil= ?  where idUsuario=?");
			put("updateVerificado", "update usuarios set verificado= ?  where idUsuario=?");
			put("updatePassUser", "update usuarios set clave=? where idUsuario=?");
		}
	};

	private Conexion cn;
	private ArrayList<Usuario> m;

	@Override
	public ArrayList<Usuario> getAll() {
		cn = new Conexion();
		m = new ArrayList<Usuario>();

		try {
			cn.Open();
			ResultSet rs = cn.query(queries.get("all"));
			while (rs.next()) {
				Usuario o = new Usuario();
				o = readPs_Usuario(rs);
				m.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return m;
	}

	@Override
	public ArrayList<Usuario> getLike(String like) {
		/*
		 * 
		 * PROXIMAMENTEEEEEE
		 * 
		 * like = "%"+like+"%"; cn = new Conexion(); usuarios = new
		 * ArrayList<Usuario>(); try { PreparedStatement ps =
		 * cn.Open().prepareStatement(qSelectLike); ps.setString(1, like);
		 * ps.setString(2, like); ps.setString(3, like); ps.setString(4, like);
		 * ResultSet rs = ps.executeQuery();
		 * 
		 * while(rs.next()) { Usuario usuario = new
		 * Usuario(rs.getLong(id),rs.getString(nombre),rs.getString(apellido),rs.getLong
		 * (dni),rs.getInt(legajo),rs.getBoolean(habilitado)); usuarios.add(usuario); }
		 * 
		 * }catch(Exception e) { e.printStackTrace(); }finally{ cn.close(); } return
		 * usuarios;
		 */
		return null;
	}

	@Override
	public int getCount() {
		cn = new Conexion();
		int cantidad = 0;
		try {
			cn.Open();
			ResultSet rs = cn.query(queries.get("count"));

			if (rs.next()) {
				cantidad = rs.getInt("cantidad");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return cantidad;
	}

	@Override
	public Usuario get(Usuario obj) {
		cn = new Conexion();
		Usuario o = null;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("get"));
			ps.setInt(1, obj.getIdUsuario());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				o = new Usuario();
				o = readPs_Usuario(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return o;
	}

	@Override
	public boolean insert(Usuario obj) {
		if (obj == null) {
			return false;
		}
		cn = new Conexion();
		boolean correcto = false;
		;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("insert"));
			ps = writePs_Usuario(obj, ps);
			LOG.info("INSERT Usuarios: " + ps.toString());
			ps.executeUpdate();
			correcto = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	@Override
	public boolean update(Usuario obj) {
		if (obj == null) {
			return false;
		}
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("update"));

			ps = writePs_Usuario(obj, ps);

			LOG.info("UPDATE Usuarios: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	@Override
	public boolean remove(Usuario obj) {
		Usuario u = new Usuario();
		u.setIdUsuario(obj.getIdUsuario());
		u = this.get(u);
		u.setHabilitado(false);
		return this.update(u);
	}

	public boolean updateUsuario(Usuario obj) {
		if (obj == null) {
			return false;
		}
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("updateUsuario"));

			ps = writePs_UsuarioUPDATE(obj, ps);

			LOG.info("UPDATE Usuarios: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;

		} catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.getMessage());
		} finally {
			cn.close();
		}
		return correcto;
	}

	public boolean updateFechaUltConexion(Usuario obj) {
		if (obj == null) {
			return false;
		}
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("updateFechaUltConexion"));

			// ps.setDate(1, obj.getFechaUltConexion());
			ps.setString(1, obj.getFechaUltConexion());
			ps.setInt(2, obj.getIdUsuario());
			LOG.info("UPDATE FechaUltConexion - Usuarios: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	public boolean updateRutaFotoPerfil(int idUsuario, String rutaFotoPerfil) {
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("updateRutaFotoPerfil"));

			// ps.setDate(1, obj.getFechaUltModificacion());
			ps.setString(1, rutaFotoPerfil);
			ps.setInt(2, idUsuario);
			LOG.info("UPDATE RutaFotoPerfil - Usuarios: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	public boolean updateClave(int idUsuario, String password) {
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("updatePassUser"));

			ps.setString(1, password);
			ps.setInt(2, idUsuario);
			LOG.info("UPDATE Contraseña - Usuarios: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	public boolean updateVerificado(int idUsuario, boolean verificado) {
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("updateVerificado"));

			ps.setBoolean(1, verificado);
			ps.setInt(2, idUsuario);
			LOG.info("UPDATE Verificado - Usuarios: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}
	/// ********************* DAO - FUNCIONES READ/ WRITE ********************** ///

	private Usuario readPs_Usuario(ResultSet rs) throws SQLException {
		Usuario o = new Usuario();
		o.setIdUsuario(rs.getInt(cCampo.idUsuario));
		o.setNombre(rs.getString(cCampo.nombre));
		o.setApellido(rs.getString(cCampo.apellido));
		o.setDni(rs.getString(cCampo.dni));
		o.setMail(rs.getString(cCampo.mail));
		o.setFechaNac(rs.getDate(cCampo.fechaNac));
		o.setUsuario(rs.getString(cCampo.usuario));
		o.setClave(rs.getString(cCampo.clave));
		o.setSexo(rs.getBoolean(cCampo.sexo));
		o.setRutaFotoPerfil(rs.getString(cCampo.rutaFotoPerfil));
		o.setAdmin(rs.getBoolean(cCampo.admin));
		o.setPuntaje(rs.getFloat(cCampo.puntaje));
		o.setHabilitado(rs.getBoolean(cCampo.habilitado));
		o.setFechaAlta(rs.getString(cCampo.fechaAlta));
		o.setFechaUltConexion(rs.getString(cCampo.fechaUltConexion));
		o.setAnteriorFechaUltConexion(o.getFechaUltConexion());
		o.setVerificado(rs.getBoolean(cCampo.verificado));
		o.setNroTelefono(rs.getString(cCampo.nroTelefono));
		o.setFechaUltModificado(rs.getString(cCampo.fechaUltModificado));
		// ------------
		String rutaFotoPerfilUsuario = null;
		rutaFotoPerfilUsuario = o.getRutaFotoPerfil();
		// if(Utilitario.existeElArchivo(rutaFotoPerfilUsuario) == false)
		// //GETCONTEXTPATH()
		if (rutaFotoPerfilUsuario.isEmpty())
			rutaFotoPerfilUsuario = Constantes.RUTAuserNoPhoto;
		o.setRutaFotoPerfil(rutaFotoPerfilUsuario);
		return o;
	}

	private PreparedStatement writePs_Usuario(Usuario obj, PreparedStatement ps) throws SQLException {
		ps.setString(1, obj.getNombre());
		ps.setString(2, obj.getApellido());
		ps.setString(3, obj.getDni());
		ps.setString(4, obj.getMail());
		ps.setDate(5, obj.getFechaNac());
		ps.setString(6, obj.getUsuario());
		ps.setString(7, obj.getClave());
		ps.setBoolean(8, obj.isSexo());
		ps.setString(9, obj.getRutaFotoPerfil());
		ps.setBoolean(10, obj.isAdmin());
		ps.setFloat(11, obj.getPuntaje());
		ps.setString(12, obj.getFechaAlta());
		ps.setString(13, obj.getFechaUltConexion());
		ps.setString(14, obj.getFechaUltModificado());
		ps.setBoolean(15, obj.isVerificado());
		ps.setBoolean(16, obj.isHabilitado());
		ps.setString(17, obj.getNroTelefono());
		ps.setInt(18, obj.getIdUsuario());
		return ps;
	}

	private PreparedStatement writePs_UsuarioUPDATE(Usuario obj, PreparedStatement ps) throws SQLException {
		ps.setString(1, obj.getNombre());
		ps.setString(2, obj.getApellido());
		ps.setString(3, obj.getDni());
		ps.setString(4, obj.getMail());
		ps.setDate(5, obj.getFechaNac());
		ps.setBoolean(6, obj.isSexo());
		// ps.setString(7, obj.getFechaUltModificado());
		ps.setBoolean(7, obj.isVerificado());
		ps.setBoolean(8, obj.isHabilitado());
		ps.setString(9, obj.getNroTelefono());
		ps.setInt(10, obj.getIdUsuario());
		return ps;
	}

	/**
	 * Valida que no se repitan los campos
	 * <ol>
	 * <li>IdUsuario</li>
	 * <li>DNI</li>
	 * <li>Mail</li>
	 * </ol>
	 * 
	 * @param objUsuario
	 * @throws ValidacionException
	 */
	public void validarCamposUnicos(Usuario obj) throws ValidacionException {
		ArrayList<Usuario> listaUsuariosDB = this.getAll();

		for (Usuario dbObj : listaUsuariosDB) {
			if (obj.getIdUsuario() == dbObj.getIdUsuario())
				throw new ValidacionException("ERROR DB: Los ID's son iguales. ID: " + obj.getIdUsuario());

			if (obj.getDni().equalsIgnoreCase(dbObj.getDni()))
				throw new ValidacionException("ERROR DB: El DNI ingresado ya se encuentra registrado en el sistema");

			if (obj.getMail().equalsIgnoreCase(dbObj.getMail()))
				throw new ValidacionException("ERROR DB: El mail ingresado ya se encuentra registrado en el sistema");

		}
	}

	/// ********************* LAMBDA - Funciones de obtención de datos ******** ///
	public Usuario getUsuarioById(int idUsuario) {
		Usuario objUsuario;
		objUsuario = this.getAll().stream().filter(item -> item.getIdUsuario() == idUsuario).findFirst().orElse(null);
		return objUsuario;
	}

	public Usuario getObjectById(int idUsuario) {
		Usuario objUsuario = new Usuario();
		objUsuario.setIdUsuario(idUsuario);
		return this.get(objUsuario);
	}

	public Usuario getUsuarioByLogin(String correoUsuario, String claveUsuario) {
		Usuario objUsuario;
		objUsuario = this.getAll().stream()
				.filter(item -> item.getMail().equalsIgnoreCase(correoUsuario) && item.getClave().equals(claveUsuario))
				.findFirst().orElse(null);
		return objUsuario;
	}

	public String getNombreApellido_Usuario(int idUsuario) {
		Usuarios usuarioDAO = new Usuarios();
		Usuario objUsuario = new Usuario();
		objUsuario = usuarioDAO.getAll().stream().filter(x -> x.getIdUsuario() == idUsuario).findFirst().orElse(null);
		return objUsuario.getNombre() + " " + objUsuario.getApellido();
	}

	public String getNombreApellidoByIdUsuario(int idUsuario) {
		Usuario objUsuario = this.getObjectById(idUsuario);
		return objUsuario.getNombre() + " " + objUsuario.getApellido();
	}

	public String getRutaFotoPerfil_Usuario(int idUsuario) {
		Usuarios usuarioDAO = new Usuarios();
		Usuario objUsuario = new Usuario();
		objUsuario = usuarioDAO.getAll().stream().filter(x -> x.getIdUsuario() == idUsuario).findFirst().orElse(null);
		return objUsuario.getRutaFotoPerfil();
	}

	public ArrayList<Usuario> getListaNuevosUsuarios(String fechaUltConexion) {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

		// getAll().forEach(item -> {
		// try {
		// if (Utilitario.compareDateString(item.getFechaAlta(), fechaUltConexion) == 1)
		// listaUsuarios.add(item);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// });

		getAll().forEach(item -> {
			if (item.getFechaAlta().compareTo(fechaUltConexion) > 0)
				listaUsuarios.add(item);
		});
		return listaUsuarios;
	}

	public ArrayList<Usuario> getListaUsuarios_SortByFechaAlta() {
		ArrayList<Usuario> listaUsuarios = getAll();
		/*
		 * listaUsuarios.sort(Comparator.comparing(obj ->obj.getFechaAlta(),
		 * Comparator.nullsFirst(Comparator.naturalOrder())));
		 */
		listaUsuarios.sort((obj1, obj2) -> obj1.getFechaAlta().compareTo(obj2.getFechaAlta()));
		return listaUsuarios;
	}
}
