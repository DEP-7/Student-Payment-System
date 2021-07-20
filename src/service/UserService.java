/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package service;

import model.User;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public static ArrayList<User> usersDB = new ArrayList();

    static {
        usersDB.add(new User("931630377V", "Magam Mudalige Dhanushka Chandimal Ranasinghe", "M.M.D.C.Ranasinghe", "Male", LocalDate.of(1993, 6, 11), "Handiya Kade, Deeyagaha, Matara", "071-6520080", "dhanushkachandimal11@gmail.com", true, LocalDateTime.of(2021, 6, 15, 13, 15, 25), "931630377V", "ȴȕɂɡȹȻȼɂȵȕȺɁȻȘ", true));
        usersDB.add(new User("9316301111V", "Institute of Java Software Engineering", "IJSE", "Male", LocalDate.of(1993, 6, 11), "Panadura", "071-6520080", "ijse@gmail.com", true, LocalDateTime.of(2021, 6, 15, 13, 15, 25), "IJSE-Admin", "ŷŮƈŻƀƖŻ", true));
        usersDB.add(new User("9316304511V", "Institute of Java Software Engineering", "ijse", "Male", LocalDate.of(1993, 6, 11), "Panadura", "071-6520080", "ijse@gmail.com", false, LocalDateTime.of(2021, 6, 15, 13, 15, 25), "IJSE-General", "ǗƮȈǛǠȖǛ", true));
        usersDB.add(new User("931630377V", "Magam Mudalige Dhanushka Chandimal Ranasinghe", "M.M.D.C.Ranasinghe", "Male", LocalDate.of(1993, 6, 11), "Handiya Kade, Deeyagaha, Matara", "071-6520080", "dhanushkachandimal11@gmail.com", true, LocalDateTime.of(2021, 6, 15, 13, 15, 25), "931630377V", "ȴȕɂɡȹȻȼɂȵȕȺɁȻȘ", true));
        usersDB.add(new User("199316300377", "Prasad Viduranha", "P.Viduranga", "Male", LocalDate.of(1995, 12, 20), "Galle", "077-2565368", "prasad@gmail.com", false, LocalDateTime.of(2021, 5, 4, 4, 55, 25), "Prasad", "1234", true));
        usersDB.add(new User("941630377V", "Manoj Randeni", "R.A.M.R.Randeni", "Male", LocalDate.of(1996, 12, 20), "Gampaha", "078-2515651", "manoj@gmail.com", false, null, "941630377V", "941630377V", true));
    }

    public void addUser(User user) throws DuplicateEntryException {
        if (getUser(user.getNic()) != null) {
            throw new DuplicateEntryException();
        }
        usersDB.add(user);
    }

    public void updateUser(User userToUpdate, String previousNIC) throws NotFoundException {
        User userBeforeUpdate = searchUser(previousNIC);
        usersDB.set(usersDB.indexOf(userBeforeUpdate), userToUpdate);
    }

    public void deleteUser(String nic) throws NotFoundException {
        User userToDelete = searchUser(nic);
        usersDB.get(usersDB.indexOf(userToDelete)).setAccountActive(false);
    }

    public List<User> searchAllUsers() {
        return usersDB;
    }

    public User searchUser(String nic) throws NotFoundException {
        User user = getUser(nic);

        if (user != null) {
            return user;
        }
        throw new NotFoundException();
    }

    public User searchUserByUsername(String username) throws NotFoundException {

        for (User user : usersDB) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new NotFoundException();
    }

    public List<User> searchUsersByKeyword(String keyword) {
        if (keyword.equals("")) {
            return searchAllUsers();
        }
        keyword = keyword.toLowerCase();
        List<User> searchResult = new ArrayList();

        for (User user : usersDB) {
            if (user.getNic().toLowerCase().contains(keyword) ||
                    user.getNameInFull().toLowerCase().contains(keyword) ||
                    user.getNameWithInitials().toLowerCase().contains(keyword) ||
                    user.getGender().toLowerCase().contains(keyword) ||
                    user.getDateOfBirth().toString().contains(keyword) ||
                    user.getAddress().toLowerCase().contains(keyword) ||
                    user.getContactNumber().contains(keyword) ||
                    user.getEmail().toLowerCase().contains(keyword) ||
                    (user.isAdmin() ? "admin".contains(keyword) : "general user".contains(keyword))) {
                searchResult.add(user);
            }
        }
        return searchResult;
    }

    public void resetAccount(String nic) throws NotFoundException {
        User userAccountToReset = searchUser(nic);
        userAccountToReset.setUsername(nic);
        userAccountToReset.setPassword(nic);
    }

    private User getUser(String nic) {
        for (User user : usersDB) {
            if (user.getNic().equals(nic)) {
                return user;
            }
        }
        return null;
    }
}
