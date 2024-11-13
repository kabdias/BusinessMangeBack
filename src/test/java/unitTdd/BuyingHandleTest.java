package unitTdd;

import com.tsconsulting.businessManage.application.domain.exception.InvalidProductPurchaseException;
import com.tsconsulting.businessManage.application.domain.exception.InvalideDatePurchaseException;
import com.tsconsulting.businessManage.application.domain.exception.InvalidePaymentPurchaseException;
import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.model.*;
import com.tsconsulting.businessManage.application.domain.ports.out.BuyingRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.ProductRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.PurchaseProductDetailRepository;
import com.tsconsulting.businessManage.application.domain.usescase.BuyingHandle;
import com.tsconsulting.businessManage.application.domain.utils.DateUtils;
import com.tsconsulting.businessManage.application.domain.utils.ExceptionUtils;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying.BuyingMemory;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying.ProductMemory;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying.PurchaseProductDetailMemory;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying.SessionMemory;
import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuyingHandleTest {

    private final BuyingRepository buyingRepository = new BuyingMemory();

    private final PurchaseProductDetailRepository purchaseProductDetailRepository = new PurchaseProductDetailMemory();
    private final ProductRepository productRepository = new ProductMemory();

    private final SessionMemory sessionMemory = new SessionMemory();
    private final BuyingHandle buyingHandle = new BuyingHandle(buyingRepository, purchaseProductDetailRepository, productRepository, sessionMemory);



    @Test
    public void saveOnePurchaseShouldBeSavedTest() throws PurchaseException {
        String providerName = "metro";
        String providerAdress = "43 rue Archereau 75019 Paris";
        String idBuying = "noway";
        LocalDate buyingDate = LocalDate.of(2023, 03, 18);
        String paymentMode = "cash";

        buyingHandle.savePurchase(buyingDate, providerName, providerAdress, paymentMode, idBuying);
        Optional<Purchase> purchase = buyingRepository.getPurchaseById("noway");
        Assert.assertNotNull(purchase);
    }

    @Test
    public void saveOnePurchaseDateIsNullOrLessTodayShouldThrowInvalidateDateException() {
        String providerName = "metro";
        String providerAdress = "43 rue Archereau 75019 Paris";
        String idBuying = "noway";
        LocalDate date = LocalDate.parse("13/03/2024", DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String paymentMode = "cash";

        InvalideDatePurchaseException exceptionDateNull = Assert.assertThrows(InvalideDatePurchaseException.class, () -> {
            buyingHandle.savePurchase(null, providerName, providerAdress, paymentMode, idBuying);
        });

        Assert.assertEquals(exceptionDateNull.getMessage(), ExceptionUtils.DATE_PURCHASE_ISNULL);
        Assert.assertEquals(exceptionDateNull.getCode(), ExceptionUtils.FUNCTIONAL_ERROR);

        InvalideDatePurchaseException exceptionDateLessToday = Assert.assertThrows(InvalideDatePurchaseException.class, () -> {
            buyingHandle.savePurchase(tomorrow, providerName, providerAdress, paymentMode, idBuying);
        });

        Assert.assertEquals(exceptionDateLessToday.getMessage(), ExceptionUtils.DATE_PURCHASE_LESS_TO_DAY);
        Assert.assertEquals(exceptionDateLessToday.getCode(), ExceptionUtils.FUNCTIONAL_ERROR);

        InvalidePaymentPurchaseException exceptionPaymentMode = Assert.assertThrows(InvalidePaymentPurchaseException.class, () -> {
            buyingHandle.savePurchase(date, providerName, providerAdress, null, idBuying);
        });

        Assert.assertEquals(exceptionPaymentMode.getMessage(), ExceptionUtils.PAYMENTMODE_PURCHASE_ISNULL);
        Assert.assertEquals(exceptionPaymentMode.getCode(), ExceptionUtils.FUNCTIONAL_ERROR);
    }

    @Test
    public void saveOnePurchaseDetailsShouldBeSavedTest() throws PurchaseException {
        String idBuying = "noway";
        String idProduct = "abc";
        int buyingQantity = 7;
        double buyingPriceUnit = 1;
        String tvaUnit = "20";
        Double currentSellingPrice = 8.0;
        buyingHandle.savePurchaseDetails(idBuying, idProduct, buyingQantity, buyingPriceUnit, tvaUnit, currentSellingPrice);
        Optional<PurchaseProductDetail> purchaseProductDetail = purchaseProductDetailRepository.getProDtlByIds(idBuying, idProduct);
        Assert.assertTrue(purchaseProductDetail.isPresent());

    }

    @Test
    public void updateStockAndPriceTest() {
        String idProduct = "abc";
        double currentSellingPrice = 3.0;
        int buyingQuantity = 7;
        Product product = new Product(idProduct, "Heinken", "boisson alcolisée", 7, "Bièrre", 12);
        productRepository.add(product);
        buyingHandle.updatePurchaseDetails(idProduct, currentSellingPrice, buyingQuantity);
        Optional<Product> productOptional = productRepository.getProductById(idProduct);
        Product productSaved = productOptional.orElse(null);
        assert productSaved != null;
        Assert.assertEquals(19, productSaved.getBuyingQuantity(), 0.0);
        Assert.assertEquals(product.getCurrentSellingPrice(), productSaved.getCurrentSellingPrice(), 0.0);
    }

    @Test
    public void updateStockTestPriceSeelingIsNullTest() {
        String idProduct = "abc";
        int buyingQuantity = 7;
        Product product = new Product(idProduct, "Heinken", "boisson alcolisée", 7, "Bièrre", 12);
        productRepository.add(product);
        buyingHandle.updatePurchaseDetails(idProduct, null, buyingQuantity);
        Optional<Product> productOptional = productRepository.getProductById(idProduct);
        Product productSaved = productOptional.orElse(null);
        assert productSaved != null;
        Assert.assertEquals(19, productSaved.getBuyingQuantity(), 0.0);
        Assert.assertEquals(7, productSaved.getCurrentSellingPrice(), 0.0);
    }

    @Test
    public void savePurchaseDetailsListTest() throws PurchaseException {
        String idBuying = "123";

        Product product1 = new Product("abc", "Heinken", "boisson alcolisée", 42.0, "Bièrre", 63);
        Product product2 = new Product("def", "coca cola", "boisson gazeuse", 54.0, "boisson", 12);
        productRepository.add(product1);
        productRepository.add(product2);

        PurchaseProductDetail purchaseProductDetail1 = new PurchaseProductDetail(idBuying, "abc", 10, 20.0, "20.0", 8.0);
        PurchaseProductDetail purchaseProductDetail2 = new PurchaseProductDetail(idBuying, "def", 5, 40.0, "20.0", 8.0);

        List<PurchaseProductDetail> purchaseProductDetails = new ArrayList<>();
        purchaseProductDetails.add(purchaseProductDetail1);
        purchaseProductDetails.add(purchaseProductDetail2);

        buyingHandle.savePurchasesDetailsAndUpdateStock(purchaseProductDetails);

        Assert.assertTrue(purchaseProductDetailRepository.getProDtlByIds("123", "abc").isPresent());
        Assert.assertTrue(purchaseProductDetailRepository.getProDtlByIds("123", "def").isPresent());

        Assert.assertEquals(productRepository.getProductById("abc").get().getStockQuantity(), 73, 0);
        Assert.assertEquals(productRepository.getProductById("abc").get().getCurrentSellingPrice(), 8.0, 0);

        Assert.assertEquals(productRepository.getProductById("def").get().getStockQuantity(), 17, 0);
        Assert.assertEquals(productRepository.getProductById("def").get().getCurrentSellingPrice(), 8.0, 0);
    }

    /**
     *  On teste que lorsqu'un achat est effectué avec plusieurs produit, ces derniers sont bien enregistrés
     * @throws PurchaseException type d'exception en cas d'erreur
     */

    @Test
    public void savePurchaseWithAllProductsTest() throws PurchaseException {
        String idBuying = "123";
        LocalDate date = LocalDate.parse("13/03/2024", DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
        String provider_name = "Auchan";
        String provider_adress = "43 rue Archereau 75019 Paris";
        String payment_mode = "cash";

        PurchaseProductDetail p1 = new PurchaseProductDetail("abc", "cristaline", 10, 1.20, "20.0", 1.70);
        PurchaseProductDetail p2 = new PurchaseProductDetail("def", "coca", 20, 1.50, "20.0", 2.50);

        List<PurchaseProductDetail> listPurchaseProductDetails = new ArrayList<>();
        listPurchaseProductDetails.add(p1);
        listPurchaseProductDetails.add(p2);

        buyingHandle.savePurchaseWithAllProducts(idBuying, date, provider_name, provider_adress, payment_mode, listPurchaseProductDetails);

        Assert.assertTrue(purchaseProductDetailRepository.getProDtlByIds("abc", "cristaline").isPresent());
        Assert.assertTrue(purchaseProductDetailRepository.getProDtlByIds("def", "coca").isPresent());
    }


    /**
     *  On teste que lorsqu'un achat est effectué avec produit à null
     * @throws PurchaseException type d'exception en cas d'erreur
     */

    @Test
    public void savePurchaseWithAllProductsNULLTest() throws PurchaseException {
        String idBuying = "123";
        LocalDate date = LocalDate.parse("13/03/2024", DateTimeFormatter.ofPattern(DateUtils.DATE_PATERN));
        String provider_name = "Auchan";
        String provider_adress = "43 rue Archereau 75019 Paris";
        String payment_mode = "cash";

        InvalidProductPurchaseException exceptionPurchaseWithProductsNull = Assert.assertThrows(InvalidProductPurchaseException.class, () -> {
            buyingHandle.savePurchaseWithAllProducts(idBuying, date, provider_name, provider_adress, payment_mode, null);
        });

        Assert.assertEquals(exceptionPurchaseWithProductsNull.getMessage(), ExceptionUtils.PRODUCT_IS_NULL);
    }

    @Test
    public void saveProductWithRoleUserNotAuthorise()  {

        String providerName = "metro";
        String providerAdress = "43 rue Archereau 75019 Paris";
        String idBuying = "noway";
        LocalDate buyingDate = LocalDate.of(2023, 03, 18);
        String paymentMode = "cash";

        Personel p = new Personel("123", "KABENE", "SAID", Rule.COLLABORATEUR);
        sessionMemory.authenticate(p);

        PurchaseException purchaseException = Assert.assertThrows(PurchaseException.class, () -> {
            buyingHandle.savePurchase(buyingDate, providerName, providerAdress, paymentMode, idBuying);
        });

        Assert.assertEquals(purchaseException.getMessage(), ExceptionUtils.USER_NOT_AUTHORIZE);
        Assert.assertEquals(purchaseException.getCode(), ExceptionUtils.ERROR_FORBIDEN);





    }
}