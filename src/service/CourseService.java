package service;

import model.Course;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    public static ArrayList<Course> courseDB = new ArrayList();

    static {
        courseDB.add(new Course("DEP", "Direct Entry Program", 100000, 2, 30, "4 - Months", "2 - Weeks", "2 - Montshs", true, null, " (A) 3 or 4 years Bachelor Degree in any stream which includes 3 main IT subjects OR,\n" + "\n" + "(B) HND in Computing (or IT related) OR,\n" + "\n" + "(C) Any equivalent or higher education qualification acceptable by the IJSE (The candidate will have to face an interview in order to be selected to the programme)", "Lecturer Name : Mr. Ranjith Suranga\nContact : 071 000 0000", LocalDate.of(2015, 4, 11)));
        courseDB.add(new Course("CMJD", "Comprehensive Master Java Developer", 150000, 3, -1, "12 - Months", "2 - Weeks", "4 - Montshs", true, null, " (A) Being an undergraduate in any stream in private or government university. OR,\n" + "\n" + "(B) Being a IT Professional who needs to get started with Java. OR,\n" + "\n" + "(C) Being a Java Enthusiasts ", "", LocalDate.of(2010, 1, 4)));
        courseDB.add(new Course("GDSE", "Graduate Diploma in Software Engineering", 200000, 4, -1, "24 - Months", "2 - Weeks", "6 - Months", true, null, " (A) 3 Pass Grades at G.C.E. Advanced Level Examination in any stream of study. OR,\n" + "\n" + "(B) 2 Pass Grades for Mathematics and English at G.C.E. Ordinary Level Examination + Pass the Aptitude Test. OR,\n" + "\n" + "(C) Any equivalent or higher qualification acceptable by the IJSE (The candidate will have to face an interview in order to be selected to the programme)", "raduate Diploma in Software Engineering also known as GDSE is the best choice for your Higher Eduction which gives you a rich Academic and Professional experience. GDSE is the most secure and reliable way in Sri Lanka to become a Software Engineer with a Degree and a High-salary.", LocalDate.of(2010, 10, 4)));
    }

    public void addCourse(Course course) throws DuplicateEntryException {
        if (getCourse(course.getCourseID()) != null) {
            throw new DuplicateEntryException();
        }
        courseDB.add(course);
    }

    public void updateCourse(Course courseToUpdate) throws NotFoundException {
        Course courseBeforeUpdate = searchCourse(courseToUpdate.getCourseID());
        courseDB.set(courseDB.indexOf(courseBeforeUpdate), courseToUpdate);
    }

    public void deleteCourse(String courseId) throws NotFoundException {
        // TODO : Can only delete null courses
        Course courseToDelete = searchCourse(courseId);
        courseDB.remove(courseToDelete);
    }

    public List<Course> searchAllCourses() {
        return courseDB;
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

        for (Course course : courseDB) {
            if (course.getCourseID().toLowerCase().contains(keyword) ||
                    course.getCourseName().toLowerCase().contains(keyword) ||
                    Integer.toString(course.getNumberOfInstallments()).contains(keyword) ||
                    Integer.toString(course.getNumberOfStudents()).contains(keyword) ||
                    course.getDuration().contains(keyword) ||
                    course.getFirstInstallment().toLowerCase().contains(keyword) ||
                    course.getInstallmentGap().toLowerCase().contains(keyword) ||
                    course.getMinimumRequirements().toLowerCase().contains(keyword) ||
                    course.getCourseInitiationDate().toString().contains(keyword)) {
                searchResult.add(course);
            }
        }
        return searchResult;
    }

    private Course getCourse(String courseId) {
        for (Course course : courseDB) {
            if (course.getCourseID().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}
