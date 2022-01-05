package bhn.buyhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Pattern(regexp = "^(?=.{6,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "username is 6-20 characters long, no _ or . at the beginning, no __ or _. or ._ or .. inside, no _ or . at the end")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-{}:;',?/*~$^+=<>]).{8,}$", message = "Minimum 8 and maximum 30 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}