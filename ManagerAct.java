import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import data.Path;

public class ManagerAct extends MemberCtrl {
	Scanner sc = new Scanner(System.in);

	public void run() {

		boolean run = false;
		String input = "";

		while (!run) {
			File file = new File(Path.EMPLIST);
			System.out.println("==================================================================");
			System.out.println("\t\t\t관리자 메뉴");
			System.out.println("==================================================================\n");
			System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
			System.out.println("1. 사원 등록");
			System.out.println("2. 사원 정보 수정");
			System.out.println("3. 사원 검색");
			System.out.println("4. 근태 신청 목록");
			System.out.println("5. 승진/징계 대상 열람");
			System.out.println("6. 직원 출퇴근 기록 열람");
			System.out.println("0. 로그아웃");
			Login lg = new Login();
			input = sc.nextLine();

			switch (input) {

			case "1":
				addMenuForManager();
				break;
			case "2":
				if (!file.exists()) {
					System.out.println("직원이 존재하지 않습니다.");
					break;
				}
				modMenuForManager();
				break;

			case "3":
				if (!file.exists()) {
					System.out.println("직원이 존재하지 않습니다.");
					break;
				}
				searchMenuForManager();
				break;

			case "4":
				System.out.println("==================================================================");
				System.out.println("\t\t\t근태 신청 목록 조회");
				System.out.println("==================================================================\n");
				System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
				System.out.println("1. 전체출력");
				System.out.println("2. 휴가 신청 및 복귀신고 목록");
				System.out.println("3. 출장 신청 및 복귀신고 목록");
				System.out.println("4. 결근 신청 및 복귀신고 목록");
				System.out.println("0. 메뉴로 돌아가기");
				String inputSecond;
				inputSecond = sc.nextLine();
				checkRequest(inputSecond);

				break;

			case "5":
				System.out.println("==================================================================");
				System.out.println("\t\t\t승진/징계 대상 목록 조회");
				System.out.println("==================================================================\n");
				System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
				System.out.println("1. 승진 목록 조회");
				System.out.println("2. 징계 목록 조회");
				System.out.println("0. 메뉴로 돌아가기");
				String inputThird = "";
				inputThird = sc.nextLine();

				switch (inputThird) {
				case "1":
					searchPromote();
					signRankUp();
					break;
				case "2":
					searchPunish();
					signRankDown();
					break;
				case "0":
					run();
					break;
				default:
					System.out.println("잘못 누르셨습니다.");
				}

				break;
			case "6":
				System.out.println("==================================================================");
				System.out.println("\t\t\t직원 출퇴근 기록 조회");
				System.out.println("==================================================================\n");
				System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
				System.out.println("1. 전체 출퇴근 기록");
				System.out.println("2. 키워드별 출퇴근 기록");
				System.out.println("0. 메뉴로 돌아가기");
				String inputFourth = "";
				inputFourth = sc.nextLine();

				readAttendence(inputFourth);
				break;

			case "0":
				lg.enterLoginMenu();
				break;
			default:

			}

		}

	}

	public void addMenuForManager() {
		String name, idNum, rank, phoneNum;
		int year;

		boolean run = false;
		while (!run) {
			System.out.println("==================================================================");
			System.out.println("\t\t\t사원 등록");
			System.out.println("==================================================================\n");
			System.out.println("메인메뉴로 돌아가시려면 \"r\"을 입력해주세요.");
			System.out.println();

			name = addName(); // 이름
			idNum = addIdNum(); // 주민등록번호
			rank = addRank(); // 직급
			phoneNum = addPhoneNum(); // 핸드폰번호
			year = addYear(); // 연차

			addEmp(name, idNum, rank, phoneNum, year);
			run();
		}

	} // 사원 등록

	// 이름 등록
	public String addName() {
		String name;
		System.out.print("이름 : ");
		name = sc.nextLine();
		if (name.equals("r")) {
			run();
		}
		return name;
	}

	// 주민등록번호 등록
	public String addIdNum() {
		String idNum;
		while (true) {
			System.out.println();
			System.out.println("존재할 수 없는 생년월일에 대해서는 입력이 불가능합니다.");
			System.out.println("ex) 901010-1000000");
			System.out.print("주민등록번호 : ");
			idNum = sc.nextLine();
			if (idNum.equals("r")) {
				run();
			}
			if (checkIdNum(idNum)) {
				break;
			}
		}
		return idNum;
	}

