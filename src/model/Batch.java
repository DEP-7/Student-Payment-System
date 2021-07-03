package model;

import javafx.scene.control.Alert;
import service.CourseServiceRedisImpl;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Batch {
    private int batchNumber;
    private Course course;
    private LocalDate startedDate;
    private LocalDate endDate;
    private String notes;

    public Batch() {
    }

    public Batch(int batchNumber, Course course, LocalDate startedDate, LocalDate endDate, String notes) {
        this.batchNumber = batchNumber;
        this.course = course;
        this.startedDate = startedDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public static Batch fromMap(String batchNumber, Map<String, String> data) {
        try {
            return new Batch(
                    Integer.parseInt(batchNumber),
                    new CourseServiceRedisImpl().searchCourse(data.get("course")), // TODO: multiple object creating. need to solve. try to use string here (Also check other models)
                    LocalDate.parse(data.get("startedDate")),
                    data.get("endDate").equals("null") ? null : LocalDate.parse(data.get("endDate")),
                    data.get("notes"));
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly gone wrong, please contact the developer").show();
            throw new RuntimeException("Saved value not exist in database");
        }
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("course", course.getCourseID());
        map.put("startedDate", startedDate + "");
        map.put("endDate", endDate + "");
        map.put("notes", notes + "");
        return map;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchNumber=" + batchNumber +
                ", course=" + course +
                ", startedDate=" + startedDate +
                ", endDate=" + endDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
