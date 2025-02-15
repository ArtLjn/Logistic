package org.example.back.demos.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsControllerCreateTransOrderInputBO {
  private String fields;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(fields);
    return args;
  }
}
