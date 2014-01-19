package mypackage.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author raisumit
 */
class DataStore {

    HashMap<Integer, Object> ProductMap = new HashMap<Integer, Object>();   //maps product id to product information
    HashMap<String, ArrayList<Integer>> ArtistMap = new HashMap<String, ArrayList<Integer>>(); //maps artist to list of product ids
    HashMap<Integer, ArrayList<String>> ProductQueryMap = new HashMap<Integer, ArrayList<String>>();  //maps product id to list of queries
    HashMap<String, ArrayList<Integer>> QueryProductMap = new HashMap<String, ArrayList<Integer>>();  //maps query to list of product ids
    HashMap<String, ArrayList<Object>> QueryMap = new HashMap<String, ArrayList<Object>>(); //maps query to list pf clicked product info

    DataStore(String dataFile, String queryFile) {
        dataProcessing(dataFile, queryFile);
        System.out.println("DataStore Initialized successfully !");
    }

    public final void dataProcessing(String dataFile, String queryFile) {
        /*BufferedReader br;
         String json;
         JSONArray jsonArray;*/
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        try {
            /* First processing data json*/
            JsonParser parser = jsonFactory.createJsonParser(getClass().getResourceAsStream("/" + dataFile));
            parser.nextToken();
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                Product prodObj = mapper.readValue(parser, Product.class);
                //System.out.println("Product obj:"+prodObj.showProduct());
                ProductMap.put(prodObj.getProductId(), prodObj);
                if (ArtistMap.containsKey(prodObj.getArtist())) { //If artistmap already contains this artist
                    ArtistMap.get(prodObj.getArtist()).add(prodObj.getProductId()); //Add this product id to this artist map
                } else {
                    ArrayList<Integer> arr = new ArrayList<Integer>();
                    arr.add(prodObj.getProductId());
                    ArtistMap.put(prodObj.getArtist(), arr);   //create new entry in artistmap
                }
            }

            parser.close();
            /* Now processing query json*/
            parser = jsonFactory.createJsonParser(getClass().getResourceAsStream("/" + queryFile));
            parser.nextToken();
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                Query queryObj = mapper.readValue(parser, Query.class);
                //System.out.println("Query obj:"+queryObj.showQuery());
                if (QueryMap.containsKey(queryObj.getQuery())) {
                    QueryMap.get(queryObj.getQuery()).add(queryObj);
                } else {
                    ArrayList<Object> arr = new ArrayList<Object>();
                    arr.add(queryObj);
                    QueryMap.put(queryObj.getQuery(), arr);
                }
                if (QueryProductMap.containsKey(queryObj.getQuery())) {
                    QueryProductMap.get(queryObj.getQuery()).add(queryObj.getProductId());
                } else {
                    ArrayList<Integer> arr = new ArrayList<Integer>();
                    arr.add(queryObj.getProductId());
                    QueryProductMap.put(queryObj.getQuery(), arr);
                }
                if (ProductQueryMap.containsKey(queryObj.getProductId())) {
                    ProductQueryMap.get(queryObj.getProductId()).add(queryObj.getQuery());
                } else {
                    ArrayList<String> arr = new ArrayList<String>();
                    arr.add(queryObj.getQuery());
                    ProductQueryMap.put(queryObj.getProductId(), arr);
                }
            }

            parser.close();
        } catch (IOException ex) {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Object> findProductsWithQuery(String query) {
        ArrayList<Object> prodList = new ArrayList<Object>();
        ArrayList<Integer> arr = QueryProductMap.get(query);
        if (arr != null) {
            for (Integer prodId : arr) {
                prodList.add(ProductMap.get(prodId));
            }
        }
        return prodList;
    }

    public ArrayList<ArrayList<Object>> findQueryWithArtist(String artist) {
        ArrayList<ArrayList<Object>> queryList = new ArrayList<ArrayList<Object>>();
        ArrayList<Integer> arr = ArtistMap.get(artist);
        if (arr != null) {
            for (Integer prodId : arr) {
                ArrayList<String> arr2 = ProductQueryMap.get(prodId);
                if (arr2 != null) {
                    for (String query : arr2) {
                        queryList.add(QueryMap.get(query));
                    }
                }

            }
        }
        return queryList;

    }
}

class Product {

    private String genre = new String();
    private String productName = new String();
    private String artist = new String();
    private Integer productId = null;
    
    Product(){
        
    }

    Product(String genre, String product, String artist, int prodId) {
        this.genre = genre;
        productName = product;
        this.artist = artist;
        productId = new Integer(prodId);
    }
    public String showProduct(){
        String str;
        str = genre + productName + artist + productId;
        return str;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getArtist() {
        return artist;
    }

    public String getProductName() {
        return productName;
    }

    public String getGenre() {
        return genre;
    }
}

class Query {

    private String query = null;
    private String timestamp = new String();
    private Integer productId = null;
    Query(){
        
    }

    Query(String query, String timestamp, int prodId) {
        this.query = query;
        this.timestamp = timestamp;
        productId = new Integer(prodId);
    }

    public Integer getProductId() {
        return productId;
    }

    public String getQuery() {
        return query;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public String showQuery(){
        String str;
        str= query + timestamp+productId;
        return str;
    }
}
