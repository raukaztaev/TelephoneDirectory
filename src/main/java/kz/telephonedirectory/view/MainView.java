package kz.telephonedirectory.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {


    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addTitle();
        addButtons();
    }

    private void addTitle() {
        H1 title = new H1("Телефонный справочник");
        title.getStyle()
                .set("color", "#333")
                .set("font-size", "3em")
                .set("text-align", "center")
                .set("margin", "20px 0")
                .set("font-family", "Arial, sans-serif");

        add(title);
    }

    private void addButtons() {
        Button personsNavigateButton = createNavigateButton("Контакты", "contacts");
        Button addAndUpdatePersonButton = createNavigateButton("Добавить новый контакт", "contacts/new");

        styleButton(personsNavigateButton);
        styleButton(addAndUpdatePersonButton);

        add(personsNavigateButton, addAndUpdatePersonButton);
    }

    private Button createNavigateButton(String text, String route) {
        return new Button(text, event -> {
            getUI().ifPresent(ui -> ui.navigate(route));
        });
    }

    private void styleButton(Button button) {
        button.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("text-align", "center")
                .set("text-decoration", "none")
                .set("display", "inline-block")
                .set("font-size", "16px")
                .set("margin", "10px")
                .set("cursor", "pointer")
                .set("border-radius", "8px")
                .set("box-shadow", "0 4px 6px rgba(0, 0, 0, 0.1)");

        button.addClickListener(e -> button.getStyle().set("transform", "scale(0.95)"));
        button.addClickListener(e -> button.getStyle().remove("transform"));
    }
}
