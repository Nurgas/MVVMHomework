package kg.geektech.to_do_mvvm.ui.add_task;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import kg.geektech.to_do_mvvm.repository.RepositoryProvider;
import kg.geektech.to_do_mvvm.room.Task;

public class AddTaskViewModel extends ViewModel {

    private boolean isNewTask;

    private MutableLiveData<Task> TASK = new MutableLiveData<>();
    private MutableLiveData<String> toolbarTitle = new MutableLiveData<>();
    private MutableLiveData<String> toastText = new MutableLiveData<>();
    private MutableLiveData<Boolean> isClose = new MutableLiveData<>();

    public AddTaskViewModel() {

        isClose.setValue(false);
    }

    public void setTask(Task task) {
        if (task != null) {
            isNewTask = false;
            TASK.postValue(task);
            toolbarTitle.setValue("Edit Task");
        } else {
            isNewTask = true;
            TASK.postValue(new Task());
            toolbarTitle.setValue("Add new Task");
        }
    }

    public MutableLiveData<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public MutableLiveData<String> getToastText() {
        return toastText;
    }

    public MutableLiveData<Boolean> getIsClose() {

        return isClose;
    }

    public void clickSave(String title, String description) {
        if (checkTask(title, description)) {
            if (isNewTask) {
                RepositoryProvider
                        .provideTaskRepository()
                        .insert(new Task(title, description));
            } else {
                Task task = TASK.getValue();
                task.setTitle(title);
                task.setDescription(description);

                RepositoryProvider
                        .provideTaskRepository()
                        .update(task);
            }
            isClose.setValue(true);

        } else {
            toastText.setValue("Please, fill all the fields");
        }
    }

    public boolean checkTask(String title, String description) {
        if (title.isEmpty())
            return false;

        if (description.isEmpty())
            return false;

        return true;
    }
}
