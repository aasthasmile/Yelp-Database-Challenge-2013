package com.tutorial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

/*Student Name: Aastha Jain
**Student ID: 014868722
*/

public class hw4
{
	private JFrame frame;
	private JTable businessTable;
	private JPanel panel_1;
	private JComboBox comboBox_pointOfInterest;
	private JTable reviewTable;
	private String selected_catg;
	private JComboBox comboBox_Searchfor; 
	private JScrollPane scrollPane_review ;
	private JComboBox comboBox_Proximity ;
	private  List<String> listattributes ;
	ArrayList<String> Selected_listCategories = new ArrayList<String>();
	ArrayList<String> Selected_listAttributes = new ArrayList<String>();
	List<String> listofBusinessId = new ArrayList<String>();
	
	/* Connection to 
	 * MongoDB*
	 */
	   MongoClient mongoClient = new MongoClient("localhost",27017);
	   DB db = mongoClient.getDB("yelpdb");
	   DBCollection dbcollection=db.getCollection("business");
	   DBCollection new_collection=db.getCollection("business_db");
		BasicDBObject businessQuery = new BasicDBObject();
		BasicDBObject projection = new BasicDBObject();		
	/*Connection 
	 * Established Successfully
	 */
/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{	
			public void run()
			{	try
				{	hw4 window = new hw4();
					window.frame.setVisible(true);
				} catch (Exception e) 	{ e.printStackTrace(); 	}
			}
		});
	}

	/**
	 * * Create the application.
	 */
	public hw4()
	{
		newCollection();
		initialize();
		
	}	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(20, 20, 1050, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 13));
		
		JButton categoryBtn = new JButton("Categories");
		categoryBtn.setForeground(new Color(0, 0, 0));
		categoryBtn.setFont(new Font("Verdana", Font.BOLD, 12));
		categoryBtn.setContentAreaFilled(false);
		categoryBtn.setBorder(new LineBorder(new Color(0, 1, 0), 1));
		categoryBtn.setBackground(new Color(238, 232, 170));
		categoryBtn.setOpaque(true);
		
		JButton attributeBtn = new JButton("Attributes");
		attributeBtn.setForeground(Color.BLACK);
		attributeBtn.setFont(new Font("Verdana", Font.BOLD, 12));
		attributeBtn.setContentAreaFilled(false);
		attributeBtn.setBorder(new LineBorder(new Color(0, 1, 0), 1));
		attributeBtn.setBackground(new Color(0, 255, 153));
		attributeBtn.setOpaque(true);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		JLabel lblNewLabel = new JLabel("Point of Interest");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		JLabel lblNewLabel_1 = new JLabel("Proximity");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		JLabel lblSearchFor = new JLabel("Search for");
		lblSearchFor.setForeground(new Color(0, 0, 0));
		lblSearchFor.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OnSearchPopulateTable();
			}
		});
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		comboBox_pointOfInterest = new JComboBox();
		
		AddressClass address1 = new AddressClass("4237 Lien Rd\nSte H\nMayfair Park\nMadison, WI 53704", -89.3083801269531, 43.1205749511719);
		AddressClass address2 = new AddressClass("4840 E Indian School Rd\nSte 101\nPhoenix, AZ 85018",  -111.983757019043, 33.4993133544922);
		AddressClass address3 = new AddressClass("Mesa, AZ 85206", -111.701843261719, 33.3951606750488);
		AddressClass address4 = new AddressClass("3921 E Baseline Rd\nSte 108\nGilbert, AZ 85234",  -111.747520446777, 33.3782119750977);
		AddressClass address5 = new AddressClass("1000 N Green Valley Pkwy\nHenderson, NV 89012", -115.083946228027, 43.1205749511719);
		
		comboBox_pointOfInterest.setModel(new DefaultComboBoxModel(new String[] {"NONE", address1.Address,address2.Address,address3.Address ,address4.Address ,address5.Address}));
		
		comboBox_Proximity = new JComboBox();
		comboBox_Proximity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OnSearchPopulateTable();
			}
		});
		comboBox_Proximity.setModel(new DefaultComboBoxModel(new String[] {"0", "5", "10", "20", "30", "50"}));
		
		comboBox_Searchfor = new JComboBox();
		comboBox_Searchfor.setModel(new DefaultComboBoxModel(new String[] {"NONE", "ANY ATTRIBUTE", "ALL ATTRIBUTE"}));
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBox_pointOfInterest, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(111)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(comboBox_Proximity, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
							.addGap(80)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox_Searchfor, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblSearchFor)
									.addGap(130)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(closeBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(searchBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 981, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(categoryBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
									.addGap(41)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(attributeBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
									.addGap(18)
									.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(categoryBtn)
						.addComponent(attributeBtn))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
						.addComponent(scrollPane_2, 0, 0, Short.MAX_VALUE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1)
								.addComponent(lblSearchFor))
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox_pointOfInterest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_Proximity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_Searchfor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(searchBtn, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
							.addGap(26)
							.addComponent(closeBtn, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
							.addGap(21))))
		);
		
		businessTable = new JTable();
		businessTable.setToolTipText("");
		scrollPane_2.setViewportView(businessTable);
		businessTable.addMouseListener(new java.awt.event.MouseAdapter()
		{@Override
			public void mouseClicked(MouseEvent evt)
			{ 	System.out.println("First Line of mouselistener");
				int rowID = businessTable.rowAtPoint(evt.getPoint());
				String business_name = businessTable.getModel().getValueAt(rowID, 0).toString();
				System.out.println("Selected business name is:"+business_name);
				DBCollection dbcollection=db.getCollection("business_db");
				BasicDBObject businessQuery=new BasicDBObject();
				BasicDBObject businessProjection=new BasicDBObject();
				businessQuery.put("name",business_name);
				businessProjection.put("b_id", 1);
				System.out.println(businessQuery+","+businessProjection);
				DBCursor cursor=dbcollection.find(businessQuery,businessProjection);
				String business_Id="";
				while(cursor.hasNext()){
					DBObject result=cursor.next();
					business_Id=(String) result.get("b_id");
					System.out.println("Business ID are:"+business_Id);
				}
				if (rowID >= 0)
				{
					JFrame reviewframe = new JFrame();
					reviewframe.setSize(600, 600);
					reviewframe.setTitle("Reviews Table");
					reviewframe.setVisible(true);
						reviewTable = new JTable();
						DBCollection dbCollection=db.getCollection("review");
						BasicDBObject reviewQuery=new BasicDBObject();
						BasicDBObject reviewProjection=new BasicDBObject();
						reviewQuery.put("business_id", business_Id);
						reviewProjection.put("date", 1);
						reviewProjection.put("stars", 1);
						reviewProjection.put("text", 1);
						reviewProjection.put("user_id", 1);
						reviewProjection.put("votes.useful", 1);
						System.out.println(reviewQuery+","+reviewProjection);
						DBCursor cursor1=dbCollection.find(reviewQuery,reviewProjection);
						DBObject result;
						/*Creating
						 * Review table 
						 */
						String[] columnNames = new String[]{"Review Date", "Stars", "Review Text","User Name","Useful Votes"};
						DefaultTableModel model1 = new DefaultTableModel(columnNames, 0);
						reviewTable.setModel(model1);
						reviewTable.setShowGrid(true);
						String user_name=null;
						   int i=0;
						while(cursor1.hasNext()){
						   i++;
						  // System.out.println("reached reviews");
						   result=cursor1.next();
						   String r_date=(String) result.get("date");
						   String r_text=(String) result.get("text");
						   int r_stars=(int)result.get("stars");
						   String r_user=(String) result.get("user_id");
						   DBObject report = (BasicDBObject)result.get("votes");
						   int r_votesUseful=(int)report.get("useful");
						   
						   DBCollection userCollection=db.getCollection("user");
							BasicDBObject userQuery=new BasicDBObject();
							BasicDBObject userProjection=new BasicDBObject();
							userQuery.put("user_id", r_user);
							userProjection.put("name", 1);
							DBCursor cursor2=userCollection.find(userQuery,userProjection);
							while(cursor2.hasNext()){
								  result=cursor2.next();
								  user_name=(String) result.get("name");
							  }
						   model1.addRow(new Object[]{r_date, r_stars, r_text,user_name,r_votesUseful});
						  // System.out.println("[R_date:"+r_date+"] [R_stars:"+r_stars+"] [R_text:"+r_text+"][R_user:"+user_name+"][ R_VotesUseful:"+r_votesUseful+"]");
						    }
					scrollPane_review = new JScrollPane();
					reviewframe.getContentPane().add(scrollPane_review);
					scrollPane_review.add(reviewTable);
					scrollPane_review.setViewportView(reviewTable);
					scrollPane_review.revalidate();
					scrollPane_review.repaint();
				}
		}
	});
		//scrollPane_review.setViewportView(reviewTable);
				
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 255, 153));
		scrollPane_1.setViewportView(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 232, 170));
		scrollPane.setViewportView(panel);
		frame.getContentPane().setLayout(groupLayout);
		
		//Code start's here
		   		   
		//Categories Data-28 categories
		String[] categories={"Active Life","Arts & Entertainment","Automotive","Car Rental","Cafes","Beauty & Spas","Convenience Stores",
				 "Dentists", "Doctors", "Drugstores", "Department Stores","Education", "Event Planning & Services", "Flowers & Gifts",
				"Food", "Health & Medical", "Home Services","Home & Garden","Hospitals","Hotels & Travel","Hardware Stores","Grocery",
				"Medical Centers", "Nurseries & Gardening","Nightlife","Restaurants","Shopping","Transportation"};
		JCheckBox checkbox1;
		panel.setLayout(new GridLayout(categories.length, 1, 0, 0));
		for(String s:categories){
			checkbox1=new JCheckBox();
			System.out.println("categories are :"+s);
			checkbox1.setText(s);
			checkbox1.addItemListener(new ItemListener()
			{	public void itemStateChanged(ItemEvent e)
				{
				JCheckBox checkbox = (JCheckBox) (e.getItemSelectable());
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					System.out.println("One Selected category is:" +checkbox.getText());
					selected_catg = checkbox.getText();
					Selected_listCategories.add(selected_catg);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					System.out.println("Unselected category is:"+checkbox.getText() );
					selected_catg = checkbox.getText();
					Selected_listCategories.remove(selected_catg);
				}
				System.out.println("Selected categories:" +Selected_listCategories);
				callAttributes();
			}
		});
		panel.add(checkbox1);
		}
		//mongoClient.close();
	}	 	//Main Function End's Here
	
	/*new_collection known as business_db*/
	
	public void newCollection(){
	
	MongoClient mongoClient = new MongoClient("localhost",27017);
	 MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
     DBCollection b_collection = client.getDB("yelpdb").getCollection("business");
	 final DBCollection new_collection = client.getDB("yelpdb").getCollection("business_db");

			           BasicDBObject query = new BasicDBObject();
			           DBCursor cursor = b_collection.find(query);
//			            while(cursor.hasNext()){
//			        	   try {
//			        		   DBObject result = cursor.next();
//			                   JSONParser parser = new JSONParser();
//			                   JSONObject jsonObject;
//			                   jsonObject = (JSONObject)parser.parse(result.toString());
//			                   String business_id = (String) jsonObject.get("business_id");
//			                   BasicDBObject doc = new BasicDBObject("b_id", business_id);
//			                   String full_address = (String)jsonObject.get("full_address");
//			                   doc.append("full_address", full_address);
//			                   String city = (String) jsonObject.get("city");
//			                   doc.append("city", city);
//			                   String review_count = jsonObject.get("review_count").toString();
//			                   doc.append("review_count", review_count);
//			                   String businessName = jsonObject.get("name").toString();
//			                   doc.append("name", businessName);
//			                   String longitude = jsonObject.get("longitude").toString();
//			                   String latitude = jsonObject.get("latitude").toString();
//			                   Float[] Loc={Float.parseFloat(longitude),Float.parseFloat(latitude)};
//			                   doc.append("Loc",new Document().append("longitude", Float.parseFloat(longitude)).append("latitude",Float.parseFloat(latitude)));
//			                   String state = (String) jsonObject.get("state");
//			                   doc.append("state", state);
//			                   String stars = jsonObject.get("stars").toString();
//			                   doc.append("stars", stars);
//			                   String bType = (String) jsonObject.get("type");
//			                   doc.append("type",bType);
//			                   JSONArray category=(JSONArray)jsonObject.get("categories");
//			                   doc.append("categories", category);
//			                   JSONObject attributes1 = (JSONObject) jsonObject.get("attributes");
//			                   Object[] keys = attributes1.keySet().toArray();
//			                   Object[] list = attributes1.values().toArray();
//			                   ArrayList<String> valueList = new ArrayList<String>();
//			                   BasicDBObject attr = new BasicDBObject();
//			                   ArrayList<String> attr_keys = new ArrayList<>();
//			                   valueList.add("yes");
//			                   valueList.add("no");
//			                   valueList.add("true");
//			                   valueList.add("false");
//			                   for(int i =0; i != keys.length; i++){
//			                       String value = list[i].toString();
//			                       if(checkforJson(value)){
//			                           JSONObject obj = (JSONObject) new JSONParser().parse(value);
//			                           Object[] keys2 = obj.keySet().toArray();
//			                           Object[] list2 = obj.values().toArray();
//			                           for (int j = 0; j < keys2.length; j++) {
//			                           	String value2 = list2[j].toString();
//			                           System.out.println(keys2[j].toString() + " " + value2);
//			                           	
//			                           	if(valueList.contains(value2) ? true : false){
//			                           		
//			                           		attr.append(keys[i].toString()+"_"+keys2[j].toString(), list2[j].toString());
//			                           		attr_keys.add(keys[i].toString()+"_"+keys2[j].toString());
//			                           	} else{
//			                                 attr.append(keys[i].toString()+"_"+keys2[j].toString()+"_"+value2,  "true");
//			                                 attr_keys.add(keys[i].toString()+"_"+keys2[j].toString()+"_"+value2);
//			                           	}
//			                           }
//			                       }
//			                       else{
//			                       	System.out.println(keys[i].toString() + " " + value);
//			                       	if(valueList.contains(value) ? true : false){
//			                       		attr.append(keys[i].toString(), list[i].toString());
//			                       		attr_keys.add(keys[i].toString());
//			                       	}else{
//			                            attr.append(keys[i].toString()+"_"+list[i].toString(), "true");
//			                            attr_keys.add(keys[i].toString()+"_"+list[i].toString());
//			                       	}
//			                       }
//			                   }
//			                   doc.append("attributes",attr );
//			                   doc.append("attr_keys", attr_keys);
//			                   new_collection.insert(doc);
////			    			   documentMap.add(doc);
//			        	   }
//			        	   catch( ParseException e){
//			        		   e.printStackTrace();
//			        		   
//			        	   }
//		           }
	}
	
	public static boolean checkforJson(String str){
        
        if (str.trim().contains("{") || str.trim().contains("[")) {
            return str.trim().charAt(0) == '{' || str.trim().charAt(0) == '[';
        } else {
            return false;
        }                    
    }
	/*Populating the attributes column*/
	
		public void callAttributes(){
		//Attributes Data
		DBCollection dbcollection=db.getCollection("business_db");
		businessQuery.put("categories", new BasicDBObject("$in", Selected_listCategories));
		projection.put("attributes",1);
		projection.put("_id",0);
		System.out.println(businessQuery+","+projection);	
		DBCursor cursor=dbcollection.find(businessQuery,projection);
		DBObject result;
		   HashMap<String, String> attr=new HashMap<>();
		   Set<String> attributes = new HashSet<String>();
		   Set<String> templistattributes = new HashSet<String>();
		   while (cursor.hasNext())
			{
			   result=cursor.next();
			   attr=(HashMap<String, String>) result.get("attributes");
			   attributes=attr.keySet();
			   templistattributes.addAll(attributes);
		   }  
		  listattributes = new ArrayList<String>(new LinkedHashSet<String>(templistattributes));
		  System.out.println("\n\nIf CATEGORIES:"+ Selected_listCategories+"\nthen ATTRIBUTES: "+listattributes);
		  
		  JCheckBox checkbox2;
		  panel_1.removeAll();
			panel_1.setLayout(new GridLayout(listattributes.size(), 1, 0, 0));
			for(String s:listattributes){
				checkbox2=new JCheckBox();
				checkbox2.setText(s);
				checkbox2.addItemListener(new ItemListener()
				{	public void itemStateChanged(ItemEvent e)
					{	JCheckBox checkboxAttr = (JCheckBox) (e.getItemSelectable());
					if (e.getStateChange() == ItemEvent.SELECTED)
					{	System.out.println("\nYour Selected Attribute is:" +checkboxAttr.getText());
						Selected_listAttributes.add(checkboxAttr.getText());
					}else if (e.getStateChange() == ItemEvent.DESELECTED) {
						System.out.println("\nYour Unselected Attribute is:"+checkboxAttr.getText());
						Selected_listAttributes.remove(checkboxAttr.getText());
					}
				System.out.println("Selected Attributes:" +Selected_listAttributes);
				}
			});
				panel_1.add(checkbox2);
				panel_1.revalidate();
				panel_1.repaint();
			}
			
	}
	public void OnSearchPopulateTable(){
			try{
	DBCollection dbcollection=db.getCollection("business_db");
	if (((comboBox_pointOfInterest.getSelectedItem().toString())=="NONE") || (comboBox_pointOfInterest.getSelectedIndex())=='1'){
		businessQuery.remove("full_address");
		businessQuery.remove("Loc");
		businessQuery.put("categories", new BasicDBObject("$in", Selected_listCategories));
    	if((comboBox_Searchfor.getSelectedItem().toString() == "ANY ATTRIBUTE")||((comboBox_Searchfor.getSelectedItem().toString() == "NONE"))){
	businessQuery.append("attr_keys", new BasicDBObject("$in", Selected_listAttributes));
    	} else if ((comboBox_Searchfor.getSelectedItem().toString() == "ALL ATTRIBUTE") ){		
    businessQuery.append("attr_keys", new BasicDBObject("$in", listattributes));	
	}
	}
	if((comboBox_pointOfInterest.getSelectedItem()) != null && (comboBox_pointOfInterest.getSelectedItem()) != "NONE"){
			//&& (comboBox_pointOfInterest.getSelectedItem())!="NONE"){
    String pointOfInterested_Value=comboBox_pointOfInterest.getSelectedItem().toString();
    //businessQuery.append("full_address", pointOfInterested_Value);
    	
    	BasicDBObject full=new BasicDBObject();
    	full.append("full_address", pointOfInterested_Value);
    	BasicDBObject proj=new BasicDBObject();
    	proj.put("Loc", 1);
    	DBCursor cursor = dbcollection.find(full,proj);
    	DBObject res;
    	float latitude = 0;
    	float longitude = 0;
    	while(cursor.hasNext()){
    	res=cursor.next();
    		System.out.println("cursor is:"+res);
    		DBObject report=(DBObject) res.get("Loc");
    		latitude=Float.parseFloat(report.get("latitude").toString().trim());
    		longitude=Float.parseFloat(report.get("longitude").toString().trim());
    		//System.out.println("latitude" + latitude);
    		//System.out.println("longitude" + longitude);
    	}
    	BasicDBList geoCoord = new BasicDBList();
        geoCoord.add(longitude);
        geoCoord.add(latitude);

        BasicDBList geoParams = new BasicDBList();
        geoParams.add(geoCoord);
        geoParams.add(Float.parseFloat(comboBox_Proximity.getSelectedItem().toString())/3963.2);

        BasicDBObject query = new BasicDBObject("Loc", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));
        if((comboBox_Proximity.getSelectedItem()) != null ){
	        businessQuery.append("Loc", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));
	        DBCursor cursor_geo = dbcollection.find(query);   	
	        DBObject res_geo;
	        while(cursor_geo.hasNext()){
	        	res_geo= cursor_geo.next();
	        	//System.out.println(res_geo);
	        }
        }
	}
	projection.clear();
	projection.put("name",1);
	projection.put("state",1);
	projection.put("city",1);
	projection.put("stars",1);
	System.out.println(businessQuery+","+projection);
	DBCursor cursor_table=dbcollection.find(businessQuery,projection);
	DBObject result_table;
	
	/*Table 
	 * creating for BUSINESS*/
	String[] columnNames = new String[]{"Name", "City", "State","Stars"};
	DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	businessTable.setModel(model);
	businessTable.setShowGrid(true);
    	   
   while(cursor_table.hasNext()){
	   //System.out.println("reached");
	   result_table=cursor_table.next();
	   String b_name=(String) result_table.get("name");
	   String b_city=(String) result_table.get("city");
	   String b_state=(String) result_table.get("state");
	   String b_stars=(String) result_table.get("stars");
	   String b_id=(String) result_table.get("b_id");
	   listofBusinessId.add(b_id);
	   model.addRow(new Object[]{b_name, b_city, b_state,b_stars});
	   //System.out.println("[B_name:"+b_name+"] [B_city:"+b_city+"][B_state:"+b_state+"][ B_stars:"+b_stars+"]");
	    }
   businessTable.setModel(model);
   businessTable.setShowGrid(true);
   cursor_table.close();
  
	}
		catch (Exception e) 	{ e.printStackTrace();}
}
	public class AddressClass{
		public String Address;
		public double Lonlongitude;
		public double Latitude;
		public AddressClass(String Address, double d, double e){
			this.Address = Address;
			this.Lonlongitude = d;
			this.Latitude = e;
		}
	}
}
