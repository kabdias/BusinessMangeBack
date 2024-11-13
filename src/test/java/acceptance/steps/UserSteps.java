package acceptance.steps;

import com.tsconsulting.businessManage.application.domain.model.Personel;
import com.tsconsulting.businessManage.application.domain.ports.out.PersonelRepository;
import javax.servlet.http.HttpSession;

import com.tsconsulting.businessManage.application.domain.ports.out.SessionAuthenticate;
import io.cucumber.java8.En;
import org.junit.Assert;

import java.util.Optional;

public class UserSteps implements En {

    private PersonelRepository personelRepository;

    public UserSteps(PersonelRepository personelRepository, SessionAuthenticate sessionAuthenticate) {

        Given("^je suis authentifié en tant que \"([^\"]*)\"$", (String lastName) -> {

            Optional<Personel> personelOptional = personelRepository.getAll().stream()
                    .filter(personel -> personel.getLastNamePersonel().equals(lastName)).findFirst();;
            personelOptional.ifPresent(sessionAuthenticate::authenticate);
            Assert.assertTrue(sessionAuthenticate.currentUser().isPresent());

        });
    }
}


