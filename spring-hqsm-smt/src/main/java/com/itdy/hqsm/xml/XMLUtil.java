package com.itdy.hqsm.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * @author Steven
 *
 */
public class XMLUtil {
    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }


  //  public static void main(String[] args) {

           /*// 创建需要转换的对象
            MyUser user = new MyUser(1, "Steven", "@sun123", new Date(), 1000.0);
            List<Computer> listt = new ArrayList<Computer>();
            listt.add(new Computer("xxxMMeedd", "asus", new Date(), 4455.5));
            listt.add(new Computer("lenvoXx", "lenvo", new Date(), 4999));
            user.setComputers(listt);
            System.out.println("---将对象转换成string类型的xml Start---");
            // 将对象转换成string类型的xml
            String str = XMLUtil.convertToXml(user);
            // 输出
            System.out.println(str);
            System.out.println("---将对象转换成string类型的xml End---");
            System.out.println();
            System.out.println("---将String类型的xml转换成对象 Start---");
            MyUser userTest = (MyUser) XMLUtil.convertXmlStrToObject(MyUser.class, str);
            System.out.println(userTest);
            System.out.println("---将String类型的xml转换成对象 End---");*/


      /*  // 创建需要转换的对象
        MyUser myUser = new MyUser(1123, "Steven123", "@sun123123", new Date(), 1000.0);
        String path = "D:\\MyUser.xml";
        System.out.println("---将对象转换成File类型的xml Start---");
        XMLUtil.convertToXml(myUser, path);
        System.out.println("---将对象转换成File类型的xml End---");
        System.out.println();
        System.out.println("---将File类型的xml转换成对象 Start---");
        MyUser user2 = (MyUser) XMLUtil.convertXmlFileToObject(MyUser.class, path);
        System.out.println(user2);
        System.out.println("---将File类型的xml转换成对象 End---");  */


    /*    MyUser users = new MyUser(1, "Steven", "@sun123", new Date(), 1000.0);
        List<Computer> list = new ArrayList<Computer>();
        list.add(new Computer("xxxMMeedd", "asus", new Date(), 4455.5));
        list.add(new Computer("lenvoXx", "lenvo", new Date(), 4999));
        user.setComputers(list);
        String path = "D:\\user.xml";
        System.out.println("---将对象转换成File类型的xml Start---");
        XMLUtil.convertToXml(users, path);
        System.out.println("---将对象转换成File类型的xml End---");
        System.out.println();
        System.out.println("---将File类型的xml转换成对象 Start---");
        MyUser user3 = (MyUser) XMLUtil.convertXmlFileToObject(MyUser.class, path);
        System.out.println(user3);
        System.out.println("---将File类型的xml转换成对象 End---");*/
 //   }
}