	// 직급 등록
	public String addRank() {
		String rank;
		while (true) {
			System.out.println();
			System.out.println("사원에 해당하는 직급을 입력해주세요.");
			System.out.println("사장 | 이사 | 부장 | 차장 | 과장 | 대리 | 주임 | 사원");
			System.out.print("직급 : ");
			rank = sc.nextLine();
			if (rank.equals("r")) {
				run();
			}
			if (rank.equals("사장") || rank.equals("이사") || rank.equals("부장") || rank.equals("차장") || rank.equals("과장")
					|| rank.equals("대리") || rank.equals("주임") || rank.equals("사원")) {
				break;
			} else {
				System.out.println("존재하는 직급이 아닙니다.");
			}
		}
		return rank;
	}

	// 핸드폰 등록
	public String addPhoneNum() {
		String phoneNum;
		while (true) {
			System.out.println();
			System.out.println("휴대폰번호는 010 이후의 숫자만을 입력주세요.");
			System.out.println("[앞자리 0과 1은 사용할 수 없는 번호입니다.]");
			System.out.println("ex) 32643264");
			System.out.print("휴대전화번호 : 010");
			phoneNum = sc.nextLine();
			if (phoneNum.equals("r")) {
				run();
			}
			if (checkPhoneNum(phoneNum)) {
				break;
			}
		}
		return "010" + phoneNum;
	}

	// 연차 등록
	public int addYear() {
		String subyear;
		int year;
		while (true) {
			try {
				System.out.println();
				System.out.println("연차는 숫자만 입력해주세요.");
				System.out.print("연차 : ");
				subyear = sc.nextLine();
				if (subyear.equals("r")) {
					run();
				}
				year = Integer.parseInt(subyear);
				break;
			} catch (Exception e) {
				System.out.println("숫자로 입력해주세요.");
			}
		}
		return year;
	}

	public void modAdminIdPw() {
		System.out.println("==================================================================");
		System.out.println("\t\t\t관리자 ID/PW 변경");
		System.out.println("==================================================================\n");
		System.out.println("새로운 관리자 아이디를 입력해주세요");
		String id = "";
		id = sc.nextLine();
		System.out.println("새로운 관리자 비밀번호를 입력해주세요");
		String password = "";
		password = sc.nextLine();
		modAdmin(id, password);
	}

	public void modMenuForManager() {

		String switchInput = "";
		String empNum = "";
		String modInput = "";
		int index = 0;
		System.out.println("==================================================================");
		System.out.println("\t\t\t사원 정보 수정");
		System.out.println("==================================================================\n");
		System.out.print("수정하실 직원의 사번을 입력하세요 : ");
		empNum = sc.nextLine();
		index = searchByEmpnoMod(empNum);

		if (index != -1) {
			System.out.println("==================================================================");
			System.out.println("\t" + loadFile().get(index).getName() + " 님의 정보를 수정합니다.");
			System.out.println("==================================================================\n");
			System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
			System.out.println("1. 연차 수정");
			System.out.println("2. 직급 수정");
			System.out.println("3. 이름 수정");
			System.out.println("4. 역량점수 수정");
			System.out.println("5. 휴대전화번호 수정");
			System.out.println("6. 사원상태 변경");
			System.out.println("0. 메뉴로 돌아가기");
			System.out.println();

			switchInput = sc.nextLine();
			switch (switchInput) {

			case "1":
				System.out.println("사원의 연차를 입력해주세요");
				modInput = addYear() + "";
				modYear(index, modInput);
				break;
			case "2":
				System.out.println("사원의 직급을 입력해주세요");
				modInput = addRank();
				modRank(index, modInput);
				break;
			case "3":
				System.out.println("사원의 이름을 입력해주세요");
				modInput = addName();
				modEmpname(index, modInput);
				break;
			case "4":
				System.out.println("사원의 역량점수를 입력해주세요");
				modInput = sc.nextLine();
				modCapScore(index, modInput);
				break;

			case "5":
				System.out.println("사원의 휴대전화번호를 입력해주세요");
				modInput = addPhoneNum();
				modPhoneNum(index, modInput);

			case "6":
				System.out.println("사원의 근태를 입력해주세요");
				modInput = sc.nextLine();
				modState(index, modInput);

			case "0":
				System.out.println("관리자 메뉴로 돌아갑니다.");
			default:
			}
		} else {
			System.out.println("존재하지 않는 사번입니다.");
		}

	}

