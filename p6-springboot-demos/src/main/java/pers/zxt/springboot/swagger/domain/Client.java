package pers.zxt.springboot.swagger.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data(staticConstructor = "of")
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String clientId;

    private String clientName;

}
