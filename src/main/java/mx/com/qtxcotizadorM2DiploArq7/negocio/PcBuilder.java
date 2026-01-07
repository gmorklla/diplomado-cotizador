package mx.com.qtxcotizadorM2DiploArq7.negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder para construir objetos PC complejos paso a paso.
 * Incluye validación de reglas de negocio antes de la creación.
 */
public class PcBuilder {

	// --- Definición de Máximos y Mínimos ---
	private static final int MAX_DISCOS = 4;
	private static final int MIN_DISCOS = 1;

	private static final int MAX_MONITORES = 2;
	private static final int MIN_MONITORES = 0;

	private static final int MAX_TARJETAS = 2;
	private static final int MIN_TARJETAS = 0;

	// Almacenamiento temporal de componentes
	private List<DiscoDuro> lstDiscos;
	private List<Monitor> lstMonitores;
	private TarjetaVideo tarjeta;

	// Almacenamiento de atributos generales (Marca, Modelo, Precio Base)
	private Map<String, Object> parametros;

	// CONSTRUCTOR
	public PcBuilder() {
		this.lstDiscos = new ArrayList<>();
		this.lstMonitores = new ArrayList<>();
		this.parametros = new HashMap<>();
	}

	public PcBuilder agregarParametro(String nombre, Object valor) {
		this.parametros.put(nombre, valor);
		return this;
	}

	public PcBuilder agregarDiscoDuro(IComponentePc disco) {
		if (disco instanceof DiscoDuro) {
			this.lstDiscos.add((DiscoDuro) disco);
		} else {
			System.err.println("El componente pasado no es un Disco Duro válido.");
		}
		return this;
	}

	public PcBuilder agregarMonitor(IComponentePc monitor) {
		if (monitor instanceof Monitor) {
			this.lstMonitores.add((Monitor) monitor);
		}
		return this;
	}

	public PcBuilder agregarTarjetaVideo(IComponentePc tarjeta) {
		if (tarjeta instanceof TarjetaVideo) {
			this.tarjeta = (TarjetaVideo) tarjeta;
		}
		return this;
	}

	public Pc build() {
		if (!configuracionValida()) {
			throw new RuntimeException("Configuración inválida: " + validarConfiguracion().toString());
		}

		List<IComponentePc> componentesTotales = new ArrayList<>();
		componentesTotales.addAll(this.lstDiscos);
		componentesTotales.addAll(this.lstMonitores);

		if (this.tarjeta != null) {
			componentesTotales.add(this.tarjeta);
		}

		String marca = obtenerParametroString("MARCA", "PC Genérica");
		String modelo = obtenerParametroString("MODELO", "Custom Build");
		String sku = obtenerParametroString("SKU", "N/A");

		return new Pc(marca, modelo, sku, componentesTotales);
	}

	private String obtenerParametroString(String llave, String valorPorDefecto) {
		if (this.parametros.containsKey(llave)) {
			return this.parametros.get(llave).toString();
		}
		return valorPorDefecto;
	}

	private boolean configuracionValida() {
		return validarConfiguracion().isEmpty();
	}

	/**
	 * Revisa las reglas de negocio (Máximos y Mínimos permitidos).
	 * @return Mapa con los errores encontrados.
	 */
	public Map<String, String> validarConfiguracion() {
		Map<String, String> errores = new HashMap<>();

		// --- Validaciones de Discos ---
		if (lstDiscos.size() > MAX_DISCOS) {
			errores.put("DISCOS_MAX", "Excede el máximo de " + MAX_DISCOS + " discos.");
		}
		if (lstDiscos.size() < MIN_DISCOS) {
			errores.put("DISCOS_MIN", "La PC debe tener al menos " + MIN_DISCOS + " disco duro.");
		}

		// --- Validaciones de Monitores ---
		if (lstMonitores.size() > MAX_MONITORES) {
			errores.put("MONITORES_MAX", "Excede el máximo de " + MAX_MONITORES + " monitores.");
		}
		if (lstMonitores.size() < MIN_MONITORES) {
			errores.put("MONITORES_MIN", "La PC debe tener al menos " + MIN_MONITORES + " monitor.");
		}

		// --- Validaciones de Tarjeta de Video ---
		if (lstMonitores.size() > MAX_TARJETAS) {
			errores.put("MAX_TARJETAS", "Excede el máximo de " + MAX_TARJETAS + " tarjeta de video.");
		}
		if (lstMonitores.size() < MIN_TARJETAS) {
			errores.put("MIN_TARJETAS", "La PC debe tener al menos " + MIN_TARJETAS + " tarjeta de video.");
		}

		return errores;
	}
}
