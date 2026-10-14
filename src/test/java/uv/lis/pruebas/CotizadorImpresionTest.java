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
    @Test
    @DisplayName("CP01")
    void CP01_usuarioEstudiante_devuelve_2(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE,1,false,false);
        assertEquals(new BigDecimal("2.00"),cotizar);
    }

    //PRUEBAS OMAR

}