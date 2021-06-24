package br.com.helpdev.jornada.unittests.service;

import br.com.helpdev.jornada.unittests.service.entity.SerasaDebits;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class SerasaService {

  public static final String CPF_WITH_DEBIT = "12345678910";

  public List<SerasaDebits> getDebits(final String document) {
    System.out.println("Calling external call to document " + document + " ...");
    try {
      Thread.sleep(1_000);
    } catch (InterruptedException ignore) {
    }

    if (new Random().nextInt(10) == 1) {
      throw new RuntimeException("Error 500 ao chamar o Serasa");
    }

    if (document.equalsIgnoreCase(CPF_WITH_DEBIT)) {
        return Collections.singletonList(new SerasaDebits(1231L));
      }

    return Collections.emptyList();
  }

}
