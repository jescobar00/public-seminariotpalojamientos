package controladoresDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import extra.Conexion;
import extra.Constantes;
import extra.LOG;
import modelo.Localidad;
import modelo.Publicacion;
import modelo.TipoAlojamiento;
import modelo.Usuario;

public class Publicaciones implements Connectable<Publicacion> {
	private final Comentarios comentariosDAO = new Comentarios();
	private static final _DAOConstantesPublicacion cPubli = new _DAOConstantesPublicacion();
	private static final _DAOConstantesNombreCampos cCampo = new _DAOConstantesNombreCampos();
	private final static String camposInsertIntoDB = "idUsuario=?, idTipoAlojamiento=?, descripcion=?"
			+ ", idLocalidad=?, codPostal=?, coordenadas=?, calle=?, altura=?, piso=?, dpto=?"
			+ ", supCubierta=?, supDescubierta=?, precioExpensas=?, precioNoche=?, chkPuedeVariarCantPersonas=?"
			+ ", cantPersonas=?, cantAmbientes=?, cantBanios=?, cantHabitaciones=?, aniosAntiguedad=?"
			+ ", fechaAlta=?, fechaUltModificado=?, puntaje=?, verificado=?, habilitado=? ";
	private static HashMap<String, String> queries = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2096761465575252395L;

