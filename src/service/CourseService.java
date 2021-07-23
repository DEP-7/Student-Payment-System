package service;

import model.Course;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;
import util.FileIO;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private static final File courseDBFile = new File("sps-courses.db");
    public static ArrayList<Course> courseList = new ArrayList();
    private static FileIO fileIO = new FileIO();

    static {
        ArrayList arrayList = fileIO.readDataFromFile(courseList, courseDBFile);
        if (arrayList != null) courseList = arrayList;
    }

    public void addCourse(Course course) throws DuplicateEntryException, FailedOperationException {
        if (getCourse(course.getCourseID()) != null) {
            throw new DuplicateEntryException();
        }
        courseList.add(course);
        if (!fileIO.writeDataToFile(courseList, courseDBFile)) {
            throw new FailedOperationException();
        }
    }

    public void updateCourse(Course courseToUpdate) throws NotFoundException, FailedOperationException {
        Course courseBeforeUpdate = searchCourse(courseToUpdate.getCourseID());
        courseList.set(courseList.indexOf(courseBeforeUpdate), courseToUpdate);
        if (!fileIO.writeDataToFile(courseList, courseDBFile)) {
            throw new FailedOperationException();
        }
    }

    public void deleteCourse(String courseId) throws NotFoundException, FailedOperationException {
        // TODO : Can only delete null courses
        Course courseToDelete = searchCourse(courseId);
        courseList.remove(courseToDelete);
        if (!fileIO.writeDataToFile(courseList, courseDBFile)) {
            throw new FailedOperationException();
        }
    }

    public List<Course> searchAllCourses() {
        return courseList;
    }

    public Course searchCourse(String courseId) throws NotFoundException {
        Course course = getCourse(courseId);

        if (course != null) {
            return course;
        }
        throw new NotFoundException();
    }

    public List<Course> searchCourseByKeyword(String keyword) {
        if (keyword.equals("")) {
            return searchAllCourses();
        }
        keyword = keyword.toLowerCase();
        List<Course> searchResult = new ArrayList();

        for (Course course : courseList) {
            if (course.getCourseID().toLowerCase().contains(keyword) ||
                    course.getCourseName().toLowerCase().contains(keyword) ||
                    Integer.toString(course.getNumberOfInstallments()).contains(keyword) ||
                    Integer.toString(course.getNumberOfStudents()).contains(keyword) ||
                    course.getDuration().contains(keyword) ||
                    course.getFirstInstallment().toLowerCase().contains(keyword) ||
                    course.getInstallmentGap().toLowerCase().contains(keyword) ||
                    course.getMinimumRequirements().toLowerCase().contains(keyword) ||
                    course.getCourseStatus().toLowerCase().contains(keyword) ||
                    course.getCourseInitiationDate().toString().contains(keyword)) {
                searchResult.add(course);
            }
        }
        return searchResult;
    }

    private Course getCourse(String courseId) {
        for (Course course : courseList) {
            if (course.getCourseID().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}
