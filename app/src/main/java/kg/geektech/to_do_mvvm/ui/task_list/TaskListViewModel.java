package kg.geektech.to_do_mvvm.ui.task_list;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import kg.geektech.to_do_mvvm.repository.RepositoryProvider;
import kg.geektech.to_do_mvvm.room.Task;

public class TaskListViewModel extends ViewModel {

    private LiveData<List<Task>> allTasks;

    public TaskListViewModel() {
        this.allTasks = RepositoryProvider
                .provideTaskRepository()
                .getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {

        return allTasks;
    }

    public void deleteAll() {
        RepositoryProvider
                .provideTaskRepository()
                .deleteAll();
    }

    public void deleteTask(Task task) {
        RepositoryProvider
                .provideTaskRepository()
                .deleteTask(task);
    }
}
