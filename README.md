## Personalized Restaurants Recommendation Service

This is a restaurant search and recommendation web service (backend) to improve personal experience for restaurant seekers. The project would provide a Java service and a database to process and store data.

`Preparation: Eclipse Java EE, Apache Tomcat server, Postman`
__________________


### What is implemented in this project?
- API(by Yelp API)
- Show items near your location (namely SearchItems)
- Set favored items (namely VisitHistory)
- Recommend new items (namely RecommendItems)
- HTTP methods (GET, POST, PUT, DELETE)

### What is Yelp API?
It is an open API for public users to access information from Yelp such that we can fetch real restaurant information including name, description, location, category, etc. It plays a role as the following:
![api](https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/yelp%20api.jpg)

### How does database work?
When the number of user is large, we need DSMS to manage data. We can fullfill functions like store all of informations related to each user, retrieve data based on conditions quackly, update, add or delete data timely, access data with authorization. We involve four tables in our case: 
*users - store user information.
*items - store item information.
*history - store user favorite/purchase history (many-to-many).
*category - store item-category relationship (many-to-many).








