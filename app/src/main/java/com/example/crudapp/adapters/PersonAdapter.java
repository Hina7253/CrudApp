package com.example.crudapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.crudapp.R;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
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
        private TextView tvName, tvOccupation, tvLocation, tvSkills;
        private Button btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvOccupation = itemView.findViewById(R.id.tvOccupation);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvSkills = itemView.findViewById(R.id.tvSkills);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(Person person) {
            tvName.setText(person.getName());
            tvOccupation.setText("Occupation: " + person.getOccupation());
            tvLocation.setText("Location: " + person.getLocation());
            tvSkills.setText("Skills: " + person.getSkills());

            btnEdit.setOnClickListener(v -> listener.onEdit(person));
            btnDelete.setOnClickListener(v -> listener.onDelete(person));
        }
    }
}