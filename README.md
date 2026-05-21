# CRUD App

A simple Android CRUD Application built using Java in Android Studio.  
This app includes Login and Register functionality using real API calls and performs CRUD operations using Room Database for local storage.

---

## Features

- User Registration using API
- User Login using API
- Create Data
- Read Data
- Update Data
- Delete Data
- Room Database Integration
- RecyclerView Implementation
- Simple and Clean UI

---

## Tech Stack

- Java
- Android Studio
- Retrofit
- Room Database
- RecyclerView
- REST API

---

## Project Structure

- Authentication Module
  - Login
  - Register
  - API Integration

- CRUD Module
  - Add Data
  - View Data
  - Update Data
  - Delete Data

- Database Module
  - Room Database
  - DAO
  - Entity

---

## App Flow

1. User registers using API
2. User logs in using API
3. After successful login, Main Activity opens
4. User can perform CRUD operations
5. Data is stored locally using Room Database

---

## Libraries Used

```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation "androidx.room:room-runtime:2.5.2"
annotationProcessor "androidx.room:room-compiler:2.5.2"


