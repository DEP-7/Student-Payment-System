package service;

import model.Student;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class StudentServiceTest {
    static StudentService studentService = new StudentService();

    public static void main(String[] args) throws DuplicateEntryException, NotFoundException, FailedOperationException {
        addStudent();
        updateStudent();
        deleteStudent();
        searchStudentsByKeyword();
    }

    public static void addStudent() throws DuplicateEntryException, NotFoundException, FailedOperationException {
        Student student1 = new Student("931630378V", "Dhanushka Chandimal", "M.M.D.C.Ranasinghe", "Male", LocalDate.of(1993, 6, 11), null, "A/L passed", "Handiya Kade, Deeyagaha, Matara", "0716520080", "dhanushkachandimal11@gmail.com", CourseService.courseDB.get(2), 7, new BigDecimal("20"));
        Student student2 = new Student("851478598V", "Manoj Randeni", "M. Randeni", "Male", LocalDate.of(1994, 5, 20), null, "B.Sc", "Kurunegala", "0772569875", "manoj@gmail.com", CourseService.courseDB.get(0), 2, new BigDecimal("0"));
        Student student3 = new Student("901630377V", "Prasad Viduranga", "P. Viduranga", "Male", LocalDate.of(1993, 10, 25), null, "B.Sc", "Galle", "0782583697", "prasad@gmail.com", CourseService.courseDB.get(0), 7, new BigDecimal("0"));
        studentService.addStudent(student1);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        assert studentService.searchStudent("931630378V") != null : "Add Student Test Failed";
        assert studentService.searchStudent("851478598V") != null : "Add Student Test Failed";
    }

    public static void updateStudent() throws NotFoundException, FailedOperationException {
        Student student1 = new Student("931630378V", "Chandimal", "M.M.D.C.Ranasinghe", "Male", LocalDate.of(1993, 6, 11), null, "A/L passed", "Handiya Kade, Deeyagaha, Matara", "0716520080", "dhanushkachandimal11@gmail.com", CourseService.courseDB.get(2), 7, new BigDecimal("20"));
        studentService.updateStudent(student1,student1.getNic());
        //assert studentService.searchStudent("931630378V").getNameInFull().equals("Dhanushka Chandimal") :"Update Student Test Failed";
        assert studentService.searchStudent("931630378V").getNameInFull().equals("Chandimal") : "Update Student Test Failed";
    }

    public static void deleteStudent() throws NotFoundException, FailedOperationException {
        studentService.deleteStudent("931630378V");
        //assert studentService.searchStudent("931630378V").getNameInFull().equals("Chandimal") :"Update Student Test Failed";
    }

    public static void searchStudentsByKeyword() {
        List<Student> results = studentService.searchStudentsByKeyword("Male");
        assert results.size() == 2 : "Update Student Test Failed";
        results = studentService.searchStudentsByKeyword("Kurune");
        assert results.size() == 1 : "Update Student Test Failed";
    }
}
