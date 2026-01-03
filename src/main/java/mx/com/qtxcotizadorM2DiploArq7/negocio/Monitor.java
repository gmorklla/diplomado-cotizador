package mx.com.qtxcotizadorM2DiploArq7.negocio;

import java.math.BigDecimal;

public class Monitor extends Articulo {

    protected Monitor(String marca, String modelo, BigDecimal precioBase, BigDecimal costo, String sku) {
		super(marca, modelo, precioBase, costo, sku);
	}

    @Override
	public TipoArticulo getTipo() { return TipoArticulo.MONITOR; }

    @Override
    public BigDecimal cotizar(Integer cantidad) {
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

}
