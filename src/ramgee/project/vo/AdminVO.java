package ramgee.project.vo;

public class MenuVO {

   private String productName;
   private String price;

   public MenuVO () {}
   
   public MenuVO (String productName, String price) {
      super();
      this.productName = productName;
      this.price = price;
   }

   //getter, setter
   public String getName() {
      return productName;
   }

   public void setName(String productName) {
      this.productName = productName;
   }

   public String getPrice() {
      return price;
   }

   public void setPrice(String price) {
      this.price = price;
   }

}