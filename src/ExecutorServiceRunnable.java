import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceRunnable {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable tarea = () -> {
            log("Inicio de la tarea");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Finaliza la tarea");
        };

        executor.submit(tarea);
        executor.shutdown(); // IMPORTANTE: apaguemos el executor luego de utilizarlo, sino quedará consumiendo recursos
    }

    private static void log(Object mensaje) {
        System.out.printf("%s [%s] %s%n",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString());
    }
}