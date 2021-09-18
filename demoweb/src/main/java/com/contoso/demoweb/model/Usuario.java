package com.contoso.demoweb.model;


import java.io.Serializable;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements Serializable{

    private String userID;
    private String password;
    private String tipoUsuario;

}
