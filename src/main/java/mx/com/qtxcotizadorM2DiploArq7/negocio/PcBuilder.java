package mx.com.qtxcotizadorM2DiploArq7.negocio;

import java.util.List;
import java.util.Map;

/**
 * @author hp835
 * @version 1.0
 * @created 03-ene.-2026 12:42:30 p. m.
 */
public class PcBuilder {

	private final int MAX_DISCOS = 4;
	private static final int MAX_MONITORES = 2;
	private static final int MAX_TARJETAS = 1;
	private List<DiscoDuro> lstDiscos;
	private List<Monitor> lstMonitores;
	private TarjetaVideo tarjeta;
	private Map<String,Object> parametros;

	public PcBuilder(){

	}

	/**
	 * 
	 * @param nombre
	 * @param valor
	 */
	public PcBuilder agregarParametro(String nombre, Object valor){
		return null;
	}

	/**
	 * 
	 * @param disco
	 */
	public PcBuilder agregarDiscoDuro(IComponentePc disco){
		return null;
	}

	/**
	 * 
	 * @param monitor
	 */
	public PcBuilder agregarMonitor(IComponentePc monitor){
		return null;
	}

	/**
	 * 
	 * @param tarjeta
	 */
	public PcBuilder agregarTarjetaVideo(IComponentePc tarjeta){
		return null;
	}

	public Pc build(){
		return null;
	}

	private boolean configuracionValida(){
		return false;
	}

	public Map<String,String> validarConfiguracion(){
		return null;
	}

}