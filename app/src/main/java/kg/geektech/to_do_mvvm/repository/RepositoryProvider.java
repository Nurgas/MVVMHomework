package kg.geektech.to_do_mvvm.repository;

public class RepositoryProvider {

    private static TaskRepositoryImpl taskRepository;

    public RepositoryProvider() {
    }

    public static TaskRepositoryImpl provideTaskRepository() {
        if (taskRepository == null)
            taskRepository = new TaskRepositoryImpl();

        return taskRepository;
    }
}
