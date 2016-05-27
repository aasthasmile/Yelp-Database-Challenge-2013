package com.java.domain;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Checkbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.UIManager;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;

public class Hw3
{

	private JFrame frmYelpTool;
	private JTable table1;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private JComboBox comboBox_3_1;
	private Review newframe;
	String selected_catg;
	String b_name;
	private String listSelectedCategories = "";
	private String list_sub_categories = "";
	private String selected_sub_catg = "";
	private String selected_attribute = "";
	/**
	 * Creating database related objects Connection, ResultSet and Statement
	 */
	public static Connection yelpDbConnection;
	private ResultSet rs;
	private Statement stmt;
	private StringBuilder query;
	public static final String url = "jdbc:oracle:thin:@localhost:1523:ORCLE";
	public static final String username = "scott";
	public static final String password = "tiger";

	ArrayList<String> listcategories = new ArrayList<String>();
	ArrayList<String> list_subcategories = new ArrayList<String>();
	ArrayList<String> list_attributes = new ArrayList<String>();
	ArrayList<String> list_sub_attributes = new ArrayList<String>();
	ArrayList<String> list_of_attributes = new ArrayList<String>();
	private ComboBoxActionListener comboBoxActionListener;

	/**
	 * Create the application.
	 */
	public Hw3()
	{
		initialize();
		init_categories();

	}

	public static Connection dbconnection()
	{

		if (yelpDbConnection == null)
		{
			try
			{
				yelpDbConnection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yelpDbConnection;

	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	private void initialize()
	{
		frmYelpTool = new JFrame();
		frmYelpTool.getContentPane().setBackground(new Color(0, 0, 128));
		frmYelpTool.setPreferredSize(new Dimension(1800, 900));
		frmYelpTool.setTitle("Yelp Tool");
		frmYelpTool.setBounds(0, 0, 1500, 900);
		frmYelpTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Query to fetch the data based on the combo boxes
		StringBuilder query = new StringBuilder(
				"Select b.b_name,b.city,b.state_name,b.stars from business_yelp b,business_hours bh,categories c "
						+ "where b.business_id=bh.b_id and bh.b_id=c.b_id ");

		JButton btnStart = new JButton("START");
		btnStart.setActionCommand("Start");
		btnStart.setHorizontalAlignment(SwingConstants.LEFT);

		btnStart.setBackground(UIManager.getColor("Button.shadow"));

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();

		JScrollPane scrollPane_2 = new JScrollPane();

		JLabel lblDayOfThe = new JLabel("Day of the week");
		lblDayOfThe.setForeground(new Color(255, 255, 255));
		lblDayOfThe.setFont(new Font("Verdana", Font.BOLD, 12));

		JLabel lblNewLabel = new JLabel("From:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));

		JLabel lblNewLabel_1 = new JLabel("To:");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));

		JLabel lblNewLabel_2 = new JLabel("Search for:");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 12));
		table1 = new JTable();
		panel = new JPanel();
		comboBoxActionListener = new ComboBoxActionListener();// Instantiating
																// ActionLisener
																// for combo box

		// Day of the week combo box
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[]
		{ "NONE ", "MONDAY ", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" }));

		// Adding actionListener to this ComboBox
		comboBox.addActionListener(comboBoxActionListener);
		String s = selected_catg;

		// String selectedDayOfTheWeek =
		// comboBoxActionListener.getItemSelected();
		//
		// if (selectedDayOfTheWeek!=null &&
		// !selectedDayOfTheWeek.equalsIgnoreCase("none")) {
		// query.append("AND
		// lower(bh.day_name)=lower('"+selectedDayOfTheWeek+"') "
		// + "AND lower(c.CATEGORY_LIST)=lower('"+ s +"')");
		// }

		// Opening time is "FROM_TIME" which is in comboBox_1
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[]
		{ "NONE", "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00",
				"05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
				"11:00", "11:30", "12:00" }));

