package org.example.back.demos.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsControllerCtorBO {
  private String company;

  private String order;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(company);
    args.add(order);
    return args;
  }
}
