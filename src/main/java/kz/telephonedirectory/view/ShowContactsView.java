package kz.telephonedirectory.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import kz.telephonedirectory.entity.Contact;
import kz.telephonedirectory.service.ContactService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Route("contacts")
@Slf4j
public class ShowContactsView extends VerticalLayout {

    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);

    public ShowContactsView(ContactService contactService) {
        this.contactService = contactService;

        customizeGrid();
        add(grid, backButton(), printButton());
    }

    private void customizeGrid() {
        grid.removeAllColumns();
        setColumns();
        grid.setItems(getContacts());
        grid.setHeight("80vh");
    }

    private void setColumns() {
        grid.addColumn("name").setHeader("Имя");
        grid.addColumn("surname").setHeader("Фамилия");
        grid.addColumn("phoneNumber").setHeader("Номер");
        grid.addColumn("email").setHeader("Эл.почта");
    }

    private Button backButton() {
        return new Button("Назад", event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
    }

    private Button printButton() {
        return new Button("Распечатать", event -> printContacts());
    }

    private void printContacts() {
        List<Contact> contacts = getContacts();
        StringBuilder htmlReport = new StringBuilder("<html><head><title>Отчет</title></head><body>");
        htmlReport.append("<h1>Список контактов</h1>");
        htmlReport.append("<ul>");
        for (Contact contact : contacts) {
            htmlReport.append("<li>")
                    .append(contact.getName())
                    .append(" | ")
                    .append(contact.getSurname())
                    .append(" | ")
                    .append(contact.getPhoneNumber())
                    .append(" | ")
                    .append(contact.getEmail())
                    .append("</li>");
        }
        htmlReport.append("</ul>");
        htmlReport.append("</body></html>");

        getElement().executeJs("const reportWindow = window.open('', '_blank'); " +
                               "reportWindow.document.open(); " +
                               "reportWindow.document.write($0); " +
                               "reportWindow.document.close(); " +
                               "reportWindow.print();", htmlReport.toString());
    }

    private List<Contact> getContacts() {
        return contactService.getAllContacts();
    }
}
