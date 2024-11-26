package kz.telephonedirectory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "contacts")
public class Contact {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("surname")
    private String surname;

    @Field("phoneNumber")
    private String phoneNumber;

    @Field("email")
    private String email;
}

