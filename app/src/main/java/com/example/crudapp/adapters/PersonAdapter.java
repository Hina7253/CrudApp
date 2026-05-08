package com.example.crudapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.crudapp.databinding.ItemPersonBinding;
import com.example.crudapp.models.Person;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person> personList;
    private OnPersonClickListener listener;

    public interface OnPersonClickListener {
        void onEdit(Person person);
        void onDelete(Person person);
    }

    public PersonAdapter(List<Person> personList, OnPersonClickListener listener) {
        this.personList = personList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPersonBinding binding = ItemPersonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.bind(person);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPersonBinding binding;

        ViewHolder(ItemPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Person person) {
            binding.tvName.setText(person.getName());
            binding.tvOccupation.setText("Occupation: " + person.getOccupation());
            binding.tvLocation.setText("Location: " + person.getLocation());
            binding.tvSkills.setText("Skills: " + person.getSkills());

            binding.btnEdit.setOnClickListener(v -> listener.onEdit(person));
            binding.btnDelete.setOnClickListener(v -> listener.onDelete(person));
        }
    }
}