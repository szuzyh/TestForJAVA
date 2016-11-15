import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import spark.Spark;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.route.HttpMethod.get;

/**
 * Created by Leo on 2016/11/11.
 */

public class Main {

    private static ActiveXComponent com;
    private static Dispatch disp;
    static final Random random=new Random();

    public String base64=null;

    public static void main(String[] args) throws SQLException {
        com = new ActiveXComponent("CLSID:0F55CC69-97EF-42A9-B63D-D1831CB2B3B9");
        disp = (Dispatch)com.getObject();

        int srcPathName=random.nextInt(Integer.MAX_VALUE);
        int ret = Dispatch.call(disp, "getCardInfo", new Variant("E:\\Img\\"+srcPathName+".bmp")).getInt();
        if(ret!=0){

//            System.out.print(Dispatch.call(disp, "Sex").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Nation").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Birthday").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Address").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "ID").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "Department").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "StartDate").getString()+"\n");
//            System.out.print(Dispatch.call(disp, "EndDate").getString()+"\n");
            System.out.println("打开设备失败！");
        }
        //获取各项单项信息
//        getMessage("Name");
//        getMessage("Sex");
//        getMessage("Nation");
//        getMessage("Birthday");
//        getMessage("Address");
//        getMessage("ID");
//        getMessage("Department");
//        getMessage("StartDate");
//        getMessage("EndDate");
//
//        //上传信息到数据库
        //uploadMessageToSql();
        //删除某个id下的身份证信息
        //deleteMessage("265287990");

        //删除某个身份证号下的所有信息
//        deleteMessageWithPersonId("441424199508272258");

//        //获取全部信息并打印成表格xml
//        //不需要身份证号
//       getAllMessageWithoutID();
     //
        //getMessageIn("265287990");



        //图片转换
//        ImgToString("E:\\Img\\head.bmp");
//        StringToImg(ImgToString("E:\\Img\\head.bmp"),"E:\\Img\\boom.bmp");
        //网页上传
        //postMessage();
        getAllMessageWithID();
    }

    private static void deleteMessageWithPersonId(String personId) throws SQLException {
        DbAccess dba=new DbAccess();
        dba.init();
        dba.deleteWithID(personId);
    }

    private static void deleteMessage(String id) throws SQLException {
        DbAccess dba=new DbAccess();
        dba.init();
        dba.delete(id);
    }

    private static String getMessageIn(String id) throws SQLException {
        DbAccess dba=new DbAccess();
        dba.init();
        String[] msg=dba.query1(id);
//        String name=null,sex=null,nation=null
//                ,birthday = null,address=null,department=null
//                ,personId=null,startDate=null,endDate=null,imgDate=null;
//        String[] text={name,sex,nation,birthday,address,personId,department,startDate,endDate,imgDate};
//        for (int i=0;i<msg.length;i++){
//            System.out.println(msg[i]);
//            text[i]=msg[i];
//
//        }
        String name=msg[0];
        String sex=msg[1];
        String nation=msg[2];
        String birthday=msg[3];
        String address=msg[4];
        String department=msg[5];
        String personId=msg[6];
        String startDate=msg[7];
        String endDate=msg[8];
        String imgDate=msg[9];
//
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
                +"姓名："+name+"<br/>"
                +"性别："+sex+"<br/>"
                +"民族："+nation+"<br/>"
                +"出生日期："+birthday+"<br/>"
                +"住址："+address+"<br/>"
                +"身份证号："+personId+"<br/>"
                +"签发机关："+department+"<br/>"
                +"发证日期："+startDate+"<br/>"
                +"有效期："+endDate+"<br/>"
                +"</news>";
    }

    //网页上传信息
    private static void postMessage() {
       Spark.post("/message/user",(request, response) -> {
           uploadMessageToSql();
           return true;
       });
    }

