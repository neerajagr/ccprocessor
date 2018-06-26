# ccprocessor
Credit Card Processor
=====================

This project consists of Rest endpoints which allows to add the credit cards to system, charge them and credit against them. There is another endpoint to get all the cards available in the system. This application is build using spring boot and uses the maven dependencies.

The four credit card processor endpoints are:
1.	/add 
2.	/charge
3.	/credit
4.	/getAll

Here add, charge and credit are implemented as POST request and getAll is a GET request.

Add
------------
This is to add a new card and it takes a JSON as input which consists of cardNumber, firstName and lastName.

- The cardNumber provided will be checked using Luhn Algorithm and should not be more than 19 digits. 
- It should contain only numbers. 
- A card once added cannot be added back. 
- The new card will be having default balance as £0.00 and limit as £100.00
- A successful request will return the cardNumber and the default balance.

The input JSON will be as below for the add request:
```
{
  "cardNumber": "4111111111111111",
  "firstName": "Neeraj",
  "lastName": "Agr"
}

```

charge
------

This is to charge the card with the given amount. It will add the balance if the new balance will still be in the limit. The operation is not permitted if the new balance will be beyond the limit.

- The input will be cardNumber, firstName, lastName and the charge. 
- Charge should be in pounds and should start with “£”.
- Negative charges are not supported.

The validation is on the combination of cardNumber, firstName and lastName. Request with only correct combination will be entertained and the balance will be updated in the system. In case of any mismatch, no updates will be made.
The input JSON should be as below:

```
{
  "cardNumber": "1234567788223",
  "firstName": "Ne",
  "lastName": "Ag",
  "charge": "£10"
}
```

credit
------
This is to credit the card balance with the given amount. The balance can go negative in this case. The validations are on the combination of cardNumber, firstName and lastName. Again the charges cannot be negative in this case and should start with “£”.
JSON input structure should be as below:

```
{
  "cardNumber": "1234567788223",
  "firstName": "Ne",
  "lastName": "Ag",
  "charge": "£10"
}

```

getAll
------

This is a GET request to fetch all the cards available. This does not require any input and gives the JSON response containing all the cards.

Handling Error
--------------

Errors has been handled for all the endpoints in a way so that JSON response will be having an error JSON node with appropriate message field.


How to Use
----------
This application is designed to use the in memory H2 database.

The application should now be up and running.

To use these endpoints, POSTMAN tools can be used. The URLs will be as below if the application is hosted on the localhost:
- http://localhost:8080/add  --> request type POST
- http://localhost:8080/charge --> request type PUT
- http://localhost:8080/credit --> request type PUT
- http://localhost:8080/getAll --> request type GET

While making the POST/PUT request, JSON input is required in the same format as mentioned above. This will be given as the request body with type as JSON.
