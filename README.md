Http Запросы отправлялись через Postman:
GET localhost:8189/category
GET localhost:8189/category?id=12
POST localhost:8189/category body {"categoryName": "delicates"}
PUT localhost:8189/category body {"categoryId": 17, "categoryName": "salad"}
DELETE localhost:8189/category?id=17
GET localhost:8189/recipe?name=shakarap
POST localhost:8189/recipe body {  "recipeName": "karri", "ingredients": "onion-1 unit, olive oil-1tbs, salt-1", "instructions": "cut tomatoes, cucambers and onion salt, pour oil and mix", "quantity": 5,"categoryId": 6}
PUT localhost:8189/recipe?id=6 body {"quantity": 4,"categoryId": 7}
DELETE localhost:8189/recipe?id=6
GET localhost:8189/auth?email=third@mail.com
POST localhost:8189/register body {"name": "third","password": "pass3","email": "third@mail.com","token": 3}
PUT localhost:8189/user?email=third@mail.com body {"name": "sdfdsfsdfsd"}
DELETE localhost:8189/delete?email=third@mail.com
