package service;

import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.ValidationUtil;

public class Validation {

    public static void main(String[] args) throws DuplicateEntryException, NotFoundException {
        isValidEmail();
        isValidContactNumber();
        isValidAddress();
        isValidPercentage();
        isValidPastDate();
        isValidNameWithInitials();
        isValidFullName();
        isValidNIC();
    }

    public static void isValidEmail() {
        assert !ValidationUtil.isValidEmail("dhanushka.@gmail.com") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("_dhanushka@gmail.com") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("dha@nushka@gmail.com") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("dhanushka@g.com") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("dhanushka@gmail.c") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("dhanushka@gm_ail.com") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("dhanushka@.gmail.com") : "Email Test Failed";
        assert !ValidationUtil.isValidEmail("dhanushka@gm..ail.com") : "Email Test Failed";
        assert ValidationUtil.isValidEmail("dhanushka@gmail.com") : "Email Test Failed";
        assert ValidationUtil.isValidEmail("dhanu456shka@gmail.com") : "Email Test Failed";
        assert ValidationUtil.isValidEmail("dhan__u...sh54ka@gmail.com") : "Email Test Failed";
    }

    public static void isValidContactNumber() {
        assert ValidationUtil.isValidContactNumber("071-6520080") : "Contact number Test Failed";
        assert !ValidationUtil.isValidContactNumber("0716520080") : "Contact number Test Failed";
        assert !ValidationUtil.isValidContactNumber("071-6520045480") : "Contact number Test Failed";
    }

    public static void isValidPercentage() {
        assert !ValidationUtil.isValidPercentage("-10.5") : "Percentage Test Failed";
        assert !ValidationUtil.isValidPercentage("100.2") : "Percentage Test Failed";
        assert ValidationUtil.isValidPercentage("25") : "Percentage Test Failed";
        assert !ValidationUtil.isValidPercentage("asdsa") : "Percentage Test Failed";
    }

    public static void isValidPastDate() {
        assert ValidationUtil.isValidPastDate("1993-06-11") : "Past Date Test Failed 1";
        assert !ValidationUtil.isValidPastDate("2025-02-04") : "Past Date Test Failed 2";
        assert !ValidationUtil.isValidPastDate("2020-15-35") : "Past Date Test Failed 3";
        assert ValidationUtil.isValidPastDate("2021-06-11") : "Past Date Test Failed 4";
    }

    public static void isValidNameWithInitials() {
        assert ValidationUtil.isValidNameWithInitials("MMDC Ranasinghe") : "Name With Initials Test Failed 1";
        assert !ValidationUtil.isValidNameWithInitials("M_s;ds.,ds") : "Name With Initials Test Failed 2";
        assert !ValidationUtil.isValidNameWithInitials("sd") : "Name With Initials Test Failed 3";
        assert !ValidationUtil.isValidNameWithInitials("asdas456") : "Name With Initials Test Failed 4";
    }

    public static void isValidFullName() {
        assert ValidationUtil.isValidFullName("Dhanushka Chandimal") : "Full Name Test Failed";
        assert !ValidationUtil.isValidFullName("1 Parakramabahu") : "Full Name Test Failed";
        assert !ValidationUtil.isValidFullName("DC") : "Full Name Test Failed";
        assert !ValidationUtil.isValidFullName("Cha=-+") : "Full Name Test Failed";
    }

    public static void isValidNIC() {
        assert ValidationUtil.isValidNIC("931630377V") : "NIC Test Failed 1";
        assert ValidationUtil.isValidNIC("831630377V") : "NIC Test Failed 2";
        assert !ValidationUtil.isValidNIC("731630377V") : "NIC Test Failed 3";
        assert !ValidationUtil.isValidNIC("934630377V") : "NIC Test Failed 4";
        assert ValidationUtil.isValidNIC("199316300377") : "NIC Test Failed 5";
        assert !ValidationUtil.isValidNIC("1993456416300377") : "NIC Test Failed 6";
        assert !ValidationUtil.isValidNIC("399316300377") : "NIC Test Failed 7";
        assert !ValidationUtil.isValidNIC("199396300377") : "NIC Test Failed 8";
        assert ValidationUtil.isValidNIC("199386300377") : "NIC Test Failed 9";
    }

    public static void isValidAddress() {
        assert ValidationUtil.isValidAddress("asdasdas") : "Address Test Failed 1";
        assert !ValidationUtil.isValidAddress("No. 00D45848 Deeyagaha, Matara.") : "Address Test Failed 2";
        assert !ValidationUtil.isValidAddress("Galle_Rd, =Galle") : "Address Test Failed 3";
    }
}
