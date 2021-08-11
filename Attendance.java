import data.Path;
import java.io.*;
import java.util.*;

public class Attendance extends MemberCtrl {
	Scanner scan = new Scanner(System.in);
	private FileReader fr = null;
	private BufferedReader br = null;
	private String line = "";
	private boolean isRenewal = false;
	private Calendar today = Calendar.getInstance();

	public void inAttend(Attendance_Record empAttendance) { // 출근시 인식 메서드
		
		Attendance_Record inAttendance_Record = new Attendance_Record();

		try {

			fr = new FileReader(Path.EMPATTENDANCE);
			br = new BufferedReader(fr);

			String[] list = new String[6]; // 근태정보담을 배열생성
			// 출력되는 순서에 맞게 순서변경 
			inAttendance_Record.setEmpno(empAttendance.getEmpno());
			inAttendance_Record.setEmpName(empAttendance.getEmpName());
			inAttendance_Record.setDate(String.format("%tF", today)); // yyyy-mm-dd 현재날짜저장
			inAttendance_Record.setTime(String.format("%tk:%tM", today, today)); // 00:00 현재시간저장
			inAttendance_Record.setWorkingTime("--:--");
			inAttendance_Record.setAttendance("출근");
	
			while ((line = br.readLine()) != null) { // 값이 있다면
				list = line.split(","); // ,로 분리, list배열에 담는다.
				if (list[0].equals(inAttendance_Record.getEmpno()) && list[2].equals(inAttendance_Record.getDate())) {
					System.out.println("당일 출근 기록이 존재합니다.\n"); //멘트변경

					isRenewal = true;
					break;
				} 
			}

		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (!isRenewal) {
				inAttendance_Record.writeAttendanceData(); // 새로 데이터 라인 생성
				System.out.printf("[ 현재시간 %tk:%tM ]\n", today, today); //추가 
				System.out.printf("%s님의 %d월 %d일 출근 기록이 완료되었습니다.\n", inAttendance_Record.getEmpName(),
						today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE)); 

				System.out.println();
				
			}


		}

	}



	public void outAttend(Attendance_Record empAttendance) { // 퇴근시 인식 메서드

		Attendance_Record outAttendance_Record = new Attendance_Record();

		Calendar clockOut = Calendar.getInstance();	//TODO today ??
		outAttendance_Record.setEmpName(empAttendance.getEmpName());
		outAttendance_Record.setEmpno(empAttendance.getEmpno());


		boolean check = false;
		String[] list = new String[6];
		String[] clockInTime = new String[2];
		int clockOutTime = 60 * clockOut.get(Calendar.HOUR_OF_DAY) + clockOut.get(Calendar.MINUTE);
		int workingTime = 0;

		try {

			fr = new FileReader(Path.EMPATTENDANCE);
			br = new BufferedReader(fr);
			boolean tmp = true;
			
			for (int j = 0; (line = br.readLine()) != null ; j++) {

				list = line.split(",");
				
			if (list[0].equals(outAttendance_Record.getEmpno()) && !(list[5].equals("출근"))) { //위의 문장을 요렇게 ("출근"외의 문자열은 퇴근시 생성
					System.out.println("당일 퇴근기록이 존재합니다.\n");
					tmp = false;
					break;
					}
			}
			
			while(tmp) {
				if (list[0].equals(outAttendance_Record.getEmpno()) && list[2].equals(String.format("%tF", clockOut))) { // 사원,
																													// 날짜일치한다면
					check = true;
					break;
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (check) {
			clockInTime = list[3].split(":"); // 00:00 으로 기록된 시간 나눠서 2개방배열에 담았음(연산을 위해)
			workingTime = clockOutTime - (60 * Integer.parseInt(clockInTime[0]) + Integer.parseInt(clockInTime[1]));
			outAttendance_Record.setTime(String.format("%tk:%tM", today, today)); //추가(퇴근시간찍히도록) 
			outAttendance_Record.setDate(list[2]);
			outAttendance_Record.setWorkingTime(String.format("%02d:%02d", workingTime / 60, workingTime % 60)); // 총근무시간(휴게포함)
																											// 00:00 셋팅
			// 근무시간>600 = + / 근무시간<540 = - / 540<근무시간<600 = 정상 셋팅
			outAttendance_Record.setAttendance(workingTime < 540 ? "미달" : (workingTime > 600) ? "초과" : "정상");

			outAttendance_Record.writeAttendanceData();
			System.out.printf("[ 현재시간 %tk:%tM ]\n", today, today); //추가 
			System.out.printf("%s님의 %d월 %d일 퇴근 기록이 완료되었습니다.\n\n", outAttendance_Record.getEmpName(),
					today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE)); //메세지 수정
		}
		returnMainMenu();  //메인메뉴로 돌아가는 함수 추가 
	}

	public void inOutAttend(Emp emp, int indexnum) throws IOException { // 출퇴근 인식 메서드

		Attendance_Record inOutAttendance_Record = new Attendance_Record();

		boolean run = false;
		inOutAttendance_Record.setEmpno(emp.getEmpNo());
		inOutAttendance_Record.setEmpName(emp.getName());

		while (!run) {
			System.out.println("==================================================================");
			System.out.println("\t\t\t  <근태 기록>");
			System.out.println("==================================================================\n");
			System.out.printf("%s님, 반갑습니다. 원하는 메뉴 번호를 입력해주세요.\n\n", emp.getName());
			System.out.println("1. 출근 기록");
			System.out.println("2. 퇴근 기록");
			System.out.println("3. 메인 메뉴로 돌아가기\n");
			System.out.print("메뉴 입력 :");
			String Menu = scan.nextLine();
			System.out.println();

			switch (Menu) {
			case "1": inAttend(inOutAttendance_Record);		break;
			case "2": outAttend(inOutAttendance_Record);		break;
			case "3": returnMainMenu(); //메인메뉴로 돌아가는 함수 추가 
			default: System.out.println("올바른 번호가 아닙니다. 다시 입력해주세요.");
				
			}
		}
	}
	
	public void returnMainMenu() {
		System.out.println("근태 기록을 종료하고 메인메뉴로 돌아갑니다.");
		System.out.println("==============================================================");
		EmpAct empAct = new EmpAct();
		empAct.enterEmpActMenu();
	}
}