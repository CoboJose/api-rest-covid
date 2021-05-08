package cbd52.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "model")
public class Model {
    @Id
    String name;
    String description;
    int date;

}
