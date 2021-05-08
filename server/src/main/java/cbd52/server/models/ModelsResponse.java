package cbd52.server.models;

import java.util.List;

import lombok.Data;

@Data
public class ModelsResponse {
    List<Model>  models;

    public ModelsResponse(List<Model> models) {
        this.models = models;
    }
}