		comboBox_1.addActionListener(comboBoxActionListener);
		//
		// String opening_time=comboBoxActionListener.getItemSelected();
		//
		// if(opening_time!=null && !opening_time.equalsIgnoreCase("none")){
		// query.append("AND bh.open_time>=lower('"+opening_time+"')");
		//
		// }
		// Closing time is "To_time" which is in comboBox_3_1
		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[]
		{ "None", "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00",
				"05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
				"11:00", "11:30", "12:00" }));

		comboBox_3.addActionListener(comboBoxActionListener);

		// String closing_time=comboBoxActionListener.getItemSelected();
		// if(closing_time!=null && !closing_time.equalsIgnoreCase("none")){
		// query.append("AND bh.close_time<=lower('"+closing_time+"')");
		// }

		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Connection conn = DriverManager.getConnection(url, username, password);
					Statement stmt = null;
					ResultSet rs = null;
					String combo = comboBox.getSelectedItem().toString().trim();
					if (combo == "NONE")
					{
						combo = "SUNDAY";
					}
					String s = selected_catg;

					String query = "Select b.b_name,b.city,b.state_name,b.stars from business_yelp b,business_hours bh,categories c "
							+ "where b.business_id=bh.b_id and bh.b_id=c.b_id AND lower(bh.day_name)=lower('" + combo
							+ "') " + "AND lower(c.CATEGORY_LIST)=lower('" + s + "')";
					// System.out.println("Query is : "+query);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					int rrowcount = 0;
					while (rs.next())
					{
						rrowcount++;
					}
					System.out.println("Rowcount for Cateory Table is : " + rrowcount);

					String[] columnNames = new String[]
					{ "Business", "City", "State", "Stars" };
					DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
					table1.setModel(tableModel);
					// table1.setShowVerticalLines(true);
					table1.setShowGrid(true);

					rs = stmt.executeQuery(query);
					while (rs.next())
					{
						b_name = rs.getString(rs.findColumn("b_name"));
						String city = rs.getString(rs.findColumn("city"));
						String state_name = rs.getString(rs.findColumn("state_name"));
						String stars = rs.getString(rs.findColumn("stars"));

						Vector<Object> data = new Vector<Object>();
						data.add(b_name);
						data.add(city);
						data.add(state_name);
						data.add(stars);
						tableModel.addRow(data);
					}
					panel.setLayout(new GridLayout(rrowcount, 1, 0, 0));

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		// comboBox_1_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Connection conn = DriverManager.getConnection(url, username, password);
					Statement stmt = null;
					ResultSet rs = null;
					String s = selected_catg;
					String combo = comboBox.getSelectedItem().toString().trim();
					System.out.println("The from time is :" + comboBox_1.getSelectedItem().toString());
					String from_time = (comboBox_1.getSelectedItem() != null
							? comboBox_1.getSelectedItem().toString().trim() : null);
					if (from_time != null && !from_time.equalsIgnoreCase("none"))
					{

						String query = "Select b.b_name,b.city,b.state_name,b.stars from business_yelp b,business_hours bh,categories c "
								+ "where b.business_id=bh.b_id and bh.b_id=c.b_id AND lower(bh.day_name)=lower('"
								+ combo + "') " + "AND lower(c.CATEGORY_LIST)=lower('" + s
								+ "') AND bh.open_time>=lower('" + from_time + "')";

						System.out.println("Query is : " + query);
						stmt = conn.createStatement();
						rs = stmt.executeQuery(query);
						int rrowcount = 0;
						while (rs.next())
						{
							rrowcount++;
						}
						System.out.println("Rowcount is : " + rrowcount);

						String[] columnNames = new String[]
						{ "Business", "City", "State", "Stars" };
						DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
						table1.setModel(tableModel);
						// table1.setShowVerticalLines(true);
						table1.setShowGrid(true);

						rs = stmt.executeQuery(query);
						while (rs.next())
						{
							String b_name = rs.getString(rs.findColumn("b_name"));
							String city = rs.getString(rs.findColumn("city"));
							String state_name = rs.getString(rs.findColumn("state_name"));
							String stars = rs.getString(rs.findColumn("stars"));

							Vector<Object> data = new Vector<Object>();
							data.add(b_name);
							data.add(city);
							data.add(state_name);
							data.add(stars);
							tableModel.addRow(data);
						}
						panel.setLayout(new GridLayout(rrowcount, 1, 0, 0));
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[]
		{ "NONE", "CITY", "STATE" }));

		comboBox_3_1 = new JComboBox();
		comboBox_3_1.setModel(new DefaultComboBoxModel(new String[]
		{ "NONE", "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00",
				"05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
				"11:00", "11:30", "12:00" }));
		comboBox_3_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Connection conn = DriverManager.getConnection(url, username, password);
					Statement stmt = null;
					ResultSet rs = null;
					String s = selected_catg;
					String combo = comboBox.getSelectedItem().toString().trim();
					if (combo == "NONE")
					{
						combo = "SUNDAY";
					}

					String from_time = (comboBox_1.getSelectedItem() != null
							? comboBox_1.getSelectedItem().toString().trim() : null);
					String to_time = (comboBox_3.getSelectedItem() != null
							? comboBox_3.getSelectedItem().toString().trim() : null);

					if (from_time != null && !from_time.equalsIgnoreCase("none"))
					{
						if (to_time != null && !to_time.equalsIgnoreCase("none"))
						{

							String query = "Select b.b_name,b.city,b.state_name,b.stars from business_yelp b,business_hours bh,categories c "
									+ "where b.business_id=bh.b_id and bh.b_id=c.b_id AND lower(bh.day_name)=lower('"
									+ combo + "') " + "AND lower(c.CATEGORY_LIST)=lower('" + s
									+ "') AND bh.open_time>=lower('" + from_time + "')" + "AND bh.close_time<=lower('"
									+ to_time + "')";

							System.out.println("Query is : " + query);

							stmt = conn.createStatement();
							rs = stmt.executeQuery(query);
							int rrowcount = 0;
							while (rs.next())
							{
								rrowcount++;
							}

							System.out.println("rowcount is : " + rrowcount);

							String[] columnNames = new String[]
							{ "Business", "City", "State", "Stars" };
							DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
							table1.setModel(tableModel);
							// table1.setShowVerticalLines(true);
							table1.setShowGrid(true);

							rs = stmt.executeQuery(query);
							while (rs.next())
							{
								String b_name = rs.getString(rs.findColumn("b_name"));
								String city = rs.getString(rs.findColumn("city"));
								String state_name = rs.getString(rs.findColumn("state_name"));
								String stars = rs.getString(rs.findColumn("stars"));

								Vector<Object> data = new Vector<Object>();
								data.add(b_name);
								data.add(city);
								data.add(state_name);
								data.add(stars);
								tableModel.addRow(data);
							}
							panel.setLayout(new GridLayout(rrowcount, 1, 0, 0));
						}
					}
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		JScrollPane scrollPane_3 = new JScrollPane();

		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frmYelpTool.dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Verdana", Font.BOLD, 12));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setActionCommand("Close");
		btnNewButton_1.setBorder(new LineBorder(new Color(255, 255, 255), 3));
		btnNewButton_1.setContentAreaFilled(false);

		JButton btnSearch = new JButton("Search");

		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Verdana", Font.BOLD, 12));
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorder(new LineBorder(new Color(255, 255, 255), 3));
		btnSearch.setActionCommand("Close");
		GroupLayout groupLayout = new GroupLayout(frmYelpTool.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
								.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(
												lblDayOfThe)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 120,
												GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewLabel))))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 300,
														GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 300,
												GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(scrollPane_3,
														GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup().addGap(47)
														.addComponent(lblNewLabel_1))
												.addGroup(groupLayout.createSequentialGroup().addGap(32).addComponent(
														comboBox_3_1, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
												.addGap(33)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblNewLabel_2)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE,
																		94, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED, 614,
																		Short.MAX_VALUE)
																.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE,
																		113, GroupLayout.PREFERRED_SIZE)
																.addGap(39).addComponent(btnNewButton_1,
																		GroupLayout.PREFERRED_SIZE, 136,
																		GroupLayout.PREFERRED_SIZE)))))
								.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(
						btnStart)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblDayOfThe)
								.addComponent(lblNewLabel).addComponent(lblNewLabel_1).addComponent(lblNewLabel_2))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_3_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 31,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
				.addGap(28)));

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(221, 160, 221));
		scrollPane_2.setViewportView(panel_2);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(153, 255, 204));
		scrollPane_1.setViewportView(panel_1);

		panel.setBackground(new Color(135, 206, 235));
		scrollPane.setViewportView(panel);
		// panel.setLayout(new GridLayout(1, 0, 0, 0));
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				comboBox.addActionListener(comboBoxActionListener);

				/*
				 * StringBuilder query1 = new StringBuilder(
				 * "Select b.b_name, b.city, b.state_name, b.stars " +
				 * "from business_yelp b, business_hours bh, categories c, business_sub_category bsc, attributes a "
				 * +
				 * "where b.business_id=bh.b_id and bh.b_id=c.b_id and c.b_id=bsc.b_id "
				 * );
				 */

				StringBuilder query1 = new StringBuilder("SELECT b.B_NAME, b.CITY, b.STATE_NAME,b.STARS")
						.append(" FROM BUSINESS_YELP b")
						.append(" LEFT OUTER JOIN CATEGORIES c ON c.B_ID = b.BUSINESS_ID")
						.append(" LEFT OUTER JOIN BUSINESS_SUB_CATEGORY bsc ON bsc.B_ID = c.B_ID")
						.append(" LEFT OUTER JOIN ATTRIBUTES_LIST a ON a.B_ID = bsc.B_ID")
						.append(" LEFT OUTER JOIN BUSINESS_HOURS bh ON bh.B_ID = bsc.B_ID WHERE ");

				String selectedValueOfCategory = selected_catg;
				System.out.println("Selected Category is : " + selectedValueOfCategory);

				if (selectedValueOfCategory != null)
				{
					query1.append(" lower(c.category_list)=lower('" + selectedValueOfCategory + "')");
				}

				String selectedValueofSubCategory = selected_sub_catg;
				System.out.println("Selected Sub Category is : " + selectedValueofSubCategory);

				if (selectedValueofSubCategory != null && !selectedValueofSubCategory.isEmpty()
						&& !selectedValueofSubCategory.equalsIgnoreCase(""))
				{
					query1.append(" AND lower(bsc.sub_category)=lower('" + selectedValueofSubCategory + "')");
				}

				String selectedValueOfAttribute = selected_attribute;
				System.out.println("Selected Category Attribute is : " + selectedValueOfAttribute);

				if (selectedValueOfAttribute != null && !selectedValueOfAttribute.isEmpty()
						&& !selectedValueOfAttribute.equalsIgnoreCase(""))
				{
					query1.append(" AND lower(bsc.sub_category)=lower('" + selectedValueOfAttribute + "')");
				}

				String selectedDayOfTheWeek = comboBoxActionListener.getItemSelected();
				System.out.println("Selected Day Of The Week is : " + selectedDayOfTheWeek);

				if (selectedDayOfTheWeek != null && !selectedDayOfTheWeek.equalsIgnoreCase("none"))
				{
					query1.append(" AND lower(bh.day_name)=lower('" + selectedDayOfTheWeek + "')");
				}

				comboBox_2.addActionListener(comboBoxActionListener);

				String opening_time = comboBoxActionListener.getItemSelected();
				System.out.println("Opening Time is : " + opening_time);

				if (opening_time != null && !opening_time.equalsIgnoreCase("none"))
				{
					query1.append(" AND bh.open_time>=lower('" + opening_time + "')");

				}
				// Closing time is "To_time" which is in comboBox_3_1

				comboBox_3.addActionListener(comboBoxActionListener);

				String closing_time = comboBoxActionListener.getItemSelected();
				System.out.println("Closing Time is : " + closing_time);

				if (closing_time != null && !closing_time.equalsIgnoreCase("none"))
				{
					query1.append(" AND bh.close_time<=lower('" + closing_time + "')");
				}

				System.out.println("Query is : " + query1);

				int rrowcount = 0;
				try
				{
					stmt = dbconnection().createStatement();
					rs = stmt.executeQuery(query1.toString());

					rrowcount = 0;
					while (rs.next())
					{
						rrowcount++;
					}
					String[] columnNames = new String[]
					{ "Business", "City", "State", "Stars" };
					DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
					table1.setModel(tableModel);
					// table1.setShowVerticalLines(true);
					table1.setShowGrid(true);
					rs = stmt.executeQuery(query1.toString());

					while (rs.next())
					{
						b_name = rs.getString(rs.findColumn("b_name"));
						String city = rs.getString(rs.findColumn("city"));
						String state_name = rs.getString(rs.findColumn("state_name"));
						String stars = rs.getString(rs.findColumn("stars"));

						Vector<Object> data = new Vector<Object>();
						data.add(b_name);
						data.add(city);
						data.add(state_name);
						data.add(stars);
						tableModel.addRow(data);
					}
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panel.setLayout(new GridLayout(rrowcount, 1, 0, 0));
			}
		});

		table1.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				System.out.println("First Line of mouselistener");
				int row = table1.rowAtPoint(evt.getPoint());
				System.out.println("The row is " + row);
				String businessName = table1.getValueAt(row, 0).toString();
				System.out.println("The selected business is :" + businessName);
				if (row > 0)
				{
					newframe = new Review(businessName);
					newframe.showmaindata(businessName);
					//newframe.setSize(500, 500);
					newframe.setVisible(true);
					newframe.revalidate();
					newframe.repaint();
				}
			}
		});
		scrollPane_3.setViewportView(table1);
		frmYelpTool.getContentPane().setLayout(groupLayout);
	}

	public void init_categories()
	{
		/* importing data for Categories-Column 1 */
		try
		{
			Statement pstmt = null;
			ResultSet rs = null;
			String query = "Select distinct Category_list from categories order by Category_list";
			pstmt = dbconnection().createStatement();
			rs = pstmt.executeQuery(query);
			final ResultSetMetaData meta = rs.getMetaData();
			int rowcount = 0;
			while (rs.next())
			{
				rowcount++;
			}
			rs = pstmt.executeQuery(query);
			// System.out.println("rs.getFetchSize() : "+rs.);
			panel.setLayout(new GridLayout(rowcount, 1, 0, 0));

			while (rs.next())
			{
				JCheckBox checkbox1 = new JCheckBox();
				checkbox1.setText(rs.getString("CATEGORY_LIST"));
				checkbox1.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent e)
					{
						Object source = e.getItemSelectable();
						JCheckBox checkbox = (JCheckBox) source;
						if (e.getStateChange() == ItemEvent.SELECTED)
						{
							System.out.println("selected");
							System.out.println(checkbox.getText());
							selected_catg = checkbox.getText();
							listcategories.add(selected_catg);

						} else if (e.getStateChange() == ItemEvent.DESELECTED)
						{
							System.out.println("unselected");
							System.out.println(checkbox.getText());
							selected_catg = checkbox.getText();
							listcategories.remove(selected_catg);
						}
						sub_Category(listcategories);
						// try
						// {
						// sub_attributes(listcategories);
						// } catch (SQLException e1)
						// {
						// // TODO Auto-generated catch block
						// e1.printStackTrace();
						// }
					}
				});

				panel.add(checkbox1);
			}
		} catch (SQLException ex)
		{
			Logger.getLogger(Hw3.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sub_Category(ArrayList<String> listOfSelectedCategories)
	{
		try
		{
			// Connection
			// conn=DriverManager.getConnection(url,username,password);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int i = 0;
			int rowcount2 = 0;
			panel_1.removeAll();
			System.out.println("selected category = " + listOfSelectedCategories);
			StringBuilder populateSubCatQuery = new StringBuilder();

			populateSubCatQuery.append("Select distinct bs.sub_category from business_sub_category bs,categories c "
					+ "where bs.b_id=c.b_id AND c.category_list IN (");
			for (int index = 0; index < listOfSelectedCategories.size(); index++)
			{
				populateSubCatQuery.append("'" + listOfSelectedCategories.get(index) + "'");
				if (index < listOfSelectedCategories.size() - 1)
				{
					populateSubCatQuery.append(",");
				}
			}
			populateSubCatQuery.append(") order by sub_category");

			pstmt = dbconnection().prepareStatement(populateSubCatQuery.toString());
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rowcount2 += rowcount2;
			}
			rs = pstmt.executeQuery();
			panel_1.setLayout(new GridLayout(rowcount2, 1, 0, 0));
			while (rs.next())
			{
				JCheckBox checkbox2 = new JCheckBox();
				checkbox2.setText(rs.getString("SUB_CATEGORY"));
				checkbox2.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent e)
					{
						Object source_2 = e.getItemSelectable();
						JCheckBox checkbox_2 = (JCheckBox) source_2;
						if (e.getStateChange() == ItemEvent.SELECTED)
						{
							System.out.println("selected");
							System.out.println(checkbox_2.getText());
							selected_sub_catg = checkbox_2.getText();
							list_subcategories.add(checkbox_2.getText());

						} else
						{

							System.out.println("unselected");
							System.out.println(checkbox_2.getText());
							selected_sub_catg = checkbox_2.getText();
							list_subcategories.remove(checkbox_2.getText());
						}
						try
						{
							sub_attributes(list_subcategories);
						} catch (SQLException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				panel_1.add(checkbox2);
				panel_1.revalidate();
				panel_1.repaint();

				// for(int col=1;col<= rs.getMetaData().getColumnCount();col++){
				// list_subcategories.add(rs.getString(col));
				// }
			}
		} catch (SQLException ex)
		{
			Logger.getLogger(Hw3.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sub_attributes(ArrayList<String> Listofselectedsubcategory) throws SQLException
	{
		try
		{
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int rowcount3 = 0;

			StringBuilder populateAttrQuery = new StringBuilder();
			populateAttrQuery
					.append("Select distinct a.attr_name from ATTRIBUTES_LIST a,BUSINESS_YELP b,BUSINESS_SUB_CATEGORY bsc where a.B_ID=b.BUSINESS_ID AND b.BUSINESS_ID=bsc.B_ID "
							+ " AND lower(bsc.SUB_CATEGORY) IN lower(");

			for (int index = 0; index < Listofselectedsubcategory.size(); index++)
			{
				populateAttrQuery.append("'" + Listofselectedsubcategory.get(index) + "'");
				if (index < Listofselectedsubcategory.size() - 1)
				{
					populateAttrQuery.append(",");
				}
			}
			populateAttrQuery.append(" ) order by a.attr_name");
			pstmt = dbconnection().prepareStatement(populateAttrQuery.toString());
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rowcount3 += rowcount3;
			}
			rs = pstmt.executeQuery();

			panel_2.setLayout(new GridLayout(rowcount3, 1, 0, 0));

			while (rs.next())
			{
				JCheckBox checkbox3 = new JCheckBox();
				checkbox3.setText(rs.getString("ATTR_NAME"));

				checkbox3.addItemListener(new ItemListener()
				{

					public void itemStateChanged(ItemEvent e)
					{
						Object source_3 = e.getItemSelectable();
						JCheckBox checkbox_3 = (JCheckBox) source_3;
						if (e.getStateChange() == ItemEvent.SELECTED)
						{

							System.out.println("selected attribute :" + checkbox_3.getText());
							System.out.println(checkbox_3.getText());
							selected_attribute = checkbox_3.getText();
							list_of_attributes.add(checkbox_3.getText());
							// sub_attributes(checkbox_3.getText());
						} else
						{

							System.out.println("unselected");
							System.out.println(checkbox_3.getText());
							selected_attribute = checkbox_3.getText();
							list_of_attributes.remove(checkbox_3.getText());
						}
					}

				});
				System.out.println("selected Sub_category is = " + Listofselectedsubcategory);
				panel_2.add(checkbox3);
				panel_2.revalidate();
				panel_2.repaint();
				// for(int col=1;col<= rs.getMetaData().getColumnCount();col++){
				// list_sub_attributes.add(rs.getString(col));
				// }
			}

		} catch (SQLException ex)
		{
			Logger.getLogger(Hw3.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) throws SQLException
	{
		/* oracle connection statements */
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e)
		{
			System.out.println("Oracle JDBC Driver not present or found");
			e.printStackTrace();
			return;
		}
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e)
		{
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
		Statement stmt = null;
		PreparedStatement p_stmt = null;
		if (conn != null)
		{
			try
			{
				stmt = conn.createStatement();
			} catch (SQLException e)
			{
				Logger.getLogger(Hw3.class.getName()).log(Level.SEVERE, null, e);
			} finally
			{
				stmt.close();
			}
		} else
		{
			System.out.println("Failed to connect to Oracle DB");
		}

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Hw3 window = new Hw3();
					window.frmYelpTool.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}

class ComboBoxActionListener implements ActionListener
{

	String itemSelected;

	public String getItemSelected()
	{
		return itemSelected;
	}

	public void setItemSelected(String itemSelected)
	{
		this.itemSelected = itemSelected;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		@SuppressWarnings("rawtypes")
		JComboBox comboBox = (JComboBox) e.getSource();
		this.itemSelected = comboBox.getSelectedItem().toString().trim();
	}

}

class Review extends JFrame
{

	private JPanel contentPane;
	private JScrollPane jScrollPane1;
	private JTable jTable1;
	private JButton jButton1;
	public static String b_name = " ";
	public static String Title = "Review's Window";
	public static final String URL = "jdbc:oracle:thin:@localhost:1523:ORCLE";
	public static final String USER = "system";
	public static final String PASS = "Oracle_2";

	/**
	 * Launch the application.
	 */
	public void showmaindata(String businessName)
	{
		System.out.println("Enter review main");
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Review frame = new Review();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Review()
	{
		initComponents();
		initmore();

	}

	public Review(String business_name)
	{
		b_name = business_name;
		initComponents();
		initmore();

	}

	private void initComponents()
	{

		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jButton1 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][]
		{
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null } }, new String[]
		{ "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(jTable1);

		jButton1.setText("jButton1");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600,
												Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
												jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}

	// </editor-fold>//GEN-END:initComponents

	private void initmore()
	{
		DefaultTableModel tmodel = new DefaultTableModel();
		jTable1.setModel(tmodel);
		tmodel.addColumn("Review Date");
		tmodel.addColumn("Stars");
		tmodel.addColumn("Review Text");
		tmodel.addColumn("UserID");
		tmodel.addColumn("Useful Votes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setTitle(Title);
		jButton1.setText("CLOSE");
		jButton1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		try
		{
			PreparedStatement statement = null;
			PreparedStatement statement2 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = null;
			con = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("Preparing stmt..");
			statement = con.prepareStatement(
					"Select r.r_date,r.stars,r.text,r.user_id,r.votes_useful from REVIEW_YELP r,BUSINESS_YELP b where r.BUSINESS_ID=b.BUSINESS_ID "
							+ " and b.b_name=?");
			System.out.println("business name is:" + b_name);
			statement.setString(1, b_name);
			System.out.println("Executing query");
			rs = statement.executeQuery();

			while (rs.next())
			{

				tmodel.addRow(new Object[]
				{ rs.getString("r_date"), rs.getString("stars"), rs.getString("TEXT"), rs.getString("USER_ID"),
						rs.getString("VOTES_USEFUL") });

			}

			statement.close();
			con.close();

			// System.out.println("business name at end:"+b_name);
			pack();
			validate();
			repaint();

		} catch (ClassNotFoundException ex)
		{
			Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex)
		{
			Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
