# Quiz-App
Simple Quiz App API with Spring Boot (JPA , mysql )

# Test the API here :

[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.gw.postman.com/run-collection/28660393-e22fa172-8b92-4e85-953a-59b611a2591f?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D28660393-e22fa172-8b92-4e85-953a-59b611a2591f%26entityType%3Dcollection%26workspaceId%3D68d3dd90-f34f-4cb2-99d2-4fb79454bbb9)


# How to Run

1- Clone the project repository from Git (if it's not already cloned).

2- Import the project into your favorite Java IDE (e.g., IntelliJ, Eclipse, etc.).

3- create public key and private key in a new folder under /src/main/resources/certs 

run this commands in terminal
```code
# create rsa key pair
openssl genrsa -out keypair.pem 2048
# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem
# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

4- Build the project to resolve dependencies.


# Project Architecture NTier
![271463980-ecd11189-f496-456e-a713-6a28146b2be3](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/42429b4a-bbe4-4762-8a7e-0146b8dfc7a4)


# features

# 1- User can add questions
# Example : 
I sent the question information that I want to add in json via postman

![Screenshot (130)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/d9c135f7-eb4e-4d56-905f-28db7d4ec45a)
The question has been added in the database 
![Screenshot (131)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/65c19ba9-10a4-4ce5-a907-b5113fe63f87)

# 2- User can get all questions
# Example with postman :
![Screenshot (132)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/94066b90-ecd0-481a-8bec-d6ed108096a2)

# 3- User can see all questions from the same category
# Example : with postman
![Screenshot (133)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/e7655285-db52-4d7e-9062-f0df05024b48)

# 4- User can Update and delete questions
# Example with postman :
- update this question with id 4 
![Screenshot (134)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/c750d7c1-4721-4483-8592-0648bac0373f)

![Screenshot (135)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/ad1e4764-5112-4214-878a-b45c833708e3)

![Screenshot (136)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/7f36f5ea-f91d-4104-af75-83ff9e048996)

# 5- The user can create quiz by specifying the category, the number of questions, and the name of the quiz,
# The quiz will be created automatically with questions from those in the database that have the same category that was chosen.

![Screenshot (137)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/3eb11ea2-d9c9-407d-b9c1-7b5e4250108b)

# 6- user can get quiz by id to start the quiz

![Screenshot (138)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/c4280956-fec6-4740-96cc-34800a6baa87)

# 7- user can submit the quiz and get the results automatically.
# Example with postman :
![Screenshot (139)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/763a50d7-f953-4354-af8d-743f9d78811e)

# database 
![Screenshot (163)](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/b769be35-53fc-48a0-9452-092599109da2)

# project Class Diagram

![quizapp](https://github.com/Sameh1Tarek/Quiz-App/assets/108232157/134b3c7a-4ecc-49d0-9674-a91973c3d20d)





