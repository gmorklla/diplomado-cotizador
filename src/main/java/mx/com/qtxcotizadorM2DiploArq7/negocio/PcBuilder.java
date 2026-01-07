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

	// Reglas de negocio (Constantes)
	private static final int MAX_DISCOS = 4;
	private static final int MAX_MONITORES = 2;
	private static final int MAX_TARJETAS = 1;

	// Almacenamiento temporal de componentes
	private List<DiscoDuro> lstDiscos;
	private List<Monitor> lstMonitores;
	private TarjetaVideo tarjeta;

	// Almacenamiento de atributos generales (Marca, Modelo, Precio Base)
	private Map<String, Object> parametros;

	// CONSTRUCTOR: Inicializa las listas y mapas para evitar NullPointerException
	public PcBuilder() {
		this.lstDiscos = new ArrayList<>();
		this.lstMonitores = new ArrayList<>();
		this.parametros = new HashMap<>();
	}

	/**
	 * Agrega parámetros generales como MARCA, MODELO o PRECIO.
	 * Retorna 'this' para permitir encadenamiento (Fluent API).
	 */
	public PcBuilder agregarParametro(String nombre, Object valor) {
		this.parametros.put(nombre, valor);
		return this; // Retornamos el mismo builder
	}

	/**
	 * Valida que el componente sea un DiscoDuro y lo agrega.
	 */
	public PcBuilder agregarDiscoDuro(IComponentePc disco) {
		if (disco instanceof DiscoDuro) {
			this.lstDiscos.add((DiscoDuro) disco);
		} else {
			// Podrías lanzar una excepción o ignorarlo, aquí lo imprimimos por simplicidad
			System.err.println("El componente pasado no es un Disco Duro válido.");
		}
		return this;
	}

	/**
	 * Valida que el componente sea un Monitor y lo agrega.
	 */
	public PcBuilder agregarMonitor(IComponentePc monitor) {
		if (monitor instanceof Monitor) {
			this.lstMonitores.add((Monitor) monitor);
		}
		return this;
	}

	/**
	 * Asigna la tarjeta de video (solo una según la definición del campo).
	 */
	public PcBuilder agregarTarjetaVideo(IComponentePc tarjeta) {
		if (tarjeta instanceof TarjetaVideo) {
			this.tarjeta = (TarjetaVideo) tarjeta;
		}
		return this;
	}

	/**
	 * MÉTODO PRINCIPAL: Construye la PC si todo es válido.
	 */
	public Pc build() {
		// 1. VALIDACIÓN: Si la configuración viola reglas, no dejamos construir nada.
		if (!configuracionValida()) {
			throw new RuntimeException("Configuración inválida: " + validarConfiguracion().toString());
		}

		// 2. CONSOLIDACIÓN DE COMPONENTES
		// Creamos una lista temporal para juntar todos los componentes dispersos
		List<IComponentePc> componentesTotales = new ArrayList<>();

		// Agregamos los discos
		componentesTotales.addAll(this.lstDiscos);

		// Agregamos los monitores
		componentesTotales.addAll(this.lstMonitores);

		// Agregamos la tarjeta de video (si existe)
		if (this.tarjeta != null) {
			componentesTotales.add(this.tarjeta);
		}

		// 3. EXTRACCIÓN DE PARÁMETROS (Marca, Modelo, SKU)
		// Usamos un método auxiliar para sacar los Strings del mapa de forma segura
		String marca = obtenerParametroString("MARCA", "PC Genérica");
		String modelo = obtenerParametroString("MODELO", "Custom Build");
		String sku = obtenerParametroString("SKU", "N/A");

		// 4. CREACIÓN DEL OBJETO (Usando el ÚNICO constructor disponible)
		return new Pc(marca, modelo, sku, componentesTotales);
	}

	/**
	 * Método auxiliar para obtener Strings del mapa sin que explote si es nulo.
	 */
	private String obtenerParametroString(String llave, String valorPorDefecto) {
		if (this.parametros.containsKey(llave)) {
			return this.parametros.get(llave).toString();
		}
		return valorPorDefecto;
	}

	/**
	 * Retorna true si no hay errores en la configuración.
	 */
	private boolean configuracionValida() {
		// Si el mapa de errores está vacío, es válida.
		return validarConfiguracion().isEmpty();
	}

	/**
	 * Revisa las reglas de negocio (Máximos permitidos).
	 * 
	 * @return Mapa con los errores encontrados.
	 */
	public Map<String, String> validarConfiguracion() {
		Map<String, String> errores = new HashMap<>();

		if (lstDiscos.size() > MAX_DISCOS) {
			errores.put("DISCOS", "Excede el máximo de " + MAX_DISCOS + " discos.");
		}

		if (lstMonitores.size() > MAX_MONITORES) {
			errores.put("MONITORES", "Excede el máximo de " + MAX_MONITORES + " monitores.");
		}

		if (lstDiscos.isEmpty()) {
			// Regla opcional discutida en clase: ¿Una PC debe tener al menos un disco?
			errores.put("DISCOS", "La PC debe tener al menos un disco duro.");
		}

		// Validación de tarjeta de video (si quisieras validar que no sea null)
		// if (this.tarjeta == null) errores.put("VIDEO", "Falta tarjeta de video");

		return errores;
	}
}
