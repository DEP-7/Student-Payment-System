package service;

import model.Student;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    public static ArrayList<Student> studentDB = new ArrayList();

    static {
        studentDB.add(new Student("931630377V", "Magam Mudalige Dhanushka Chandimal Ranasinghe", "M.M.D.C.Ranasinghe", "Male", LocalDate.of(1993, 6, 11), null, "A/L passed", "Handiya Kade, Deeyagaha, Matara", "0716520080", "dhanushkachandimal11@gmail.com", "DEP", 7, new BigDecimal("0")));
        studentDB.add(new Student("931630377V", "Magam Mudalige Dhanushka Chandimal Ranasinghe", "M.M.D.C.Ranasinghe", "Male", LocalDate.of(1993, 6, 11), null, "A/L passed", "Handiya Kade, Deeyagaha, Matara", "0716520080", "dhanushkachandimal11@gmail.com", "DEP", 7, new BigDecimal("0")));
    }

    public void addStudent(Student student) throws DuplicateEntryException {
        if (getStudent(student.getNic()) != null) {
            throw new DuplicateEntryException();
        }
        studentDB.add(student);
    }

    public void updateStudent(Student studentToUpdate, String previousNIC) throws NotFoundException {
        Student studentBeforeUpdate = searchStudent(studentToUpdate.getNic());
        studentDB.set(studentDB.indexOf(studentBeforeUpdate), studentToUpdate);
    }

    public void deleteStudent(String nic) throws NotFoundException {
        // TODO : Students without any single payments can delete. So check payment details before delete
        Student studentToDelete = searchStudent(nic);
        studentDB.remove(studentToDelete);
    }

    public List<Student> searchAllStudents() {
        return studentDB;
    }

    public Student searchStudent(String nic) throws NotFoundException {
        Student student = getStudent(nic);

        if (student != null) {
            return student;
        }
        throw new NotFoundException();
    }

    public List<Student> searchStudentsByKeyword(String keyword) {
        if (keyword.equals("")) {
            return searchAllStudents();
        }
        keyword = keyword.toLowerCase();
        List<Student> searchResult = new ArrayList();

        for (Student student : studentDB) {
            if (student.getNic().toLowerCase().contains(keyword) ||
                    student.getNameInFull().toLowerCase().contains(keyword) ||
                    student.getNameWithInitials().toLowerCase().contains(keyword) ||
                    student.getGender().toLowerCase().contains(keyword) ||
                    student.getDateOfBirth().toString().contains(keyword) ||
                    student.getEduQualification().toLowerCase().contains(keyword) ||
                    student.getAddress().toLowerCase().contains(keyword) ||
                    student.getContactNumber().contains(keyword) ||
                    student.getEmail().toLowerCase().contains(keyword) ||
                    student.getCourseId().toLowerCase().contains(keyword) ||
                    Integer.toString(student.getBatchNumber()).contains(keyword)) {
                searchResult.add(student);
            }
        }
        return searchResult;
    }

    private Student getStudent(String nic) {
        for (Student student : studentDB) {
            if (student.getNic().equals(nic)) {
                return student;
            }
        }
        return null;
    }
}
