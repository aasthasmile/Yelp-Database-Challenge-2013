
CREATE TABLE business_yelp (
    business_id VARCHAR2(100) not null  ,
    full_address VARCHAR2(1000)  ,
    b_open VARCHAR2(22),
     
    city VARCHAR2(100)  ,
    state_name VARCHAR2(10),
        
    latitude NUMERIC(8, 6),
    longitude NUMERIC(9, 6),
    review_count numeric(4),
    b_name VARCHAR2(100) not null  ,
    stars Numeric(4,2),
    b_type VARCHAR2(19)  ,
    constraint business_yelp_pk Primary key (business_id),
    constraint b_open_ck1 check (b_open in ('true','false')),
    constraint b_type_ck2 check (b_type in ('business'))
    );
    /
    
    

   CREATE TABLE user_yelp (
    yelping_since varchar2(32),
    votes_cool INT,
    votes_funny INT,
    votes_useful INT,
    review_count numeric(4),
    user_name VARCHAR2(128) ,
    user_id VARCHAR2(22),
    --friends VARCHAR2(1000),
    fans INT,
    average_stars NUMERIC(3, 2),
    type varchar2(6),
    --compliments VARCHAR2(654),
   -- elite_list VARCHAR2(116),
    constraint user_yelp_pk PRIMARY KEY (user_id),
    constraint u_type_ck2 check (type in ('user'))
    );   
   /

    CREATE TABLE review_yelp (
    votes_cool INT,
    votes_funny INT,
    votes_useful INT,
    user_id VARCHAR2(22),
    review_id VARCHAR2(22),
    stars INT,
    r_date timestamp,
    text long,
    type VARCHAR2(6),
    business_id VARCHAR2(22),
    constraint yelp_review_pk PRIMARY KEY (review_id),
    constraint yelp_review_fk1 FOREIGN KEY (business_id) REFERENCES business_yelp(BUSINESS_ID),
    constraint yelp_review_fk2 FOREIGN KEY (user_id) REFERENCES USER_YELP(USER_ID),
    constraint type_ck2 check (type in ('review'))
    );
/
 

 

   CREATE TABLE CHECKIN (
   BUSINESS_ID VARCHAR2(100) ,
   TYPE VARCHAR2(7),
   --CHECKIN_INFO VARCHAR2(1000),
   constraint checkin_user_fk foreign key (business_id) references business_yelp(business_id),
   constraint checkin_user_ck check (type in('checkin'))
   );
   /
    
   

   CREATE TABLE TIPS(
   USER_ID VARCHAR2(6),
   TEXT VARCHAR2(256),
   BUSINESS_ID VARCHAR2(6),
   LIKES VARCHAR2(6),
   TIP_DATE TIMESTAMP,
   TYPE VARCHAR2(6),
   constraint tips_fk1 FOREIGN KEY (business_id) REFERENCES business_yelp(BUSINESS_ID),
   constraint tips_fk2 FOREIGN KEY (user_id) REFERENCES USER_YELP(USER_ID),
   constraint t_type_ck check (type in ('tip'))
   );
   /
   

   CREATE TABLE business_HOURS(
    b_id VARCHAR2(100) not null  ,
    day_name VARCHAR2(1000) ,
    open_time VARCHAR2(10) ,
    close_time VARCHAR2(10) ,
    constraint bh_pk primary key (b_id,day_name),
    constraint day_name_ck check (day_name IN ('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday')),
    constraint bh_fk foreign key (b_id) references business_yelp(business_id)ON DELETE CASCADE
   );
    

   CREATE TABLE categories(
    b_id VARCHAR2(100) not null  ,
    category_list VARCHAR2(1000) ,
    constraint categories_pk primary key (b_id,category_list),
    constraint categories_fk foreign key (b_id) references business_yelp(business_id)ON DELETE CASCADE
   );
   /
    

   CREATE TABLE NEIGHBORHOOD(
    b_id VARCHAR2(100) not null  ,
    area VARCHAR2(1000) ,
    constraint neighborhood_pk primary key (b_id,area),
    constraint neighborhood_fk foreign key (b_id) references business_yelp(business_id) ON DELETE CASCADE
    );
    /
     

    CREATE TABLE attributes_list(
    b_id VARCHAR2(100) not null  ,
    attr_name VARCHAR2(3000) ,
    attr_value VARCHAR2(2000),
    
    constraint attr_fk foreign key (b_id) references business_yelp(business_id) ON DELETE CASCADE
    );
    /
    

    CREATE TABLE elite_list(
    u_id VARCHAR2(100) not null  ,
    year int ,
    constraint elite_pk primary key (u_id,year),
    constraint elite_fk foreign key (u_id) references user_yelp(user_id) ON DELETE CASCADE
    
    );
    /
     

    CREATE TABLE FRIENDS(
    u_id VARCHAR2(100) not null  ,
    F_ID varchar2(36) ,
    constraint frnd_pk primary key (u_id,F_ID),
    constraint frnd_fk foreign key (u_id) references user_yelp(user_id) ON DELETE CASCADE
    );
    /
     

    CREATE TABLE COMPLIMENTS(
    u_id VARCHAR2(100) not null  ,
    complement_type varchar2(1000) ,
    constraint compliment_pk primary key (u_id),
    constraint compliment_fk foreign key (u_id) references user_yelp(user_id) ON DELETE CASCADE
       
    );
    /
     
   
    CREATE TABLE CHECKIN_INFO(
    b_id varchar2(32) not null,
    checkin_info varchar2(3000),
      -- constraint checkin_info_pk primary key (b_id),
    constraint checkin_info_fk foreign key (b_id) references business_yelp(business_id) ON DELETE CASCADE
    );
    /
     
    

    create table business_sub_category(
    B_ID VARCHAR2(100) NOT NULL , 
	  SUB_CATEGORY VARCHAR2(1000) ,
     constraint sub_categories_fk foreign key (b_id) references business_yelp(business_id)ON DELETE CASCADE
   );
   /
   
  