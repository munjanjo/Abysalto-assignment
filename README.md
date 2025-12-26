# Abysalto – Junior Developer Assignment (Java)

## Opis
Ova aplikacija implementira sustav za upravljanje narudžbama u skladu s tehničkim zadatkom za Junior Developer poziciju.

Aplikacija omogućuje:
- ✅ dodavanje novih narudžbi  
- ✅ pregled postojećih narudžbi  
- ✅ promjenu statusa narudžbi  
- ✅ automatski izračun ukupnog iznosa  
- ✅ sortiranje narudžbi prema ukupnom iznosu  

Podaci se spremaju u bazu podataka (H2 in-memory).

---

## Tehnologije
- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JDBC  
- Spring Security (Basic Auth)  
- H2 Database  
- Maven  

---

## Pokretanje aplikacije

### Preduvjeti
- Java 17+
- Maven

### Pokretanje
```bash
mvn spring-boot:run
```

Aplikacija će biti dostupna na:
```
http://localhost:8080
```

---

## Autentikacija

Svi endpointi su zaštićeni **Basic Authentication** mehanizmom.

**Kredencijali:**
- username: `user`
- password: `password`

---

## Inicijalizacija baze (OBAVEZNO)

Prije korištenja orders endpointa potrebno je inicijalizirati bazu:

```
POST /init-data
```

Ovaj endpoint:
- kreira sve potrebne tablice
- puni bazu početnim testnim podacima (buyer, buyer_address)

⚠️ Bez ovog koraka orders endpointi neće raditi ispravno.

---

## Orders API

### Create order
```
POST /orders
```

#### Request body (primjer)
```json
{
  "order": {
    "buyerId": 1,
    "orderStatus": "PREPARING",
    "orderTime": "2025-12-24T10:30:00",
    "paymentOption": "CASH",
    "deliveryAddressId": 1,
    "contactNumber": "0911111111",
    "currency": "EUR",
    "note": "Bez luka"
  },
  "items": [
    {
      "name": "Pizza",
      "quantity": 2,
      "price": 9.75
    },
    {
      "name": "Cola",
      "quantity": 1,
      "price": 2.50
    }
  ]
}
```

Ukupni iznos (`totalPrice`) se računa automatski na backendu.

---

### Get all orders
```
GET /orders
```

---

### Get order by ID
```
GET /orders/{id}
```

---

### Get order items
```
GET /orders/{id}/items
```

---

### Update order status
```
PATCH /orders/{id}/status
```

```json
{
  "status": "DONE"
}
```

Mogući statusi:
- WAITING_FOR_CONFIRMATION
- PREPARING
- DONE

---

### Sort orders by total price
```
GET /orders/sorted?descending=true
GET /orders/sorted?descending=false
```

---

## Arhitektura

- Controller – tanak sloj (HTTP logika)
- Manager – poslovna logika
- DTO – modeli za API
- Mapper – mapiranje entiteta u DTO
- Repository – pristup bazi
- DatabaseInitializer – inicijalizacija baze

Primijenjeni principi:
- OOP
- DRY
- KISS
- YAGNI

---

## Napomena
Projekt koristi H2 in-memory bazu. Podaci se brišu pri restartu aplikacije.
Nakon svakog restarta potrebno je ponovno pozvati:
```
POST /init-data
```

---

## Autor
Rješenje izrađeno kao dio Abysalto Junior Developer tehničkog zadatka.
