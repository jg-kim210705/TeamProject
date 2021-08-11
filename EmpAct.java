import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import data.Path;

public class EmpAct extends MemberCtrl {

	private Scanner sc;
	private int indexNum;
	private Calendar cal;
	private String use;
	private String stateCh;

	// EmpAct 생성자
	public EmpAct() {
		this.sc = new Scanner(System.in);
		this.indexNum = 0;
		this.cal = Calendar.getInstance();
		this.use = "";
	}

	// EmpAct 생성자(오버로딩) - 로그인과 연동을 위해
	public EmpAct(int index) {
		this.sc = new Scanner(System.in);
		this.indexNum = index;
		this.cal = Calendar.getInstance();
		this.use = "";
	}

	// 사원메뉴(번호)
	public int selectEmpActMain() {
		
		String text = "";
		if (!loadFile().get(indexNum).getState().equals("N")) {
			text = "현재 상태 : [ " + loadFile().get(indexNum).getState() + " ]\n";
		}

		System.out.println("==================================================================");
		System.out.printf("사원 로그인 || %s %s님, 로그인 환영 합니다.\n", loadFile().get(indexNum).getName(),
				loadFile().get(indexNum).getRank());
		System.out.print(text);
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴번호를 입력해주세요.\n");
		System.out.println("1. 사원 정보 수정");
		System.out.println("2. 출/퇴근 메뉴");
		System.out.println("3. 근태 신청");
		System.out.println("4. 증명서 발급");
		System.out.println("5. 로그 아웃");
		int selectNum = 0;
		do {
			try {
				selectNum = Integer.parseInt(sc.nextLine());
				if (selectNum > 0 && selectNum < 6) {
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

	// 사원메뉴(선택)
	public void enterEmpActMenu() {
		switch (selectEmpActMain()) {
		case 1:
			enterEmpModMenu();
			break;
		case 2:
			showCommuteMenu();
			break;
		case 3:
			enterState();
			break;
		case 4:
			showCertiMenu();
			break;
		case 5:
			logOut();
			break;
		default:
			break;
		}

	}

	// 사원 수정 모드(메뉴)
	public int selectEmpMod() {
		System.out.println("==================================================================");
		System.out.println("\t\t\t사원 정보 수정");
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴번호를 입력해주세요.\n");
		System.out.println("1. 핸드폰 번호 수정");
		System.out.println("2. 비밀번호 수정");
		System.out.println("0. 메인메뉴로");
		int selectnum = 0;
		do {
			try {
				selectnum = Integer.parseInt(sc.nextLine());
				if (selectnum > 0 && selectnum < 4) {
					break;
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);

		return selectnum;
	}

	// 사원 수정 모드(선택)
	public void enterEmpModMenu() {
		switch (selectEmpMod()) {
		case 1:
			modEmpPhoneNum();
			break;
		case 2:
			modEmpPassword();
			break;
		case 0:
			System.out.println();
			enterEmpActMenu();
			break;
		default:
			break;
		}

	}

	// 핸드폰번호 수정
	public void modEmpPhoneNum() {
		String phoneNum = "";
		System.out.println("==================================================================");
		System.out.println("\t\t\t핸드폰 번호 수정");
		System.out.println("==================================================================\n");
		System.out.println("변경하실 번호의 010과 -(대시)를 제외한 8자리를 입력해주세요.");
		System.out.println("메뉴로 돌아가기는 \"r\"키를 눌러주세요.\n");
		phoneNum = sc.nextLine();
		if (phoneNum.equals("r")) {
			enterEmpModMenu();
		}
		if (checkPhoneNum(phoneNum)) {
			modPhoneNum(indexNum,"010"+phoneNum);
			System.out.println("변경완료");
			System.out.println();
			enterEmpModMenu();
		} else {
			modEmpPhoneNum();
		}

	}

	// 비밀번호 수정
	public void modEmpPassword() {
		String password = "";
		System.out.println("==================================================================");
		System.out.println("\t\t\t비밀번호 수정");
		System.out.println("==================================================================\n");
		System.out.println("메뉴로 돌아가기는 \"r\"키를 눌러주세요.\n");
		System.out.println("변경하실 비밀번호를 입력해주세요.");
		System.out.println("영문 대소문자와 숫자와 특수문자 (!,@) 최소 둘 중 하나를 포함한 7~14자리로 입력해주세요.");

		password = sc.nextLine();
		if (password.equals("r")) {
			enterEmpModMenu();
		}
		if (checkPassword(password)) {
			modPassword(indexNum, password);
			System.out.println("변경완료");
			System.out.println();
			enterEmpModMenu();
		} else {
			modEmpPassword();
		}
	}

	// 비밀번호 적합성 확인
	public boolean checkPassword(String password) {
		boolean checkFinal = false;
		boolean checkerLength = false;
		boolean checkerWord = false;
		int checkSpecial = 0;

		for (int i=0; i<password.length(); i++) {
			if (password.length()>6 || password.length()<15) {
				checkerLength = true;
				break;
			}
			
			int charCode = (int)password.charAt(i);
			if (charCode>=33 && charCode<=126) {
				checkerWord = true; 
			}
			
			if(charCode==33 || charCode==64) {
				checkSpecial++;
			}
		}
		
		if(checkerLength&&checkerWord&&(checkSpecial!=0)) {
			checkFinal = true;
		}
		
		return checkFinal;
	}
	
	
	// 출퇴근 메뉴
	public void showCommuteMenu() {
		System.out.println("==================================================================");
		System.out.println("\t\t\t출/퇴근 메뉴");
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴번호를 입력해주세요.\n");
		System.out.println("1. 출/퇴근 기록");
		System.out.println("2. 출/퇴근 기록 조회");
		System.out.println("0. 메뉴로 돌아가기");
		int selectNum = 0;
		do {
			selectNum = Integer.parseInt(sc.nextLine());
			try {
				if (selectNum == 1) {
					Attendance a = new Attendance();
					try {
						a.inOutAttend(loadFile().get(indexNum),indexNum);	
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (selectNum == 2) {
					showCommuteRecord();
				} else if (selectNum == 0) {
					enterEmpActMenu();
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);
	}
	
	
	// 출퇴근 기록 확인
	public void showCommuteRecord() {
		String[] list = new String[6];
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(Path.EMPATTENDANCE);
			br = new BufferedReader(fr);

			String line="";
			for(int i = 0 ; (line = br.readLine())!=null ; i++) {
				if(line.indexOf(loadFile().get(indexNum).getName())!=-1) {
					list = line.split(",");
					if(!list[5].equals("출근")) {
						list[5] = "퇴근";
					}
					System.out.printf("[%s 시간] : %s %s\n",list[5],list[2],list[3]);
				}

			}
			
		} catch (Exception e) {
			
		}finally {
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println();
		showCommuteMenu();
		
	}
	

	// 증명서 출력 메뉴
	public void showCertiMenu() {
		System.out.println("==================================================================");
		System.out.println("\t\t\t증명서 발급");
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴번호를 입력해주세요.\n");
		System.out.println("1. 급여 명세서");
		System.out.println("2. 재직 증명서");
		System.out.println("0. 메뉴로 돌아가기");
		int selectNum = 0;
		do {
			selectNum = Integer.parseInt(sc.nextLine());
			try {
				if (selectNum == 1) {
					readSalary();
				} else if (selectNum == 2) {
					readCareer();
				} else if (selectNum == 0) {
					enterEmpActMenu();
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);
	}

	// 이번달 급여 조회
	public void readSalary() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");

		System.out.println();
		System.out.println("---------------------------");
		System.out.println("---------------------------");
		System.out.println("사원이름\t : " + loadFile().get(indexNum).getName() + " 님");
		System.out.println("현재직급\t : " + loadFile().get(indexNum).getRank());
		System.out.println("연차\t\t : " + loadFile().get(indexNum).getyear() + " 년차");
		System.out.println(
				"이번달\t 급여 : " + decimalFormat.format((int) (loadFile().get(indexNum).getSalaly() / 12)) + " 원");
		System.out.println("---------------------------");
		System.out.println("---------------------------\n");
		System.out.println("급여 명세서를 출력하고자 한다면 1번을 눌러주세요. [돌아가기는 2번]");
		int selectNum = 0;
		do {
			selectNum = Integer.parseInt(sc.nextLine());
			try {
				if (selectNum == 1) {
					printSal();
				} else if (selectNum == 2) {
					enterEmpActMenu();
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);
	}

	// 급여명세서 출력
	public void printSal() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");

		checkFolder();

		PrintWriter pw = null;

		try {

			pw = new PrintWriter("C:\\Samjo\\Salary.txt");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("     yyyy년 MM월 || 급 여 명 세 서");
			pw.println("===========================");
			pw.println(simpleDateFormat.format(cal.getTime()));
			pw.println("----------------------------------------");
			pw.println("----------------------------------------");
			pw.println("       사원이름\t : " + loadFile().get(indexNum).getName() + " 님");
			pw.println("       현재직급\t : " + loadFile().get(indexNum).getRank());
			pw.println("       연차\t : " + loadFile().get(indexNum).getyear() + " 년차");
			pw.println("       이번달급여\t : " + "\\ "
					+ decimalFormat.format((int) (loadFile().get(indexNum).getSalaly() / 12)));
			pw.println("----------------------------------------");
			pw.println("----------------------------------------");
			pw.println("귀하의 노고에 대단히 감사드립니다.");
			pw.println("               (주) 보람삼조");
			pw.println("===========================\n");
			pw.println();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				pw.close();
				System.out.println("출력되었습니다 !");
				System.out.println("C:\\Samjo\\Salary.txt 파일을 확인해주세요.");
				System.out.println();
				showCertiMenu();
			} catch (Exception e2) {
			}

		}

	}

	// 재직 증명 조회
	public void readCareer() {
		
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("사원이름\t : " + loadFile().get(indexNum).getName() + " 님");
		System.out.println("주민등록번호\t : " + loadFile().get(indexNum).getIdNum());
		System.out.println("직급\t\t : " + loadFile().get(indexNum).getRank());
		System.out.println("회사명\t\t : (주)보람삼조 || 사업자번호 : 123456789");
		System.out.println("재직기간\t : " + loadFile().get(indexNum).getyear() + " 년");
		System.out.println("-------------------------------------------------");
		System.out.println("-------------------------------------------------\n");
		writeCareerUse();
		System.out.println("재직 증명서를 출력하고자 한다면 1번을 눌러주세요. [돌아가기는 2번]");

		int selectNum = 0;
		do {
			try {
				selectNum = Integer.parseInt(sc.nextLine());
				if (selectNum == 1) {
					printCar();
				} else if (selectNum == 2) {
					enterEmpActMenu();
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);
	}

	// 재직증명서 용도
	public void writeCareerUse() {

		int selectNum = 0;
		do {
			try {
				System.out.println("출력하시는 용도를 적어주세요.");
				use = sc.nextLine();
				System.out.printf("[%s] 용도가 맞으면 1번 아니면 2번을 눌러주세요.\n", use);
				selectNum = Integer.parseInt(sc.nextLine());
				if (selectNum == 1) {
					break;
				} else if (selectNum == 2) {
					continue;
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);

	}

	// 재직증명서 출력
	public void printCar() {

		checkFolder();

		PrintWriter pw = null;

		try {

			pw = new PrintWriter("C:\\Samjo\\Career.txt");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
			pw.println("========================================");
			pw.println("                                 재 직 증 명 서");
			pw.println("----------------------------------------------------------------");
			pw.println("----------------------------------------------------------------");
			pw.println("사원이름\t\t : " + loadFile().get(indexNum).getName() + " 님");
			pw.println("주민등록번호\t : " + loadFile().get(indexNum).getIdNum());
			pw.println("직급\t\t : " + loadFile().get(indexNum).getRank());
			pw.println("회사명\t\t : (주)보람삼조  ||  사업자번호 : 123456789");
			pw.println("재직기간\t\t : " + loadFile().get(indexNum).getyear() + " 년");
			pw.println("주소지\t\t : 서울특별시 강남구");
			pw.println("----------------------------------------------------------------");
			pw.println("용도 : " + use);
			pw.println("----------------------------------------------------------------");
			pw.println("----------------------------------------------------------------");
			pw.println();
			pw.println("                   상기와 같이 재직 중임을 증명함.");
			pw.println();
			pw.println("                          " + simpleDateFormat.format(cal.getTime()));
			pw.println();
			pw.println("                          (주) 보 람 상 조");
			pw.println();
			pw.println("========================================");
			pw.println();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				pw.close();
				System.out.println("출력되었습니다 !");
				System.out.println("C:\\Samjo\\Career.txt 파일을 확인해주세요.");
				System.out.println();
				use = "";
				showCertiMenu();
			} catch (Exception e2) {
			}

		}

	}

	// 폴더 생성
	public void checkFolder() {

		File folder = new File("C:\\Samjo");

		if (!folder.exists()) {
			try {
				folder.mkdir();
				System.out.println("C:\\에 Samjo 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 생성되어 있어 출력을 진행합니다.");
		}
	}

	// 근태 메뉴 진입
	public void enterState() {
		String state = loadFile().get(indexNum).getState();
		if (state.indexOf("신청") != -1) {
			cancelState();
		} else if (state.indexOf("휴가") != -1 || state.indexOf("출장") != -1) {
			returnState();
		} else {
			enterStateMenu();
		}
	}

	// 근태 신청 (메뉴)
	public int selectStateMenu() {
		System.out.println("==================================================================");
		System.out.println("\t\t\t근태 신청");
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴번호를 입력해주세요.\n");
		System.out.println("1. 휴가 신청");
		System.out.println("2. 결근 신청");
		System.out.println("3. 출장 신청");
		System.out.println("0. 메인메뉴로");
		int selectNum = 0;
		do {
			try {
				selectNum = Integer.parseInt(sc.nextLine());
				if (selectNum > 0 && selectNum < 5) {
					break;
				} else {
					throw new Exception("");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				System.out.println();
			}
		} while (true);

		return selectNum;
	}

	// 근태 신청 (선택)
	public void enterStateMenu() {
		switch (selectStateMenu()) {
		case 1:
			stateCh = "휴가 [신청]";
			break;
		case 2:
			stateCh = "결근 [신청]";
			break;
		case 3:
			stateCh = "출장 [신청]";
			break;
		case 0:
			System.out.println();
			enterEmpActMenu(); // 메인메뉴로
			break;
		default:
			break;
		}
		changeState();

	}

	// 근태 변경 신청
	public void changeState() {
		modState(indexNum, stateCh);
		System.out.printf("%s 승인을 기다려주세요.\n", stateCh);
		System.out.println();
		enterEmpActMenu();
	}

	// 근태 취소
		public void cancelState() {
			String state = loadFile().get(indexNum).getState();
			int selectNum = 0;
			do {
				System.out.printf("현재 %s 상태입니다. 취소하시겠습니까?\n", state);
				System.out.println("1. 취소  || 2. 나가기");
				try {
					selectNum = Integer.parseInt(sc.nextLine());
					if (selectNum == 1) {
						modState(indexNum, "N");
						System.out.println("취소 완료 하였습니다.");
						System.out.println();
						enterEmpActMenu();
					} else if (selectNum == 2) {
						enterEmpActMenu();
						break;
					} else {
						throw new Exception("");
					}

				} catch (Exception e) {
					System.out.println("다시 입력해주세요.");
					System.out.println();
				}

			} while (true);

		}

	// 근태 복귀
	public void returnState() {
		String state = loadFile().get(indexNum).getState();
		int selectNum = 0;
		do {
			System.out.printf("현재 %s 상태입니다. 복귀 신고하시겠습니까?\n", state);
			System.out.println("1. 복귀 신고 || 2. 나가기");
			try {
				selectNum = Integer.parseInt(sc.nextLine());
				if (selectNum == 1) {
					modState(indexNum, state + "[복귀 신고]");
					System.out.println("복귀 신고 완료 하였습니다.");
					System.out.println();
					enterEmpActMenu();
				} else if (selectNum == 2) {
					enterEmpActMenu();
					break;
				} else {
					throw new Exception("");
				}

			} catch (Exception e) {
				System.out.println("다시 입력해주세요.");
				System.out.println();
			}

		} while (true);
	}

	// 로그아웃
	public void logOut() {
		System.out.println();
		Login login = new Login();
		login.enterLoginMenu();
	}

}
