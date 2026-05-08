package com.example.crudapp.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.crudapp.databinding.ActivityAddEditPersonBinding;
import com.example.crudapp.models.Person;
import com.example.crudapp.repository.PersonRepository;
import com.example.crudapp.utils.ValidationUtils;

public class AddEditPersonActivity extends AppCompatActivity {
    private ActivityAddEditPersonBinding binding;
    private PersonRepository personRepository;
    private Person existingPerson;
    private boolean isEditMode = false;
    private int personId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personRepository = new PersonRepository(this);

        // Check if editing existing person
        if (getIntent().hasExtra("person_id")) {
            isEditMode = true;
            personId = getIntent().getIntExtra("person_id", -1);
            binding.title.setText("Edit Person");
            loadPersonData();
        }

        binding.btnSave.setOnClickListener(v -> {
            if (validateForm()) {
                if (isEditMode) {
                    updatePerson();
                } else {
                    savePerson();
                }
            }
        });
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
        binding.etName.setText(existingPerson.getName());
        binding.etAge.setText(String.valueOf(existingPerson.getAge()));
        binding.etLocation.setText(existingPerson.getLocation());
        binding.etOccupation.setText(existingPerson.getOccupation());
        binding.etQualification.setText(existingPerson.getQualification());
        binding.etContact.setText(existingPerson.getContact());
        binding.etEmail.setText(existingPerson.getEmail());
        binding.etSkills.setText(existingPerson.getSkills());
    }

    private boolean validateForm() {
        if (!ValidationUtils.isValidName(binding.etName.getText().toString().trim())) {
            binding.etName.setError("Name is required");
            return false;
        }

        if (!ValidationUtils.isValidAge(binding.etAge.getText().toString().trim())) {
            binding.etAge.setError("Valid age is required");
            return false;
        }

        if (!ValidationUtils.isNotEmpty(binding.etLocation.getText().toString().trim())) {
            binding.etLocation.setError("Location is required");
            return false;
        }

        if (!ValidationUtils.isNotEmpty(binding.etOccupation.getText().toString().trim())) {
            binding.etOccupation.setError("Occupation is required");
            return false;
        }

        if (!ValidationUtils.isValidEmail(binding.etEmail.getText().toString().trim())) {
            binding.etEmail.setError("Valid email is required");
            return false;
        }

        if (!ValidationUtils.isValidContact(binding.etContact.getText().toString().trim())) {
            binding.etContact.setError("Valid 10-digit contact number is required");
            return false;
        }

        return true;
    }

    private void savePerson() {
        binding.progressBar.setVisibility(android.view.View.VISIBLE);

        Person person = new Person(
                binding.etName.getText().toString().trim(),
                Integer.parseInt(binding.etAge.getText().toString().trim()),
                binding.etLocation.getText().toString().trim(),
                binding.etOccupation.getText().toString().trim(),
                binding.etQualification.getText().toString().trim(),
                binding.etContact.getText().toString().trim(),
                binding.etEmail.getText().toString().trim(),
                binding.etSkills.getText().toString().trim()
        );

        personRepository.insertPerson(person, new PersonRepository.OnPersonActionListener() {
            @Override
            public void onSuccess() {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(AddEditPersonActivity.this, "Person saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(AddEditPersonActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePerson() {
        binding.progressBar.setVisibility(android.view.View.VISIBLE);

        existingPerson.setName(binding.etName.getText().toString().trim());
        existingPerson.setAge(Integer.parseInt(binding.etAge.getText().toString().trim()));
        existingPerson.setLocation(binding.etLocation.getText().toString().trim());
        existingPerson.setOccupation(binding.etOccupation.getText().toString().trim());
        existingPerson.setQualification(binding.etQualification.getText().toString().trim());
        existingPerson.setContact(binding.etContact.getText().toString().trim());
        existingPerson.setEmail(binding.etEmail.getText().toString().trim());
        existingPerson.setSkills(binding.etSkills.getText().toString().trim());

        personRepository.updatePerson(existingPerson, new PersonRepository.OnPersonActionListener() {
            @Override
            public void onSuccess() {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(AddEditPersonActivity.this, "Person updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(AddEditPersonActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}