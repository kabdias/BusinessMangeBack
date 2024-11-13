package com.tsconsulting.businessManage.application.domain.usescase;

import com.tsconsulting.businessManage.application.domain.exception.InvalidProductPurchaseException;
import com.tsconsulting.businessManage.application.domain.exception.InvalideDatePurchaseException;
import com.tsconsulting.businessManage.application.domain.exception.InvalidePaymentPurchaseException;
import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.model.*;
import com.tsconsulting.businessManage.application.domain.ports.out.BuyingRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.ProductRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.PurchaseProductDetailRepository;
import com.tsconsulting.businessManage.application.domain.ports.out.SessionAuthenticate;
import com.tsconsulting.businessManage.application.domain.utils.CollectionUtils;
import com.tsconsulting.businessManage.application.domain.utils.ExceptionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BuyingHandle {

    private final BuyingRepository buyingRepository;
    private final PurchaseProductDetailRepository purchaseProductDetailRepository;
    private final ProductRepository productRepository;

    private final SessionAuthenticate sessionMemory;


    public BuyingHandle(BuyingRepository buyingRepository, PurchaseProductDetailRepository purchaseProductDetailRepository, ProductRepository productRepository, SessionAuthenticate sessionMemory) {
        this.purchaseProductDetailRepository = purchaseProductDetailRepository;
        this.buyingRepository = buyingRepository;
        this.productRepository = productRepository;
        this.sessionMemory = sessionMemory;
    }

    public void savePurchase(LocalDate buyingDate, String provider_name, String provider_adress, String paymentMode, String idBuying) throws PurchaseException {

        Optional<Personel> po = sessionMemory.currentUser();
        Personel p = po.orElse(null);
        if (p == null || p.getRule().getCode() != Rule.GESTIONNAIRE.getCode())
            throw new PurchaseException(ExceptionUtils.USER_NOT_AUTHORIZE, ExceptionUtils.ERROR_FORBIDEN);

        checkPurchaseData(paymentMode, buyingDate, idBuying);
        Purchase purchase = new Purchase(idBuying, buyingDate, provider_name, provider_adress, paymentMode);
        buyingRepository.savePurchase(purchase);

    }

    public void savePurchaseDetails(String idBuying, String idProduct, int buyingQuantity, Double buyingPriceUnit, String tvaUnit, Double currentSellingPrice ) throws PurchaseException {
        PurchaseProductDetail purchaseProductDetail = new PurchaseProductDetail( idBuying, idProduct, buyingQuantity, buyingPriceUnit, tvaUnit, currentSellingPrice);
        purchaseProductDetailRepository.savePurchaseProductDetail(purchaseProductDetail);
    }

    public void savePurchaseDetailsObject(PurchaseProductDetail purchaseProductDetail) {
        purchaseProductDetailRepository.savePurchaseProductDetail(purchaseProductDetail);
    }

    public void updatePurchaseDetails(String idProduct, Double currentSellingPrice, int buyingQuantity) {
        Optional<Product> productOptional = productRepository.getProductById(idProduct);
        if (productOptional.isPresent()) {
            Product productUpdated = productOptional.get();
            productUpdated.updateCurrentSellingPrice(currentSellingPrice);
            productUpdated.updateBuyingQuantity(productUpdated.getStockQuantity(), buyingQuantity);
        }
    }

    public void savePurchasesDetailsAndUpdateStock(List<PurchaseProductDetail> purchaseProductDetails) throws PurchaseException {
        if (CollectionUtils.isNullOrEmpty(purchaseProductDetails)){
            throw new InvalidProductPurchaseException(ExceptionUtils.DETAIL_PRODUCT_PURCHASE_ISNULL, ExceptionUtils.FUNCTIONAL_ERROR);
        }

        purchaseProductDetails.forEach(purchaseProductDetail -> {
            this.savePurchaseDetailsObject(purchaseProductDetail);
            this.updatePurchaseDetails(purchaseProductDetail.getIdProduct(), purchaseProductDetail.getCurrentSellingPrice(), purchaseProductDetail.getBuyingQuantity());
        });
    }

    /**
     * Vérifie si le mode le paiement est renseigné sinon renvoie une exception PAYMENTMODE_PURCHASE_ISNULL
     * @param payment_mode mode de paiement à tester
     * @throws InvalidePaymentPurchaseException InvalidePaymentPurchaseException si le mode de paiement est à null
     */
    private void checkPurchasePaymentMode(String payment_mode) throws InvalidePaymentPurchaseException {
        if (payment_mode == null)
            throw  new InvalidePaymentPurchaseException(ExceptionUtils.PAYMENTMODE_PURCHASE_ISNULL, ExceptionUtils.FUNCTIONAL_ERROR);
    }

    /**
     * Vérifie si la date est renseigné sinon renvoie une exception DATE_PURCHASE_ISNULL
     * Vérifie si la date est inférieure à la date d'aujourd'hui sinon renvoie une exception DATE_PURCHASE_LESSTODAY
     * @param date date à tester
     * @throws InvalideDatePurchaseException InvalidePaymentPurchaseException si le mode de paiement est à null
     */
    private void checkPurchaseDate(LocalDate date) throws InvalideDatePurchaseException  {
        if (date == null)
            throw new InvalideDatePurchaseException(ExceptionUtils.DATE_PURCHASE_ISNULL, ExceptionUtils.FUNCTIONAL_ERROR);
        LocalDate today = LocalDate.now();
        if (date.isAfter(today))
            throw new InvalideDatePurchaseException(ExceptionUtils.DATE_PURCHASE_LESS_TO_DAY, ExceptionUtils.FUNCTIONAL_ERROR);
    }

    /**
     *
     *  Vérifie les données liés à un achat
     *  @param payment_mode mode de paiement à tester
     *  @param date date à tester
     * @throws InvalideDatePurchaseException InvalideDatePurchaseException si la date est null ou supérieur à la date du jour
     * @throws InvalidePaymentPurchaseException InvalidePaymentPurchaseException si le mode de paiement est à null
     */
    private void checkPurchaseData(String payment_mode, LocalDate date, String idBuying) throws PurchaseException {
        checkPurchasePaymentMode(payment_mode);
        checkPurchaseDate(date);
        checkPurchaseIdBuying(idBuying);
    }

    /**
     *
     * @param idBuying vérifie l'id d'un achat
     * @throws PurchaseException exception renvoyé en cas de idbuying à null
     */
    private void checkPurchaseIdBuying(String idBuying) throws PurchaseException {
        if (idBuying == null) {
            throw new InvalidProductPurchaseException(ExceptionUtils.PRODUCT_IS_NULL, ExceptionUtils.FUNCTIONAL_ERROR);
        }
    }

    /**
     *
     * Sauvegarde d'un achat avec plusieurs produits
     * @param idBuying id lié à un achat
     * @param date date  d'achat
     * @param providerName fournisseur des produits
     * @param providerAdress adresse du fournisseur
     * @param paymentMode mode de paiement lié à l'chat
     * @param purchaseProductDetails liste de detail pour chaque produit
     * @throws PurchaseException si un des paramettre est en erreur
     */
    public void savePurchaseWithAllProducts(String idBuying, LocalDate date, String providerName, String providerAdress, String paymentMode, List<PurchaseProductDetail> purchaseProductDetails) throws PurchaseException {

        if (CollectionUtils.isNullOrEmpty(purchaseProductDetails)){
            throw new InvalidProductPurchaseException(ExceptionUtils.PRODUCT_IS_NULL, ExceptionUtils.FUNCTIONAL_ERROR);
        }
        savePurchase(date, providerName, providerAdress, paymentMode, idBuying);
        savePurchasesDetailsAndUpdateStock(purchaseProductDetails);
    }
}