		{
			put("all", "select * from publicaciones");
			put("insert", String.format("INSERT into publicaciones SET  %s , idPublicacion=?", camposInsertIntoDB));
			put("count", "select count(*) as cantidad from publicaciones");
			put("update", String.format("update publicaciones set %s where idPublicacion=?", camposInsertIntoDB));
			put("updatePuntuacion", "update publicaciones set puntaje=? where idPublicacion=?");
			put("updateVerificado", "update publicaciones set verificado=? where idPublicacion=?");
			put("get", "select * from publicaciones where idPublicacion=?");
			put("limit", "select * from publicaciones limit ?, ?");

		}
	};

	private Conexion cn;
	private ArrayList<Publicacion> m;

	@Override
	public ArrayList<Publicacion> getAll() {
		cn = new Conexion();
		m = new ArrayList<Publicacion>();

		try {
			cn.Open();
			ResultSet rs = cn.query(queries.get("all"));
			while (rs.next()) {
				Publicacion o = new Publicacion();
				o = readPs_Publicacion(rs);
				LOG.info(rs.getStatement().toString());
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
	public ArrayList<Publicacion> getLike(String like) {
		// TODO Auto-generated method stub
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
	public Publicacion get(Publicacion obj) {
		cn = new Conexion();
		Publicacion o = null;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("get"));
			ps.setInt(1, obj.getIdPublicacion());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				o = new Publicacion();
				o = readPs_Publicacion(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return o;
	}

	@Override
	public boolean insert(Publicacion obj) {
		if (obj == null) {
			return false;
		}
		cn = new Conexion();
		boolean correcto = false;

		try {
			// FALSE: Si no existe el id en los registros de Usuarios, TiposAlojamientos,
			// Localidades
			Usuarios usuarios = new Usuarios();
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(obj.getIdUsuario());
			if (usuarios.get(usuario) == null)
				return false;

			TiposAlojamientos alojamientos = new TiposAlojamientos();
			TipoAlojamiento alojamiento = new TipoAlojamiento();
			alojamiento.setIdTipoAlojamiento(obj.getIdTipoAlojamiento());
			if (alojamientos.get(alojamiento) == null)
				return false;

			Localidades localidades = new Localidades();
			Localidad localidad = new Localidad();
			localidad.setIdLocalidad(obj.getIdLocalidad());
			if (localidades.get(localidad) == null)
				return false;

			PreparedStatement ps = cn.Open().prepareStatement(queries.get("insert"));
			ps = writePs_Publicacion(obj, ps);
			LOG.info("INSERT Publicaciones: " + ps.toString());

			if (ps.executeUpdate() != 0) {
				LOG.info("INSERT Publicaciones se ejecutó con éxito");
				correcto = true;
			} else
				LOG.info("ERROR INSERT Publicaciones");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	@Override
	public boolean update(Publicacion obj) {
		if (obj == null) {
			return false;
		}
		cn = new Conexion();
		boolean correcto = false;
		try {

			Usuarios usuarios = new Usuarios();
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(obj.getIdUsuario());
			if (usuarios.get(usuario) == null)
				return false;

			TiposAlojamientos alojamientos = new TiposAlojamientos();
			TipoAlojamiento alojamiento = new TipoAlojamiento();
			alojamiento.setIdTipoAlojamiento(obj.getIdTipoAlojamiento());
			if (alojamientos.get(alojamiento) == null)
				return false;

			Localidades localidades = new Localidades();
			Localidad localidad = new Localidad();
			localidad.setIdLocalidad(obj.getIdLocalidad());
			if (localidades.get(localidad) == null)
				return false;

			PreparedStatement ps = cn.Open().prepareStatement(queries.get("update"));
			ps = writePs_Publicacion(obj, ps);
			LOG.info("UPDATE Publicaciones: " + ps.toString());
			if (ps.executeUpdate() != 0) {
				LOG.info("UPDATE Publicaciones se ejecutó con éxito");
				correcto = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	@Override
	public boolean remove(Publicacion obj) {
		Publicacion u = new Publicacion();
		u.setIdPublicacion(obj.getIdPublicacion());
		u = this.get(u);
		u.setHabilitado(false);
		return this.update(u);
	}

	public boolean updatePuntuacion(int idPublicacion, float nuevoPuntaje) {
		cn = new Conexion();
		boolean correcto = false;
		try {

			PreparedStatement ps = cn.Open().prepareStatement(queries.get("update"));
			ps.setFloat(1, nuevoPuntaje);
			ps.setInt(2, idPublicacion);
			LOG.info("UPDATE Publicacion - Puntuacion: " + ps.toString());
			if (ps.executeUpdate() != 0) {
				LOG.info("UPDATE Publicacion - Puntuacion se ejecutó con éxito");
				correcto = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	public boolean updateVerificado(int idPublicacion, boolean verificado) {
		cn = new Conexion();
		boolean correcto = false;
		try {
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("updateVerificado"));

			ps.setBoolean(1, verificado);
			ps.setInt(2, idPublicacion);
			LOG.info("UPDATE Verificado - Publicacion: " + ps.toString());
			if (ps.executeUpdate() != 0)
				correcto = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return correcto;
	}

	/// ********************* DAO - Métodos READ/ WRITE ********************** ///

	private Publicacion readPs_Publicacion(ResultSet rs) throws SQLException {
		Publicacion o = new Publicacion();

		o.setIdPublicacion(rs.getInt(cPubli.idPublicacion));
		o.setIdUsuario(rs.getInt(cPubli.idUsuario));

		int idTipoAlojamiento = rs.getInt(cPubli.idTipoAlojamiento);
		String callePublicacion = rs.getString(cPubli.calle);
		int alturaPublicacion = rs.getInt(cPubli.altura);

		o.setIdTipoAlojamiento(idTipoAlojamiento);
		TiposAlojamientos tiposAlojamientosDAO = new TiposAlojamientos();
		String descTipoAlojamiento = tiposAlojamientosDAO.getTipoAlojamiento(idTipoAlojamiento).getDescripcion();
		String nombrePublicacion = "";
		if (descTipoAlojamiento.isEmpty()) {
			nombrePublicacion = String.format("%s en %s al %d", Constantes.nombreTipoAlojamiento, callePublicacion,
					alturaPublicacion);
		} else {
			nombrePublicacion = String.format("%s en %s al %d", descTipoAlojamiento, callePublicacion,
					alturaPublicacion);
		}
		o.setNombre(nombrePublicacion);
		o.setDescripcion(rs.getString(cPubli.descripcion));

		o.setIdLocalidad(rs.getInt(cPubli.idLocalidad));
		o.setCodPostal(rs.getInt(cPubli.codPostal));
		o.setCoordenadas(rs.getString(cPubli.coordenadas));
		o.setCalle(callePublicacion);
		o.setAltura(alturaPublicacion);
		o.setPiso(rs.getInt(cPubli.piso));
		o.setDpto(rs.getString(cPubli.dpto));

		o.setSupCubierta(rs.getInt(cPubli.supCubierta));
		o.setSupDescubierta(rs.getInt(cPubli.supDescubierta));
		o.setPrecioExpensas(rs.getInt(cPubli.precioExpensas));
		o.setPrecioNoche(rs.getInt(cPubli.precioNoche));
		o.setHabilitado(rs.getBoolean(cPubli.chkPuedeVariarCantPersonas));

		o.setCantPersonas(rs.getInt(cPubli.cantPersonas));
		o.setCantAmbientes(rs.getInt(cPubli.cantAmbientes));
		o.setCantBanios(rs.getInt(cPubli.cantBanios));
		o.setCantHabitaciones(rs.getInt(cPubli.cantHabitaciones));
		o.setAniosAntiguedad(rs.getInt(cPubli.aniosAntiguedad));

		o.setFechaAlta(rs.getDate(cPubli.fechaAlta));
		o.setFechaUltModificado(rs.getDate(cPubli.fechaUltModificado));
		// o.setPuntaje(rs.getFloat(cPubli.puntaje));
		float puntuacionPromedio = comentariosDAO.getPuntuacionPromedioByIdPublicacion(o.getIdPublicacion());
		o.setPuntaje(puntuacionPromedio);
		o.setVerificado(rs.getBoolean(cCampo.verificado));
		o.setHabilitado(rs.getBoolean(cPubli.habilitado));

		if (o.getPrecioExpensas() > 0)
			o.setChkExpensas(true);
		else
			o.setChkExpensas(false);

		return o;
	}

	private PreparedStatement writePs_Publicacion(Publicacion obj, PreparedStatement ps) throws SQLException {
		ps.setInt(1, obj.getIdUsuario());
		ps.setInt(2, obj.getIdTipoAlojamiento());
		ps.setString(3, obj.getDescripcion());
		ps.setInt(4, obj.getIdLocalidad());
		ps.setInt(5, obj.getCodPostal());
		ps.setString(6, obj.getCoordenadas());
		ps.setString(7, obj.getCalle());
		ps.setInt(8, obj.getAltura());
		ps.setInt(9, obj.getPiso());
		ps.setString(10, obj.getDpto());
		ps.setInt(11, obj.getSupCubierta());
		ps.setInt(12, obj.getSupDescubierta());
		ps.setInt(13, obj.getPrecioExpensas());
		ps.setInt(14, obj.getPrecioNoche());
		ps.setBoolean(15, obj.isChkPuedeVariarCantPersonas());
		ps.setInt(16, obj.getCantPersonas());
		ps.setInt(17, obj.getCantAmbientes());
		ps.setInt(18, obj.getCantBanios());
		ps.setInt(19, obj.getCantHabitaciones());
		ps.setInt(20, obj.getAniosAntiguedad());
		ps.setDate(21, obj.getFechaAlta());
		ps.setDate(22, obj.getFechaUltModificado());
		ps.setFloat(23, obj.getPuntaje());
		ps.setBoolean(24, obj.isVerificado());
		ps.setBoolean(25, obj.isHabilitado());
		ps.setInt(26, obj.getIdPublicacion());
		return ps;
	}

	/// ********************* LAMBDA - Métodos de obtención de datos ******** ///

	public Publicacion getObjectByID(int idPublicacion) {
		Publicacion objPublicacion = new Publicacion();
		objPublicacion.setIdPublicacion(idPublicacion);
		objPublicacion = this.get(objPublicacion);
		return objPublicacion;
	}

	public Publicacion getObjectByIDLambda(int idPublicacion) {
		Publicacion obj = new Publicacion();
		obj = this.getAll().stream().filter(item -> item.getIdPublicacion() == idPublicacion).findFirst().orElse(null);
		return obj;
	}

	public ArrayList<Publicacion> getAllByIdUsuario(int idUsuario) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		getAll().forEach(item -> {
			if (item.getIdUsuario() == idUsuario) {
				item.iniciarYcargarObjPublicacionInfo();
				listaFiltrada.add(item);
			}
		});
		return listaFiltrada;
	}

	public ArrayList<Publicacion> getAllByIdUsuarioOld(int idUsuario) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		getAll().forEach(item -> {
			if (item.getIdUsuario() == idUsuario) {
				listaFiltrada.add(item);
			}
		});
		return listaFiltrada;
	}

	/// ********************* LAMBDA - Filtros de datos ******** ///
	// Lambda - Publicaciones - Filtros
	public ArrayList<Publicacion> filtroGetAllByTipoAlojamiento(int idTipoAlojamiento) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		getAll().forEach(item -> {
			if (item.getIdTipoAlojamiento() == idTipoAlojamiento)
				listaFiltrada.add(item);
		});
		return listaFiltrada;
	}

	public ArrayList<Publicacion> filtroGetAllByIsVerificado() {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		getAll().forEach(item -> {
			if (item.isVerificado())
				listaFiltrada.add(item);
		});
		return listaFiltrada;
	}

	public ArrayList<Publicacion> filtroGetAllByIdLocalidad(int idLocalidad) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		getAll().forEach(item -> {
			if (item.getIdLocalidad() == idLocalidad)
				listaFiltrada.add(item);
		});
		return listaFiltrada;
	}

	// Lambda - Publicaciones - Filtros Combinados
	public ArrayList<Publicacion> filtroGetAllByTipoAlojamiento(int idTipoAlojamiento,
			ArrayList<Publicacion> listaPublicacionYaFiltrada) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		listaPublicacionYaFiltrada.forEach(item -> {
			if (item.getIdTipoAlojamiento() == idTipoAlojamiento)
				listaFiltrada.add(item);
		});
		return listaFiltrada;
	}

	public ArrayList<Publicacion> filtroGetAllByIsVerificado(ArrayList<Publicacion> listaPublicacionYaFiltrada) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		listaPublicacionYaFiltrada.forEach(item -> {
			if (item.isVerificado())
				listaFiltrada.add(item);
		});
		return listaFiltrada;
	}

	public ArrayList<Publicacion> filtroGetAllByIdLocalidad(int idLocalidad,
			ArrayList<Publicacion> listaPublicacionYaFiltrada) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		listaPublicacionYaFiltrada.forEach(item -> {
			if (item.getIdLocalidad() == idLocalidad)
				listaFiltrada.add(item);

		});
		return listaFiltrada;
	}

	// Lambda - Publicaciones - Orden
	public ArrayList<Publicacion> getSortListByPrecio(ArrayList<Publicacion> listaPublicacionYaFiltrada) {
		listaPublicacionYaFiltrada.sort(
				Comparator.comparing(item -> item.getPrecioNoche(), Comparator.nullsLast(Comparator.naturalOrder())));
		return listaPublicacionYaFiltrada;
	}

	public ArrayList<Publicacion> getSortListByFechaAlta(ArrayList<Publicacion> listaPublicacionYaFiltrada) {
		listaPublicacionYaFiltrada.sort((obj1, obj2) -> obj1.getFechaAlta().compareTo(obj2.getFechaAlta()));
		return listaPublicacionYaFiltrada;
	}

	/**
	 * No tiene prioridad
	 * 
	 * @param idPartido
	 * @param idLocalidad
	 * @return
	 */
	@Deprecated
	public ArrayList<Publicacion> filtroGetAllByIdPartido(int idPartido, int idLocalidad) {
		ArrayList<Publicacion> listaFiltrada = new ArrayList<Publicacion>();
		// Localidades localidadesDAO = new Localidades();
		// localidadesDAO.getAllByIdPartido(idPartido).forEach(item -> {
		// if(item.getIdLocalidad() == idLocalidad)
		// //listaFiltrada.add(item);
		// });
		return listaFiltrada;
	}
	/// ********************* Demás Funcionalidades ************************ ///

	// Esto es para que funcione la paginacion, se recupera con una consulta una
	// cantidad de datos predefinida por los parametros//

	public ArrayList<Publicacion> getLimit(int paginaActual, int cantidadRegistros) {

		cn = new Conexion();
		m = null;

		int inicio = paginaActual * cantidadRegistros - cantidadRegistros;

		try {
			cn.Open();
			m = new ArrayList<Publicacion>();
			PreparedStatement ps = cn.Open().prepareStatement(queries.get("limit"));
			ps.setInt(1, inicio);
			ps.setInt(2, cantidadRegistros);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Publicacion o = new Publicacion();
				o = readPs_Publicacion(rs);
				LOG.info(rs.getStatement().toString());
				m.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return m;
	}

}
