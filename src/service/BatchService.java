package service;

import model.Batch;
import model.Course;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static service.CourseService.courseDB;

public class BatchService {

    public static ArrayList<Batch> batchesDB = new ArrayList();

    static {
        batchesDB.add(new Batch(1, courseDB.get(0), LocalDate.of(2015, 1, 7), LocalDate.of(2015, 5, 7), "First batch"));
        batchesDB.add(new Batch(2, courseDB.get(0), LocalDate.of(2016, 1, 7), LocalDate.of(2016, 5, 7), "Second batch"));
        batchesDB.add(new Batch(3, courseDB.get(0), LocalDate.of(2017, 1, 7), LocalDate.of(2017, 5, 7), "Third batch"));
        batchesDB.add(new Batch(4, courseDB.get(0), LocalDate.of(2018, 1, 7), LocalDate.of(2018, 5, 7), ""));
        batchesDB.add(new Batch(5, courseDB.get(0), LocalDate.of(2019, 1, 7), LocalDate.of(2019, 5, 7), ""));
        batchesDB.add(new Batch(6, courseDB.get(0), LocalDate.of(2020, 1, 7), LocalDate.of(2020, 5, 7), ""));
        batchesDB.add(new Batch(7, courseDB.get(0), LocalDate.of(2021, 5, 3), null, ""));
        batchesDB.add(new Batch(1, courseDB.get(1), LocalDate.of(2017, 10, 7), LocalDate.of(2018, 10, 7), "First batch"));
        batchesDB.add(new Batch(2, courseDB.get(1), LocalDate.of(2018, 2, 7), LocalDate.of(2018, 5, 7), ""));
        batchesDB.add(new Batch(3, courseDB.get(1), LocalDate.of(2019, 12, 7), LocalDate.of(2020, 12, 21), ""));
        batchesDB.add(new Batch(4, courseDB.get(1), LocalDate.of(2020, 3, 7), LocalDate.of(2021, 5, 7), ""));
    }

    public void addBatch(Batch batch) throws DuplicateEntryException {
        if (getBatch(batch.getBatchNumber(), batch.getCourse()) != null) {
            throw new DuplicateEntryException();
        }
        batchesDB.add(batch);
    }

    public void updateBatch(Batch batchToUpdate) throws NotFoundException {
        Batch batchBeforeUpdate = searchBatch(batchToUpdate.getBatchNumber(), batchToUpdate.getCourse());
        batchesDB.set(batchesDB.indexOf(batchBeforeUpdate), batchToUpdate);
    }

    public void deleteBatch(int batchNumber, Course course) throws NotFoundException {
        // TODO : Can only delete null batches
        Batch batchToDelete = searchBatch(batchNumber, course);
        batchesDB.remove(batchToDelete);
    }

    public List<Batch> searchAllBatchs() {
        return batchesDB;
    }

    public Batch searchBatch(int batchNumber, Course course) throws NotFoundException {
        Batch batch = getBatch(batchNumber, course);

        if (batch != null) {
            return batch;
        }
        throw new NotFoundException();
    }

    public List<Batch> searchBatchByKeyword(String keyword, Course course) {
        if (keyword.equals("")) {
            return searchAllBatchs();
        }
        keyword = keyword.toLowerCase();
        List<Batch> searchResult = new ArrayList();

        for (Batch batch : batchesDB) {

            if (batch.getCourse() != course) { continue; }

            if (Integer.toString(batch.getBatchNumber()).contains(keyword) ||
                    batch.getCourse().getCourseName().toLowerCase().contains(keyword) ||
                    batch.getStartedDate().toString().contains(keyword) ||
                    batch.getEndDate().toString().contains(keyword) ||
                    batch.getNotes().contains(keyword)) {
                searchResult.add(batch);
            }
        }
        return searchResult;
    }

    private Batch getBatch(int batchNumber, Course course) {
        for (Batch batch : batchesDB) {
            if (batch.getBatchNumber() == batchNumber && batch.getCourse().equals(course)) {
                return batch;
            }
        }
        return null;
    }
}
