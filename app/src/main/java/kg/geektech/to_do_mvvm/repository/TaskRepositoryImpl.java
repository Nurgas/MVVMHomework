package kg.geektech.to_do_mvvm.repository;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import kg.geektech.to_do_mvvm.AppDelegate;
import kg.geektech.to_do_mvvm.room.AppDatabase;
import kg.geektech.to_do_mvvm.room.Task;
import kg.geektech.to_do_mvvm.room.TaskDao;


public class TaskRepositoryImpl implements TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepositoryImpl() {
        AppDatabase db = AppDatabase.getDataBase(AppDelegate.getAppContext());
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    @Override
    public LiveData<List<Task>> getAllTasks() {

        return allTasks;
    }

    @Override
    public void insert(Task task) {

        new insertAsyncTask(taskDao).execute(task);
    }

    @Override
    public void deleteTask(Task task) {
        new deleteAsyncTask(taskDao).execute(task);

    }

    @Override
    public void deleteAll() {
        new deleteAllAsyncTask(taskDao).execute();


    }

    @Override
    public void update(Task task) {
        new updateAsyncTask(taskDao).execute(task);

    }

    
    private static class insertAsyncTask extends android.os.AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public insertAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends android.os.AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public deleteAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends android.os.AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public deleteAllAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteAll();
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public updateAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }
}
