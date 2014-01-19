/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * 
 */
public class Assignment {

    public static void main(String args[]) {
        DataStore store = new DataStore("data.json", "query.json");
        System.out.println(store.ProductMap.size() + "," + store.QueryMap.size() + "," + store.ArtistMap.size() + "," + store.QueryProductMap.size() + "," + store.ProductQueryMap.size());
        
            try {
                System.out.println("Enter An Option (1,2,3):");
                System.out.println("1. Search Clicked Products for a Query");
                System.out.println("2. Search Queries for An Artist");
                System.out.println("3. Exit");
                System.out.println("Anything Else to search again..");
                System.out.print("Enter Choice:");
                
                
                BufferedReader br = new BufferedReader(new InputStreamReader(
        				System.in));
        		int choice; String str="";
        		while(true){
        			System.out.println("------MENU------");
        			System.out.println("0. Exit");
        			System.out.println("1. Search by query string");
        			System.out.println("2. Search by artist");
        			choice = Integer.parseInt(br.readLine());
        			switch(choice){
        				case 0: 
        					System.exit(0);
        					break;
        				case 1:
        					System.out.println("Enter query string :: ");
        					str=br.readLine();
        					ArrayList<Object> arr = store.findProductsWithQuery(str);
                            if (arr == null) {
                                System.out.println("No Results found for Query=" + str);
                                break;
                            }
                            for (Object tobj : arr) {
                                Product obj = (Product) tobj;
                                System.out.println(obj.getProductId() + "\t" + obj.getProductName() + "\t" + obj.getArtist() + "\t" + obj.getGenre());
                            }
                            
        					break;
        				case 2:
        					System.out.println("Enter artist :: ");
        					str=br.readLine();
        					ArrayList<ArrayList<Object>> arr2 = store.findQueryWithArtist(str);
                            if (arr2 == null) {
                                System.out.println("No Results found for Artist=" + str);
                                break;
                            }
                            for (ArrayList<Object> arr3 : arr2) {
                                for (Object tobj : arr3) {
                                    Query obj = (Query) tobj;
                                    System.out.println(obj.getQuery() + "\t" + obj.getProductId() + "\t" + obj.getTimestamp());
                                }
                            }
        					break;
        			}
        			
        		}
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    
}
