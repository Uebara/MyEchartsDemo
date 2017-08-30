package com.echarts.action;

/**
 * Created by User on 2016-6-28.
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.echarts.bean.Colors;
import com.echarts.bean.EchartsData;
import com.echarts.bean.Links;
import com.echarts.bean.Nodes;
import com.opensymphony.xwork2.ActionSupport;

public class echartsDisplayAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EchartsData echartsData = new EchartsData();
	private Nodes addNodeData = new Nodes();
	private Links addLinkData = new Links();

	private String attributes;
	private String target;
	private String source;

	private String name;
	private String color;
	private String size;

	private static File dbFile = new File("chartData.db");
	// private static SQLiteDataBase db = new SQLiteDataBase(
	// dbFile.getAbsolutePath());
	private static ArrayList<String> nodeNames = new ArrayList<String>();

	public String echartsDisplay() throws Exception {

		File dbFile = new File("chartData.db");

		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:"
				+ dbFile.getAbsolutePath());

		Statement stmt = conn.createStatement();
		ResultSet nodesList = stmt.executeQuery("SELECT * FROM NODES");

		List<Nodes> nodes = new ArrayList<Nodes>();
		while (nodesList.next()) {
			if (!nodeNames.contains(nodesList.getString("NAME")))
				nodeNames.add(nodesList.getString("NAME").trim());

			Nodes node = new Nodes();
			node.setName(nodesList.getString("NAME"));
			node.setColor(nodesList.getString("COLOR"));
			node.setSize(Integer.valueOf(nodesList.getString("SIZE")));
			nodes.add(node);
		}

		ResultSet linkList = stmt.executeQuery("SELECT * FROM LINKS");

		List<Links> links = new ArrayList<Links>();
		while (linkList.next()) {
			Links link = new Links();
			link.setValue(linkList.getString("ATTRIBUTES"));
			link.setSource(linkList.getString("SOURCE"));
			link.setTarget(linkList.getString("TARGET"));
			links.add(link);
		}

		echartsData.setNodes(nodes);
		echartsData.setLinks(links);

		conn.close();

		return SUCCESS;
	}

	public String echartsInsertNodes() throws Exception {
		System.out.println(nodeNames.toString() + "-" + getName());
		if (nodeNames.contains(getName()))
			return ERROR;
		else
			nodeNames.add(getName());
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:"
				+ dbFile.getAbsolutePath());

		Statement stmt = conn.createStatement();

		// boolean bl = db.tableExist("Nodes");
		// if (!bl) {
		// // 创建表
		// db.createTable("create table Nodes(name text, color text,size text)");
		// }

		String[] colorStrArr = Colors.colorsMap.get(getColor());
		int index = (int) (Math.random() * colorStrArr.length);
		System.out.println(index + "--" + colorStrArr.length);

		// String name = new String(getName().getBytes("iso-8859-1"),"UTF-8");

		String sql = "INSERT INTO NODES (NAME,COLOR,SIZE) VALUES ('"
				+ getName() + "','" + colorStrArr[index] + "','" + getSize()
				+ "')";
		stmt.execute(sql);
		System.out.println("insert!!");
		conn.close();

		addNodeData.setColor(colorStrArr[index]);
		addNodeData.setName(getName());
		addNodeData.setSize(Integer.valueOf(getSize()));

		return SUCCESS;
	}

	public String echartsInsertLinks() throws Exception {

		if (!nodeNames.contains(getTarget())
				|| !nodeNames.contains(getSource()))
			return ERROR;
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:"
				+ dbFile.getAbsolutePath());

		// boolean bl = db.tableExist("Links");
		// if (!bl) {
		// // 创建表
		// db.createTable("create table Links(attributes text, target text,source text)");
		// }

		Statement stmt = conn.createStatement();

		String sql = "INSERT INTO LINKS (attributes,target,source) VALUES ('"
				+ getAttributes() + "','" + getTarget() + "','" + getSource()
				+ "')";
		stmt.execute(sql);
		conn.close();

		addLinkData.setSource(getSource());
		addLinkData.setTarget(getTarget());
		addLinkData.setValue(getAttributes());

		return SUCCESS;
	}

	public EchartsData getEchartsData() {
		return echartsData;
	}

	public void setEchartsData(EchartsData echartsData) {
		this.echartsData = echartsData;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Nodes getAddNodeData() {
		return addNodeData;
	}

	public void setAddNodeData(Nodes addNodeData) {
		this.addNodeData = addNodeData;
	}

	public Links getAddLinkData() {
		return addLinkData;
	}

	public void setAddLinkData(Links addLinkData) {
		this.addLinkData = addLinkData;
	}

}
