package uv.lis.pruebas;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Cotizador de trabajos de impresion del Centro de Computo.
 *
 * <p>Calcula el total a pagar por un trabajo de impresion a partir del tipo de
 * usuario, el numero de paginas, si es a color y si es a doble cara. Las reglas
 * de calculo estan en la especificacion de requisitos, R1 a R7.</p>
 *
 * <p>Orden del calculo: subtotal (R2), descuento por volumen (R3), descuento por
 * doble cara (R4), ajuste por tipo de usuario (R5), redondeo (R7) y cargo
 * minimo (R6).</p>
 */
public class CotizadorImpresion {

    /** Minimo de paginas por trabajo (R1.2). */
    public static final int PAGINAS_MIN = 1;

    /** Maximo de paginas por trabajo (R1.2). */
    public static final int PAGINAS_MAX = 500;

    /** Precio por pagina en blanco y negro (R2.1). */
    public static final BigDecimal PRECIO_BN = new BigDecimal("0.80");

    /** Precio por pagina a color (R2.2). */
    public static final BigDecimal PRECIO_COLOR = new BigDecimal("4.50");

    /** Cargo minimo por trabajo (R6). */
    public static final BigDecimal CARGO_MINIMO = new BigDecimal("2.00");

    /**
     * Cotiza un trabajo de impresion.
     *
     * @param tipo      tipo de usuario; no puede ser nulo (R1.1)
     * @param paginas   numero de paginas, de 1 a 500 inclusive (R1.2)
     * @param color     {@code true} si el trabajo es a color (R2)
     * @param dobleCara {@code true} si se imprime por las dos caras (R4)
     * @return total a pagar en pesos, siempre con dos decimales (R7)
     * @throws IllegalArgumentException si el tipo es nulo o el numero de
     *                                  paginas esta fuera de rango (R1)
     */
    public BigDecimal cotizar(TipoUsuario tipo, int paginas, boolean color, boolean dobleCara) {
        validar(tipo, paginas);

        BigDecimal precio = color ? PRECIO_COLOR : PRECIO_BN;
        BigDecimal total = precio.multiply(BigDecimal.valueOf(paginas));

        total = total.multiply(factorVolumen(paginas));

        if (dobleCara) {
            total = total.multiply(new BigDecimal("0.90"));
        }

        total = total.multiply(factorUsuario(tipo));
        total = total.setScale(2, RoundingMode.DOWN);

        if (total.compareTo(CARGO_MINIMO) < 0) {
            total = CARGO_MINIMO;
        }
        return total;
    }

    private void validar(TipoUsuario tipo, int paginas) {
        if (tipo == null) {
            throw new IllegalArgumentException("TIPO DE USUARIO REQUERIDO");
        }
        if (paginas < PAGINAS_MIN || paginas >= PAGINAS_MAX) {
            throw new IllegalArgumentException("PAGINAS FUERA DE RANGO: " + paginas);
        }
    }

    /**
     * Factor del descuento por volumen (R3).
     *
     * @param paginas numero de paginas del trabajo
     * @return 1.00 de 1 a 99 paginas, 0.95 de 100 a 299 y 0.88 de 300 a 500
     */
    BigDecimal factorVolumen(int paginas) {
        if (paginas >= 300) {
            return new BigDecimal("0.88");
        }
        if (paginas > 100) {
            return new BigDecimal("0.95");
        }
        return BigDecimal.ONE;
    }

    /**
     * Factor del ajuste por tipo de usuario (R5).
     *
     * @param tipo tipo de usuario
     * @return 0.80 para estudiante, 0.50 para docente y 1.15 para externo
     */
    BigDecimal factorUsuario(TipoUsuario tipo) {
        switch (tipo) {
            case ESTUDIANTE:
                return new BigDecimal("0.80");
            case DOCENTE:
                return new BigDecimal("0.50");
            default:
                return new BigDecimal("1.15");
        }
    }
}
