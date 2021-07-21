package service;

import model.Student;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private static final File studentDBFile = new File("sps-students.db");
    public static ArrayList<Student> studentList = new ArrayList();

    static {
        readDataFromFile();
    }

    public void addStudent(Student student) throws DuplicateEntryException, FailedOperationException {
        if (getStudent(student.getNic()) != null) {
            throw new DuplicateEntryException();
        }
        studentList.add(student);
        if (!writeDataToFile()) {
            throw new FailedOperationException();
        }
    }

    public void updateStudent(Student studentToUpdate, String previousNIC) throws NotFoundException, FailedOperationException {
        Student studentBeforeUpdate = searchStudent(previousNIC);
        studentList.set(studentList.indexOf(studentBeforeUpdate), studentToUpdate);
        if (!writeDataToFile()) {
            throw new FailedOperationException();
        }
    }

    public void deleteStudent(String nic) throws NotFoundException, FailedOperationException {
        // TODO : Students without any single payments can delete. So check payment details before delete
        Student studentToDelete = searchStudent(nic);
        studentList.remove(studentToDelete);
        if (!writeDataToFile()) {
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

    private boolean writeDataToFile(){
        try (FileOutputStream fos = new FileOutputStream(studentDBFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(studentList);
            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void readDataFromFile() {

        if (!studentDBFile.exists()) return;

        try (FileInputStream fis = new FileInputStream(studentDBFile);
             ObjectInputStream oos = new ObjectInputStream(fis)) {

            studentList = (ArrayList<Student>) oos.readObject();

        } catch (IOException | ClassNotFoundException e) {

            if (e instanceof EOFException) {
                studentDBFile.delete();
            }else {
                e.printStackTrace();
            }
        }
    }
}
