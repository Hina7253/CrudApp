package com.example.crudapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.example.crudapp.R;
import com.example.crudapp.models.Person;
import com.example.crudapp.repository.PersonRepository;
import com.example.crudapp.utils.ValidationUtils;
import java.util.List;

public class AddEditPersonActivity extends AppCompatActivity {

    private TextInputEditText etName, etAge, etLocation, etOccupation, etQualification, etContact, etEmail, etSkills;
    private Button btnSave;
    private ProgressBar progressBar;

    private PersonRepository personRepository;
    private Person existingPerson;
    private boolean isEditMode = false;
    private int personId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_person);

        initViews();

        personRepository = new PersonRepository(this);

        if (getIntent().hasExtra("person_id")) {
            isEditMode = true;
            personId = getIntent().getIntExtra("person_id", -1);
            loadPersonData();
        }

        btnSave.setOnClickListener(v -> {
            if (validateForm()) {
                if (isEditMode) {
                    updatePerson();
                } else {
                    savePerson();
                }
            }
        });
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etLocation = findViewById(R.id.etLocation);
        etOccupation = findViewById(R.id.etOccupation);
        etQualification = findViewById(R.id.etQualification);
        etContact = findViewById(R.id.etContact);
        etEmail = findViewById(R.id.etEmail);
        etSkills = findViewById(R.id.etSkills);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);
    }

    private void loadPersonData() {
        personRepository.getAllPersons(new PersonRepository.OnPersonsLoadedListener() {
            @Override
            public void onLoaded(List<Person> persons) {
                for (Person person : persons) {
                    if (person.getId() == personId) {
                        existingPerson = person;
                        fillFormWithData();
                        break;
                    }
                }
            }
        });
    }

    private void fillFormWithData() {
        etName.setText(existingPerson.getName());
        etAge.setText(String.valueOf(existingPerson.getAge()));
        etLocation.setText(existingPerson.getLocation());
        etOccupation.setText(existingPerson.getOccupation());
        etQualification.setText(existingPerson.getQualification());
        etContact.setText(existingPerson.getContact());
        etEmail.setText(existingPerson.getEmail());
        etSkills.setText(existingPerson.getSkills());
    }

    private boolean validateForm() {
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String occupation = etOccupation.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();

        if (!ValidationUtils.isValidName(name)) {
            etName.setError("Name is required");
            return false;
        }

        if (!ValidationUtils.isValidAge(ageStr)) {
            etAge.setError("Valid age is required");
            return false;
        }

        if (!ValidationUtils.isNotEmpty(location)) {
            etLocation.setError("Location is required");
            return false;
        }

        if (!ValidationUtils.isNotEmpty(occupation)) {
            etOccupation.setError("Occupation is required");
            return false;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Valid email is required");
            return false;
        }

        if (!ValidationUtils.isValidContact(contact)) {
            etContact.setError("Valid 10-digit contact number is required");
            return false;
        }

        return true;
    }

    private void savePerson() {
        progressBar.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);

        Person person = new Person(
                etName.getText().toString().trim(),
                Integer.parseInt(etAge.getText().toString().trim()),
                etLocation.getText().toString().trim(),
                etOccupation.getText().toString().trim(),
                etQualification.getText().toString().trim(),
                etContact.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etSkills.getText().toString().trim()
        );

        personRepository.insertPerson(person, new PersonRepository.OnPersonActionListener() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
                Toast.makeText(AddEditPersonActivity.this, "Person saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
                Toast.makeText(AddEditPersonActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePerson() {
        progressBar.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);

        existingPerson.setName(etName.getText().toString().trim());
        existingPerson.setAge(Integer.parseInt(etAge.getText().toString().trim()));
        existingPerson.setLocation(etLocation.getText().toString().trim());
        existingPerson.setOccupation(etOccupation.getText().toString().trim());
        existingPerson.setQualification(etQualification.getText().toString().trim());
        existingPerson.setContact(etContact.getText().toString().trim());
        existingPerson.setEmail(etEmail.getText().toString().trim());
        existingPerson.setSkills(etSkills.getText().toString().trim());

        personRepository.updatePerson(existingPerson, new PersonRepository.OnPersonActionListener() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
                Toast.makeText(AddEditPersonActivity.this, "Person updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
                Toast.makeText(AddEditPersonActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}