package com.lingkj.common.utils.payment.wxpay;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: rizenguo
 * Date: 2014/11/1
 * Time: 14:06
 */
public class XMLParser {

    public static Map<String, Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }


    private static void mapToXMLTest2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value) {
                value = "";
            }
            if (value.getClass().getName().equals("java.util.ArrayList")) {  
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");  
                for (int i = 0; i < list.size(); i++) {  
                    HashMap hm = (HashMap) list.get(i);
                    mapToXMLTest2(hm, sb);  
                }  
                sb.append("</" + key + ">");  
  
            } else {  
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");  
                    mapToXMLTest2((HashMap) value, sb);
                    sb.append("</" + key + ">");  
                } else {  
                    sb.append("<" + key + ">" + value + "</" + key + ">");  
                }  
  
            }  
  
        }  
   	}
    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
    public static void main(String[] args) {

//    	static String  xml = "<xml> " +
//    			 "<appid>wx2421b1c4370ec43b</appid> " +
//    			 "<attach>支付测试</attach> " +
//    			 "<body>JSAPI支付测试</body> " +
//    			 "<mch_id>10000100</mch_id> " +
//    			 "<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str> " +
//    			 "<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url> " +
//    			 "<openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid> " +
//    			 "<out_trade_no>1415659990</out_trade_no> " +
//    			 "<spbill_create_ip>14.23.150.211</spbill_create_ip> " +
//    			 "<total_fee>1</total_fee> " +
//    			 "<trade_type>NATIVE</trade_type> " +
//    			 "<sign>0CB01533B8C1EF103065174F50BCA001</sign> " +
//    			 "</xml> ";
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("appid", "wx2421b1c4370ec43b");
    	map.put("attach", "支付fdfdfd</>测试");
    	StringBuffer sb = new StringBuffer();
        sb.append("<xml>");  
        mapToXMLTest2(map, sb);  
        sb.append("</xml>");  
        System.out.println(sb);
//    	Map<String, Object> webMap = new HashMap<String, Object>();
//    	webMap.put("xml", map);
//    	System.out.println(parseToXML(map, null));

//        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8"));
        
        //将要提交给API的数据对象转换成XML格式数据Post给API
//        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
	}
    
      
}