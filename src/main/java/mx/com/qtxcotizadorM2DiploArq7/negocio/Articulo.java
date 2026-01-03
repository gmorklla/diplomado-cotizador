package mx.com.qtxcotizadorM2DiploArq7.negocio;
import java.math.BigDecimal;
import java.util.Map;

public abstract class Articulo {
	public static final String CVE_MARCA = "marca";
	public static final String CVE_MODELO = "modelo";
	public static final String CVE_PRECIO_BASE = "precioBase";
	public static final String CVE_COSTO = "costo";
	public static final String CVE_SKU = "sku";
	public static final String CVE_CAPACIDAD_ALM = "capacidad";
	public static final String CVE_MEMORIA = "memoria";
	
    private String marca;
    private String modelo;
    protected BigDecimal precioBase;
    private BigDecimal costo;
    private String sku;
    
    protected Articulo(String marca, String modelo, BigDecimal precioBase, BigDecimal costo, String sku) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.precioBase = precioBase;
		this.costo = costo;
		this.sku = sku;
	}

    /**
     * Factory Method para centralizar la creación de suntipos de artículo -exceptuando la pc-
     * @param tipo
     * @param mapValores
     * @return
     */
    public static Articulo crearArticulo(TipoArticulo tipo, Map<String,Object> mapValores) {
//    	System.out.println(mapValores);
    	String marca = (String) mapValores.get(Articulo.CVE_MARCA);
    	String modelo = (String) mapValores.get(Articulo.CVE_MODELO);
    	BigDecimal precioBase = (BigDecimal) mapValores.get(Articulo.CVE_PRECIO_BASE); 
    	BigDecimal costo = (BigDecimal) mapValores.get(Articulo.CVE_COSTO);
    	String sku = (String) mapValores.get(Articulo.CVE_SKU);
    	
    	switch(tipo) {
	    	case DISCO_DURO ->{  		
	        	String capacidadAlm = (String) mapValores.get(Articulo.CVE_CAPACIDAD_ALM);
	    		Articulo disco = new DiscoDuro(marca, modelo, precioBase, costo, sku, capacidadAlm);
	    		return disco;
	    	}
	    	case TARJETA_VIDEO ->{
	    		String memoria = (String) mapValores.get(Articulo.CVE_MEMORIA);
	    		Articulo tarjeta = new TarjetaVideo(marca, modelo, precioBase, costo, sku, memoria);
	    		return tarjeta;
	    	}
	    	case MONITOR ->{
	    		Articulo monitor = new Monitor(marca, modelo, precioBase, costo, sku);
	    		return monitor;
	    	}
     	}
    	return null;
    };
    

	// ----- Getters y Setters -----

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    // Las subclases CONCRETAS deben implementar este método!!
    public abstract TipoArticulo getTipo();
//    public void setTipo(TipoArticulo tipo) { this.tipo = tipo; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    // ----- Lógica de cotización -----

    public BigDecimal cotizar(Integer cantidad) {
//    	switch(this.tipo) {
//	    	case DISCO_DURO->{
//	    		return this.cotizarDiscoDuro(cantidad);
//	    	}
//	    	case MONITOR->{
//	    		return this.cotizarMonitor(cantidad);
//	    	}
//	    	case TARJETA_VIDEO->{
//	    		return this.cotizarTarjetaVideo(cantidad);
//	    	}
//	    	case PC->{
//	    		return this.cotizarPc(cantidad);
//	    	}
//	    	default->{
	    		return this.precioBase.multiply(BigDecimal.valueOf(cantidad));	    		
//	    	}
//    	}
     }
      
}
