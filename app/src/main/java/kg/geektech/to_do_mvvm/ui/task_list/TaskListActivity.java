package kg.geektech.to_do_mvvm.ui.task_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import kg.geektech.to_do_mvvm.R;
import kg.geektech.to_do_mvvm.repository.RepositoryProvider;
import kg.geektech.to_do_mvvm.room.Task;
import kg.geektech.to_do_mvvm.ui.add_task.AddTaskActivity;

public class TaskListActivity extends AppCompatActivity {

    @BindView(R.id.recycler_taskList)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private TaskListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders
                .of(this)
                .get(TaskListViewModel.class);

        initiate();
    }

    public void initiate() {
        setSupportActionBar(toolbar);

        final RecyclerTaskListAdapter adapter = new RecyclerTaskListAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        viewModel.getAllTasks().observe(this, adapter::setItems);

        fab.setOnClickListener(v -> {
            launchAddTaskActivity(null);
        });

        adapter.setClickListener((v, position) -> {
            launchAddTaskActivity(adapter.getTaskAtPosition(position));
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Task task = adapter.getTaskAtPosition(position);
                viewModel.deleteTask(task);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            viewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchAddTaskActivity(Task task){
        Intent intent = new Intent(this, AddTaskActivity.class);
        if (task != null)
            intent.putExtra("TASK", task);

        startActivity(intent);
    }
}
