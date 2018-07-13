package net.wrage.wsimpleselector;

import java.util.List;

public class CarModelBean {
     String fullname;
     String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String id;
     String initial;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public List<CarModel> getList() {
        return list;
    }

    public void setList(List<CarModel> list) {
        this.list = list;
    }

    List<CarModel> list;
    public class CarModel{
         String id;    //string	ID
         String name;//	string	名称
         String fullname;//	string	全称
         String initial;//string	首字母
         String logo;//	string	LOGO
         String salestate;//	string	销售状态
         String depth;//	string	层级

         public String getId() {
             return id;
         }

         public void setId(String id) {
             this.id = id;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }

         public String getFullname() {
             return fullname;
         }

         public void setFullname(String fullname) {
             this.fullname = fullname;
         }

         public String getInitial() {
             return initial;
         }

         public void setInitial(String initial) {
             this.initial = initial;
         }

         public String getLogo() {
             return logo;
         }

         public void setLogo(String logo) {
             this.logo = logo;
         }

         public String getSalestate() {
             return salestate;
         }

         public void setSalestate(String salestate) {
             this.salestate = salestate;
         }

         public String getDepth() {
             return depth;
         }

         public void setDepth(String depth) {
             this.depth = depth;
         }
     }
}
