package service;

import model.Student;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;
import util.FileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private static final File studentDBFile = new File("sps-students.db");
    public static ArrayList<Student> studentList = new ArrayList();
    private static FileIO fileIO = new FileIO();

    static {
        ArrayList arrayList = fileIO.readDataFromFile(studentList, studentDBFile);
        if (arrayList != null) studentList = arrayList;
    }

    public void addStudent(Student student) throws DuplicateEntryException, FailedOperationException {
        if (getStudent(student.getNic()) != null) {
            throw new DuplicateEntryException();
        }
        studentList.add(student);
        if (!fileIO.writeDataToFile(studentList, studentDBFile)) {
            studentList.remove(student);
            throw new FailedOperationException();
        }
    }

    public void updateStudent(Student studentToUpdate, String previousNIC) throws NotFoundException, FailedOperationException {
        Student studentBeforeUpdate = searchStudent(previousNIC);
        studentList.set(studentList.indexOf(studentBeforeUpdate), studentToUpdate);
        if (!fileIO.writeDataToFile(studentList, studentDBFile)) {
            studentList.set(studentList.indexOf(studentToUpdate), studentBeforeUpdate);
            throw new FailedOperationException();
        }
    }

    public void deleteStudent(String nic) throws NotFoundException, FailedOperationException {
        // TODO : Students without any single payments can delete. So check payment details before delete
        Student studentToDelete = searchStudent(nic);
        studentList.remove(studentToDelete);
        if (!fileIO.writeDataToFile(studentList, studentDBFile)) {
            studentList.add(studentToDelete);
            throw new FailedOperationException();
        }
    }

    public List<Student> searchAllStudents() {
        return studentList;
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

        for (Student student : studentList) {
            if (student.getNic().toLowerCase().contains(keyword) ||
                    student.getNameInFull().toLowerCase().contains(keyword) ||
                    student.getNameWithInitials().toLowerCase().contains(keyword) ||
                    student.getGender().toLowerCase().contains(keyword) ||
                    student.getDateOfBirth().toString().contains(keyword) ||
                    student.getEduQualification().toLowerCase().contains(keyword) ||
                    student.getAddress().toLowerCase().contains(keyword) ||
                    student.getContactNumber().contains(keyword) ||
                    student.getEmail().toLowerCase().contains(keyword) ||
                    student.getCourse().getCourseID().toLowerCase().contains(keyword) ||
                    Integer.toString(student.getBatchNumber()).contains(keyword)) {
                searchResult.add(student);
            }
        }
        return searchResult;
    }

    private Student getStudent(String nic) {
        for (Student student : studentList) {
            if (student.getNic().equals(nic)) {
                return student;
            }
        }
        return null;
    }
}
