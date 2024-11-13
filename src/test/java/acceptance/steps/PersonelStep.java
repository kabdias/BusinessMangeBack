package acceptance.steps;

import com.tsconsulting.businessManage.application.domain.model.Personel;
import com.tsconsulting.businessManage.application.domain.model.Rule;
import com.tsconsulting.businessManage.application.domain.ports.out.PersonelRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonelStep implements En {
    public PersonelStep(PersonelRepository personelRepository) {
        Given("^personel existant:$", (DataTable table) -> {
            List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {
                String idPersonel = dataMap.get("id") ;
                String firstNamePersonel = dataMap.get("firstName");
                String lastNamePersonel = dataMap.get("lastName");
                String rule = dataMap.get("role");

                Personel personel = new Personel(idPersonel, firstNamePersonel, lastNamePersonel, Rule.getRule(rule));
                personelRepository.add(personel);
            });
            Assert.assertEquals(personelRepository.getAll().size(), 2);
        });
    }
}
