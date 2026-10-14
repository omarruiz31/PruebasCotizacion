package uv.lis.pruebas;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CotizadorImpresionTest {
    CotizadorImpresion cotizador = new CotizadorImpresion();

    @Test
    void CP01(){
        BigDecimal cotizar = cotizador.cotizar(TipoUsuario.ESTUDIANTE,1,false,false);
        assertEquals(new BigDecimal("2.00"),cotizar);
    }
}