package ramgee.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import ramgee.project.db.DBProperties;
import ramgee.project.vo.ProductVO;

public class ProductDAO {

	private String url = DBProperties.URL;
	private String uid = DBProperties.UID;
	private String upw = DBProperties.UPW;

		   private Connection connection;

		   private Map<String, ProductVO> products;

		   Scanner scan = new Scanner(System.in);

		   Map<String, String> memAdmin = new HashMap<>();
		   Map<String, String> cafeAdmin = new HashMap<>();
		   //HashMap map = new HashMap();
		   String product = null;
		   String price = null;
		   String mname = null;
		   String minfo = null;


		   //생성자
		   public ProductDAO() {
		      products = new HashMap<>();

		      try {
		         Class.forName("oracle.jdbc.driver.OracleDriver");   
		         connection = DriverManager.getConnection(url, uid, upw);
		      } catch (Exception e) {
		         System.out.println("CLASS FOR NAME ERR");
		         e.printStackTrace();
		      }
		   }

		   //메뉴추가
		   public int addProduct(ProductVO productVO ) {
		      int result = 0;
		      
		      Connection conn = null;
		      PreparedStatement statement = null;

		      String query = "INSERT INTO MENU (product_no, product_name, price, explanation, product_count) VALUES (product_no_seq.NEXTVAL, ?, ?, ?, ?)";
		      //Product_SEQ.NEXTVAL, ?, ?

		      try{

		         conn = DriverManager.getConnection(url, uid, upw);

		         //메뉴, 가격 설정
		         statement = conn.prepareStatement(query);
		         statement.setString(1, productVO.getProduct_name());
		         statement.setInt(2, productVO.getPrice());
		         statement.setString(3,  productVO.getExplanation());
		         statement.setInt(4, productVO.getProduct_count());

		         result = statement.executeUpdate();  // 쿼리를 실행 - 메뉴 추가.

		      } catch (Exception e) {
		         e.printStackTrace();
		      } finally {

		         try {
		            conn.close();
		            if (statement != null) {
		               statement.close();
		            }
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		      }

		      return result;

		   }

		   //메뉴 수정
		   public void updateProduct (ProductVO productVO) throws SQLException {
		      String query = "UPDATE MENU SET product_name = ?, price = ?, explanation =?, product_count = ? WHERE product_no = ?";
		      try (PreparedStatement statement = connection.prepareStatement(query)) {
		         statement.setString(1, productVO.getProduct_name());
		         statement.setInt(2, productVO.getPrice());
		         statement.setString(3,  productVO.getExplanation());
		         statement.setInt(4, productVO.getProduct_count());
		         statement.setInt(5,  productVO.getProdut_no());
		         statement.executeUpdate();  //메뉴 수정.
		      }
		   }

		   //메뉴 삭제
		   public void deleteProduct(ProductVO productVO) throws SQLException {
		      String query = "DELETE FROM MENU WHERE product_name = ?";
		      try (PreparedStatement statement = connection.prepareStatement(query)) {
		         statement.setString(1, productVO.getProduct_name());

		         statement.executeUpdate();  // 쿼리를 실행하여 회원을 삭제합니다.
		      }
		   }

		   //전체 메뉴 조회
		   public List<ProductVO> findAllProduct() throws SQLException {
		      List<ProductVO> productAll = new ArrayList<>();
		      String query = "SELECT * FROM MENU";
		      try (
		            PreparedStatement statement = connection.prepareStatement(query);
		            ResultSet resultSet = statement.executeQuery()) {

		         while (resultSet.next()) {

		            ProductVO product_name = mapResultSetToProduct(resultSet);  // 조회된 결과를 productVO 객체로 매핑.
		            productAll.add(product_name);
		         }
		      }catch (Exception e) {
		         e.printStackTrace();
		         System.out.print("사유 : " + e.getMessage());
		      }
		      return productAll;
		   }       

		   // ResultSet을 productVO 객체로 매핑하는 보조 메서드
		   private ProductVO mapResultSetToProduct(ResultSet resultSet) throws SQLException {
		      int product_no = resultSet.getInt("product_no");
		      String product_name = resultSet.getString("product_name");
		      int price = resultSet.getInt("price");
		      String explanation = resultSet.getString("explanation");
		      int product_count = resultSet.getInt("product_count");
		      return new ProductVO(product_no, product_name, price, explanation, product_count);
		   }


		   public void admin() {
		      System.out.println("\n=====  관리자 모드로 접속합니다 =====");
		      System.out.println("1. 회원 관리");
		      System.out.println("2. 카페 메뉴 관리");
		      System.out.println("3. 종료");
		      System.out.println(">> ");
		   }

		   public void member() {
		      System.out.println("\n=====  회원 관리 =====");
		      System.out.println("1. 회원 수정"); 
		      System.out.println("2. 회원 삭제");              
		      System.out.println("3. 전체 회원 조회");              
		      System.out.println("4. 프로그램 종료");       
		   }

		   public void cafeProduct() {
		      System.out.println("\n===== 카페 메뉴 관리 =====");
		      System.out.println("1. 메뉴 등록"); 
		      System.out.println("2. 메뉴판 전체 보기"); 
		      System.out.println("3. 메뉴판 수정"); 
		      System.out.println("4. 메뉴판 삭제"); 
		      System.out.println("5. 프로그램 종료"); 
		   }

		   public void productModify() {
		      System.out.println("\n1. 메뉴 수정");
		      System.out.println("2. 메뉴 삭제");
		      System.out.println("3. 나가기");
		      System.out.print(">> ");
		   }

		   public void adminManagement() {   

		      String adminId;
		      String adminPw;
		      System.out.println("관리자 페이지 입니다. ID와 PW를 입력하세요.");
		      System.out.println("ID >> ");
		      adminId = scan.next();

		      if ( !adminId.equals("admin") ) { 
		         System.out.println("관리자ID가 아닙니다.");
		      } else if( adminId.equals("admin") ) {
		         System.out.println("PW >> ");
		         adminPw = scan.next();
		         if( !adminPw.equals("1234") ) {
		            System.out.println("잘못된 비밀번호입니다.");
		         } else if ( adminPw.equals("1234") ) {               

		            while(true) {

		               admin();

		               switch (scan.nextInt() ) {

		               //회원관리
		               case 1:

		                  member();

		                  switch (scan.nextInt()) {
		                  case 1: //회원수정

		                     System.out.print("수정할 회원 입력 : ");
		                     mname = scan.next();
		                     if( memAdmin.containsKey(mname)==false) {
		                        System.out.println("해당 회원이 존재하지않습니다.");
		                        continue;
		                     }else {
		                        System.out.print("수정할 정보 입력 : ");
		                        minfo = scan.next();
		                        memAdmin.put(mname, minfo);
		                        //map.replace(product, price); 기존 값을 추가하는 게 아닌 수정하는 거라면 가능함.
		                        System.out.println( mname +" : "+ memAdmin.get(minfo) );
		                        System.out.println("수정되었습니다.");
		                        continue;
		                     }
		                  case 2: //회원 삭제
		                     System.out.print("삭제할 회원 입력 : ");
		                     mname = scan.next();
		                     if(memAdmin.containsKey(mname)==false) {
		                        System.out.println("해당 회원이 없습니다.");
		                     }else {
		                        memAdmin.remove(mname);
		                        System.out.println("삭제되었습니다.");
		                        continue;
		                     }

		                  case 3: //전체 회원 조회
		                     System.out.print("===== 전체 회원 조회 =====");
		                     Iterator itm = memAdmin.keySet().iterator();
		                     if(itm.hasNext()==true) {                  
		                        for(Entry<String, String> entry : cafeAdmin.entrySet() ) {
		                           System.out.println( entry.getKey() + " : " + entry.getValue() + "명");
		                        }
		                     } else {
		                        System.out.println("\n등록된 회원이 없습니다.");
		                        continue;
		                     }
		                  default:
		                     System.out.println("이전으로 돌아갑니다.");
		                     continue;
		                  }

		                  //카페 메뉴 관리
		               case 2:

		                  cafeProduct();

		                  System.out.println("관리할 메뉴를 선택하세요.");
		                  int cafe = scan.nextInt();

		                  //1. 메뉴 등록
		                  if( cafe == 1 ) {

		                     System.out.println("등록할 메뉴를 입력하세요.");
		                     String name = scan.next();
		                     if( cafeAdmin.containsKey(name) == true ) {
		                        System.out.println("이미 등록한 메뉴입니다.");
		                        continue;
		                     } 
		                     System.out.println("메뉴의 가격을 입력하세요.");
		                     price = scan.next();
		                     cafeAdmin.put(name, price);
		                     System.out.println("메뉴가 정상적으로 등록되었습니다.");
		                     System.out.println("메뉴명 : " + name + " / 가격 : " + cafeAdmin.get(name) + "원");
		                     //1. 메뉴 등록 end

		                     //2. 메뉴판 전체 보기
		                  } else if( cafe == 2 ) {
		                     //메뉴판 가 등록되지 않은 경우도 고려
		                     Iterator it = cafeAdmin.keySet().iterator();
		                     if(it.hasNext()==true) {                  
		                        for(Entry<String, String> entry : cafeAdmin.entrySet() ) {
		                           System.out.println( entry.getKey() + " : " + entry.getValue() + "원" );
		                        }
		                     } else {
		                        System.out.println("등록한 메뉴가 없습니다.");
		                        continue;
		                     }
		                  } //2. 메뉴판 보기 end

		                  //메뉴 관리 (수정 및 삭제)
		                  productModify();

		                  switch (scan.nextInt()) {
		                  case 1 : //메뉴 수정
		                     System.out.println("현재 등록된 메뉴" + cafeAdmin.keySet() );
		                     System.out.println("수정할 메뉴를 입력하세요 : ");
		                     product = scan.next();

		                     if ( cafeAdmin.containsKey(product) == false ) {
		                        System.out.println("메뉴가 존재하지 않습니다.");
		                        continue;
		                     } else {
		                        System.out.println("수정할 가격을 입력하세요 : ");
		                        price = scan.next();
		                        cafeAdmin.put(product, price);
		                        System.out.println( product + " : " + cafeAdmin.get(product) + "원");
		                        System.out.println("메뉴가 수정되었습니다.");
		                        continue;               
		                     }

		                  case 2 : //메뉴삭제
		                     System.out.println("삭제할 메뉴를 입력하세요 : ");
		                     product = scan.next();
		                     if( cafeAdmin.containsKey(product) == false ) {
		                        System.out.println("메뉴가 존재하지 않습니다.");
		                     } else {
		                        cafeAdmin.remove(product);
		                        System.out.println("메뉴가 삭제되었습니다.");
		                        continue;
		                     }

		                  default:
		                     System.out.println("이전으로 돌아갑니다.");
		                     continue;
		                  }


		                  //관리자 모드 종료 (전체 프로그램 종료)   
		               case 3:
		                  System.out.println("프로그램을 종료합니다.");
		                  System.exit(0);
		                  break;            
		               }
		            }
		         }
		      }
		   }

		}

