package com.example.crudapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.crudapp.R;
import com.example.crudapp.adapters.PersonAdapter;
import com.example.crudapp.models.Person;
import com.example.crudapp.repository.PersonRepository;
import com.example.crudapp.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private FloatingActionButton fabAdd;
    private Button btnLogout;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    private SessionManager sessionManager;
    private PersonRepository personRepository;
    private PersonAdapter personAdapter;
    private List<Person> personList = new ArrayList<>();
    private List<Person> fullPersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        sessionManager = new SessionManager(this);
        personRepository = new PersonRepository(this);

        if (!sessionManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }

        setupRecyclerView();
        loadPersons();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditPersonActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> logout());

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPersons(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        fabAdd = findViewById(R.id.fabAdd);
        btnLogout = findViewById(R.id.btnLogout);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);
    }

    private void setupRecyclerView() {
        personAdapter = new PersonAdapter(personList, new PersonAdapter.OnPersonClickListener() {
            @Override
            public void onEdit(Person person) {
                Intent intent = new Intent(MainActivity.this, AddEditPersonActivity.class);
                intent.putExtra("person_id", person.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Person person) {
                showDeleteConfirmationDialog(person);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(personAdapter);
    }

    private void loadPersons() {
        progressBar.setVisibility(android.view.View.VISIBLE);

        personRepository.getAllPersons(new PersonRepository.OnPersonsLoadedListener() {
            @Override
            public void onLoaded(List<Person> persons) {
                progressBar.setVisibility(android.view.View.GONE);
                personList.clear();
                personList.addAll(persons);
                fullPersonList.clear();
                fullPersonList.addAll(persons);
                personAdapter.notifyDataSetChanged();

                if (personList.isEmpty()) {
                    tvEmpty.setVisibility(android.view.View.VISIBLE);
                } else {
                    tvEmpty.setVisibility(android.view.View.GONE);
                }
            }
        });
    }

    private void searchPersons(String query) {
        if (query.isEmpty()) {
            personList.clear();
            personList.addAll(fullPersonList);
            personAdapter.notifyDataSetChanged();
            return;
        }

        personRepository.searchPersons(query, new PersonRepository.OnPersonsLoadedListener() {
            @Override
            public void onLoaded(List<Person> persons) {
                personList.clear();
                personList.addAll(persons);
                personAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showDeleteConfirmationDialog(Person person) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Person")
                .setMessage("Are you sure you want to delete " + person.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> deletePerson(person))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deletePerson(Person person) {
        progressBar.setVisibility(android.view.View.VISIBLE);

        personRepository.deletePerson(person, new PersonRepository.OnPersonActionListener() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                loadPersons();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        sessionManager.logout();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPersons();
    }
}