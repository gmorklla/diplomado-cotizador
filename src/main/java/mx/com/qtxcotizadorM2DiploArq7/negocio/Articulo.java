package mx.com.qtxcotizadorM2DiploArq7.negocio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Articulo {
	public static final int PROMO_LLEVE_NXM_VALOR_N = 3;
	public static final int PROMO_LLEVE_NXM_VALOR_M = 2; 
	
    private String marca;
    private String modelo;
    private BigDecimal precioBase;
    private BigDecimal costo;
    private TipoArticulo tipo;
    private String memoria;
    private int capacidadAlmacenamiento;
    private String sku;

    // Un artículo puede estar compuesto de otros artículos
    private List<Articulo> listComponentes = new ArrayList<>();

    // ----- Getters y Setters -----

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public TipoArticulo getTipo() { return tipo; }
    public void setTipo(TipoArticulo tipo) { this.tipo = tipo; }

    public String getMemoria() { return memoria; }
    public void setMemoria(String memoria) { this.memoria = memoria; }

    public int getCapacidadAlmacenamiento() { return capacidadAlmacenamiento; }
    public void setCapacidadAlmacenamiento(int capacidadAlmacenamiento) {
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public void agregarComponente(Articulo componente) {
    	if(this.tipo != TipoArticulo.PC) {
    		throw new RuntimeException("Ese artículo no tiene sub-compontes");
    	}
    	this.listComponentes.add(componente);
    }
 //   public List<Articulo> getListComponentes() { return listComponentes; }

    public void setListComponentes(List<Articulo> listComponentes) {
        this.listComponentes = listComponentes;
    }

    // ----- Lógica de cotización -----

    public BigDecimal cotizar(Integer cantidad) {
    	switch(this.tipo) {
	    	case DISCO_DURO->{
	    		return this.cotizarDiscoDuro(cantidad);
	    	}
	    	case MONITOR->{
	    		return this.cotizarMonitor(cantidad);
	    	}
	    	case TARJETA_VIDEO->{
	    		return this.cotizarTarjetaVideo(cantidad);
	    	}
	    	case PC->{
	    		return this.cotizarPc(cantidad);
	    	}
	    	default->{
	    		return this.precioBase.multiply(BigDecimal.valueOf(cantidad));	    		
	    	}
    	}
     }
    
    private BigDecimal cotizarDiscoDuro(Integer cantidad) {
		return this.precioBase.multiply(BigDecimal.valueOf(cantidad));	    		
	}
    
    // ------------------------------------------------------------
    // a) Cotizar TARJETA DE VIDEO con regla "compre 3 y pague 2"
    // ------------------------------------------------------------
    public BigDecimal cotizarTarjetaVideo(int cantidad) {
        if (precioBase == null) {
            return BigDecimal.ZERO;
        }

//        BigDecimal total = calcularPromocion3X2(cantidad);
        BigDecimal total = calcularPromocionNXM(cantidad,PROMO_LLEVE_NXM_VALOR_N,PROMO_LLEVE_NXM_VALOR_M);
        
        return total;
    }
    
	private BigDecimal calcularPromocion3X2(int cantidad) {
		return this.calcularPromocionNXM(cantidad, 3, 2);
		/*
		 * // Por cada 3 unidades, se paga solo 2 int trios = cantidad / 3; int
		 * restantes = cantidad % 3;
		 * 
		 * // Total a pagar = precio * (trios*2 + restantes) int unidadesAPagar = (trios
		 * * 2) + restantes;
		 * 
		 * BigDecimal total = precioBase.multiply(BigDecimal.valueOf(unidadesAPagar));
		 * return total;
		 */
	}
	
	private BigDecimal calcularPromocionNXM(int cantidad, int N, int M) {
		// Por cada 3 unidades, se paga solo 2
        int trios = cantidad / N;
        int restantes = cantidad % N;

        // Total a pagar = precio * (trios*2 + restantes)
        int unidadesAPagar = (trios * M) + restantes;

        BigDecimal total = precioBase.multiply(BigDecimal.valueOf(unidadesAPagar));
		return total;
	}

    // ------------------------------------------------------------
    // b) Cotizar MONITOR con descuentos por volumen
    // ------------------------------------------------------------
    public BigDecimal cotizarMonitor(int cantidad) {
        if (precioBase == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotal = precioBase.multiply(BigDecimal.valueOf(cantidad));

        if (cantidad >= 3 && cantidad <= 5) {
            // 5% de descuento
            BigDecimal descuento = subtotal.multiply(BigDecimal.valueOf(0.05));
            return subtotal.subtract(descuento);
        } else if (cantidad >= 6) {
            // 10% de descuento
            BigDecimal descuento = subtotal.multiply(BigDecimal.valueOf(0.10));
            return subtotal.subtract(descuento);
        }

        // Menos de 3 → sin descuento
        return subtotal;
    }

    
	public BigDecimal cotizarPc(Integer cantidad) {
        if (precioBase == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = new BigDecimal("0.00");
        for(Articulo cmpI:this.listComponentes) {
        	total = total.add(cmpI.cotizar(1));
        }
        
        return total.multiply(new BigDecimal("0.8"));
    }
}