	public void searchMenuForManager() {

		String switchInput = "";
		String inputParam = "";
		System.out.println("==================================================================");
		System.out.println("\t\t\t사원 정보 수정");
		System.out.println("==================================================================\n");
		System.out.println("원하시는 메뉴의 번호를 입력해주세요.");
		System.out.println("1. 전체목록조회");
		System.out.println("2. 사번으로 조회");
		System.out.println("3. 이름으로 조회");
		System.out.println("4. 직급으로 조회");
		System.out.println("5. 근무상태로 조회");
		System.out.println("0. 메뉴로 돌아가기");
		System.out.println();

		switchInput = sc.nextLine();
		switch (switchInput) {

		case "1":
			listAll();
			break;
		case "2":
			inputParam = sc.nextLine();
			searchByEmpno(inputParam);
			break;
		case "3":
			inputParam = sc.nextLine();
			searchByName(inputParam);
			break;
		case "4":
			inputParam = sc.nextLine();
			searchByRank(inputParam);
			break;

		case "5":
			inputParam = sc.nextLine();
			searchByState(inputParam);
			break;
		case "0":
			System.out.println("관리자 메뉴로 돌아갑니다.");

		default:
		}
	}

	public void readAttendence(String in) {

		File file = new File(Path.EMPATTENDANCE);

		if (!file.exists()) {
			System.out.println("출근 기록이 없습니다.");
		}
		switch (in) {

		case "1":
			readAttendSelect();

			break;

		case "2":
			System.out.println("키워드를 입력해주세요");
			System.out.printf("사번 검색\t : SSS0000\n");
			System.out.printf("날짜 검색\t : 2021 or 08 or 2021-08-10");
			System.out.printf("상태 예시\t : 출근\n");
			System.out.println();
			String input = "";
			input = sc.nextLine();
			readAttendSelect(input);
			break;

		case "0":
			run();
			break;

		default:
		}

	}

