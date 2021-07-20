/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package service;

import model.Course;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CourseServiceRedisImpl {

    private static final String DB_PREFIX = "c#";
    private final Jedis client;

    public CourseServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public void addCourse(Course course) throws DuplicateEntryException {

        if (client.exists(DB_PREFIX + course.getCourseID())) {
            throw new DuplicateEntryException();
        }

        client.hset(DB_PREFIX + course.getCourseID(), course.toMap());
    }

    public void updateCourse(Course courseToUpdate) throws NotFoundException {

        if (!client.exists(DB_PREFIX + courseToUpdate.getCourseID())) {
            throw new NotFoundException();
        }

        client.hset(DB_PREFIX + courseToUpdate.getCourseID(), courseToUpdate.toMap());
    }

    public void deleteCourse(String courseId) throws NotFoundException {
        // TODO : Can only delete null courses
        if (!client.exists(DB_PREFIX + courseId)) {
            throw new NotFoundException();
        }
        client.del(DB_PREFIX + courseId);
    }

    public Course searchCourse(String courseId) throws NotFoundException {

        if (!client.exists(DB_PREFIX + courseId)) {
            throw new NotFoundException();
        }
        return Course.fromMap(courseId, client.hgetAll(DB_PREFIX + courseId));
    }

    public List<Course> searchAllCourses() {
        List<Course> courseList = new ArrayList<>();
        Set<String> courseIdList = client.keys(DB_PREFIX + "*");

        for (String courseId : courseIdList) {
            courseList.add(Course.fromMap(courseId.substring(2), client.hgetAll(courseId)));
        }

        return courseList;
    }

    public List<Course> searchCourseByKeyword(String keyword) {
        if (keyword.equals("")) {
            return searchAllCourses();
        }
        keyword = keyword.toLowerCase();
        List<Course> searchResult = new ArrayList();
        Set<String> courseIdList = client.keys(DB_PREFIX + "*");

        for (String courseId : courseIdList) {

            if (courseId.substring(2).toLowerCase().contains(keyword)) {
                searchResult.add(Course.fromMap(courseId.substring(2), client.hgetAll(courseId)));
            } else {
                for (String data : client.hvals(courseId)) {
                    if (data.toLowerCase().contains(keyword)) {
                        searchResult.add(Course.fromMap(courseId.substring(2), client.hgetAll(courseId)));
                        break;
                    }
                }
            }
        }
        return searchResult;
    }
}
