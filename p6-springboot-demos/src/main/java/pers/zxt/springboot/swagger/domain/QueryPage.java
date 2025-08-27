package pers.zxt.springboot.swagger.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPage {

    private int page;

    private int size;

}
