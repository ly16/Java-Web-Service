## Personalized Restaurants Recommendation Service

This is a restaurant search and recommendation web service (backend) to improve personal experience for restaurant seekers. The project would provide a Java service and a database to process and store data.

`Preparation: Eclipse Java EE, Apache Tomcat server, Postman`
__________________


## What is implemented in this project?
- API(by Yelp API)
- Show items near your location (namely SearchItems)
- Set favored items (namely VisitHistory)
- Recommend new items (namely RecommendItems)
- HTTP methods (GET, POST, PUT, DELETE)

## What is Yelp API?
It is an open API for public users to access information from Yelp such that we can fetch real restaurant information including name, description, location, category, etc. It plays a role as the following:
<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/api.png" height="60%" width="60%" >

## How does database work?
When the number of user is large, we need DSMS to manage data. We can fullfill functions like store all of informations related to each user, retrieve data based on conditions quackly, update, add or delete data timely, access data with authorization. We involve four tables in our case: 
![table](https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/tablessql.png)
+ users - store user information.
+ items - store item information.
+ history - store user favorite/purchase history (many-to-many).
+ category - store item-category relationship (many-to-many).
<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/db.png" height = 60%
width=60%>

e.g. We want to get the table of history `http://localhost:8080/Titan/history?user_id=1111`
![histroy](https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/histry_postman.png)


## How does recommendation system work？
- User-based method(similarity of users) 
- Item-base method(similarity of items)
- Content-based recommendation(similarity of characterastics)

When the recommendation system does not contain enough users/data, we use contend-based method firstly, combined with user/ item-based recommendation method. In this case, We recommend restaurants with similar categories as those in history table and rank items based on distance.


##  Results & Screen Shots
- Items in history table









