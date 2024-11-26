package kz.telephonedirectory.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import kz.telephonedirectory.entity.Contact;
import kz.telephonedirectory.service.ContactService;
import kz.telephonedirectory.validator.InputFieldsValidator;
import lombok.extern.slf4j.Slf4j;

@Route("contacts/new")
@Slf4j
public class AddContactView extends VerticalLayout {
    private final ContactService contactService;

    public AddContactView(ContactService contactService) {
        this.contactService = contactService;

        TextField nameField = new TextField("Имя");
        TextField surnameField = new TextField("Фамилия");
        TextField phoneField = new TextField("Номер телефона");
        TextField emailField = new TextField("Электронная почта");
        Button saveButton = saveButton(nameField, surnameField, phoneField, emailField);
        Button backButton = backButton();

        nameField.setRequired(true);
        phoneField.setRequired(true);

        add(nameField, surnameField, phoneField, emailField, saveButton, backButton);
    }

    private Button backButton() {
        return new Button("Назад", event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
    }

    private Button saveButton(TextField nameField, TextField surnameField, TextField phoneField, TextField emailField) {
        return new Button("Сохранить", event -> {
            try {
                InputFieldsValidator.validateRequiredField(nameField);
                InputFieldsValidator.validateRequiredField(phoneField);
                InputFieldsValidator.validatePhoneNumber(phoneField.getValue());
                InputFieldsValidator.validateEmail(emailField.getValue());
            } catch (IllegalArgumentException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
                return;
            }

            Contact contact = contactService.findContactByPhoneNumber(phoneField.getValue());
            if (contact != null) {
                updateContactDialog(nameField, surnameField, emailField, contact);
            } else {
                createContact(nameField, surnameField, phoneField, emailField);
            }
        });
    }

    private void updateContactDialog(TextField nameField, TextField surnameField, TextField emailField, Contact contact) {
        Dialog confirmUpdateDialog = new Dialog();
        confirmUpdateDialog.setCloseOnOutsideClick(false);

        Div message = new Div();
        message.setText("Контакт с таким номером уже существует. Вы хотите обновить данные для этого контакта?");

        Button yesButton = new Button("Да", eventYes -> {
            confirmUpdateDialog.close();
            updateExistingContact(nameField, surnameField, emailField, contact);
        });
        Button noButton = new Button("Нет", eventNo -> confirmUpdateDialog.close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
        buttonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        confirmUpdateDialog.add(message, buttonsLayout);
        confirmUpdateDialog.open();
    }

    private void updateExistingContact(TextField nameField, TextField surnameField, TextField emailField, Contact contact) {
        contact.setName(nameField.getValue().isBlank() ? contact.getName() : nameField.getValue());
        contact.setSurname(surnameField.getValue().isBlank() ? contact.getSurname() : surnameField.getValue());
        contact.setEmail(emailField.getValue().isBlank() ? contact.getEmail() : emailField.getValue());

        contactService.createContact(contact);

        Notification.show("Контакт успешно обновлен!", 3000, Notification.Position.MIDDLE);
    }

    private void createContact(TextField name, TextField surname, TextField phoneNumber, TextField email) {
        Contact contact = new Contact();
        contact.setName(name.getValue());
        contact.setSurname(surname.getValue());
        contact.setPhoneNumber(phoneNumber.getValue());
        contact.setEmail(email.getValue());

        contactService.createContact(contact);

        name.clear();
        surname.clear();
        phoneNumber.clear();
        email.clear();

        Notification.show("Контакт успешно добавлен!", 3000, Notification.Position.MIDDLE);
    }
}