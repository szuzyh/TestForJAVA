import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import spark.Spark;

import static spark.route.HttpMethod.get;

/**
 * Created by Leo on 2016/11/11.
 */

public class Main {

    private static ActiveXComponent com;
    private static Dispatch disp;
    public static void main(String[] args){
        com = new ActiveXComponent("CLSID:0F55CC69-97EF-42A9-B63D-D1831CB2B3B9");
        disp = (Dispatch)com.getObject();
        int ret = Dispatch.call(disp, "getCardInfo", new Variant("E:\\Img\\head.jpg")).getInt();
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
        getMessage("Name");
        getMessage("Sex");
        getMessage("Nation");
        getMessage("Birthday");
        getMessage("Address");
        getMessage("ID");
        getMessage("Department");
        getMessage("StartDate");
        getMessage("EndDate");

        //获取全部信息并打印成表格xml
        //不需要身份证号
        getAllMessageWithoutID();

    }

    private static void getAllMessageWithoutID() {

        Spark.get("/message",(request, response) -> {
            String name=Dispatch.call(disp,"Name").getString().trim();
            String sex=Dispatch.call(disp,"Sex").getString().trim();
            String nation=Dispatch.call(disp,"Nation").getString().trim();
            String birthday=Dispatch.call(disp,"Birthday").getString().trim();
            String address=Dispatch.call(disp,"Address").getString().trim();
            String ID=Dispatch.call(disp,"ID").getString().trim();
            String department=Dispatch.call(disp,"Department").getString().trim();
            String startDate=Dispatch.call(disp,"StartDate").getString().trim();
            String endDate=Dispatch.call(disp,"EndDate").getString().trim();
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"
                    +"姓名："+name+"<br/>"
                    +"性别："+sex+"<br/>"
                    +"民族："+nation+"<br/>"
                    +"出生日期："+birthday+"<br/>"
                    +"住址："+address+"<br/>"
                    +"身份证号："+ID+"<br/>"
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
