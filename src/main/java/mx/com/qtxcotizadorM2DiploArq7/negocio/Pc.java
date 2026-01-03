package mx.com.qtxcotizadorM2DiploArq7.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pc extends Articulo {
    // Una pc esta compuesta de otros artículos
    private List<Articulo> listComponentes = new ArrayList<>();
       
    public Pc(String marca, String modelo, String sku,
			List<Articulo> listComponentes) {
		super(marca, modelo, null, null, sku);
		this.listComponentes = new ArrayList<>(listComponentes);
	}

    @Override
	public TipoArticulo getTipo() { return TipoArticulo.PC; }   
    
    public void agregarComponente(Articulo componente) {
    	this.listComponentes.add(componente);
    }
    
    @Override
	public BigDecimal cotizar(Integer cantidad) {
//        if (precioBase == null) {
//            return BigDecimal.ZERO;
//        }
//        
        BigDecimal total = new BigDecimal("0.00");
        for(Articulo cmpI:this.listComponentes) {
        	total = total.add(cmpI.cotizar(1));
        }
        
        return total.multiply(new BigDecimal("0.8"));
    }

}
