package service;

import model.Batch;
import model.Course;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BatchServiceRedisImpl { // TODO: Waste of DB memory. Try to merge batch with course

    private static final String DB_PREFIX = "b#";
    private final Jedis client;

    public BatchServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public void addBatch(Batch batch) throws DuplicateEntryException {

        if (client.exists(DB_PREFIX + batch.getCourse().getCourseID() + batch.getBatchNumber())) {
            throw new DuplicateEntryException();
        }

        client.hset(DB_PREFIX + batch.getCourse().getCourseID() + batch.getBatchNumber(), batch.toMap());
    }

    public void updateBatch(Batch batchToUpdate) throws NotFoundException {

        if (!client.exists(DB_PREFIX + batchToUpdate.getCourse().getCourseID() + batchToUpdate.getBatchNumber())) {
            throw new NotFoundException();
        }

        client.hset(DB_PREFIX + batchToUpdate.getCourse().getCourseID() + batchToUpdate.getBatchNumber(), batchToUpdate.toMap());
    }

    public void deleteBatch(int batchNumber, Course course) throws NotFoundException {
        // TODO : Can only delete null batches
        if (!client.exists(DB_PREFIX + course.getCourseID() + batchNumber)) {
            throw new NotFoundException();
        }
        client.del(DB_PREFIX + course.getCourseID() + batchNumber);
    }

    public List<Batch> searchAllBatches(Course course) {
        List<Batch> batchList = new ArrayList<>();
        Set<String> batchNumberList = client.keys(DB_PREFIX + course.getCourseID() + "*");

        for (String batchNumber : batchNumberList) {
            batchList.add(Batch.fromMap(batchNumber.substring(2 + course.getCourseID().length()), client.hgetAll(batchNumber)));
        }

        return batchList;
    }

    public Batch searchBatch(int batchNumber, Course course) throws NotFoundException {

        if (!client.exists(DB_PREFIX + course.getCourseID() + batchNumber)) {
            throw new NotFoundException();
        }
        return Batch.fromMap(batchNumber + "", client.hgetAll(DB_PREFIX + course.getCourseID() + batchNumber));
    }

    public List<Batch> searchBatchByKeyword(Course course, String keyword, boolean ongoingBatchesOnly) {

        if (keyword.isEmpty() && !ongoingBatchesOnly) {
            return searchAllBatches(course);
        }

        keyword = keyword.toLowerCase();
        List<Batch> searchResult = new ArrayList();
        Set<String> batchNumberList = client.keys(DB_PREFIX + course.getCourseID() + "*");

        for (String batchNumber : batchNumberList) {

            if (ongoingBatchesOnly && !client.hget(batchNumber, "endDate").isEmpty()) {
                continue;
            }

            if (batchNumber.substring(2 + course.getCourseID().length()).contains(keyword)) {
                searchResult.add(Batch.fromMap(batchNumber.substring(2 + course.getCourseID().length()), client.hgetAll(batchNumber)));
            } else {
                for (String data : client.hvals(batchNumber)) {
                    if (data.toLowerCase().contains(keyword)) {
                        searchResult.add(Batch.fromMap(batchNumber.substring(2 + course.getCourseID().length()), client.hgetAll(batchNumber)));
                        break;
                    }
                }
            }
        }
        return searchResult;
    }

    public int getLastBatchNumber(Course course) {
        Set<String> batchNumberList = client.keys(DB_PREFIX + course.getCourseID() + "*");
        int count = batchNumberList.size()+1;

        for (int i = 0; i < batchNumberList.size() + 1; i++) {
            if (!client.exists(DB_PREFIX + course.getCourseID() + count)) {
                return count == 0 ? 0 : count - 1;
            }
            count++;
        }
        return count - 1;
    }
}
