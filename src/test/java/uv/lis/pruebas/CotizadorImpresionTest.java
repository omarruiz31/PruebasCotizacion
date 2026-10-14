package uv.lis.pruebas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CotizadorImpresionTest {

    @Test
    void aplicaCargoMinimo() {
        CotizadorImpresion cotizador = new CotizadorImpresion();
        assertEquals("2.00", cotizador.cotizar(TipoUsuario.ESTUDIANTE, 1, false, false).toPlainString());
    }

}