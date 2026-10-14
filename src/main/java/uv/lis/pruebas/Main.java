package uv.lis.pruebas;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Interfaz de linea de comandos del cotizador de impresiones.
 *
 * <p>Uso con argumentos:</p>
 * <pre>java -jar ImpresionCC.jar TIPO PAGINAS COLOR DOBLE_CARA</pre>
 * <p>Ejemplo: {@code java -jar ImpresionCC.jar ESTUDIANTE 120 si no}</p>
 * <p>Sin argumentos, el programa pregunta cada dato.</p>
 */
public class Main {

    /**
     * Punto de entrada.
     *
     * @param args tipo de usuario, paginas, color (si/no) y doble cara (si/no)
     */
    public static void main(String[] args) {
        try {
            if (args.length == 4) {
                mostrar(cotizar(args[0], args[1], args[2], args[3]));
            } else if (args.length == 0) {
                interactivo();
            } else {
                uso();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Error: ENTRADA INCOMPLETA");
        }
    }

    private static void interactivo() {
        Scanner in = new Scanner(System.in);
        System.out.println("Cotizador de impresiones del Centro de Computo");
        System.out.print("Tipo de usuario (ESTUDIANTE, DOCENTE, EXTERNO): ");
        String tipo = in.nextLine();
        System.out.print("Numero de paginas: ");
        String paginas = in.nextLine();
        System.out.print("A color (si/no): ");
        String color = in.nextLine();
        System.out.print("Doble cara (si/no): ");
        String doble = in.nextLine();
        mostrar(cotizar(tipo, paginas, color, doble));
    }

    private static BigDecimal cotizar(String tipo, String paginas, String color, String doble) {
        TipoUsuario t;
        try {
            t = TipoUsuario.valueOf(tipo.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("TIPO DE USUARIO NO VALIDO: " + tipo);
        }
        int p;
        try {
            p = Integer.parseInt(paginas.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("PAGINAS NO NUMERICAS: " + paginas);
        }
        return new CotizadorImpresion().cotizar(t, p, siNo(color), siNo(doble));
    }

    private static boolean siNo(String valor) {
        String v = valor.trim().toLowerCase();
        if (v.equals("si")) {
            return true;
        }
        if (v.equals("no")) {
            return false;
        }
        throw new IllegalArgumentException("SE ESPERABA si O no: " + valor);
    }

    private static void mostrar(BigDecimal total) {
        System.out.println("Total a pagar: $" + total.toPlainString());
    }

    private static void uso() {
        System.out.println("Uso: java -jar ImpresionCC.jar TIPO PAGINAS COLOR DOBLE_CARA");
        System.out.println("Ejemplo: java -jar ImpresionCC.jar ESTUDIANTE 120 si no");
        System.out.println("Sin argumentos, el programa pregunta cada dato.");
    }
}
