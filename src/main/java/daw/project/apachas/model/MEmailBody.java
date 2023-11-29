package daw.project.apachas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MEmailBody {
    private String content;
    private String email;
    private String subject;
}
