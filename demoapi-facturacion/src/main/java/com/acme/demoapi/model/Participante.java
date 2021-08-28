package com.acme.demoapi.model;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participante {

    private String id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    
}
