package service;

import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

public class ValidationUtilTest {

    public static void main(String[] args) throws DuplicateEntryException, NotFoundException {
        isValidEmail();
        isValidContactNumber();
        isValidAddress();
        isValidPercentage();
        isValidPastDate();
        isValidNameWithInitials();
        isValidFullName();
        isValidNIC();
        getNewIdNumber();
    }

    public static void isValidEmail() {
        assert !util.ValidationUtil.isValidEmail("dhanushka.@gmail.com") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("_dhanushka@gmail.com") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("dha@nushka@gmail.com") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("dhanushka@g.com") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("dhanushka@gmail.c") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("dhanushka@gm_ail.com") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("dhanushka@.gmail.com") : "Email Test Failed";
        assert !util.ValidationUtil.isValidEmail("dhanushka@gm..ail.com") : "Email Test Failed";
        assert util.ValidationUtil.isValidEmail("dhanushka@gmail.com") : "Email Test Failed";
        assert util.ValidationUtil.isValidEmail("dhanu456shka@gmail.com") : "Email Test Failed";
        assert util.ValidationUtil.isValidEmail("dhan__u...sh54ka@gmail.com") : "Email Test Failed";
    }

    public static void isValidContactNumber() {
        assert util.ValidationUtil.isValidContactNumber("071-6520080") : "Contact number Test Failed";
        assert !util.ValidationUtil.isValidContactNumber("0716520080") : "Contact number Test Failed";
        assert !util.ValidationUtil.isValidContactNumber("071-6520045480") : "Contact number Test Failed";
    }

    public static void isValidPercentage() {
        assert !util.ValidationUtil.isValidPercentage("-10.5") : "Percentage Test Failed";
        assert !util.ValidationUtil.isValidPercentage("100.2") : "Percentage Test Failed";
        assert util.ValidationUtil.isValidPercentage("25") : "Percentage Test Failed";
        assert !util.ValidationUtil.isValidPercentage("asdsa") : "Percentage Test Failed";
    }

    public static void isValidPastDate() {
        assert util.ValidationUtil.isValidPastDate("1993-06-11") : "Past Date Test Failed 1";
        assert !util.ValidationUtil.isValidPastDate("2025-02-04") : "Past Date Test Failed 2";
        assert !util.ValidationUtil.isValidPastDate("2020-15-35") : "Past Date Test Failed 3";
        assert util.ValidationUtil.isValidPastDate("2021-06-11") : "Past Date Test Failed 4";
    }

    public static void isValidNameWithInitials() {
        assert util.ValidationUtil.isValidNameWithInitials("MMDC Ranasinghe") : "Name With Initials Test Failed 1";
        assert !util.ValidationUtil.isValidNameWithInitials("M_s;ds.,ds") : "Name With Initials Test Failed 2";
        assert !util.ValidationUtil.isValidNameWithInitials("sd") : "Name With Initials Test Failed 3";
        assert !util.ValidationUtil.isValidNameWithInitials("asdas456") : "Name With Initials Test Failed 4";
    }

    public static void isValidFullName() {
        assert util.ValidationUtil.isValidFullName("Dhanushka Chandimal") : "Full Name Test Failed";
        assert !util.ValidationUtil.isValidFullName("1 Parakramabahu") : "Full Name Test Failed";
        assert !util.ValidationUtil.isValidFullName("DC") : "Full Name Test Failed";
        assert !util.ValidationUtil.isValidFullName("Cha=-+") : "Full Name Test Failed";
    }

    public static void isValidNIC() {
        assert util.ValidationUtil.isValidNIC("931630377V") : "NIC Test Failed 1";
        assert util.ValidationUtil.isValidNIC("831630377V") : "NIC Test Failed 2";
        assert !util.ValidationUtil.isValidNIC("731630377V") : "NIC Test Failed 3";
        assert !util.ValidationUtil.isValidNIC("934630377V") : "NIC Test Failed 4";
        assert util.ValidationUtil.isValidNIC("199316300377") : "NIC Test Failed 5";
        assert !util.ValidationUtil.isValidNIC("1993456416300377") : "NIC Test Failed 6";
        assert !util.ValidationUtil.isValidNIC("399316300377") : "NIC Test Failed 7";
        assert !util.ValidationUtil.isValidNIC("199396300377") : "NIC Test Failed 8";
        assert util.ValidationUtil.isValidNIC("199386300377") : "NIC Test Failed 9";
    }

    public static void isValidAddress() {
        assert util.ValidationUtil.isValidAddress("asdasdas") : "Address Test Failed 1";
        assert !util.ValidationUtil.isValidAddress("No. 00D45848 Deeyagaha, Matara.") : "Address Test Failed 2";
        assert !util.ValidationUtil.isValidAddress("Galle_Rd, =Galle") : "Address Test Failed 3";
    }
}
