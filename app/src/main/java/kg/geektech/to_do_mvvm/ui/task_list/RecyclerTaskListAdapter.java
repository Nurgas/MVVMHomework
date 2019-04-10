package kg.geektech.to_do_mvvm.ui.task_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import kg.geektech.to_do_mvvm.R;
import kg.geektech.to_do_mvvm.room.Task;

public class RecyclerTaskListAdapter extends RecyclerView.Adapter<RecyclerTaskListAdapter.ViewHolder> {

    private List<Task> tasks;
    private static ClickListener clickListener;

    public RecyclerTaskListAdapter() {

        this.tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new RecyclerTaskListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task taskModel = tasks.get(position);
        holder.title.setText(taskModel.getTitle());
        holder.description.setText(taskModel.getDescription());
    }

    @Override
    public int getItemCount() {
        if (tasks != null)
            return tasks.size();
        else
            return 0;
    }

    public void setItems(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }

    Task getTaskAtPosition(int position) {

        return tasks.get(position);
    }

    public void setClickListener(ClickListener clickListener) {
        RecyclerTaskListAdapter.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_title)
        TextView title;
        @BindView(R.id.textView_description)
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view ->
                    clickListener.onItemClick(view,
                            ViewHolder.this.getAdapterPosition())
            );
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
