package org.logistics.back.model.bo;

import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsControllerGetPerChaseCompanyInputBO {
  private String company_addr;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(company_addr);
    return args;
  }
}