    //上传信息
    private static void uploadMessageToSql() throws SQLException {
        int id1=random.nextInt(Integer.MAX_VALUE);
        String id=id1+"";
        String name=Dispatch.call(disp,"Name").getString().trim();
        String sex=Dispatch.call(disp,"Sex").getString().trim();
        String nation=Dispatch.call(disp,"Nation").getString().trim();
        String birthday=Dispatch.call(disp,"Birthday").getString().trim();
        String address=Dispatch.call(disp,"Address").getString().trim();
        String ID=Dispatch.call(disp,"ID").getString().trim();
        String department=Dispatch.call(disp,"Department").getString().trim();
        String startDate=Dispatch.call(disp,"StartDate").getString().trim();
        String endDate=Dispatch.call(disp,"EndDate").getString().trim();

        DbAccess dba=new DbAccess();
        dba.init();
        dba.insert(id,name,sex,nation,birthday,address,ID,department,startDate,endDate,"123");
    }
//字符串转化为图片并保存
    private static boolean StringToImg(String base64String, String createPath) {
        if (base64String==null){
            return false;
        }
        BASE64Decoder decoder=new BASE64Decoder();
        try {
            byte[] bytes=decoder.decodeBuffer(base64String);
            for(int i=0;i<bytes.length;i++){
                if (bytes[i]<0){
                    bytes[i]+=256;
                }
            }

            OutputStream out=new FileOutputStream(createPath);
            out.write(bytes);
            out.flush();
            out.close();
            return  true;
        } catch (IOException e) {
            return false;
        }
    }
//图片转化为字符串并加密保存？？不加密如何
    private static String ImgToString(String path) {
        byte[] data=null;
        try{
            InputStream in=new FileInputStream(path);
            data=new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder=new BASE64Encoder();
        System.out.println(encoder.encode(data));
        return encoder.encode(data);

    }

    //在此之前是不是应该要有个上传信息的方法？？
    private static void getAllMessageWithID() {
        Spark.get("/message/user/:id",(request, response) -> {
            DbAccess dba=new DbAccess();
            dba.init();
            String[] msg=dba.query1(request.params(":id"));
            String name=msg[0];
            String sex=msg[1];
            String nation=msg[2];
            String birthday=msg[3];
            String address=msg[4];
            String department=msg[5];
            String personId=msg[6];
            String startDate=msg[7];
            String endDate=msg[8];
            String imgDate=msg[9];
//            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
//                    +"姓名："+name+"<br/>"
//                    +"性别："+sex+"<br/>"
//                    +"民族："+nation+"<br/>"
//                    +"出生日期："+birthday+"<br/>"
//                    +"住址："+address+"<br/>"
//                    +"身份证号："+personId+"<br/>"
//                    +"签发机关："+department+"<br/>"
//                    +"发证日期："+startDate+"<br/>"
//                    +"有效期："+endDate+"<br/>"
//                    +"</news>";
            String callback="{\"UserID\":\""+request.params(":id")+"\",\"Message\":{\"name\":\""+name+"\"" +
                    ",\"sex\":\""+sex+"\",\"natiom\":\"" + nation + "\",\"birthday\":\"" + birthday + "\"" +
                    ",\"address\":\"" + address + "\",\"personId\":\"" + personId + "\",\"department\":\"" + department + "\"" +
                    ",\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate + "\"}}";
            return callback;
        });
    }

    private static void getAllMessageWithoutID() {

        Spark.get("/message/user",(request, response) -> {
            String name=Dispatch.call(disp,"Name").getString().trim();
            String sex=Dispatch.call(disp,"Sex").getString().trim();
            String nation=Dispatch.call(disp,"Nation").getString().trim();
            String birthday=Dispatch.call(disp,"Birthday").getString().trim();
            String address=Dispatch.call(disp,"Address").getString().trim();
            String personId=Dispatch.call(disp,"ID").getString().trim();
            String department=Dispatch.call(disp,"Department").getString().trim();
            String startDate=Dispatch.call(disp,"StartDate").getString().trim();
            String endDate=Dispatch.call(disp,"EndDate").getString().trim();
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
                    +"姓名："+name+"<br/>"
                    +"性别："+sex+"<br/>"
                    +"民族："+nation+"<br/>"
                    +"出生日期："+birthday+"<br/>"
                    +"住址："+address+"<br/>"
                    +"身份证号："+personId+"<br/>"
                    +"签发机关："+department+"<br/>"
                    +"发证日期："+startDate+"<br/>"
                    +"有效期："+endDate+"<br/>"
                    +"</news>";
        });
    }

    private static void getMessage(String s) {
        Spark.get("/message/"+s,(request, response) -> {
            return Dispatch.call(disp,s).getString().trim();
        });
    }


}
