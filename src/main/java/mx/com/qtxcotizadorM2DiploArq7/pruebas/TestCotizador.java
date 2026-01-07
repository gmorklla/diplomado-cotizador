package mx.com.qtxcotizadorM2DiploArq7.pruebas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mx.com.qtxcotizadorM2DiploArq7.negocio.Articulo;
import mx.com.qtxcotizadorM2DiploArq7.negocio.Cotizador;
import mx.com.qtxcotizadorM2DiploArq7.negocio.IComponentePc;
import mx.com.qtxcotizadorM2DiploArq7.negocio.Pc;
import mx.com.qtxcotizadorM2DiploArq7.negocio.PcBuilder;
import mx.com.qtxcotizadorM2DiploArq7.negocio.TipoArticulo;

public class TestCotizador {

    public static void main(String[] args) {
        testCotizador();
    }

    public static void testCotizador() {

        // 1. CREACIÓN DEL DISCO DURO (Vía Factory)
        Map<String, Object> mapParams = Map.of(
                Articulo.CVE_MARCA, "Seagate",
                Articulo.CVE_MODELO, "Disco SSD 1TB",
                Articulo.CVE_PRECIO_BASE, new BigDecimal("1200"),
                Articulo.CVE_COSTO, new BigDecimal("600"),
                Articulo.CVE_SKU, "X-SSD-1",
                Articulo.CVE_CAPACIDAD_ALM, "1Tb");
        Articulo disco = Articulo.crearArticulo(TipoArticulo.DISCO_DURO, mapParams);

        // 2. CREACIÓN DE TARJETA DE VIDEO 1 (Vía Factory)
        // Nota: En tu código original la variable se llama 'ram', pero es una
        // TarjetaVideo
        Map<String, Object> mapParamsTv = Map.of(
                Articulo.CVE_MARCA, "Nvidia",
                Articulo.CVE_MODELO, "NAV-500",
                Articulo.CVE_PRECIO_BASE, new BigDecimal("800"),
                Articulo.CVE_COSTO, new BigDecimal("400"),
                Articulo.CVE_SKU, "NV-16-23",
                Articulo.CVE_MEMORIA, "16GB");
        Articulo ram = Articulo.crearArticulo(TipoArticulo.TARJETA_VIDEO, mapParamsTv);

        // ---------------------------------------------------------
        // AQUÍ ESTÁ EL CAMBIO PRINCIPAL: USANDO EL BUILDER
        // ---------------------------------------------------------
        // En lugar de: new Pc("Dell", "PC Gamer" ... List.of(...));

        Pc pc = new PcBuilder()
                .agregarParametro("MARCA", "Dell")
                .agregarParametro("MODELO", "PC Gamer")
                .agregarParametro("SKU", "DGAME-3411")
                // Necesitamos el cast (IComponentePc) porque 'disco' y 'ram'
                // están declarados como 'Articulo' genérico.
                .agregarDiscoDuro((IComponentePc) disco)
                .agregarTarjetaVideo((IComponentePc) ram)
                .build();

        // ---------------------------------------------------------

        // 3. USO DEL COTIZADOR
        Cotizador cot = new Cotizador();
        cot.agregarItemCotizacion(pc, 1);
        cot.cotizar();

        // 4. CREACIÓN DE TARJETA DE VIDEO 2 (Para el segundo cotizador)
        Map<String, Object> mapParamsTv2 = Map.of(
                Articulo.CVE_MARCA, "Nvidia",
                Articulo.CVE_MODELO, "X-600",
                Articulo.CVE_PRECIO_BASE, new BigDecimal("2000.00"),
                Articulo.CVE_COSTO, new BigDecimal("700.00"),
                Articulo.CVE_SKU, "X-66-23-1",
                Articulo.CVE_MEMORIA, "16GB");
        Articulo tarjetaVideo = Articulo.crearArticulo(TipoArticulo.TARJETA_VIDEO, mapParamsTv2);

        // Crear OTRO cotizador
        Cotizador cotizador2 = new Cotizador();
        cotizador2.agregarItemCotizacion(tarjetaVideo, 5);

        cotizador2.cotizar();
    }
}
