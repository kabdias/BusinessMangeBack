package acceptance.steps;


import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.model.Product;
import com.tsconsulting.businessManage.application.domain.model.Purchase;
import com.tsconsulting.businessManage.application.domain.model.PurchaseProductDetail;
import com.tsconsulting.businessManage.application.domain.ports.out.BuyingRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.ProductRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.PurchaseProductDetailRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.SessionAuthenticate;
import com.tsconsulting.businessManage.application.domain.usescase.BuyingHandle;
import com.tsconsulting.businessManage.application.domain.utils.DateUtils;
import com.tsconsulting.businessManage.application.domain.utils.ExceptionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BuyingStep implements En {

    private final BuyingHandle buyingHandle;

    public BuyingStep(BuyingRepository buyingRepository, PurchaseProductDetailRepository purchaseProductDetailRepository , ProductRepository productRepository, SessionAuthenticate sessionMemory) {

        buyingHandle = new BuyingHandle(buyingRepository, purchaseProductDetailRepository, productRepository, sessionMemory);
            When("^je tente de déclarer un achat dont les informations sont les suivantes: date : \"([^\"]*)\", fournisseur : \"([^\"]*)\", \"([^\"]*)\", mode de paiement \"([^\"]*)\", identifiant \"([^\"]*)\"$", (String buying_date, String provider_name, String provider_adress, String payment_mode, String idBuying) -> {
            LocalDate date = LocalDate.parse(buying_date, DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
            buyingHandle.savePurchase(date, provider_name, provider_adress, payment_mode, idBuying);
            Optional<Purchase> purchase = buyingRepository.getPurchaseById(idBuying);
            Assert.assertNotNull(purchase);
        });

        And("^j'enregistre le dètails de l'achat \"([^\"]*)\" liès aux produits achetés suivant:$", (String idBuying, DataTable table) -> {
            List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {
                try {
                    buyingHandle.savePurchaseDetails(idBuying, dataMap.get("idProduct"), Integer.parseInt(dataMap.get("buyingQuantity")), Double.parseDouble(dataMap.get("buyingPriceUnit")), dataMap.get("TvaUnit"), Double.valueOf(dataMap.get("currentSellingPrice")));
                } catch (PurchaseException e) {
                    assertEquals(e.getMessage(), ExceptionUtils.TVA_NOT_VALID);
                }
            });
            Assert.assertNotNull(buyingRepository.getPurchaseById(idBuying));
        });
        And("^pour les produits existant, je mets à jour le stock. si le prix de vente est renseignè je le mets également à jour:$", (DataTable table) -> {
            List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {
                buyingHandle.updatePurchaseDetails(dataMap.get("idProduct"), Double.valueOf(dataMap.get("currentSellingPrice")), Integer.parseInt(dataMap.get("buyingQuantity")));
            });
        });

        Then("^l'enregistrement de l'achat \"([^\"]*)\" est effectif$", (String idBuying) -> {
            Assert.assertNotNull(buyingRepository.getPurchaseById(idBuying));
            Assert.assertNotNull(purchaseProductDetailRepository.getPurchaseDetailsByIdBuying(idBuying));
        });

        And("^la quantité de stock du produit \"([^\"]*)\" est de \"([^\"]*)\"$", (String idProduct, String stockQuantity) -> {
            Optional<Product> productOptional = productRepository.getProductById(idProduct);
            assertTrue(productOptional.isPresent());
            assertEquals(Integer.parseInt(stockQuantity), productOptional.get().getBuyingQuantity(), 0);
        });

        And("^la prix de vente du produit \"([^\"]*)\" est de \"([^\"]*)\"$", (String idProduct1, String sellingPrice1) -> {
            Optional<Product> productOptional = productRepository.getProductById(idProduct1);
            assertTrue(productOptional.isPresent());
            assertEquals(Integer.parseInt(sellingPrice1), productOptional.get().getCurrentSellingPrice(), 0);
        });

        And("^j'enregistre le dètails de plusiers achat \"([^\"]*)\" liès aux produits achetés suivant et je mets à jour le stock et le prix de chaque produit :$", (String idBuying, DataTable table) -> {
            List<Map<String, String>> listPurchaseDetailsRaw = table.asMaps(String.class, String.class);
            List<PurchaseProductDetail> purchaseProductDetails = new ArrayList<>();
            listPurchaseDetailsRaw.forEach(purchaseDetailMap -> {
                PurchaseProductDetail newPurchaseDetail = null;
                try {
                    newPurchaseDetail = new PurchaseProductDetail(idBuying, purchaseDetailMap.get("idProduct"), Integer.parseInt(purchaseDetailMap.get("buyingQuantity")), Double.valueOf(purchaseDetailMap.get("buyingPriceUnit")), purchaseDetailMap.get("TvaUnit"), Double.valueOf(purchaseDetailMap.get("currentSellingPrice")));
                } catch (PurchaseException e) {
                    assertEquals(e.getMessage(), ExceptionUtils.PURCHASEDETAIL_NOVALID);
                }
                purchaseProductDetails.add(newPurchaseDetail);
            });
            buyingHandle.savePurchasesDetailsAndUpdateStock(purchaseProductDetails);
            Assert.assertNotNull(buyingRepository.getAll());
        });

        When("^je tente de déclarer un achat dont : id : \"([^\"]*)\" avec une date null ou superieure à la date du jour:$", (String idBuying, DataTable table) -> {
            List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {
                LocalDate parsedDate = null;
                if (dataMap.get("buying_date") != null && !dataMap.get("buying_date").isEmpty()){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN);
                    parsedDate = LocalDate.parse(dataMap.get("buying_date"), formatter);
                }
                try {
                    buyingHandle.savePurchase(parsedDate, dataMap.get("provider_name"), dataMap.get("provider_adress"), dataMap.get("payment_mode"), idBuying);
                } catch (PurchaseException e) {
                    assertTrue(e.getMessage().equals(ExceptionUtils.DATE_PURCHASE_LESS_TO_DAY) || e.getMessage().equals(ExceptionUtils.DATE_PURCHASE_ISNULL));
                }
            });
        });

        Then("^l'enregistrement de l'achat \"([^\"]*)\" ne peut pas etre effectué$", (String idBuying) -> {
            assert buyingRepository.getPurchaseById(idBuying).isEmpty();
        });

        When("^je tente de déclarer un achat id : \"([^\"]*)\"  avec un mode de paiement non renseigné \"([^\"]*)\":$", (String idBuying , String  payment_mode, DataTable table) -> {
            List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {
                LocalDate date = LocalDate.parse(dataMap.get("buying_date"), DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
                try {
                    buyingHandle.savePurchase(date, dataMap.get("provider_name"), dataMap.get("provider_adress"), null, idBuying);
                } catch (PurchaseException e) {
                    assertEquals(e.getMessage(), ExceptionUtils.PAYMENTMODE_PURCHASE_ISNULL);
                }
            });
        });

        When("^je tente de déclarer un achat dont les informations sont les suivantes: date : \"([^\"]*)\", fournisseur : \"([^\"]*)\", \"([^\"]*)\", mode de paiement \"([^\"]*)\", identifiant \"([^\"]*)\", sans renseigner de produit$", (String buying_date, String provider_name, String provider_adress, String payment_mode, String idBuying) -> {
            try {
                LocalDate date = LocalDate.parse(buying_date, DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
                buyingHandle.savePurchaseWithAllProducts(idBuying, date, provider_name, provider_adress, payment_mode, null);
            }catch (PurchaseException e){
                assertEquals(e.getMessage(), ExceptionUtils.PRODUCT_IS_NULL);
                assertEquals(e.getCode(), ExceptionUtils.FUNCTIONAL_ERROR);
            }
        });

        Then("^l'enregistrement de l'achat \"([^\"]*)\" ne peut pas etre effectué et renvoie un message d'erreur à l'utilisateur$", (String idBuying) -> {
            try {
                assert buyingRepository.getPurchaseById(idBuying).isEmpty();
            } catch (PurchaseException e) {
                assert e.getMessage().equals(ExceptionUtils.ERROR_WHILE_RECORDING_PRODUCT);
            }
        });

        When("^je declare un achat \"([^\"]*)\" avec une liste de produit dont le detail est incomplet$", (String idBuying, DataTable table) -> {
           List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {

                Optional<PurchaseProductDetail> productDetail = Optional.empty();

                try {
                    if (dataMap.get("buyingQuantity") == null || dataMap.get("buyingPriceUnit") == null || dataMap.get("currentSellingPrice") == null ) {
                        throw new PurchaseException();
                    }
                    int buyingQuantity = Integer.parseInt(dataMap.get("buyingQuantity"));
                    double buyingPriceUnit = Double.parseDouble(dataMap.get("buyingPriceUnit"));
                    double currentSellingPrice = Double.parseDouble(dataMap.get("currentSellingPrice"));

                    productDetail = Optional.of(new PurchaseProductDetail(idBuying, dataMap.get("idProduct"), buyingQuantity, buyingPriceUnit, dataMap.get("tvaUnit"), currentSellingPrice));
                    buyingHandle.savePurchaseDetails(productDetail.get().getIdBuying(),productDetail.get().getIdProduct(), productDetail.get().getBuyingQuantity(), productDetail.get().getBuyingPriceUnit(), productDetail.get().getTvaUnit(), productDetail.get().getCurrentSellingPrice());
                } catch (PurchaseException e) {
                    assertTrue(e.getMessage().equals(ExceptionUtils.UNKNOWN_ERROR_PURCHASE) || e.getMessage().equals(ExceptionUtils.PRODUCT_IS_NULL));
                }
            });
        });
        When("^je tente de déclarer un achat id : \"([^\"]*)\" avec un role autre que gestionnaire :$", (String idBuying, DataTable table) -> {

            List<Map<String, String>> dataMaps = table.asMaps(String.class, String.class);
            dataMaps.forEach(dataMap -> {

                LocalDate date = LocalDate.parse(dataMap.get("buying_date"), DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
                PurchaseException purchaseException = Assert.assertThrows(PurchaseException.class, () -> {
                    buyingHandle.savePurchase(date, dataMap.get("provider_name"), dataMap.get("provider_adress"), dataMap.get("payment_mode"), idBuying);
                });

                Assert.assertEquals(purchaseException.getMessage(), ExceptionUtils.USER_NOT_AUTHORIZE);
                Assert.assertEquals(purchaseException.getCode(), ExceptionUtils.ERROR_FORBIDEN);

            });
        });
    }
}
