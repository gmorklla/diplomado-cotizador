package mx.com.qtxcotizadorM2DiploArq7.negocio;

import java.math.BigDecimal;

public class TarjetaVideo extends Articulo {
	public static final int PROMO_LLEVE_NXM_VALOR_N = 3;
	public static final int PROMO_LLEVE_NXM_VALOR_M = 2; 
	
    private String memoria;

    protected TarjetaVideo(String marca, String modelo, BigDecimal precioBase, BigDecimal costo, String sku,
			String memoria) {
		super(marca, modelo, precioBase, costo, sku);
		this.memoria = memoria;
	}
    
    public String getMemoria() { return memoria; }
    public void setMemoria(String memoria) { this.memoria = memoria; }
    
    @Override
    public TipoArticulo getTipo() { return TipoArticulo.TARJETA_VIDEO; }

    @Override
    public BigDecimal cotizar(Integer cantidad) {
        if (precioBase == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = calcularPromocionNXM(cantidad,PROMO_LLEVE_NXM_VALOR_N,PROMO_LLEVE_NXM_VALOR_M);
        
        return total;
    }
    
	public BigDecimal calcularPromocion3X2(int cantidad) {
		return this.calcularPromocionNXM(cantidad, 3, 2);
	}
		
	public BigDecimal calcularPromocionNXM(int cantidad, int N, int M) {
			// Por cada 3 unidades, se paga solo 2
	        int trios = cantidad / N;
	        int restantes = cantidad % N;

	        // Total a pagar = precio * (trios*2 + restantes)
	        int unidadesAPagar = (trios * M) + restantes;

	        BigDecimal total = precioBase.multiply(BigDecimal.valueOf(unidadesAPagar));
			return total;
		}


}
