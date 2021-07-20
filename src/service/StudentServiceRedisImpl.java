/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package service;

import model.Student;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentServiceRedisImpl {

    private static final String DB_PREFIX = "s#";
    private final Jedis client;

    public StudentServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public void addStudent(Student student) throws DuplicateEntryException {

        if (client.exists(DB_PREFIX + student.getNic())) {
            throw new DuplicateEntryException();
        }

        client.hset(DB_PREFIX + student.getNic(), student.toMap());
    }

    public void updateStudent(Student studentToUpdate, String previousNIC) throws NotFoundException {

        if (!client.exists(DB_PREFIX + previousNIC)) {
            throw new NotFoundException();
        }

        client.rename(DB_PREFIX + previousNIC, DB_PREFIX + studentToUpdate.getNic());
        client.hset(DB_PREFIX + studentToUpdate.getNic(), studentToUpdate.toMap());
    }

    public void deleteStudent(String nic) throws NotFoundException {
        // TODO : Students without any single payments can delete. So check payment details before delete
        if (!client.exists(DB_PREFIX + nic)) {
            throw new NotFoundException();
        }
        client.del(DB_PREFIX + nic);
    }

    public Student searchStudent(String nic) throws NotFoundException {

        if (!client.exists(DB_PREFIX + nic)) {
            throw new NotFoundException();
        }
        return Student.fromMap(nic, client.hgetAll(DB_PREFIX + nic));
    }

    public List<Student> searchStudentsByKeyword(String keyword) {
        if (keyword.equals("")) {
            return searchAllStudents();
        }
        keyword = keyword.toLowerCase();
        List<Student> searchResult = new ArrayList();
        Set<String> nicList = client.keys(DB_PREFIX + "*");

        for (String nic : nicList) {

            if (nic.substring(2).toLowerCase().contains(keyword)) {
                searchResult.add(Student.fromMap(nic.substring(2), client.hgetAll(nic)));
            } else {
                for (String data : client.hvals(nic)) {
                    if (data.toLowerCase().contains(keyword)) {
                        searchResult.add(Student.fromMap(nic.substring(2), client.hgetAll(nic)));
                        break;
                    }
                }
            }
        }
        return searchResult;
    }

    public List<Student> searchAllStudents() {
        List<Student> studentList = new ArrayList<>();
        Set<String> nicList = client.keys(DB_PREFIX + "*");

        for (String nic : nicList) {
            studentList.add(Student.fromMap(nic.substring(2), client.hgetAll(nic)));
        }

        return studentList;
    }

    private boolean existStudent(String nic) {
        return client.exists(nic);
    }
}
