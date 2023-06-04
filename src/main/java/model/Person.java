package model;


import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Person extends model.ObjectPlus implements Serializable {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String gender;
    private String nationality;

    public Person(String name,String surname, LocalDate birthDate, String nationality, String gender){
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setGender(gender);
        setNationality(nationality);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }


    public void setName(String name){
        if (name == null || name.trim().isBlank()){
            throw new ValidationException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.trim().isBlank()){
            throw new ValidationException("Surname cannot be empty");
        }
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null){
            throw new ValidationException("Birth date cannot be empty");
        }
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        if (gender == null){
            throw new ValidationException("Gender cannot be empty");
        }
        this.gender = gender;
    }

    public void setNationality(String nationality) {
        if (nationality == null){
            throw new ValidationException("Nationality cannot be empty");
        }
        this.nationality = nationality;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }

}
