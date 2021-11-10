# MAF-Challenge


Documentation Notes:

The Documentaion has been generated using swagger schema after been modified.
Swagger is implemented in this project so you can see the full schema by visiting 
http://localhost:8080/v2/api-docs
or you can use the swagger UI 
http://localhost:8080/swagger-ui.html


Projects Notes:

N-Tier architicture has been used in this project
The DAO layer is hard coded
This Application contanins 3 services
1- retrieve the response of the hotel provider in our case (BestHotel,CrazyHotel). one service for each. and you can add more providers.
2- retrieve all the available hotels from all the providers in a sorted way based ont the rating as mentioned in the requirements (The highest rating shows as the first item)


Testing Notes:

The coverage report for this project is attached if you would like to take a look. please **clone** the coverage folder and open index.html
according to the coverage report there are 6 packages that are covered 100%.
the tests involved the following for every service.
1- Test if reponse retunred with a content
2- Test if reponse retunred with empty content
3- Test to throw exception if the request is invalid




