package service;

import model.User;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;
import util.FileIO;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public static ArrayList<User> userList = new ArrayList();
    private static final File userDBFile = new File("sps-users.db");
    private static FileIO fileIO = new FileIO();

    static {
        ArrayList arrayList = fileIO.readDataFromFile(userList, userDBFile);
        if (arrayList != null) userList = arrayList;
    }

    public void addUser(User user) throws DuplicateEntryException, FailedOperationException {
        if (getUser(user.getNic()) != null) {
            throw new DuplicateEntryException();
        }
        userList.add(user);
        if (!fileIO.writeDataToFile(userList, userDBFile)) {
            userList.remove(user);
            throw new FailedOperationException();
        }
    }

    public void updateUser(User userToUpdate, String previousNIC) throws NotFoundException, FailedOperationException {
        User userBeforeUpdate = searchUser(previousNIC);
        userList.set(userList.indexOf(userBeforeUpdate), userToUpdate);
        if (!fileIO.writeDataToFile(userList, userDBFile)) {
            userList.set(userList.indexOf(userToUpdate), userBeforeUpdate);
            throw new FailedOperationException();
        }
    }

    public void deleteUser(String nic) throws NotFoundException, FailedOperationException {
        User userToDelete = searchUser(nic);
        userList.get(userList.indexOf(userToDelete)).setAccountActive(false);
        if (!fileIO.writeDataToFile(userList, userDBFile)) {
            userList.add(userToDelete);
            throw new FailedOperationException();
        }
    }

    public List<User> searchAllUsers() {
        return userList;
    }

    public User searchUser(String nic) throws NotFoundException {
        User user = getUser(nic);

        if (user != null) {
            return user;
        }
        throw new NotFoundException();
    }

    public User searchUserByUsername(String username) throws NotFoundException {

        for (User user : userList) {
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

        for (User user : userList) {
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
        for (User user : userList) {
            if (user.getNic().equals(nic)) {
                return user;
            }
        }
        return null;
    }
}
