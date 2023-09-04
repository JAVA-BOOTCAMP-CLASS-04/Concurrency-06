import java.time.Duration;
import java.time.Instant;

public class ThreadRunnable {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) {

        /*
            La tarea va a ejecutar en un hilo aparte y simulamos que demora 5 segundos
         */
        Runnable tarea = () -> {
            try {
                log("Empieza la tarea");
                Thread.sleep(5000);
                log("Termina la tarea");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread hilo = new Thread(tarea);

        hilo.start();

        /*
            Mientras la tarea se ejecuta en un hilo aparte, el hilo principal (main) esperara 3 segundos por si la tarea termina
         */
        try {
            log("Se empieza a esperar al hilo");
            hilo.join(3000);
            log("Se termina de esperar al hilo");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void log(Object mensaje) {
        System.out.printf("%s [%s] %s%n",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString());
    }
}
