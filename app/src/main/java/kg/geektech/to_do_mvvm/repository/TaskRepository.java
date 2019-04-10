package kg.geektech.to_do_mvvm.repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import kg.geektech.to_do_mvvm.room.Task;

public interface TaskRepository {

    LiveData<List<Task>> getAllTasks();

    void insert(Task task);

    void deleteTask(Task task);

    void deleteAll();

    void update(Task task);
}
