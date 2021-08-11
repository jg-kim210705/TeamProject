import java.util.Scanner;

public class Login extends MemberCtrl {

	private Scanner sc;
	private String loginId;
	private String loginPassword;
	private int indexNum;
	private static int adminHide = 0;

	// Login 생성자
	public Login() {
		sc = new Scanner(System.in);
		loginId = "";
		loginPassword = "";
		indexNum = 0;
	}
	
	// Login 화면(메뉴)
	public int selectLoginMenu() {


		System.out.println("==================================================================");
		System.out.println("\t보람삼조 직원통합연동시스템에 오신것을 환영합니다.");
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
		System.out.println("1. 직원 로그인");
		System.out.println("2. 관리자 로그인");
		System.out.println("3. ID / PW 조회");
		System.out.println("0. 프로그램 종료");
		int selectNum = 0;
		do {
			try {
				selectNum = Integer.parseInt(sc.nextLine());
				if (selectNum >= 0 && selectNum < 4) {
					break;
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		} while (true);

		return selectNum;
	}

	// Login 화면(선택)
	public void enterLoginMenu() {
		switch (selectLoginMenu()) {
		case 1:
			System.out.println("메뉴로 돌아가기는 \"r\"키를 눌러주세요");
			checkMemberValid();
			break;
		case 2:
			enterAdminLogin();
			break;
		case 3:
			findMemberIdPw();
			break;
		case 0:
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		default:
			break;
		}

	}

	// ID 입력
	public boolean checkLoginId() {
		boolean check = false;
		System.out.printf("아이디 :");
		this.loginId = sc.nextLine();
		if (this.loginId.equals("r")) {
			enterLoginMenu();
		}
		for (int i = 0; i < loadFile().size(); i++) {
			if ((loadFile().get(i).getId()).equals(loginId)) {
				indexNum = i;
				check = true;
			}
		}
		return check;
	}

	// PW 입력
	public boolean checkLoginPassword() {
		boolean check = false;
		System.out.print("비밀번호: ");
		this.loginPassword = sc.nextLine();
		if (this.loginPassword.equals("r")) {
			enterLoginMenu();
		}
		if ((loadFile().get(indexNum).getPassword()).equals(loginPassword)) {
			check = true;
		}
		return check;
	}

	// IDPW 유효성 확인
	public void checkMemberValid() {

		while (true) {
			if (!checkLoginId()) {
				System.out.println("잘못된 아이디입니다. 다시 입력해주세요.\n");
				continue;
			} else {
				if (checkLoginPassword()) {
					EmpAct ea = new EmpAct(indexNum);
					ea.enterEmpActMenu();
				} else {
					System.out.println("잘못된 비밀번호 입니다. 다시 입력해주세요.\n");
					continue;
				}

			}
		}

	}

	
	// IDPW 찾기
	public void findMemberIdPw() {
		String spaceIdNum;
		System.out.println("==================================================================");
		System.out.println("\t\t\tID / PW 조회");
		System.out.println("==================================================================\n");
		System.out.println("귀하의 주민등록번호를 입력해주세요.");
		System.out.println("메뉴로 돌아가기는 \"r\"키를 눌러주세요");
		spaceIdNum = sc.nextLine();
		if (spaceIdNum.equals("r")) {
			enterLoginMenu();
		}

		for (int i = 0; i < loadFile().size(); i++) {
			if ((loadFile().get(i).getIdNum()).equals(spaceIdNum)) {
				System.out.println("회원님의 정보는 아래와 같습니다.");
				System.out.printf("ID : %s  ||  PW : %s\n", loadFile().get(i).getId(), loadFile().get(i).getPassword());
				System.out.println();
				enterLoginMenu();
				break;
			}
		}

		System.out.println("해당되는 정보가 없습니다. 다시 입력해주세요.");
		findMemberIdPw();

	}

	// 관리자 모드 진입
    public void enterAdminLogin() {
        String adminId;
        String adminPw;
        {
            adminId="";
            adminPw="";
        }
		System.out.println("==================================================================");
		System.out.println("\t\t\t관리자 로그인");
		System.out.println("==================================================================");
        System.out.println("관리자 모드로 시작합니다.");
        System.out.println("메뉴로 돌아가기는 \"r\"키를 눌러주세요\n");
        if(adminHide==0) {
        System.out.println("초기 아이디는 admin 비밀번호는 0000입니다.");
        System.out.println("추후 수정해주세요.\n");
        }
        while (true) {
            System.out.printf("관리자 ID :");
            adminId = sc.nextLine();
            if (adminId.equals("r")) {
            	enterLoginMenu();
            }
            System.out.printf("관리자 비밀번호 :");
            adminPw = sc.nextLine();
            if (adminPw.equals("r")) {
            	enterLoginMenu();
            }
            if (adminId.equals(loadAdminFile().get(0).getId()) && adminPw.equals(loadAdminFile().get(0).getPassword())) {
            	adminHide++;
            	ManagerAct manageract = new ManagerAct();
            	manageract.run();
                break;
            } else {
                System.out.printf("%s 다시 입력해주세요.\n\n",!adminId.equals(loadAdminFile().get(0).getId()) ? "처음부터" : !adminPw.equals(loadAdminFile().get(0).getPassword()) ? "PW를" : "");
            }
        }
    }


}
