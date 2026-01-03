package mx.com.qtxcotizadorM2DiploArq7.pruebas;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mx.com.qtxcotizadorM2DiploArq7.negocio.Articulo;
import mx.com.qtxcotizadorM2DiploArq7.negocio.Cotizador;
import mx.com.qtxcotizadorM2DiploArq7.negocio.DiscoDuro;
import mx.com.qtxcotizadorM2DiploArq7.negocio.Pc;
import mx.com.qtxcotizadorM2DiploArq7.negocio.TarjetaVideo;
import mx.com.qtxcotizadorM2DiploArq7.negocio.TipoArticulo;

public class TestCotizador {

    public static void main(String[] args) {
        testCotizador();
    }

    public static void testCotizador() {
    	
    	Map<String,Object> mapParams = Map.of(Articulo.CVE_MARCA,"Seagate",
    			                              Articulo.CVE_MODELO, "Disco SSD 1TB",
    			                              Articulo.CVE_PRECIO_BASE, new BigDecimal("1200"),
    			                              Articulo.CVE_COSTO, new BigDecimal("600"),
    			                              Articulo.CVE_SKU,"X-SSD-1",
    			                              Articulo.CVE_CAPACIDAD_ALM,"1Tb");
    	Articulo disco = Articulo.crearArticulo(TipoArticulo.DISCO_DURO,mapParams);
    	
//    	DiscoDuro disco = new DiscoDuro("Seagate","Disco SSD 1TB",new BigDecimal("1200"),
//    			new BigDecimal("600"),"X-SSD-1","1Tb");

    	Map<String,Object> mapParamsTv = Map.of(
    			Articulo.CVE_MARCA,"Nvidia",
                Articulo.CVE_MODELO, "NAV-500",
                Articulo.CVE_PRECIO_BASE, new BigDecimal("800"),
                Articulo.CVE_COSTO, new BigDecimal("400"),
                Articulo.CVE_SKU,"NV-16-23",
                Articulo.CVE_MEMORIA,"16GB");
    	
    	Articulo ram = Articulo.crearArticulo(TipoArticulo.TARJETA_VIDEO, mapParamsTv);
    	
//        TarjetaVideo ram = new TarjetaVideo("Nvidia","NAV-500",new BigDecimal("800"),new BigDecimal("400"),"NV-16-23",
//        		"16GB");

        Pc pc = new Pc("Dell","PC Gamer", "DGAME-3411", List.of(disco,ram));

        // Crear cotizador
        Cotizador cot = new Cotizador();
        cot.agregarItemCotizacion(pc, 1);
        cot.cotizar();
        
    	Map<String,Object> mapParamsTv2 = Map.of(
    			Articulo.CVE_MARCA,"Nvidia",
                Articulo.CVE_MODELO, "X-600",
                Articulo.CVE_PRECIO_BASE, new BigDecimal("2000.00"),
                Articulo.CVE_COSTO, new BigDecimal("700.00"),
                Articulo.CVE_SKU,"X-66-23-1",
                Articulo.CVE_MEMORIA,"16GB");
    	
    	Articulo tarjetaVideo = Articulo.crearArticulo(TipoArticulo.TARJETA_VIDEO, mapParamsTv2);  	
    	
//        TarjetaVideo tarjetaVideo = new TarjetaVideo("Nvidia","X-600",new BigDecimal("2000.00"),new BigDecimal("700.00"),"X-66-23-1",
//        		"16GB");
        
        // Crear OTRO cotizador
        Cotizador cotizador2 = new Cotizador();
        cotizador2.agregarItemCotizacion(tarjetaVideo, 5);

         cotizador2.cotizar();
    }
}
