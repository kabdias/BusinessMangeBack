package com.tsconsulting.businessManage.application.domain.utils;

public class ExceptionUtils {

    public final static String INVALIDATE_DATE_PURCHASE = "l'achat ne peut pas etre effectué car la date n'est pas defini";
    public final static String DATE_PURCHASE_ISNULL = "l'achat ne peut pas etre effectué car la date n'est pas defini";
    public final static String DATE_PURCHASE_LESS_TO_DAY = "l'achat ne peut pas etre effectué car la date est supèrieure à la date du jour";
    public static final String PAYMENTMODE_PURCHASE_ISNULL = "l'achat ne peut pas etre effectué car le mode de paiement n'est pas defini";
    public final static String INVALIDATE_PATERN_DATE_PURCHASE = "format de la date invalide, doit respecter le patern dd/MM/yyyy";
    public final static String UNKNOWN_ERROR_PURCHASE = "Erreur lors du processus d'achat";
    public final static String PRODUCT_IS_NULL = "Le produit n'est pas renseigné";
    public static final String ERROR_WHILE_RECORDING_PRODUCT = "Achat avec l'ID n'a pas pu être enregistré";
    public static  final  String DETAIL_PRODUCT_PURCHASE_ISNULL = "le detail du produit n'est pas renseigné";
    public static  final  String TVA_NOT_VALID = "La tva n'est pas valide";
    public static  final  String BUYINGQANTITY_IS_INVALIDE = "La quantité du produit est invalide";
    public static  final  String BUYINGPRICEUNIT_IS_INVALIDE = "Le prix d'achat du produit est invalide";
    public static  final  String CURRENTSELLINGPRICE_IS_INVALIDE = "Le prix de vente est inferieure au prix d'achat";

    public static final String  PURCHASEDETAIL_NOVALID = "Le detail du produit n'est pas valide";
    public static final String  PARAMETER_INNULL = "un des paramettres est null";

    public static final String USER_NOT_AUTHORIZE = " utilisateur non authorisé";
    public final static int FUNCTIONAL_ERROR = 400;
    public final static int SECURITY_ERROR = 403;
    public final static int UNKNOWN_ERROR = 503;
    public final static int ERROR_FORBIDEN = 401;


}
