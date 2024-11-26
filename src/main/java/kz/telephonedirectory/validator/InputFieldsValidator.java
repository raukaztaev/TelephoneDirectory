package kz.telephonedirectory.validator;


import com.vaadin.flow.component.textfield.TextField;

public class InputFieldsValidator {
    private InputFieldsValidator() {
    }

    private static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static void validateRequiredField(TextField textField) {
        if (textField.isEmpty()) {
            throw new IllegalArgumentException("Заполните обязательные поля");
        }
    }

    public static void validatePhoneNumber(String number) {
        if (!number.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Некорректный номер телефона");
        }
    }

    public static void validateEmail(String email) {
        if (email.isBlank()) {
            return;
        }

        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Некорректная электронная почта");
        }
    }
}