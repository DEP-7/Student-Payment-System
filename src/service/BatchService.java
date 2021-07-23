package service;

import model.Batch;
import model.Course;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;
import util.FileIO;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static service.CourseService.courseList;

public class BatchService {

    public static ArrayList<Batch> batchList = new ArrayList();
    private static final File batchDBFile = new File("sps-batches.db");
    private static FileIO fileIO = new FileIO();

    static {
        ArrayList arrayList = fileIO.readDataFromFile(batchList, batchDBFile);
        if (arrayList != null) batchList = arrayList;
    }

    public void addBatch(Batch batch) throws DuplicateEntryException, FailedOperationException {
        if (getBatch(batch.getBatchNumber(), batch.getCourse()) != null) {
            throw new DuplicateEntryException();
        }
        batchList.add(batch);
        if (!fileIO.writeDataToFile(batchList, batchDBFile)) {
            batchList.remove(batch);
            throw new FailedOperationException();
        }
    }

    public void updateBatch(Batch batchToUpdate) throws NotFoundException, FailedOperationException {
        Batch batchBeforeUpdate = searchBatch(batchToUpdate.getBatchNumber(), batchToUpdate.getCourse());
        batchList.set(batchList.indexOf(batchBeforeUpdate), batchToUpdate);
        if (!fileIO.writeDataToFile(batchList, batchDBFile)) {
            batchList.set(batchList.indexOf(batchToUpdate), batchBeforeUpdate);
            throw new FailedOperationException();
        }
    }

    public void deleteBatch(int batchNumber, Course course) throws NotFoundException, FailedOperationException {
        // TODO : Can only delete null batches
        Batch batchToDelete = searchBatch(batchNumber, course);
        batchList.remove(batchToDelete);
        if (!fileIO.writeDataToFile(batchList, batchDBFile)) {
            batchList.add(batchToDelete);
            throw new FailedOperationException();
        }
    }

    public List<Batch> searchAllBatches(Course course) {
        List<Batch> results = new ArrayList();

        for (Batch batch : batchList) {
            if (batch.getCourse().getCourseID().equals(course.getCourseID())) {
                results.add(batch);
            }
        }
        return results;
    }

    public List<Batch> searchAllOngoingBatches() {
        List<Batch> results = new ArrayList();

        for (Batch batch : batchList) {

            if (batch.getEndDate() == null) { // TODO: If there is null value to another case it will show as ongoing batch. Try to change this apart from null
                results.add(batch);
            }
        }
        return results;
    }

    public Batch searchBatch(int batchNumber, Course course) throws NotFoundException {
        Batch batch = getBatch(batchNumber, course);

        if (batch != null) {
            return batch;
        }
        throw new NotFoundException();
    }

    public List<Batch> searchBatchByKeyword(Course course, String keyword, boolean ongoingBatchesOnly) {

        if (keyword.equals("")) {
            return ongoingBatchesOnly ? searchAllOngoingBatches() : searchAllBatches(course);
        }

        keyword = keyword.toLowerCase();
        List<Batch> searchResult = new ArrayList();

        for (Batch batch : ongoingBatchesOnly ? searchAllOngoingBatches() : batchList) {

            if (batch.getCourse()==course &&
                    (Integer.toString(batch.getBatchNumber()).contains(keyword) ||
                    batch.getCourse().getCourseName().toLowerCase().contains(keyword) ||
                    batch.getStartedDate().toString().contains(keyword) ||
                    (batch.getEndDate() != null && batch.getEndDate().toString().contains(keyword)) ||
                    batch.getNotes().contains(keyword))) {
                searchResult.add(batch);
            }
        }
        return searchResult;
    }

    public int getLastBatchNumber(Course course) {
        int count = 0;
        for (Batch batch : batchList) {
            if (batch.getCourse() == course) {
                count++;
            }
        }
        for (int i = 0; i < batchList.size(); i++) {
            if (getBatch(count, course) == null) {
                return count == 0 ? 0 : count - 1;
            }
            count++;
        }
        return count - 1;
    }

    private Batch getBatch(int batchNumber, Course course) {
        for (Batch batch : batchList) {
            if (batch.getBatchNumber() == batchNumber && batch.getCourse().equals(course)) {
                return batch;
            }
        }
        return null;
    }
}
