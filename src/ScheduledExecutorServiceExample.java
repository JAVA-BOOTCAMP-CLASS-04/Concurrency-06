import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        Runnable tareaA = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Ejecución de tarea A");
        };

        Runnable tareaB = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Ejecución de tarea B");
        };

        Runnable tareaC = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Ejecución de tarea C");
        };

        // Iniciar con un especifico delay
        scheduledExecutor.schedule(tareaA, 3, TimeUnit.SECONDS);

        // Ejecutar con un rate fijo
        scheduledExecutor.scheduleAtFixedRate(tareaB, 2, 1, TimeUnit.SECONDS);

        // Ejecutar con un delay fijo
        scheduledExecutor.scheduleWithFixedDelay(tareaC, 2, 1, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduledExecutor.shutdown();
    }

    private static void log(Object mensaje) {
        System.out.printf("%s [%s] %s%n",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString());
    }
}
