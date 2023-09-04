import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceCallable {

    private static final Instant INICIO = Instant.now();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> tarea = () -> {
            log("Inicio de la tarea");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Finaliza la tarea");
            return "Resultado de la tarea";
        };

        Future<String> future = executor.submit(tarea);

        log(future.isDone());
        String resultado = future.get();  // IMPORTANTE: invocando a get() estamos bloqueando el hilo principal hasta que la tarea termine
        log(future.isDone());

        log(resultado);
        executor.shutdown();
    }

    private static void log(Object mensaje) {
        System.out.printf("%s [%s] %s%n",
                Duration.between(INICIO, Instant.now()), Thread.currentThread().getName(), mensaje.toString());
    }
}
