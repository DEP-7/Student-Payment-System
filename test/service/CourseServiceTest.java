package service;

import model.Course;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CourseServiceTest {

    static CourseService courseService = new CourseService();

    public static void main(String[] args) throws DuplicateEntryException, NotFoundException {
        addCourse();
        updateCourse();
        deleteCourse();
        searchCoursesByKeyword();
    }

    public static void addCourse() throws DuplicateEntryException, NotFoundException {
        Course course1 = new Course("DEP1", "Direct Entry Program", 100000, 2, 30, "4 Months", "2 Weeks", "2 Montshs", true, null, " (A) 3 or 4 years Bachelor Degree in any stream which includes 3 main IT subjects OR,\n" + "\n" + "(B) HND in Computing (or IT related) OR,\n" + "\n" + "(C) Any equivalent or higher education qualification acceptable by the IJSE (The candidate will have to face an interview in order to be selected to the programme)", "Lecturer Name : Mr. Ranjith Suranga\nContact : 071 000 0000", LocalDate.of(2015, 4, 11));
        Course course2 = new  Course("CMJD1", "Comprehensive Master Java Developer", 150000, 3, -1, "12 Months", "2 Weeks", "4 Montshs", true, null, " (A) Being an undergraduate in any stream in private or government university. OR,\n" + "\n" + "(B) Being a IT Professional who needs to get started with Java. OR,\n" + "\n" + "(C) Being a Java Enthusiasts ", "", LocalDate.of(2010, 1, 4));
        Course course3 = new Course("GDSE1", "Graduate Diploma in Software Engineering", 200000, 4, -1, "24 Months", "2 Weeks", "6 Montshs", true, null, " (A) 3 Pass Grades at G.C.E. Advanced Level Examination in any stream of study. OR,\n" + "\n" + "(B) 2 Pass Grades for Mathematics and English at G.C.E. Ordinary Level Examination + Pass the Aptitude Test. OR,\n" + "\n" + "(C) Any equivalent or higher qualification acceptable by the IJSE (The candidate will have to face an interview in order to be selected to the programme)", "raduate Diploma in Software Engineering also known as GDSE is the best choice for your Higher Eduction which gives you a rich Academic and Professional experience. GDSE is the most secure and reliable way in Sri Lanka to become a Software Engineer with a Degree and a High-salary.", LocalDate.of(2010, 10, 4));

        courseService.addCourse(course1);
        courseService.addCourse(course2);
        courseService.addCourse(course3);
        assert courseService.searchCourse("DEP1") != null : "Add Course Test Failed";
        assert courseService.searchCourse("CMJD1") != null : "Add Course Test Failed";
    }

    public static void updateCourse() throws NotFoundException {
        Course course1 = new Course("DEP1", "DEP", 100000, 2, 30, "4 Months", "2 Weeks", "2 Montshs", true, null, " (A) 3 or 4 years Bachelor Degree in any stream which includes 3 main IT subjects OR,\n" + "\n" + "(B) HND in Computing (or IT related) OR,\n" + "\n" + "(C) Any equivalent or higher education qualification acceptable by the IJSE (The candidate will have to face an interview in order to be selected to the programme)", "Lecturer Name : Mr. Ranjith Suranga\nContact : 071 000 0000", LocalDate.of(2015, 4, 11));
        courseService.updateCourse(course1);
        assert !courseService.searchCourse("DEP1").getCourseName().equals("Direct Entry Program") :"Update Course Test Failed - 1";
        assert courseService.searchCourse("DEP1").getCourseName().equals("DEP") : "Update Course Test Failed 2";
    }

    public static void deleteCourse() throws NotFoundException {
        courseService.deleteCourse("DEP1");
        //assert !courseService.searchCourse("DE1P").getCourseName().equals("DEP") :"Update Course Test Failed";
    }

    public static void searchCoursesByKeyword() {
        List<Course> results = courseService.searchCourseByKeyword("Comprehensive");
        //assert results.size() == 1 : "Update Course Test Failed";
        results = courseService.searchCourseByKeyword("2 Weeks");
        //assert results.size() == 2 : "Update Course Test Failed";
    }
}
