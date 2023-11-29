package daw.project.apachas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MRetrievePassword {

    private String userEmail;
    private String tokenPassword;
    private String newPassword;
}
