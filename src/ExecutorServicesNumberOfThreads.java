import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ExecutorServicesNumberOfThreads {

    public static void main(String[] args) throws InterruptedException {

        log(Runtime.getRuntime().availableProcessors()); // Cantidad de procesadores con que cuento

        log(ForkJoinPool.commonPool().getParallelism()); // Cantidad de procesos simultaneos que podra realizar el pool

        List<ExecutorService> executorServices = Arrays.asList(
                Executors.newCachedThreadPool(), // pool variable, crea tantos hilos como sea necesario, reutiliza los que ya
                                                 // estan en el pool y va destruyendo los que pasaron el umbral de inactividad
                Executors.newFixedThreadPool(3),  // pool de tamaño fijo seteado por el usuario
                Executors.newSingleThreadExecutor(), // la opcion anterior pero seteado a 1
                Executors.newWorkStealingPool(),  // se basa en el forkjoinpool, crea un pool basado en la cantidad de procesadores disponibles
                Executors.newScheduledThreadPool(5), // pool de tamaño fijo pero para hilos scheduled
                ForkJoinPool.commonPool());  // utiliza un forkjoinpool con (procesadores disponibles - 1)

        List<Callable<Object>> tareas =
                Stream.generate(ExecutorServicesNumberOfThreads::getTareaSleepUnSegundo)
                        .limit(40)
                        .toList();

        for(ExecutorService executorService: executorServices) {
            Instant inicio = Instant.now();
            executorService.invokeAll(tareas);
            log(Duration.between(inicio, Instant.now()));
        }

        executorServices.forEach(ExecutorService::shutdown);
    }

    private static Callable<Object> getTareaSleepUnSegundo() {
        return Executors.callable(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void log(Object mensaje) {
        System.out.printf("%s%n", mensaje.toString());
    }
}
