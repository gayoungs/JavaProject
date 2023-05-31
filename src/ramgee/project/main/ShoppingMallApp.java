package ramgee.project.main;

import ramgee.project.dao.*;
import ramgee.project.vo.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingMallApp {

    private static Scanner scanner = new Scanner(System.in);
    private static MemberDAO memberDAO = new MemberDAO();
    private static CartDAO cartDAO = new CartDAO();
    private static OrderDAO orderDAO = new OrderDAO();
    private static PayDAO payDAO = new PayDAO();
    private static ProductDAO productDAO = new ProductDAO();
    private static AuthorityDAO authorityDAO = new AuthorityDAO();

    private static boolean loggedIn = false;
    private static boolean isAdmin = false;

    private static LoggedInUserVO loggedInUser = null;

    private static class LoggedInUserVO {
        private MemberVO member;
        private boolean isAdmin;

        public LoggedInUserVO(MemberVO member, boolean isAdmin) {
            this.member = member;
            this.isAdmin = isAdmin;
        }

        public MemberVO getMember() {
            return member;
        }

        public boolean isAdmin() {
            return isAdmin;
        }
    }


    private static void welcome() {
        System.out.println("================ 물건 샵에 오신 것을 환영합니다 ================");
    }

    private static int showAdminMenu() {
        System.out.println("원하는 옵션을 선택하세요:");
        System.out.println("1. 상품 관리");
        System.out.println("2. 회원 관리");
        System.out.println("3. 모든 카트 보기");
        System.out.println("4. 모든 주문 보기");
        System.out.println("5. 모든 결제 보기");
        System.out.println("6. 모든 권한 보기");
        System.out.println("7. 로그아웃");
        System.out.println("8. 종료");

        System.out.print("선택하세요: ");
        return scanner.nextInt();
    }

    private static int showUserMenu() {
        System.out.println("원하는 옵션을 선택하세요:");
        System.out.println("1. 쇼핑하기");
        System.out.println("2. 회원 정보 보기");
        System.out.println("3. 카트 보기");
        System.out.println("4. 로그아웃");
        System.out.println("5. 종료");

        System.out.print("선택하세요: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        return choice;
    }


    private static void shop() {
        System.out.println("모든 상품:");

        List<ProductVO> products = productDAO.findAllProducts();
        for (ProductVO product : products) {
            System.out.println(product);
        }

        System.out.print("상품 번호를 입력하세요: ");
        int productNo = scanner.nextInt();
        scanner.nextLine();

        ProductVO selectedProduct = productDAO.findProductByProductNo(productNo);
        if (selectedProduct == null) {
            System.out.println("유효하지 않은 상품 번호입니다.");
            return;
        }

        System.out.print("구매할 개수를 입력하세요: ");
        int quantity = scanner.nextInt();

        int memberNo = loggedInUser.getMember().getMember_no();

        List<ProductVO> orderedProducts = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            orderedProducts.add(selectedProduct);
        }
        CartVO cart = cartDAO.findCartByMemberNo(memberNo);
        if (cart == null) {
            cart = new CartVO(0, memberNo, new ArrayList<>());
            cartDAO.addCart(cart);
        }
        cart = cartDAO.findCartByMemberNo(memberNo);
        OrderVO order = new OrderVO(0, cart.getCart_no(), new java.sql.Date(System.currentTimeMillis()), selectedProduct.getPrice() * quantity, "주문완료", orderedProducts);

        cart.getOrders().add(order);
        cartDAO.updateCart(cart);

        System.out.println("상품이 카트에 추가되었습니다.");
    }




    private static int showLoginMenu() {
        System.out.println("원하는 옵션을 선택하세요:");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");

        System.out.print("선택하세요: ");
        return scanner.nextInt();
    }


    private static void login() {
        System.out.print("아이디: ");
        String username = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();

        MemberVO member = memberDAO.findMemberByUsernameAndPassword(username, password);
        if (member != null) {
            if (member.getAuthorityVO().getAuthorityName().equalsIgnoreCase("ADMIN")) {
                loggedInUser = new LoggedInUserVO(member, true);
                loggedIn = true;
                isAdmin = true;
                System.out.println("관리자로 로그인되었습니다.");
            } else {
                loggedInUser = new LoggedInUserVO(member, false);
                loggedIn = true;
                System.out.println("로그인되었습니다.");
            }
        } else {
            System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    private static void logout() {
        loggedIn = false;
        isAdmin = false;
        loggedInUser = null;
        System.out.println("로그아웃되었습니다.");
    }

    private static void displayAllProducts() {
        List<ProductVO> products = productDAO.findAllProducts();
        System.out.println("모든 물건:");
        for (ProductVO product : products) {
            System.out.println(product);
        }
        System.out.println();
    }

    private static void displayMemberInfo() {
        if (loggedIn) {
            MemberVO member = loggedInUser.getMember();
            System.out.println("현재 로그인된 회원 정보:");
            System.out.println(member);

            int choice = showMemberOptionsMenu();
            switch (choice) {
                case 1:
                    updateMemberInfo(member);
                    break;
                case 2:
                    deleteMember(member);
                    break;
                default:
                    System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                    break;
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private static void updateMemberInfo(MemberVO member) {
        System.out.println(member.getUsername()+ "회원님의 회원 정보를 수정합니다.");

        System.out.print("새로운 비밀번호: ");
        String password = scanner.next();
        member.setPassword(password);

        System.out.print("새로운 이름: ");
        String name = scanner.next();
        member.setName(name);

        System.out.print("새로운 이메일: ");
        String email = scanner.next();
        member.setEmail(email);

        System.out.print("새로운 전화번호: ");
        String phoneNumber = scanner.next();
        member.setPhoneNumber(phoneNumber);

        memberDAO.updateMember(member);

        System.out.println("회원 정보가 업데이트되었습니다.");
        System.out.println("업데이트된 회원 정보:");
        System.out.println(member);
    }

    private static void deleteMember(MemberVO member) {
        System.out.println("회원 탈퇴를 진행합니다.");
        System.out.println("정말로 탈퇴하시겠습니까? (Y/N)");

        String answer = scanner.next();
        if (answer.equalsIgnoreCase("Y")) {
            int member_no = member.getMember_no();
            memberDAO.deleteMember(member_no);

            logout();

            System.out.println("회원 탈퇴가 완료되었습니다.");
        } else {
            System.out.println("회원 탈퇴를 취소하였습니다.");
        }
    }


    private static int showMemberOptionsMenu() {
        System.out.println("원하는 옵션을 선택하세요:");
        System.out.println("1. 회원 정보 수정");
        System.out.println("2. 회원 탈퇴");
        System.out.print("선택하세요: ");
        return scanner.nextInt();
    }


    private static void displayCart() {
        if (loggedIn) {
            int member_no = loggedInUser.getMember().getMember_no();
            CartVO cart = cartDAO.findCartByMemberNo(member_no);
            if (cart != null) {
                System.out.println("현재 로그인된 회원의 카트 정보:");
                System.out.println(cart);
            } else {
                System.out.println("카트 정보가 없습니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }



    private static void displayAllMembers() {
        List<MemberVO> members = memberDAO.findAllMembers();
        System.out.println("모든 회원:");
        for (MemberVO member : members) {
            System.out.println(member);
        }
        System.out.println();
    }

    private static void displayAllCarts() {
        List<CartVO> carts = cartDAO.findAllCarts();
        System.out.println("모든 카트:");
        for (CartVO cart : carts) {
            System.out.println(cart);
        }
        System.out.println();
    }

    private static void displayAllOrders() {
        List<OrderVO> orders = orderDAO.findAllOrders();
        System.out.println("모든 주문:");
        for (OrderVO order : orders) {
            System.out.println(order);
        }
        System.out.println();
    }

    private static void displayAllPayments() {
        List<PayVO> payments = payDAO.findAllPays();
        System.out.println("모든 결제:");
        for (PayVO payment : payments) {
            System.out.println(payment);
        }
        System.out.println();
    }

    private static void displayAllAuthorities() {
        List<AuthorityVO> authorities = authorityDAO.findAllAuthorities();
        System.out.println("모든 권한:");
        for (AuthorityVO authority : authorities) {
            System.out.println(authority);
        }
        System.out.println();
    }

    private static void register() {
        System.out.print("아이디: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("이메일: ");
        String email = scanner.nextLine();
        System.out.print("전화번호: ");
        String phoneNumber = scanner.nextLine();

        int authorityNo;
        String authorityName;
        boolean isAdmin = false;

        // 회원의 권한을 설정합니다.
        System.out.print("권한(1: 일반 회원, 2: 관리자): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리

        if (choice == 2) {
            authorityNo = 1;
            authorityName = "ADMIN";
            isAdmin = true;
        } else {
            authorityNo = 2;
            authorityName = "USER";
        }

        AuthorityVO authority = new AuthorityVO(authorityNo, authorityName);
        MemberVO member = new MemberVO(0, username, password, name, email, phoneNumber, authority);
        memberDAO.addMember(member);

        System.out.println("회원가입이 완료되었습니다.");

    }

    //상품관리(관리자)
    private static void showProductManagementMenu() {
        boolean exit = false;
        while (!exit) {
            int choice = getProductManagementOption();

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                    break;
            }
        }
    }

    private static int getProductManagementOption() {
        System.out.println("상품 관리 메뉴:");
        System.out.println("1. 모든 상품 보기");
        System.out.println("2. 상품 추가");
        System.out.println("3. 상품 수정");
        System.out.println("4. 상품 삭제");
        System.out.println("5. 이전 메뉴로 돌아가기");

        System.out.print("선택하세요: ");
        return scanner.nextInt();
    }

    private static void addProduct() {
        System.out.print("상품명: ");
        String productName = scanner.next();
        System.out.print("가격: ");
        double price = scanner.nextDouble();
        System.out.print("상품 설명: ");
        String description = scanner.next();
        System.out.print("수량: ");
        int amount = scanner.nextInt();

        ProductVO product = new ProductVO(0, productName, price, description, amount);
        productDAO.addProduct(product);

        System.out.println("상품이 추가되었습니다.");
    }

    private static void updateProduct() {
        System.out.print("수정할 상품의 ID: ");
        int productId = scanner.nextInt();

        ProductVO existingProduct = productDAO.findProductByProductNo(productId);
        if (existingProduct == null) {
            System.out.println("해당 ID의 상품이 존재하지 않습니다.");
            return;
        }

        System.out.print("새로운 상품명: ");
        String newProductName = scanner.next();
        System.out.print("새로운 가격: ");
        double newPrice = scanner.nextDouble();
        System.out.print("새로운 상품 설명: ");
        String newDescription = scanner.next();
        System.out.print("새로운 수량: ");
        int newAmount = scanner.nextInt();

        existingProduct.setName(newProductName);
        existingProduct.setPrice(newPrice);
        existingProduct.setDescription(newDescription);
        existingProduct.setAmount(newAmount);

        productDAO.updateProduct(existingProduct);

        System.out.println("상품이 수정되었습니다.");
    }

    private static void deleteProduct() {
        System.out.print("삭제할 상품의 ID: ");
        int productId = scanner.nextInt();

        ProductVO existingProduct = productDAO.findProductByProductNo(productId);
        if (existingProduct == null) {
            System.out.println("해당 ID의 상품이 존재하지 않습니다.");
            return;
        }

        productDAO.deleteProduct(productId);

        System.out.println("상품이 삭제되었습니다.");
    }

    private static void showMemberManagementMenu() {
        boolean exit = false;
        while (!exit) {
            int choice = displayMemberManagementMenu();
            switch (choice) {
                case 1:
                    displayAllMembers();
                    break;
                case 2:
                    addMember();
                    break;
                case 3:
                    updateMember();
                    break;
                case 4:
                    deleteMember();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                    break;
            }
        }
    }

    private static int displayMemberManagementMenu() {
        System.out.println("원하는 옵션을 선택하세요:");
        System.out.println("1. 모든 회원 보기");
        System.out.println("2. 회원 추가");
        System.out.println("3. 회원 수정");
        System.out.println("4. 회원 삭제");
        System.out.println("5. 이전 메뉴로 돌아가기");

        System.out.print("선택하세요: ");
        return scanner.nextInt();
    }

    private static void addMember() {
        System.out.print("아이디: ");
        String username = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();
        System.out.print("이름: ");
        String name = scanner.next();
        System.out.print("이메일: ");
        String email = scanner.next();
        System.out.print("전화번호: ");
        String phoneNumber = scanner.next();

        int authorityNo;
        String authorityName;

        System.out.print("권한(1: 일반 회원, 2: 관리자): ");
        int choice = scanner.nextInt();

        if (choice == 2) {
            authorityNo = 1;
            authorityName = "ADMIN";
        } else {
            authorityNo = 2;
            authorityName = "USER";
        }

        AuthorityVO authority = new AuthorityVO(authorityNo, authorityName);
        MemberVO member = new MemberVO(0, username, password, name, email, phoneNumber, authority);
        memberDAO.addMember(member);

        System.out.println("회원이 추가되었습니다.");
    }

    private static void updateMember() {
        System.out.print("수정할 회원의 아이디: ");
        String username = scanner.next();

        MemberVO existingMember = memberDAO.findMemberByUsername(username);
        if (existingMember == null) {
            System.out.println("해당 아이디의 회원이 존재하지 않습니다.");
            return;
        }

        System.out.print("새로운 비밀번호: ");
        String newPassword = scanner.next();
        System.out.print("새로운 이름: ");
        String newName = scanner.next();
        System.out.print("새로운 이메일: ");
        String newEmail = scanner.next();
        System.out.print("새로운 전화번호: ");
        String newPhoneNumber = scanner.next();

        existingMember.setPassword(newPassword);
        existingMember.setName(newName);
        existingMember.setEmail(newEmail);
        existingMember.setPhoneNumber(newPhoneNumber);

        memberDAO.updateMember(existingMember);

        System.out.println("회원 정보가 수정되었습니다.");
    }

    private static void deleteMember() {
        System.out.print("삭제할 회원의 아이디: ");
        String username = scanner.next();

        MemberVO existingMember = memberDAO.findMemberByUsername(username);
        if (existingMember == null) {
            System.out.println("해당 아이디의 회원이 존재하지 않습니다.");
            return;
        }

        memberDAO.deleteMember(existingMember.getMember_no());

        System.out.println("회원이 삭제되었습니다.");
    }


    public static void main(String[] args) {
        welcome();

        boolean exit = false;
        while (!exit) {
            if (loggedIn) {
                if (isAdmin) {
                    int choice = showAdminMenu();
                    switch (choice) {
                        case 1:
                            showProductManagementMenu();
                            break;
                        case 2:
                            showMemberManagementMenu();
                            break;
                        case 3:
                            displayAllCarts();
                            break;
                        case 4:
                            displayAllOrders();
                            break;
                        case 5:
                            displayAllPayments();
                            break;
                        case 6:
                            displayAllAuthorities();
                            break;
                        case 7:
                            logout();
                            break;
                        case 8:
                            exit = true;
                            break;
                        default:
                            System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                            break;
                    }

                } else {
                    int choice = showUserMenu();
                    switch (choice) {
                        case 1:
                            shop();
                            break;
                        case 2:
                            displayMemberInfo();
                            break;
                        case 3:
                            displayCart();
                            break;
                        case 4:
                            logout();
                            break;
                        case 5:
                            exit = true;
                            break;
                        default:
                            System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                            break;
                    }
                }
            } else {
                int choice = showLoginMenu();
                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                        break;
                }
            }
        }
        System.out.println("쇼핑몰 애플리케이션을 이용해주셔서 감사합니다. 안녕히 가세요!");
    }

}
