package acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:FeaturesReport/report.html"}, features = {"src/test/resources"})
public class AllTestAcceptanceTest {
}
