Feature: gestion des achats

  Le gestionnaire peut déclarer un achat dans le système et le consulter

  Background:
    Given  personel existant:
      | id  | firstName | lastName | role         |
      | abc | Said      | KABENE   | gestionnaire |
      | efg | Ghiles    | METAHRI  | collaborateur|
    Given produits existant:
      | idProduct | productName | description        | sellingPrice | categoryProduct | stockQuantity |
      | abc       | Heinken     | boisson alcoolisée | 12.0         | Bière           | 5             |
      | 123       | Coca cola   | boisson gazeuse    | 5.0          | boisson         | 7             |
      | 456       | Cristaline  | Eau minérale       | 0.20         | Eau             | 5             |


  Scenario Outline: déclarer un achats dans le système en tant que gestionnaire authentifié avec un seul produit enregistrer
    Given je suis authentifié en tant que "<lastName>"
    When  je tente de déclarer un achat dont les informations sont les suivantes: date : "<buying_date>", fournisseur : "<provider_name>", "<provider_adress>", mode de paiement "<payment_mode>", identifiant "<idBuying>"
    And   j'enregistre le dètails de l'achat "<idBuying>" liès aux produits achetés suivant:
      | idProduct    | buyingQuantity    | buyingPriceUnit    | TvaUnit    | currentSellingPrice |
      | <idProduct1> | <buyingQuantity1> | <buyingPriceUnit1> | <TvaUnit1> | <sellingPrice1>     |

    And  pour les produits existant, je mets à jour le stock. si le prix de vente est renseignè je le mets également à jour:
      | idProduct    | currentSellingPrice | buyingQuantity    |
      | <idProduct1> | <sellingPrice1>     | <buyingQuantity1> |

    Then l'enregistrement de l'achat "<idBuying>" est effectif
    And  la quantité de stock du produit "<idProduct1>" est de "<stockQuantity1>"
    And  la prix de vente du produit "<idProduct1>" est de "<sellingPrice1>"
    Examples:
      | lastName | idBuying | buying_date | provider_adress              | provider_name | payment_mode | idProduct1 | sellingPrice1 | buyingQuantity1 | buyingPriceUnit1 | stockQuantity1 | TvaUnit1 |
      | KABENE   | noway    | 18/12/2023  | 43 rue Archereau 75019 Paris | metro         | cash         | abc        | 3             | 7               | 1                | 12             | 20%      |


  Scenario Outline: déclarer un achats dans le système en tant que gestionnaire authentifié avec plusieurs produit

    Given je suis authentifié en tant que "<lastName>"

    When  je tente de déclarer un achat dont les informations sont les suivantes: date : "<buying_date>", fournisseur : "<provider_name>", "<provider_adress>", mode de paiement "<payment_mode>", identifiant "<idBuying>"
    And   j'enregistre le dètails de plusiers achat "<idBuying>" liès aux produits achetés suivant et je mets à jour le stock et le prix de chaque produit :
      | idProduct    | buyingQuantity    | buyingPriceUnit    | TvaUnit    | currentSellingPrice |
      | <idProduct1> | <buyingQuantity1> | <buyingPriceUnit1> | <TvaUnit1> | <sellingPrice1>     |
      | <idProduct2> | <buyingQuantity2> | <buyingPriceUnit2> | <TvaUnit2> | <sellingPrice2>     |

    Then l'enregistrement de l'achat "<idBuying>" est effectif
    And  la prix de vente du produit "<idProduct1>" est de "<sellingPrice1>"
    And  la quantité de stock du produit "<idProduct1>" est de "<stockQuantity1>"

    And  la prix de vente du produit "<idProduct2>" est de "<sellingPrice2>"
    And  la quantité de stock du produit "<idProduct2>" est de "<stockQuantity2>"
    Examples:
      | lastName | idBuying | buying_date | provider_adress          | provider_name | payment_mode | idProduct1 | sellingPrice1 | buyingQuantity1 | buyingPriceUnit1 | stockQuantity1 | TvaUnit1 | idProduct2 | sellingPrice2 | buyingQuantity2 | buyingPriceUnit2 | stockQuantity2 | TvaUnit2 |
      | KABENE   | 1728     | 18/12/2023  | 5 Rue saint gobain 93300 | auchan        | cash         | 123        | 88            | 10              | 44               | 17             | 20%      | 456        | 55            | 15              | 30               | 20             | 20%      |


  Scenario Outline: lorsque j'effectue un achat, la date doit etre obligatoire et inférieure ou égale à la date du jour.

    Given je suis authentifié en tant que "<lastName>"

    When  je tente de déclarer un achat dont : id : "<idBuying>" avec une date null ou superieure à la date du jour:
      | buying_date   | provider_name   | provider_adress   | payment_mode   |
      | <buying_date> | <provider_name> | <provider_adress> | <payment_mode> |

    Then  l'enregistrement de l'achat "<idBuying>" ne peut pas etre effectué

    Examples:
      | lastName | idBuying | buying_date | provider_adress          | provider_name | payment_mode |
      | KABENE   | 1728     |             | 5 Rue saint gobain 93300 | auchan        | cash         |
      | KABENE   | 1728     | 30/03/2040  | 5 Rue saint gobain 93300 | auchan        | cash         |


  Scenario Outline: lorsque j'effectue un achat, le mode de paiement doit etre obligatoire.

    Given je suis authentifié en tant que "<lastName>"

    When  je tente de déclarer un achat id : "<idBuying>"  avec un mode de paiement non renseigné "<payment_mode>":
      | buying_date   | provider_name   | provider_adress   | payment_mode   |
      | <buying_date> | <provider_name> | <provider_adress> | <payment_mode> |

    Then  l'enregistrement de l'achat "<idBuying>" ne peut pas etre effectué

    Examples:
      | lastName | idBuying | buying_date | provider_adress          | provider_name | payment_mode |
      | KABENE   | 1728     | 31/03/2024  | 5 Rue saint gobain 93300 | auchan        |              |


  Scenario Outline: lorsque j'effectue un achat, le produit doit etre obligatoire

    Given je suis authentifié en tant que "<lastName>"

    When  je tente de déclarer un achat dont les informations sont les suivantes: date : "<buying_date>", fournisseur : "<provider_name>", "<provider_adress>", mode de paiement "<payment_mode>", identifiant "<idBuying>", sans renseigner de produit

    Then  l'enregistrement de l'achat "<idBuying>" ne peut pas etre effectué et renvoie un message d'erreur à l'utilisateur

    Examples:
      | lastName | idBuying | buying_date | provider_adress          | provider_name | payment_mode | idProduct |
      | KABENE   | 1728     | 31/12/2024  | 5 Rue saint gobain 93300 | auchan        | cash         |           |

  Scenario Outline: lorsque je declare un achat, le detail d'un produit doit etre obligatoire à renseigner

    Given je suis authentifié en tant que "<lastName>"

    When  je declare un achat "<idBuying>" avec une liste de produit dont le detail est incomplet
      | idProduct   | buyingQuantity   | buyingPriceUnit   | tvaUnit   | currentSellingPrice   |
      | <idProduct> | <buyingQuantity> | <buyingPriceUnit> | <tvaUnit> | <currentSellingPrice> |

    Then  l'enregistrement de l'achat "<idBuying>" ne peut pas etre effectué et renvoie un message d'erreur à l'utilisateur

    Examples:
      | lastName | idProduct | buyingQuantity | buyingPriceUnit | tvaUnit | currentSellingPrice | idBuying
      | KABENE   |           | 10             | 12              | 20      | 30                  | abc
      | KABENE   | 1728      |                | 12              | 20      | 30                  | abc
      | KABENE   | 1728      | 10             |                 | 20      | 30                  | abc
      | KABENE   | 1728      | 10             | 12              |         | 30                  | abc
      | KABENE   | 1728      | 10             | 12              | 20      |                     | abc



  Scenario Outline: lorsque j'enregitre un achat avec un role spécifié.

    Given je suis authentifié en tant que "<lastName>"

    When  je tente de déclarer un achat id : "<idBuying>" avec un role autre que gestionnaire :
      | buying_date   | provider_name   | provider_adress   | payment_mode   |
      | <buying_date> | <provider_name> | <provider_adress> | <payment_mode> |

    Then  l'enregistrement de l'achat "<idBuying>" ne peut pas etre effectué

    Examples:
      | lastName | idBuying | buying_date | provider_adress          | provider_name | payment_mode |
      | METAHRI  | 1728     | 31/03/2024  | 5 Rue saint gobain 93300 | auchan        | cash         |