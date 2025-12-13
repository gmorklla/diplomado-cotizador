package mx.com.qtxcotizadorM2DiploArq7.pruebas;
import java.math.BigDecimal;

import mx.com.qtxcotizadorM2DiploArq7.negocio.Articulo;
import mx.com.qtxcotizadorM2DiploArq7.negocio.Cotizador;
import mx.com.qtxcotizadorM2DiploArq7.negocio.TipoArticulo;

public class TestCotizador {

    public static void main(String[] args) {
        testCotizador();
    }

    public static void testCotizador() {

        // Crear componentes
        Articulo disco = new Articulo();
        disco.setModelo("Disco SSD 1TB");
        disco.setPrecioBase(new BigDecimal("1200"));
        disco.setTipo(TipoArticulo.DISCO_DURO);

        Articulo ram = new Articulo();
        ram.setModelo("Memoria RAM 16GB");
        ram.setPrecioBase(new BigDecimal("800"));
        ram.setTipo(TipoArticulo.TARJETA_VIDEO);

        // Crear un PC compuesto por disco y RAM
        Articulo pc = new Articulo();
        pc.setModelo("PC Gamer");
//        pc.setPrecioBase(new BigDecimal("15000"));
        pc.setTipo(TipoArticulo.PC);

        pc.agregarComponente(disco);
//        pc.getListComponentes().add(disco);
        
        pc.agregarComponente(ram);
//        pc.getListComponentes().add(ram);

        // Crear cotizador
        Cotizador cot = new Cotizador();
        cot.agregarItemCotizacion(pc, 1);
        cot.cotizar();
        
        Articulo tarjetaVideo = new Articulo();
        tarjetaVideo.setTipo(TipoArticulo.TARJETA_VIDEO);
        tarjetaVideo.setMarca("Nvidia");
        tarjetaVideo.setModelo("X-600");
        tarjetaVideo.setCosto(new BigDecimal("700.00"));
        tarjetaVideo.setPrecioBase(new BigDecimal("2000.00"));
        tarjetaVideo.setSku("X-66-23-1");
        
        Cotizador cotizador2 = new Cotizador();
        cotizador2.agregarItemCotizacion(tarjetaVideo, 5);

         cotizador2.cotizar();
    }
}
