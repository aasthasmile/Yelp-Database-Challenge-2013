package com.yelpdata.domain;

/**
 * @author Aastha
 * Student Name:Aastha Jain ,Student Id:014868722
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.apache.commons.*;
import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONException;
//import org.json.simple.JSONObject;
import org.json.JSONObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class populate
{
	public static final String url="jdbc:oracle:thin:@localhost:1523:ORCLE";
	public static final String username="scott";
	public static final String password="tiger";
	
	public static void main(String[] args) throws SQLException 
	{
		String filepath1="";
		String filepath4="";
		String filepath3="";
		String filepath2="";
		
		if (args.length >= 4) {
			filepath1=args[0];
			filepath4=args[1];
			filepath3=args[2];
			filepath2=args[3];
		}
//		final String filepath1="C:\\Users\\Aastha\\workspace\\YelpDataSet\\json\\yelp_business.json";
//		final String filepath2="C:\\Users\\Aastha\\workspace\\YelpDataSet\\json\\yelp_user.json";
//		final String filepath3="C:\\Users\\Aastha\\workspace\\YelpDataSet\\json\\yelp_checkin.json";
//		final String filepath4="C:\\Users\\Aastha\\workspace\\YelpDataSet\\json\\yelp_review.json";
	 try{
		 Class.forName("oracle.jdbc.driver.OracleDriver");}
	 catch(ClassNotFoundException e){
		 System.out.println("Oracle JDBC Driver not present or found");
		 e.printStackTrace();
		 return;
	 }
	Connection conn = null;
	try{
		conn = DriverManager.getConnection(url,username,password);
	}
	catch(SQLException e){
		 System.out.println("Connection Failed!");
		 e.printStackTrace();
		 return;
	}
	Statement stmt = null;
	PreparedStatement p_stmt=null;
	PreparedStatement del_stmt=null;
	if (conn != null) {
	try{
		stmt = conn.createStatement();
		
		/*Drop_table will be used
		 * to truncate all the data from all Tables
		 */
		drop_table(conn,del_stmt);
		
		/*14 Tables-
		 * Inserting Data through 14 functions
		 * (one for each)
		 */
		business_sub_catgn(conn,p_stmt,filepath1);
		business_yelp(conn,p_stmt,filepath1);
		user_yelp(conn,p_stmt,filepath2);
		review_yelp(conn,p_stmt,filepath4);
		checkin(conn,p_stmt,filepath3);
		
		business_neighborhood(conn, p_stmt, filepath1);
		elite_list(conn,p_stmt,filepath2);
		friends(conn,p_stmt,filepath2);
		business_hours(conn,p_stmt,filepath1);
		business_attributes(conn,p_stmt,filepath1);
		
		checkin_info(conn,p_stmt,filepath3);
		 compliments(conn, p_stmt, filepath2);
		
	}
			catch(SQLException e){
			e.printStackTrace();
			}
			finally{
			   if(stmt!=null){ stmt.close();}
			   if(conn!=null){ conn.close();}
			}
	}
	else{
		System.out.println("Failed to connect to Oracle DB");
	
	}
	
}
	public static void drop_table(Connection conn,PreparedStatement del_stmt){
		try{
			PreparedStatement delstmt=null;
			delstmt=conn.prepareStatement("Truncate table business_yelp ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt = conn.prepareStatement("Alter table user_yelp disable constraint user_yelp_pk cascade");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table user_yelp ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Alter table user_yelp enable constraint user_yelp_pk");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table review_yelp");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table CHECKIN ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table TIPS");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table business_HOURS ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table categories ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table NEIGHBORHOOD ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table attributes_list ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table elite_list ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table FRIENDS ");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table COMPLIMENTS");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table CHECKIN_INFO");
			delstmt.executeUpdate();
			delstmt.close();
			
			delstmt=conn.prepareStatement("Truncate table business_sub_category");
			delstmt.executeUpdate();
			delstmt.close();
				}
		 catch (SQLException ex) {
	           ex.printStackTrace();
	        }
	}
	public static void business_sub_catgn(Connection conn,PreparedStatement p_stmt,String filepath1){
		try{
			FileReader reader=new FileReader(filepath1);
			BufferedReader br=new BufferedReader (reader);
			String read;
			
			JSONArray jarr;
			String name=null;
			String[] categories={"Active Life","Arts & Entertainment","Automotive","Car Rental","Cafes","Beauty & Spas","Convenience Stores",
					 "Dentists", "Doctors", "Drugstores", "Department Stores","Education", "Event Planning & Services", "Flowers & Gifts",
					"Food", "Health & Medical", "Home Services","Home & Garden","Hospitals","Hotels & Travel","Hardware Stores","Grocery",
					"Medical Centers", "Nurseries & Gardening","Nightlife","Restaurants","Shopping","Transportation"};
			//List valid_list=Arrays.asList(categories);
			
			
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				jarr=jobj.getJSONArray("categories");
				
				String businessid=jobj.getString("business_id");
				 ArrayList<String> list = new ArrayList<String>();
	                if (jarr != null) {
	                    
	                    for (int i = 0; i < jarr.length(); i++) {
	                        list.add(jarr.get(i).toString());
	                    }
	                    
	                    for (int i = 0; i < list.size(); i++) {
	                        name = list.get(i);
	                        //System.out.println(name);
	                        if (!Arrays.asList(categories).contains(name)) {
	                        
	                           	p_stmt = conn.prepareStatement("INSERT INTO BUSINESS_SUB_CATEGORY(B_ID, SUB_CATEGORY) VALUES(?, ?)");
	                        	
	                            p_stmt.setString(1, businessid);
	                            p_stmt.setString(2, name);
	                            System.out.println("Data inserted into SUB_CATEGORY TABLE");
	                        } 
	                        else
	                        {
	                        	
	                        p_stmt = conn.prepareStatement("INSERT INTO CATEGORIES(B_ID, CATEGORY_LIST) VALUES(?, ?)");
	                        	
	                            p_stmt.setString(1, businessid);
	                            p_stmt.setString(2, name);
	                            System.out.println("Data inserted into CATEGORIES TABLE");
	                        }
	                        p_stmt.executeUpdate();
	                        p_stmt.close();
	                    }
	                }
			}
			reader.close();
			//conn.close();
		}
			catch (IOException e) {
	            e.printStackTrace();
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            
	        }catch (JSONException ex) {
	            ex.printStackTrace();
	        }
				
			
	}
	public static void business_yelp(Connection conn,PreparedStatement p_stmt,String filepath1){
			try{
			FileReader reader=new FileReader(filepath1);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject obj;
						
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				
				String id=jobj.getString("business_id");
				String fullAddress=jobj.getString("full_address").replaceAll("'", "''");
				fullAddress.replaceAll(",", ";");
				String b_open=jobj.getString("open");
				//String street=jobj.getString("street").replaceAll("'", "''");
				//street.replaceAll(",", ";");
				String city=jobj.getString("city");
				String state_name=jobj.getString("state");
				//String pin=jobj.getString("pin");
				double latitude =jobj.getDouble("latitude");
				double longitude=jobj.getDouble("longitude");
				Integer review_count=jobj.getInt("review_count");
				String b_name=jobj.getString("name");
				Integer stars=jobj.getInt("stars");
				String b_type=jobj.getString("type");
				
				p_stmt=conn.prepareStatement("INSERT into business_yelp(business_id,full_address,b_open,city,state_name,latitude,longitude,review_count,b_name,stars,b_type) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				
				p_stmt.setString(1,id);
				p_stmt.setString(2, fullAddress);
				if(b_open=="true")
				p_stmt.setString(3, "true");
				else
				p_stmt.setString(3, "false");
				//p_stmt.setString(4, street);
				p_stmt.setString(4,city);
				p_stmt.setString(5, state_name);
				//p_stmt.setString(7, pin);
				p_stmt.setDouble(6, latitude);
				p_stmt.setDouble(7, longitude);
				p_stmt.setInt(8, review_count);
				p_stmt.setString(9, b_name);
				p_stmt.setInt(10, stars);
				p_stmt.setString(11, b_type);
				System.out.println("Data inserted into Business Table...YOOOO!");
				p_stmt.executeUpdate();
				p_stmt.close();
				 
							}
			reader.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
}
	public static void user_yelp(Connection conn,PreparedStatement p_stmt,String filepath2){
		
		try{
			FileReader reader=new FileReader(filepath2);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject obj;
						
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				
				String yelping_since=jobj.getString("yelping_since");
				obj=jobj.getJSONObject("votes");
				int votes_cool=obj.getInt("cool");
				int votes_funny=obj.getInt("funny");
				int votes_useful=obj.getInt("useful");
				int review_count =jobj.getInt("review_count");
			    String uname =jobj.getString("name");
			    String user_id =jobj.getString("user_id");
			    int fans =jobj.getInt("fans");
			    int average_stars =jobj.getInt("average_stars");
			    String type=jobj.getString("type");
			    
				p_stmt=conn.prepareStatement("INSERT into user_yelp(yelping_since,votes_cool,votes_funny,votes_useful,review_count,user_name,user_id,fans,average_stars,type) VALUES(?,?,?,?,?,?,?,?,?,?)");
				
				p_stmt.setString(1,yelping_since);
				p_stmt.setInt(2,votes_cool);
				p_stmt.setInt(3,votes_funny);
				p_stmt.setInt(4, votes_useful);
				p_stmt.setInt(5,review_count);
				p_stmt.setString(6, uname);
				p_stmt.setString(7, user_id);
				p_stmt.setInt(8, fans);
				p_stmt.setInt(9, average_stars);
				p_stmt.setString(10, type);
				
				System.out.println("Data inserted into USER Table...YOOOO2!");
				p_stmt.executeUpdate();
				p_stmt.close();
				 
							}
			reader.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
			}


	public static void review_yelp(Connection conn,PreparedStatement p_stmt,String filepath4){
		
		try{
			FileReader reader=new FileReader(filepath4);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject obj;
						
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				obj=jobj.getJSONObject("votes");
				int votes_cool=obj.getInt("cool");
				int votes_funny=obj.getInt("funny");
				int votes_useful=obj.getInt("useful");
				String user_id=jobj.getString("user_id");
				String review_id=jobj.getString("review_id");
				int stars=jobj.getInt("stars");
				String r_date=jobj.getString("date");
				String text=jobj.getString("text");
				String type=jobj.getString("type");
				String bid=jobj.getString("business_id");
				
							
				p_stmt=conn.prepareStatement("INSERT into review_yelp(votes_cool,votes_funny,votes_useful,user_id,review_id,stars,r_date,text,type,business_id) VALUES(?,?,?,?,?,?,?,?,?,?)");
				p_stmt.setInt(1,votes_cool);
				p_stmt.setInt(2, votes_funny);
				p_stmt.setInt(3, votes_useful);
				p_stmt.setString(4,user_id);
				p_stmt.setString(5, review_id);
				p_stmt.setInt(6, stars);
				p_stmt.setTimestamp(7, Timestamp.valueOf(r_date+" 00:00:00"));
				p_stmt.setString(8, text);
				p_stmt.setString(9, type);
				p_stmt.setString(10, bid);
				System.out.println("Data inserted into REVIEW Table...YOOOO2!");
				p_stmt.executeUpdate();
				p_stmt.close();
				 
	}
			reader.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
		
}
	public static void checkin(Connection conn,PreparedStatement p_stmt,String filepath3){
		
		try{
			FileReader reader=new FileReader(filepath3);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject obj;
					
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				String bid=jobj.getString("business_id");
				String type=jobj.getString("type");
				
				p_stmt=conn.prepareStatement("INSERT into checkin(business_id,type) VALUES(?,?)");
				p_stmt.setString(1, bid);
				p_stmt.setString(2, type);
				
				System.out.println("Data inserted into CHECKIN Table  ");
				p_stmt.executeUpdate();
				p_stmt.close();
				 
	}
			reader.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
		
}
	public static void business_hours(Connection conn,PreparedStatement p_stmt,String filepath1){
		
		try{
			FileReader reader=new FileReader(filepath1);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject hours_obj;
			
			String[] day_name={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				
				String id=jobj.getString("business_id");
				hours_obj=jobj.getJSONObject("hours");
				JSONObject j_time=null ;
				String open_time=null;
				String close_time=null;
				for(int i=0;i<day_name.length;i++){
					if(hours_obj.has(day_name[i])){
						j_time=hours_obj.getJSONObject(day_name[i]);
							if(j_time.has("open")){
							open_time=j_time.getString("open");}
							if(j_time.has("close")) {
								close_time=j_time.getString("close");}
					}
				p_stmt=conn.prepareStatement("INSERT into business_hours(b_id,day_name,open_time,close_time) VALUES(?,?,?,?)");
				
				p_stmt.setString(1,id);
				p_stmt.setString(2, day_name[i]);
				p_stmt.setString(3,open_time);
				p_stmt.setString(4,close_time);
				
				System.out.println("Data inserted into BUSINESS_HOURS Table ");
			
				p_stmt.executeUpdate();
				p_stmt.close();
			}
				}
							
			reader.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
}
	public static void business_neighborhood(Connection conn,PreparedStatement p_stmt,String filepath1){
		
		try{
			FileReader reader=new FileReader(filepath1);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject obj;
						
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				
				String id=jobj.getString("business_id");
				JSONArray area = jobj.getJSONArray("neighborhoods");
				
				for(int i=0;i<area.length();i++){
					String area_name= area.getString(i);
				
				p_stmt=conn.prepareStatement("Insert into NEIGHBORHOOD(b_id,area) VALUES(?,?)");
				
				p_stmt.setString(1,id);
				p_stmt.setString(2,area_name);
				System.out.println("Data inserted into Business-Neighborhood Table..");
				p_stmt.executeUpdate();
				p_stmt.close();
				}
							}
			reader.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
}
public static void business_attributes(Connection conn,PreparedStatement p_stmt,String filepath){
		
		try{
			FileReader reader=new FileReader(filepath);
			BufferedReader br=new BufferedReader (reader);
			String read;
			JSONObject obj;
						
			while((read =br.readLine())!=null){
				JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
				String business_id=jobj.getString("business_id");
				JSONObject attributes = (JSONObject)jobj.get("attributes");
				p_stmt=conn.prepareStatement("Insert into attributes_list(b_id,attr_name,attr_value) VALUES(?,?,?)");
				
				String[] keys=attributes.getNames(attributes);
				if(keys!=null){
				String[] values=new String[keys.length];
				
				for (int i=0;i<attributes.length();i++){
										
						//System.out.println(keys[i]);
						values[i]=attributes.getString(keys[i]);
						//System.out.println(values[i]);
				}
				for(int i=0;i!=keys.length;i++){
					String key_new=keys[i].toString();
					String value_new=values[i].toString();
					if(verifyJSON(value_new)){
						obj=(JSONObject) new JSONTokener(read).nextValue();
						String[] keys_2=obj.getNames(obj);
						if(keys_2!=null){
						String[] values_2=new String[keys_2.length];
						for (int j=0;j<obj.length();j++){
							values_2[j]=obj.getString(keys_2[j]);
						}
						
						for(int k=0;k<keys_2.length;k++){
							p_stmt.setString(1,business_id);
							p_stmt.setString(2,keys[i].toString()+"_"+keys_2[k].toString());
							p_stmt.setString(3, values_2[k].toString());
							p_stmt.addBatch();
						}
					}}
					else
					{
						p_stmt.setString(1,business_id);
						p_stmt.setString(2,keys[i].toString());
						p_stmt.setString(3, values[i].toString());
						p_stmt.addBatch();
					}
						
				}
				}
				p_stmt.executeBatch();
				System.out.println("Data inserted into Business-Attributes Table..");
				
				p_stmt.close();
				
				}
							
			reader.close();
			br.close();
			//conn.close();
}
		
		catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
}
public static boolean verifyJSON(String string){
	if(string.trim().contains("{") || string.trim().contains("[")){
		return string.trim().charAt(0)=='{' || string.trim().charAt(0)=='[';
			}
	else
	{
		return false;
	}
}
public static void elite_list(Connection conn,PreparedStatement p_stmt,String filepath){
	
	try{
		FileReader reader=new FileReader(filepath);
		BufferedReader br=new BufferedReader (reader);
		String read;
		JSONObject obj=null;
					
		while((read =br.readLine())!=null){
			JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
			
			String id=jobj.getString("user_id");
			JSONArray attributes = jobj.getJSONArray("elite");
			
			for(int i=0;i<attributes.length();i++){
			String elite= attributes.getString(i);
			
			p_stmt=conn.prepareStatement("Insert into elite_list(u_id,year) VALUES(?,?)");
			
			p_stmt.setString(1,id);
			p_stmt.setString(2,elite);
			System.out.println("Data inserted into Elite-List(User) Table..");
			p_stmt.executeUpdate();
			p_stmt.close();
			}
			}
		reader.close();
		//conn.close();
}
	
	catch (IOException e) {
        e.printStackTrace();
    } catch (SQLException ex) {
       ex.printStackTrace();
    } catch (JSONException ex) {
       ex.printStackTrace();
    }
}
public static void friends(Connection conn,PreparedStatement p_stmt,String filepath){
	
	try{
		FileReader reader=new FileReader(filepath);
		BufferedReader br=new BufferedReader (reader);
		String read;
		JSONObject obj=null;
					
		while((read =br.readLine())!=null){
			JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
			
			String id=jobj.getString("user_id");
			JSONArray friends = jobj.getJSONArray("friends");
			
			for(int i=0;i<friends.length();i++){
			String frnd= friends.getString(i);
			
			p_stmt=conn.prepareStatement("Insert into friends(u_id,f_id) VALUES(?,?)");
			
			p_stmt.setString(1,id);
			p_stmt.setString(2,frnd);
			System.out.println("Data inserted into FRIENDS(User) Table..");
			p_stmt.executeUpdate();
			p_stmt.close();
			}
			}
		reader.close();
		//conn.close();
}
	
	catch (IOException e) {
        e.printStackTrace();
    } catch (SQLException ex) {
       ex.printStackTrace();
    } catch (JSONException ex) {
       ex.printStackTrace();
    }
}

public static void compliments(Connection conn,PreparedStatement p_stmt,String filepath){
	
	try{
		FileReader reader=new FileReader(filepath);
		BufferedReader br=new BufferedReader (reader);
		String read;
		JSONObject obj=null;
					
		while((read =br.readLine())!=null){
			JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
			
			String id=jobj.getString("user_id");
			String json_frnd = jobj.getString("compliments");
			
			/*for(int i=0;i<json_frnd.length();i++){
				System.out.println("The"+i+"first element of array:"+json_frnd.get(i));
			}
			String comp_id=null;
			Iterator<?> comp=jobj.keys();
			while(comp.hasNext()){
				String key=(String) comp.next();
				Object innerObj = jobj.get(key);
				if( innerObj instanceof JSONObject){
					comp_id=key;
				}
			}*/
			p_stmt=conn.prepareStatement("Insert into compliments(u_id,complement_type) VALUES(?,?)");
			
			p_stmt.setString(1,id);
			p_stmt.setString(2,json_frnd);
			System.out.println("Data inserted into COMPLIMENTS(User) Table..");
			p_stmt.executeUpdate();
			p_stmt.close();
			
			}
		reader.close();
		//conn.close();
}
	
	catch (IOException e) {
        e.printStackTrace();
    } catch (SQLException ex) {
       ex.printStackTrace();
    } catch (JSONException ex) {
       ex.printStackTrace();
    }
}
public static void checkin_info(Connection conn,PreparedStatement p_stmt,String filepath){
	
	try{
		FileReader reader=new FileReader(filepath);
		BufferedReader br=new BufferedReader (reader);
		String read;
		JSONObject obj=null;
		int[] time={0,0,0,0,0,0,0};			
		while((read =br.readLine())!=null){
			JSONObject jobj=(JSONObject) new JSONTokener(read).nextValue();
			
			String id=jobj.getString("business_id");
			String info = jobj.getString("checkin_info");
			/*obj=jobj.getJSONObject("checkin_info");
			Iterator<?> keys=obj.keys();
			while(keys.hasNext()){
				String key=(String) keys.next();
				int check = obj.getInt(key);
				int day=Integer.parseInt(key.substring(key.length()-1, key.length()));
				time[day]+=check;
			}
			String info=""+Integer.toString(time[0]);
			for(int i=1;i<time.length;i++){
				info=";"+Integer.toString(time[i]);
			}*/
				
			
			
			p_stmt=conn.prepareStatement("Insert into checkin_info(b_id,checkin_info) VALUES(?,?)");
			
			p_stmt.setString(1,id);
			p_stmt.setString(2,info);
			System.out.println("Data inserted into Checkin_Information Table..");
			p_stmt.executeUpdate();
			p_stmt.close();
			
			}
		reader.close();
		//conn.close();
}
	
	catch (IOException e) {
        e.printStackTrace();
    } catch (SQLException ex) {
       ex.printStackTrace();
    } catch (JSONException ex) {
       ex.printStackTrace();
    }
}
		
}