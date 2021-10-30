# NextDoor Farm Backend API

- **Type**: API docs
- **Authors**: Andrey Volkov
- **Version**: 0.1 (MVP)

## Abstract

This is a description of basic API (MVP) for `NextDoor Farm` backend application used in WEB & Android applications.

## Table of Contents

* [Auth Controller](#auth-controller)
* [Customers Controller](#customers-controller)
* [Farmesr controller](#farmers-controller)
* [Products controller](#products-controller)
* [Orders controller](#orders-controller)

## Auth Controller

### `/log-in`

**Description**: Check whether username and password are valid.

**Method**: `POST`

**Body**: `UserLogInDto`

```json
{
  "username": "ivan",
  "password": "pass123",
  "userType": "customer"
}
```

Note: UserType can be either `customer` or `farmer`.

**Response**: `UserLoggedInDto`

```json
{
  "id": 1,
  "username": "ivan",
  "userType": "customer",
  "token": "jwt_token",
  "ttl": 1000000
}
```

### `/sign-up`

**Description**: Create new user of two different types - customer and farmer.

**Method**: `POST`

**Body**: `UserSignInDto`

```json
{
  "firstName": "ivan", 
  "lastName": "ivanov",
  "username": "ivan",
  "email": "ivan@yandex.ru",
  "password": "pass123",
  "userType": "customer"
}
```

Note: userType can be either `customer` or `farmer`.

**Response**: `UserSignedInDto`

```json
{
  "id": 1,
  "username": "ivan",
  "userType": "customer",
  "token": "jwt_token",
  "ttl": 1000000
}
```

## Customers controller

All methods below will use header `Authorization: Bearer $jwtToken`.

### `/customers`

---

**Description**: Return customer by id, extracted from JWT token. Orders will be displayed on the same screen as customer.

**Method**: `GET`

**Response**: `CustomerAndOrdersDto`

```json
{
  "id": 1,
  "firstName": "ivan",
  "lastName": "ivanov",
  "address": "Moscow, Kremlin",
  "username": "ivan",
  "email": "ivan@yandex.ru",
  "phone": "+79999542153",
  "orders": [
    {
      "id": 123,
      "products": [
        {
          "id": 1,
          "name": "carrot",
          "description": "fresh carrot",
          "pricePerKg": 40,
          "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
          "amount": 1
        }
      ],
      "status": "fillingIn"
    }
  ]
}
```

---

**Description**: Update customer by id, extracted from JWT token.

**Method**: `PUT`

**Body**: `CustomerDto`

```json
{
  "id": 1,
  "firstName": "ivan", 
  "lastName": "ivanov", 
  "address": "Moscow, Kremlin",
  "username": "ivan",
  "email": "ivan@yandex.ru",
  "phone": "+79999542153"
}
```

**Response**: `CustomerAndOrdersDto`

```json
{
  "id": 1,
  "firstName": "ivan",
  "lastName": "ivanov",
  "address": "Moscow, Kremlin",
  "username": "ivan",
  "email": "ivan@yandex.ru",
  "phone": "+79999542153",
  "orders": [
    {
      "id": 123,
      "products": [
        {
          "id": 1,
          "name": "carrot",
          "description": "fresh carrot",
          "pricePerKg": 40,
          "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
          "amount": 1
        }
      ],
      "status": "fillingIn"
    }
  ]
}
```

## Farmers controller

All methods below will use header `Authorization: Bearer $jwtToken`.

### `/farmers`

---

**Description**: Return farmer by id, extracted from JWT token. Products will be displayed on the same screen as farmer.

**Method**: `GET`

**Response**: `FarmerAndProductsDto`

```json
{
  "id": 1,
  "firstName": "Kate",
  "lastName": "Popova",
  "address": "Moscow, Red Square",
  "username": "kate",
  "email": "kate@yandex.ru",
  "phone": "+79999542153",
  "description": "Info",
  "rating": 7.8,
  "products": [
    {
      "name": "carrot",
      "description": "fresh carrot",
      "pricePerKg": 40,
      "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
      "amount": 30
    }
  ]
}
```

Note: the id of the user will be extracted from jwt token.

---

**Description**: Update farmer by id, extracted from JWT token.

**Method**: `PUT`

**Body**: `FarmerDto`

```json
{
  "id": 1,
  "firstName": "Kate",
  "lastName": "Popova",
  "address": "Moscow, Red Square",
  "username": "kate",
  "email": "kate@yandex.ru",
  "phone": "+79999542153",
  "description": "Info",
  "rating": 7.8
}
```

Note: rating [0;10]

**Response**: `FarmerAndProductsDto`

```json
{
  "id": 1,
  "firstName": "ivan",
  "lastName": "ivanov",
  "address": "Moscow, Kremlin",
  "username": "ivan",
  "email": "ivan@yandex.ru",
  "phone": "+79999542153",
  "description": "Info",
  "rating": 7.8,
  "products": [
    {
      "name": "carrot",
      "description": "fresh carrot",
      "pricePerKg": 40,
      "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
      "amount": 30
    }
  ]
}
```

## Products controller

All methods below will use header Authorization: Bearer $jwtToken.

### `/products`

**Description**: Return list of available products.

**Method**: `GET`

**Response**: `List<ProductDto>`

```json
[
  {
    "id": 1,
    "name": "carrot",
    "description": "fresh carrot",
    "pricePerKg": 40,
    "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
    "amount": 30,
    "farmerId": 1
  },
  {
    "id": 2,
    "name": "pear",
    "description": "fresh pear",
    "pricePerKg": 34,
    "imageLink": "https://jooinn.com/images/pear-1.jpg",
    "amount": 52,
    "farmerId": 1
  }
]
```

### `/products/{id}`

**Description**: Return product by id.

**Method**: `GET`

**Response**: `ProductDto`

```json
{
  "id": 1,
  "name": "carrot",
  "description": "fresh carrot",
  "pricePerKg": 40,
  "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
  "amount": 30,
  "farmerId": 1
}
```

### `/products`

**Description**: Create product for farmer.

**Method**: `POST`

**Body**: `ProductDto`

```json
{
  "name": "carrot",
  "description": "fresh carrot",
  "pricePerKg": 40,
  "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
  "amount": 30
}
```

**Response**: `ProductDto`

```json
{
  "id": 1,
  "name": "carrot",
  "description": "fresh carrot",
  "pricePerKg": 40,
  "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
  "amount": 30,
  "farmerId": 1
}
```

### `/products`

**Description**: Update product for farmer.

**Method**: `PUT`

**Body**: `ProductDto`

```json
{
  "id": 1,
  "name": "carrot",
  "description": "fresh carrot",
  "pricePerKg": 40,
  "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
  "amount": 30
}
```

**Response**: `ProductDto`

```json
{
  "id": 1,
  "name": "carrot",
  "description": "fresh carrot",
  "pricePerKg": 40,
  "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
  "amount": 30,
  "farmerId": 1
}
```

## Orders controller

All methods below will use header Authorization: Bearer $jwtToken.

### `/orders`

---

**Description**: Add new product to customer's order.

**Method**: `PUT`

**Body**: `OrderProductDto`

```json
{
  "productId": 1
}
```

**Response**: `OrderDto`

```json
{
  "id": 123,
  "products": [
    {
      "id": 1,
      "name": "carrot",
      "description": "fresh carrot",
      "pricePerKg": 40,
      "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
      "amount": 1
    }
  ],
  "status": "fillingIn"
}
```

---

**Description**: Delete specified amount of products from customer's order.

**Method**: `DELETE`

**Body**: `OrderProductDto`

```json
{
  "productId": 1,
  "amount": 1
}
```

**Response**: `OrderDto`

```json
{
  "id": 123,
  "products": [],
  "status": "fillingIn"
}
```

### `/orders/submit`

**Description**: Change status of the order to 'submitted'.

**Method**: `POST`

**Response**: `OrderDto`

```json
{
  "id": 123,
  "products": [
    {
      "id": 1,
      "name": "carrot",
      "description": "fresh carrot",
      "pricePerKg": 40,
      "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
      "amount": 1
    }
  ],
  "status": "submitted"
}
```

### `/orders/cancel`

**Description**: Change status of the order to 'canceled'.
 
**Method**: `POST`

**Body**: `OrderDto`

```json
{
  "id": 1
}
```

**Response**: `OrderDto`

```json
{
  "id": 123,
  "products": [
    {
      "id": 1,
      "name": "carrot",
      "description": "fresh carrot",
      "pricePerKg": 40,
      "imageLink": "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-benefits-pin.jpg",
      "amount": 1
    }
  ],
  "status": "canceled"
}
```
