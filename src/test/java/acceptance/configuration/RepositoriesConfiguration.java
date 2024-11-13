package acceptance.configuration;

import com.tsconsulting.businessManage.application.domain.ports.out.*;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RepositoriesConfiguration {
    @Bean
    @Scope("cucumber-glue")
    PersonelRepository personelRepository(){return new PersonelMemory();};

    @Bean
    @Scope("cucumber-glue")
    ProductRepository productRepository(){return new ProductMemory();};

    @Bean
    @Scope("cucumber-glue")
    SessionAuthenticate sessionAuthenticate(){return new SessionMemory();};

    @Bean
    @Scope("cucumber-glue")
    BuyingRepository buyingRepository(){return new BuyingMemory();};

    @Bean
    @Scope("cucumber-glue")
    PurchaseProductDetailRepository purchaseProductDetailRepository(){return new PurchaseProductDetailMemory();};
}
