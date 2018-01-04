## Personalized Restaurants Recommendation Service

This is a restaurant search and recommendation web service (backend) to improve personal experience for restaurant seekers. The project would provide a Java service and a database to process and store data.

`Preparation: Eclipse Java EE, Apache Tomcat server, Postman`
__________________


### What are implemented in this project?
- API(by Yelp API)
- Show items near your location (named SearchItems)
- Set favored items (named VisitHistory)
- Recommend new items (named RecommendItems)
- HTTP methods (GET, POST, PUT, DELETE)

### What is Yelp API?
It is an open API for public users to access information from Yelp such that we can fetch real restaurant information including name, description, location, category, etc. It plays a role as the following:
<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/api.png" height="60%" width="60%" >

### How does database work?
When the number of user is large, we need DSMS to manage data. We can fullfill functions like store all of informations related to each user, retrieve data based on conditions quackly, update, add or delete data timely, access data with authorization. We involve four tables in our case: 
<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/tablessql.png" height=80% width=80%>
+ users - store user information.
+ items - store item information.
+ history - store user favorite/purchase history (many-to-many).
+ category - store item-category relationship (many-to-many).
<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/db.png" height = 60%
width=60%>

E.g. we want to get the table of history 

`http://localhost:8080/Titan/history?user_id=1111`

<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/histry_postman.png" height=80% width=80%>


### How does recommendation system work？
- User-based method(similarity of users) 
- Item-base method(similarity of items)
- Content-based recommendation(similarity of characterastics)

When the recommendation system does not contain enough users/data, we use contend-based method firstly, combined with user/ item-based recommendation method. In this case, We recommend restaurants with similar categories as those in history table and rank items based on distance.

____________
###  Results & Screen Shots

- Items in history table
<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/gistory_mysql.png" height=80% width=80%>

- Recommendations by different latitude and longitude

`http://localhost:8080/Titan/recommendation?user_id=1111&lat=37.38&lon=-122.08`

<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/server.png" height=80% width=80%>

<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/recom_CA.png" height=80% width=80%>

____________________

`http://localhost:8080/Titan/recommendation?user_id=1111&lat=40.73&lon=-73.94`

<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/recom_nY.png" height=80% width=80%>

___________________

`http://localhost:8080/Titan/recommendation?user_id=1111&lat=27.99&lon=-81.76`

<img src="https://github.com/ly16/Personalized-Restaurants-Recommendation-Service/blob/master/results/recommendation_FL.png" height=80% width=80%>



