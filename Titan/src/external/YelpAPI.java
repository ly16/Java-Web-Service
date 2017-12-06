
package external;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import entity.Item;
import entity.Item.ItemBuilder;


public class YelpAPI {
  private static final String API_HOST = "https://api.yelp.com";
  private static final String DEFAULT_TERM = "dinner";
  private static final int SEARCH_LIMIT = 20;
  private static final String SEARCH_PATH = "/v3/businesses/search";

  private static final String TOKEN_HOST = "https://api.yelp.com/oauth2/token";
  private static final String CLIENT_ID = "your_CLIENT_ID";
  private static final String CLIENT_SECRET = "your_CLIENT_SECRET";
  private static final String GRANT_TYPE = "client_credentials";
  private static final String TOKEN_TYPE = "Bearer";

  // Cache your access token so you get faster access to our data by eliminating an extra request
  // for each API call.
  private static String accessToken;

  public YelpAPI() {}


  /**
   * Create and send a request to Yelp Token Host and return the access token
   */
  private String getAccessToken() {
    if (accessToken != null) {
      return accessToken;
    }
 
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(TOKEN_HOST).openConnection();

      // set HTTP POST method
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");

      // add request body
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
      String query = String.format("grant_type=%s&client_id=%s&client_secret=%s", GRANT_TYPE,
          CLIENT_ID, CLIENT_SECRET);
      wr.write(query.getBytes());

      // get response
      int responseCode = connection.getResponseCode();
      System.out.println("\nSending 'POST' request to URL : " + TOKEN_HOST);
      System.out.println("Response Code : " + responseCode);

      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

      accessToken = (new JSONObject(response.toString())).getString("access_token");
      System.out.println("Get access token : " + accessToken);
      return accessToken;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Creates and sends a request to the Search API by term and location.
   */
  public List<Item> search(double lat, double lon, String term) {
    String latitude = lat + "";
    String longitude = lon + "";
    if (term == null || term.isEmpty()) {
      term = DEFAULT_TERM;
    }
    term = urlEncodeHelper(term);
    String query = String.format("term=%s&latitude=%s&longitude=%s&limit=%s", term, latitude,
        longitude, SEARCH_LIMIT);
    String url = API_HOST + SEARCH_PATH;
    try {
      HttpURLConnection connection =
          (HttpURLConnection) new URL(url + "?" + query).openConnection();

      // optional default is GET
      connection.setRequestMethod("GET");

      // add request header
      connection.setRequestProperty("Authorization", TOKEN_TYPE + " " + getAccessToken());

      int responseCode = connection.getResponseCode();
      System.out.println("\nSending 'GET' request to URL : " + url + "?" + query);
      System.out.println("Response Code : " + responseCode);

      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

      // Get businesses array only.
      JSONObject jsonObject = new JSONObject(response.toString());
      JSONArray array = (JSONArray) jsonObject.get("businesses");

      // Convert it to list of items.
      return getItemList(array);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
 private String urlEncodeHelper(String term) {
	    try {
	      term = java.net.URLEncoder.encode(term, "UTF-8");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return term;
	  }


  /**
   * Queries the Search API based on the command line arguments and takes the first result to query
   * the Business API.
   */
  private void queryAPI(double lat, double lon) {
	    List<Item> list = search(lat, lon, null);
	    try {
	      for (Item item : list) {
	        // This is a thin version of the original JSONObject fetched from Yelp.
	        JSONObject jsonObject = item.toJSONObject();
	        System.out.println(jsonObject);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

  }
  
//Convert JSONArray to a list of item objects.
private List<Item> getItemList(JSONArray array) throws JSONException {
  List<Item> list = new ArrayList<>();

  for (int i = 0; i < array.length(); i++) {
    JSONObject object = array.getJSONObject(i);
    // Parse json object fetched from Yelp API specifically.
    ItemBuilder builder = new ItemBuilder();
    // Builder pattern gives us flexibility to construct an item.
    builder.setItemId(object.getString("id"));
    JSONArray jsonArray = (JSONArray) object.get("categories");
    Set<String> categories = new HashSet<>();
    for (int j = 0; j < jsonArray.length(); j++) {
      JSONObject subObejct = jsonArray.getJSONObject(j);
      categories.add(subObejct.getString("title"));
    }
    builder.setCategories(categories);
    builder.setName(object.getString("name"));
    builder.setImageUrl(object.getString("image_url"));
    builder.setRating(object.getDouble("rating"));
    JSONObject coordinates = (JSONObject) object.get("coordinates");
    builder.setLatitude(coordinates.getDouble("latitude"));
    builder.setLongitude(coordinates.getDouble("longitude"));
    JSONObject location = (JSONObject) object.get("location");
    builder.setCity(location.getString("city"));
    builder.setState(location.getString("state"));
    builder.setZipcode(location.getString("zip_code"));
    JSONArray addresses = (JSONArray) location.get("display_address");
    String fullAddress = addresses.join(",");
    builder.setAddress(fullAddress);
    builder.setImageUrl(object.getString("image_url"));
    builder.setUrl(object.getString("url"));

    // Uses this builder pattern we can freely add fields.
    Item item = builder.build();
    list.add(item);
  }
  return list;
}

  /**
   * Main entry for sample Yelp API requests.
   */
  public static void main(String[] args) {
    YelpAPI yelpApi = new YelpAPI();
    yelpApi.queryAPI(37.38, -122.08);
  }
}
