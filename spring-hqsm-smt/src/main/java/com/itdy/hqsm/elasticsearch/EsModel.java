package com.itdy.hqsm.elasticsearch;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class EsModel {
	
	
	 
    private String id;
    private String name;
    private int age;
    private Date date;



}