	public void readAttendSelect() {

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(Path.EMPATTENDANCE);
			br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void readAttendSelect(String input) {

		FileReader fr = null;
		BufferedReader br = null;

		try {

			fr = new FileReader(Path.EMPATTENDANCE);
			br = new BufferedReader(fr);

			String attend = "";
			String line = "";
			while ((line = br.readLine()) != null) {

				if (line.contains(input)) {
					attend += line + "\n";
				}

			}

			System.out.println(attend);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkRequest(String input) {

		switch (input) {
		case "1": // 1을 입력받으면 휴가 , 출장, 결근을 출력
			searchByState("휴가 [신청]");
			searchByState("휴가[복귀 신고]");
			searchByState("출장 [신청]");
			searchByState("출장[복귀 신고]");
			searchByState("결근 [신청]");
			searchByState("결근[복귀 신고]");
			System.out.println("승인은 개별 메뉴에서 승인해주세요.");

		case "2":
			searchByState("휴가 [신청]"); // 2번을 입력하면 휴가 신청 및 복귀 신청 사원목록 출력
			searchByState("휴가[복귀 신고]");
			signState("휴가");
			break;

		case "3":
			searchByState("출장 [신청]"); // 3번을 입력하면 출장 신청 및 복귀 신청 사원목록 출력
			searchByState("출장[복귀 신고]");
			signState("출장");
			break;

		case "4":
			searchByState("결근 [신청]"); // 4번을 입력하면 결근 신청 및 복귀 신청 사원목록 출력
			searchByState("결근[복귀 신고]");
			signState("결근");
			break;

		case "0":
			run();
			break;
		default: // 자동 브레이크

			// 대략 2줄정도의 메소드를 덜 불러올 수 있었다.
		}

	}

	// 근태 즉시 변경 함수
	public void signState(String state) {
		String signNo;
		System.out.println("메뉴로 돌아가고자하시면 \"r\"을 눌러주세요");
		System.out.println("승인하고자 하는 사원의 사원번호를 입력해주세요.");

		do {
			signNo = sc.nextLine();
			if (signNo.equals("r")) {
				run();
			}

			for (int i = 0; i < loadFile().size(); i++) {
				if (loadFile().get(i).getEmpNo().equals(signNo)) {
					if (loadFile().get(i).getState().contains(state)) {
						if (loadFile().get(i).getState().contains("신청")) {
							modInState(i, state);
							System.out.println("승인 완료");
						} else if (loadFile().get(i).getState().contains("복귀")) {
							modInState(i, "N");
							System.out.println("승인 완료");
						}
					}
				} else {
					System.out.println("해당되는 사원의 번호가 없습니다.");
					run();
				}
			}
		} while (true);

	}

	// 승진 함수
	public void signRankUp() {
		String signNo;

		System.out.println("메뉴로 돌아가고자하시면 \"r\"을 눌러주세요");
		System.out.println("승진하고자 하는 사원의 사원번호를 입력해주세요.");
		
		do {
			signNo = sc.nextLine();
			if (signNo.equals("r")) {
				run();
			}
			
			
			for (int i = 0; i < loadFile().size(); i++) {
				if (loadFile().get(i).getEmpNo().equals(signNo)) {
					if (loadFile().get(i).getCapScore()>=80) {
						if (loadFile().get(i).getRank().equals("사원")) {
							modRank(i, "주임");
							modCapScore(i, "50");
							System.out.println("사원->주임 승진완료");
							run();
						} else if (loadFile().get(i).getRank().equals("주임")) {
							modRank(i, "대리");
							modCapScore(i, "50");
							System.out.println("주임->대리 승진완료");
							run();
						} else if (loadFile().get(i).getRank().equals("대리")) {
							modRank(i, "과장");
							modCapScore(i, "50");
							System.out.println("대리->과장 승진완료");
							run();
						} else if (loadFile().get(i).getRank().equals("과장")) {
							modRank(i, "차장");
							modCapScore(i, "50");
							System.out.println("과장->차장 승진완료");
							run();
						} else if (loadFile().get(i).getRank().equals("차장")) {
							modRank(i, "부장");
							modCapScore(i, "50");
							System.out.println("차장->부장 승진완료");
							run();
						} else if (loadFile().get(i).getRank().equals("부장")) {
							modRank(i, "이사");
							modCapScore(i, "50");
							System.out.println("부장->이사 승진완료");
							run();
						} else if (loadFile().get(i).getRank().equals("이사")) {
							System.out.println("bb");
							modRank(i, "사장");
							modCapScore(i, "50");
							System.out.println("이사->사장 승진완료");
							run();
						}
					}
				}
			}
			
		} while (true);

	}
	
	// 직급 하락 함수
		public void signRankDown() {
			String signNo;

			System.out.println("메뉴로 돌아가고자하시면 \"r\"을 눌러주세요");
			System.out.println("직급 변경하고자 하는 사원의 사원번호를 입력해주세요.");
			
			do {
				signNo = sc.nextLine();
				if (signNo.equals("r")) {
					run();
				}
				
				
				for (int i = 0; i < loadFile().size(); i++) {
					if (loadFile().get(i).getEmpNo().equals(signNo)) {
						if (loadFile().get(i).getCapScore()<=20) {
							if (loadFile().get(i).getRank().equals("주임")) {
								modRank(i, "사원");
								modCapScore(i, "50");
								System.out.println("주임->사원 변경완료");
								run();
							} else if (loadFile().get(i).getRank().equals("대리")) {
								modRank(i, "주임");
								modCapScore(i, "50");
								System.out.println("대리->주임 변경완료");
								run();
							} else if (loadFile().get(i).getRank().equals("과장")) {
								modRank(i, "대리");
								modCapScore(i, "50");
								System.out.println("과장->대리 변경완료");
								run();
							} else if (loadFile().get(i).getRank().equals("차장")) {
								modRank(i, "과장");
								modCapScore(i, "50");
								System.out.println("차장->과장 변경완료");
								run();
							} else if (loadFile().get(i).getRank().equals("부장")) {
								modRank(i, "차장");
								modCapScore(i, "50");
								System.out.println("부장->차장 변경완료");
								run();
							} else if (loadFile().get(i).getRank().equals("이사")) {
								modRank(i, "부장");
								modCapScore(i, "50");
								System.out.println("이사->부장 변경완료");
								run();
							} else if (loadFile().get(i).getRank().equals("사장")) {
								System.out.println("bb");
								modRank(i, "이사");
								modCapScore(i, "50");
								System.out.println("사장->이사 변경완료");
								run();
							}
						}
					}
				}
				
			} while (true);

		}
	

}
