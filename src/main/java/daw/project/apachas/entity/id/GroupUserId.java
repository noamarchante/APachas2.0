package daw.project.apachas.entity.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GroupUserId implements Serializable {
    private long groupId;

    private long userId;
}
