package uv.lis.pruebas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CotizadorImpresionTest {
    private CotizadorImpresion cotizador;
    @BeforeEach
    void init(){
        cotizador = new CotizadorImpresion();

    }

    //PRUEBAS DIEGO

    @Test
    @DisplayName("CP01. Usuario No Nulo; Devuelve 2.00")
    void CP01_usuarioNoNuloDevuelve200(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 1, false, false);
        assertEquals(new BigDecimal("2.00"), cotizar);
    }

    @Test
    @DisplayName("CP02. Usuario Nulo; Devuelve Excepcion de Usuario Requerido")
    void CP02_usuarioNuloDevuelveExcepcionUsuarioRequerido(){
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            cotizador.cotizar(null, 1, false, false);
        });

        assertEquals("TIPO DE USUARIO REQUERIDO", excepcion.getMessage());
    }

    @Test
    @DisplayName("CP03. 1 Página BN; Devuelve 2.00")
    void CP03_1PaginaDevuelve200(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE,1,false,false);
        assertEquals(new BigDecimal("2.00"), cotizar);
    }

    @Test
    @DisplayName("CP04. 50 Páginas BN; Devuelve 32.00")
    void CP04_50PaginasDevuelve3200(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 50,false,false);
        assertEquals(new BigDecimal("32.00"), cotizar);
    }

    @Test
    @DisplayName("CP05. 500 Páginas BN; Devuelve 320.00")
    void CP05_500PaginasDevuelve320(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 500,false,false);
        assertEquals(new BigDecimal("320.00"), cotizar);
    }

    @Test
    @DisplayName("CP06. 501 Páginas; Devuelve Excepcion Fuera de Rango")
    void CP06_501PaginasDevuelveExcepcionFueraDeRango(){
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            cotizador.cotizar(TipoUsuario.ESTUDIANTE, 501, false, false);
        });

        assertEquals("PAGINAS FUERA DE RANGO: 501", excepcion.getMessage());
    }

    @Test
    @DisplayName("CP07. 0 Páginas; Devuelve Excepcion Fuera de Rango")
    void CP07_0PaginasDevuelveExcepcionFueraDeRango(){
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            cotizador.cotizar(TipoUsuario.ESTUDIANTE, 0, false, false);
        });

        assertEquals("PAGINAS FUERA DE RANGO: 0", excepcion.getMessage());
    }

    @Test
    @DisplayName("CP08. Usuario Nulo, 0 Páginas; Devuelve Excepcion Usuario Requerido")
    void CP08_usuarioNuloY0PaginasDevuelveExcepcionUsuarioRequerido(){
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            cotizador.cotizar(null, 0, false, false);
        });

        assertEquals("TIPO DE USUARIO REQUERIDO", excepcion.getMessage());
    }

    @Test
    @DisplayName("CP09. Usuario Nulo, 501 Páginas; Devuelve Excepcion Usuario Requerido")
    void CP09_usuarioNuloY501PaginasDevuelveExcepcionUsuarioRequerido(){
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            cotizador.cotizar(null, 501, false, false);
        });

        assertEquals("TIPO DE USUARIO REQUERIDO", excepcion.getMessage());
    }

    //Pruebas bugatti

    @Test
    @DisplayName("CP10")
    void CP10_usuarioEstudiante_devuelve_2() {
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 2, false, false);
        assertEquals(new BigDecimal("2.00"), cotizar);
    }

    @Test
    @DisplayName("CP11")
    void CP11_usuarioEstudiante_devuelve_6() {
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 10, false, false);
        assertEquals(new BigDecimal("6.40"), cotizar );
    }
    @Test
    @DisplayName("CP12")
    void CP12_usuarioEstudiante_devuelve_3() {
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 1, true, false);
        assertEquals(new BigDecimal("3.60"), cotizar );
    }

    @Test
    @DisplayName("CP13")
    void CP13_usuarioDocente_devuelve_222() {
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.DOCENTE, 99, true, false);
        assertEquals(new BigDecimal("222.75"), cotizar );
    }

    @Test
    @DisplayName("CP14")
    void CP14_usuarioDocente_devuelve_16() {
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.DOCENTE, 40, false, false);
        assertEquals(new BigDecimal("16.00"), cotizar );
    }

    //PRUEBAS ALEXA
    @Test
    void CP15_noventaYNuevePaginas_sinDescuentoPorVolumen() {
        BigDecimal total = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 99, false, false);
        assertEquals(new BigDecimal("63.36"), total);
    }

    @Test
    void CP16_cienPaginas_aplicaCincoPorCiento() {
        BigDecimal total = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 100, false, false);
        assertEquals(new BigDecimal("60.80"), total);
    }

    @Test
    void CP17_doscientasNoventaYNuevePaginas_sigueEnCincoPorCiento() {
        BigDecimal total = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 299, false, false);
        assertEquals(new BigDecimal("181.79"), total);
    }

    @Test
    void CP18_trescientasPaginas_aplicaDocePorCiento() {
        BigDecimal total = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 300, false, false);
        assertEquals(new BigDecimal("168.96"), total);
    }

    @Test
    void CP19_dobleCara_aplicaDiezPorCientoAdicional() {
        BigDecimal total = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 50, false, true);
        assertEquals(new BigDecimal("28.80"), total);
    }

    @Test
    void CP20_unaPagina_dobleCaraNoDaDescuento() {
        BigDecimal total = cotizador.cotizar(TipoUsuario.ESTUDIANTE, 1, true, true);
        assertEquals(new BigDecimal("3.60"), total);
    }

    //PRUEBAS VANE

        // CP21 - R5.1: Estudiante con 20% de descuento
        @Test
        void CP21_estudiante_descuentoVeintePorciento() {
            CotizadorImpresion cotizador = new CotizadorImpresion();

            BigDecimal obtenido = cotizador.cotizar(
                    TipoUsuario.ESTUDIANTE, 50, false, false
            );

            assertEquals(new BigDecimal("32.00"), obtenido);
        }

        // CP22 - R5.1: Estudiante con color, doble cara y volumen
        @Test
        void CP22_estudiante_color_dobleCara() {
            CotizadorImpresion cotizador = new CotizadorImpresion();

            BigDecimal obtenido = cotizador.cotizar(
                    TipoUsuario.ESTUDIANTE, 120, true, true
            );

            assertEquals(new BigDecimal("369.36"), obtenido);
        }

        // CP23 - R5.2: Docente con 50% de descuento
        @Test
        void CP23_docente_descuentoCincuentaPorciento() {
            CotizadorImpresion cotizador = new CotizadorImpresion();

            BigDecimal obtenido = cotizador.cotizar(
                    TipoUsuario.DOCENTE, 20, true, false
            );

            assertEquals(new BigDecimal("45.00"), obtenido);
        }

        // CP24 - R5.2: Docente con descuento por volumen y doble cara
        @Test
        void CP24_docente_volumen_dobleCara() {
            CotizadorImpresion cotizador = new CotizadorImpresion();

            BigDecimal obtenido = cotizador.cotizar(
                    TipoUsuario.DOCENTE, 300, false, true
            );

            assertEquals(new BigDecimal("95.04"), obtenido);
        }

        // CP25 - R5.3: Externo con recargo de 15%
        @Test
        void CP25_externo_recargoQuincePorciento() {
            CotizadorImpresion cotizador = new CotizadorImpresion();

            BigDecimal obtenido = cotizador.cotizar(
                    TipoUsuario.EXTERNO, 50, false, false
            );

            assertEquals(new BigDecimal("46.00"), obtenido);
        }

        // CP26 - R5.3: Externo con color, doble cara y volumen
        @Test
        void CP26_externo_color_dobleCara_recargo() {
            CotizadorImpresion cotizador = new CotizadorImpresion();

            BigDecimal obtenido = cotizador.cotizar(
                    TipoUsuario.EXTERNO, 100, true, true
            );

            assertEquals(new BigDecimal("442.46"), obtenido);
        }

    //PRUEBAS OMAR

    @Test
    @DisplayName("CP027")
    void CP027_usuarioDocente_devuelve_2(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.DOCENTE,2,false,false);
        assertEquals(new BigDecimal("2.00"),cotizar);
    }

    @Test
    @DisplayName("CP028")
    void CP028_usuarioEstudiante_devuelve_2(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE,4,false,false);
        assertEquals(new BigDecimal("2.56"),cotizar);
    }

    @Test
    @DisplayName("CP029")
    void CP029_usuarioDocente_devuelve_2(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.DOCENTE,1,false,true);
        assertEquals(new BigDecimal("2.00"),cotizar);
    }

    @Test
    @DisplayName("CP030")
    void CP030_usuarioExterno_devuelve_442(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.EXTERNO,100,true,true);
        assertEquals(new BigDecimal("442.46"),cotizar);
    }

    @Test
    @DisplayName("CP031")
    void CP031_usuarioDocente_devuelve_442(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.DOCENTE,400,false,true);
        assertEquals(new BigDecimal("126.72"),cotizar);
    }

    @Test
    @DisplayName("CP032")
    void CP032_usuarioEstudiante_devuelve_2(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE,3,false,false);
        assertEquals(new BigDecimal("2.00"),cotizar);
    }
}