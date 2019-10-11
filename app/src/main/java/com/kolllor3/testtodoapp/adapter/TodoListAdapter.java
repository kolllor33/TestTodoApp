package com.kolllor3.testtodoapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.kolllor3.testtodoapp.BR;
import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.model.TodoItemViewModel;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoItemViewHolder> {

    private List<TodoItem> todoItems;
    private TodoItemViewModel modelView;
    private int layoutId;

    public TodoListAdapter(TodoItemViewModel modelView, int layoutId) {
        this.modelView = modelView;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false);

        return new TodoItemViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        TodoItem item = todoItems.get(position);
        holder.bind(item, modelView);
    }

    @Override
    public int getItemCount() {
        return todoItems == null ? 0 : todoItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    public void setTodoItems(List<TodoItem> items){
        todoItems = items;
    }

    class TodoItemViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        //this constructor is tho bind the data and the xml together
        TodoItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TodoItem item, TodoItemViewModel model){
            binding.setVariable(BR.item, item);
            binding.setVariable(BR.viewModel, model);
        }
    }
}
