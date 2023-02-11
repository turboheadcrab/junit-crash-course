package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public void setUpAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setUp() {
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Should Create Contact")
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> contactManager.addContact(null, "Doe", "0123456789"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> contactManager.addContact("John", null, "0123456789"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> contactManager.addContact("John", "Doe", null));
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Should be executed at the end of the Test");
    }

    @Test
    @DisplayName("Should Create Contact Only On Mac OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac OS")
    public void shouldCreateContactOnlyOnMacOs() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Should Not Create Contact On Windows")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldNotCreateContactOnWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldTestContactCreationOnDev() {
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456789", "0123456789");
    }

    @Nested
    class RepeatedNestedTest {
        @RepeatedTest(value = 5,
                name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        @DisplayName("Repeat Contact Creation Test 5 times")
        public void shouldTestContactCreationRepeatedly() {
            contactManager.addContact("John", "Doe", "0123456789");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @Nested
    class ParametrizedNestedTest {

        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
        @DisplayName("Repeat Contact Creation Test 5 times")
        public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456789", "0123456789"})
        public void shouldTestPhoneNumberFormatUsingCsvSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV File Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldTestPhoneNumberFormatUsingCsvFileSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }

}
