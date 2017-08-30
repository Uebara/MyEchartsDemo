package com.meiko.sql;

import java.io.File;
import java.util.*;

/**
 * @author user
 * 
 */
public class SQLiteTest extends Thread {
	public void run() {
		File dbFile = new File("chartData.db");
		SQLiteDataBase db = new SQLiteDataBase(dbFile.getAbsolutePath());

//		Map<String, Object> keyMap = new HashMap<String, Object>();
//		keyMap.put("id", 11);
//		keyMap.put("aa", "123");
		boolean bl = db.tableExist("nodes");
		if (!bl) {
			// 创建表
			db.createTable("create table t_student(id integer primary key, name varchar2(50),age int,sex varchar2(50))");
		}

		// 插入表数据
		for (int i = 0; i < 10; i++) {
			Map<String, Object> fieldMap = new HashMap<String, Object>();
			fieldMap.put("name", "user" + i);
			fieldMap.put("size", i + 1);
			fieldMap.put("color", i / 2 == 0 ? "男" : "女");
			db.insert("t_student", fieldMap);
		}
//
//		// 删除表数据
//		db.delete("t_student", "name", "user3");
//
//		// 修改表数据
//		Map<String, Object> fieldMap = new HashMap<String, Object>();
//		fieldMap.put("name", "hisunsray");
//		fieldMap.put("age", 100);
//
//		keyMap = new HashMap<String, Object>();
//		keyMap.put("id", 10);
//		db.update("t_student", keyMap, fieldMap);

		List<Map<String, String>> resultList = db
				.queryList("select * from color");
		for (int j = 0; j < resultList.size(); j++) {
			Map<String, String> map = (Map<String, String>) resultList.get(j);
//			Iterator entries = map.entrySet().iterator();
//			Map.Entry entry;
//			while (entries.hasNext()) {
//				entry = (Map.Entry) entries.next();
//				String name = (String) entry.getKey();
//				String value = entry.getValue().toString();
//				System.out.print(name + "=" + value + " ");
//			}
			System.out.println("NAME="+map.get("NAME"));
			System.out.println("COLORS"+map.get("COLORS").split(","));
		}
	}

	public static void main(String args[]) {
//		for (int i = 0; i < 1; i++) {
			SQLiteTest test = new SQLiteTest();
			test.start();
//		}
	}
}
