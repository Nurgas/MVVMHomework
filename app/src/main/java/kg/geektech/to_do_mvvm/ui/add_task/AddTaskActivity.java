package kg.geektech.to_do_mvvm.ui.add_task;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import kg.geektech.to_do_mvvm.R;
import kg.geektech.to_do_mvvm.room.Task;

public class AddTaskActivity extends AppCompatActivity {

    @BindView(R.id.editText_title)
    EditText editText_title;

    @BindView(R.id.editText_description)
    EditText editText_description;

    @BindView(R.id.toolbar_add)
    Toolbar toolbar;

    @BindView(R.id.btn_save)
    Button btn_save;

    private AddTaskViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this)
                .get(AddTaskViewModel.class);

        initiate();
    }

    private void initiate() {

        editText_title = findViewById(R.id.editText_title);
        editText_description = findViewById(R.id.editText_description);

        Task task = (Task) getIntent().getSerializableExtra("TASK");
        editText_title.setText(task.getTitle());
        editText_description.setText(task.getDescription());
        viewModel.setTask(task);


        viewModel.getToolbarTitle().observe(this, s -> {
            toolbar.setTitle(s);
            setSupportActionBar(toolbar);
        });

        btn_save.setOnClickListener(v -> {
            String title = editText_title.getText().toString();
            String description = editText_description.getText().toString();
            viewModel.clickSave(title, description);
        });

        viewModel.getIsClose().observe(this, isClose -> {
            if (isClose) {
                finish();
            }
        });

        viewModel.getToastText().observe(this, s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
    }
}
