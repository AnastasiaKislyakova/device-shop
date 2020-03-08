# ak

## Usage
```
GET /api/<entity>
GET /api/<entity>/id
POST /api/<entity> (Body: JSON)
```

supported entities:

`customer: `

| Field Name | Type                            | Description                       |   Example  |
|:----------:|:-------------------------------:|-----------------------------------|:----------:|
|  firstName | string                          |                        |    Петр    |
| middleName | string                          |                        |  Петрович  |
|  lastName  | string                          |                        |   Петров   |
|  birthdate | string, date format: dd.MM.YYYY | birth date             | 25.04.2019 |
|     id     | Integer number                  | unique id generated upon creation |    9000    |

`device: `

|    Field Name   |               Type              |             Description             |                Example                |
|:---------------:|:-------------------------------:|:-----------------------------------:|:-------------------------------------:|
|    deviceType   |              string             | device type (from a predefined set) | Smartphone Laptop Smart Watch Tablet  |
|    modelName    |              string             |                                     |              Galaxy S10+              |
| manufactureDate | string, date format: dd.MM.YYYY | date of issue                       |               25.04.2019              |
|   manufacturer  |              string             |                                     |                Samsung                |
|    colorName    |              string             |                                     |                 черный                |
|     colorRGB    |          Integer number         | integer representation (rgb)        |                   0                   |
|      price      |          Integer number         | integer price                       |                12499000               |
|        id       |          Integer number         | unique id generated upon creation   |                1126970                |

`bill: `

|    Field Name    |                     Type                     |             Description            |       Example       |
|:----------------:|:--------------------------------------------:|:----------------------------------:|:-------------------:|
|    customerId    |                Integer number                |                                    |        9001         |
|    totalPrice    |                Integer number                | integer price represented in penny |       31415926      |
| purchaseDateTime | String, date/time format:dd.MM.YYYY HH:mm:ss | purchase time and date             | 01.01.2019 07:50:22 |
|       items      |               List of BillItem               |                                    |                     |


`BillItem: ` (as part of `bill`)

| Field Name |      Type      |           Description           | Example |
|:----------:|:--------------:|:-------------------------------:|:-------:|
|  deviceId  | Integer number |                                 | 1004709 |
|  quantity  | Integer number | number of devices               |   100   |
|    price   | Integer number | price at the moment of purchase |   490   |


##Examples

### Device

Post /api/device  
```
{
    "deviceType": "Smart Watches",
    "price": 2000,
    "modelName": "MyBand 2",
    "colorName": "Черный",
    "colorRGB": "0",
    "manufactureDate": "01.01.2017",
    "manufacturer": "Xiaomi"
} 


Get /api/device/15
Get /api/device
Get /api/device?priceTo=2100&priceFrom=1900&colorName=Черный&orderBy=price,-manufacturer


Query Params for device: 
    deviceType, price, priceTo, priceFrom, manufactureDate, manufactureDateTo, manufactureDateFrom, 
    colorName, colorRGB, modelName, manufacturer, 
    page (default value = 1), pageItems (default value = 10),
    orderBy (deviceType, price,  manufactureDate, colorName, colorRGB, modelName, manufacturer)
```

### Customer

Post /api/customer 
```
{
	"firstName" : "Ivan",
	"lastName" : "Ivanov",
	"middleName" : "Ivanovich",
	"birthdate" : "02.01.2011"
}  

Get /api/customer/16
Get /api/customer
Get /api/customer?firstName=Ivan&orderBy=-middleName,lastName,-birthdate


Query Params for customer: 
    firstName, lastName, middleName, birthdate, birthdateFrom, birthdateTo,
    page (default value = 1), pageItems (default value = 10),
    orderBy (firstName, lastName, middleName, birthdate)
```

### Bill

Post /api/bill
```
{
	"customerId" : "20",
	"items" : [{
		"deviceId" : "19",
		"quantity" : "2",
		"price" : "2200"
	},
	{
		"deviceId" : "17",
		"quantity" : "2",
		"price" : "2000"	
	}]
}


Get /api/bill/22
Get /api/bill
Get /api/bill?customerId=19&deviceIds=17,19&orderBy=purchaseDateTime,-totalPrice

Query Params for bill:
    customerId, totalPrice, totalPriceTo, totalPriceFrom, purchaseDateTime, purchaseDateTimeTo, purchaseDateTimeFrom, deviceIds
    page (default value = 1), pageItems (default value = 10),
    orderBy(customerId, totalPrice, purchaseDateTime)
```
