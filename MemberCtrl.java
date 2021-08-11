import data.Path;

import java.io.*;
import java.util.ArrayList;

public class MemberCtrl extends EmpList {

	/*******************************************/

	/*******************************************/
	/* Add */
	/*******************************************/
	/*******************************************/

	// 사원 등록 함수
	public void addEmp(String name, String idNum, String rank, String phoneNum, int year) {

		File f = new File(Path.EMPLIST);

		if (!f.exists()) {

			empArrayList.add((new Emp(name, idNum, rank, phoneNum, year)));
			empArrayList.get(0).setEmpNo(empArrayList.get(0).makeEmpno());
			empArrayList.get(0).setId(empArrayList.get(0).getEmpNo() + "@samjo.com");
			saveFile(empArrayList);

			empArrayList = null;

		} else {
			empArrayList = loadFile();
			empArrayList.add(new Emp(name, idNum, rank, phoneNum, year));
			empArrayList.get(loadFile().size()).setPointer(loadFile().size());
			empArrayList.get(loadFile().size()).setEmpNo(empArrayList.get(loadFile().size()).makeEmpno());
			empArrayList.get(loadFile().size()).setId(empArrayList.get(loadFile().size()).getEmpNo() + "@samjo.com");
			saveFile(empArrayList);
			System.out.println("추가하신 정보는 다음과 같습니다.");
			System.out.println(loadFile().get(loadFile().size() - 1).toString());
			empArrayList = null;
		}
	}

	// 주민등록번호 체커
	public boolean checkIdNum(String idNum) {
		boolean checker = false;

		if (idNum.indexOf("-") != -1) {

			if (idNum.indexOf("-") == 6) {
				String idnumMod = idNum.replace("-", "");
				if (idnumMod.length() == 13) {
					int idnumMonth = Integer.parseInt(idnumMod.substring(2, 4));
					if (idnumMonth >= 1 && idnumMonth <= 12) {
						int idnumDay = Integer.parseInt(idnumMod.substring(4, 6));
						if (idnumDay >= 1 && idnumDay <= checkMonth(idnumMonth)) {
							int gender = Integer.parseInt(idnumMod.substring(6, 7));
							if (gender >= 1 && gender <= 4) {
								checker = true;
							} else {
								System.out.println("뒷번호의 앞자리를 다시 입력해주세요.");
							}

						} else {
							System.out.println("해당되지 않은 일입니다. 다시 입력해주세요.");
						}

					} else {
						System.out.println("1~12월 사이로 다시 입력해주세요.");
					}

				} else {
					System.out.println("주민등록번호가 13자리가 아닙니다. 다시 입력해주세요.");
				}

			} else {
				System.out.println("대쉬(-)의 위치가 올바르지 않습니다. 다시 입력해주세요.");
			}

		} else {
			System.out.println("대쉬(-)가 없습니다. 다시 입력해주세요.");
		}

		return checker;

	}

	// 적합성 보조 - 월별 일수 체크
	public int checkMonth(int month) {
		int day = 0;

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2:
			day = 28;
			break;
		}

		return day;

	}

	// 핸드폰 번호 적합성 확인
	public boolean checkPhoneNum(String phoneNum) {
		boolean checker = false;
		int tmp = 0;
		try {
			tmp = Integer.parseInt(phoneNum);
			if (phoneNum.substring(0, 1).equals("0")) {
				System.out.println("앞자리 0은 사용할 수 없는 번호입니다.");
			} else if (tmp < 10000000 || tmp >= 100000000) {
				System.out.println("8자리로 입력해주세요.");
			} else if (tmp >= 10000000 && tmp < 20000000) {
				System.out.println("앞자리 1은 사용할 수 없는 번호입니다.");
			} else {
				File f = new File(Path.EMPLIST);
				if (!f.exists()) {
					checker = true;
				}else {
					for (int i = 0; i < loadFile().size(); i++) {
						String tmps = loadFile().get(i).getPhoneNum().substring(3, 11);
						if (tmps.equals(phoneNum)) {
							System.out.println("중복된 번호가 존재합니다.");
							break;
						} else {
							checker = true;
						}
					}
				}
						

			}
		} catch (Exception e) {
			System.out.println("다시 입력해주세요.");
		}
		return checker;
	}
	/*******************************************/

	/*******************************************/
	/* Mod */
	/*******************************************/
	/*******************************************/

	public void modPassword(int index, String password) {
		ArrayList<Emp> empArrayList = loadFile();
		empArrayList.get(index).setPassword(password);
		saveFile(empArrayList);
		System.out.println(loadFile().get(index).toString());

	}

	public void modEmpname(int index, String name) {
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		empArrayList.get(index).setName(name); // name을 변경합니다.
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println(loadFile().get(index).toString()); // 방금 수정한 객체의 정보를 가져옵니다.
	}

	public void modRank(int index, String rank) {
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		Emp emp = new Emp(24000000);
		empArrayList.get(index).setRank(rank); // 직급을 변경합니다.
		empArrayList.get(index).setSalaly((long) (empArrayList.get(index).rankToSal() * emp.getSalaly()
				+ emp.getSalaly() * ((double) empArrayList.get(index).getyear() / 10)));
		// 직급의 변동에 의한 연봉책정을 다시합니다.
		empArrayList.get(index).setEmpNo(empArrayList.get(index).makeEmpno());
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println(loadFile().get(index).toString()); // 방금 수정한 객체의 정보를 가져옵니다.
	}

	public void modPhoneNum(int index, String phoneNum) {
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		empArrayList.get(index).setPhoneNum(phoneNum); // phoneNum을 변경합니다.
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println(loadFile().get(index).toString()); // 방금 수정한 객체의 정보를 가져옵니다.

	}

	public void modState(int index, String state) {
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		empArrayList.get(index).setState(state); // state를 변경합니다.
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println(loadFile().get(index).toString());
	}

	public void modInState(int index, String state) {
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		empArrayList.get(index).setState(state); // state를 변경합니다.
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println("재설정 완료");
	}

	public void modYear(int index, String subYear) {
		int year = 0;
		year = Integer.parseInt(subYear);
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		Emp emp = new Emp(24000000); // 오버로딩된 생성자를 통해 기본급을 입력해 현재 연봉을 초기화시킵니다.
		empArrayList.get(index).setyear(year); // year를 변경합니다.
		empArrayList.get(index).setSalaly((long) (empArrayList.get(index).rankToSal() * emp.getSalaly()
				+ emp.getSalaly() * ((double) year / 10)));
		// 변경된 year에 맞게 salaly의 값을 변경합니다.
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println(loadFile().get(index).toString()); // 방금 수정한 객체의 정보를 가져옵니다.
	}

	public void modCapScore(int index, String subCapScore) {
		int capScore = 0;
		capScore = Integer.parseInt(subCapScore);
		ArrayList<Emp> empArrayList = loadFile(); // loadfile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		empArrayList.get(index).setCapScore(capScore); // name을 변경합니다.
		saveFile(empArrayList); // loadfile >> empArrayList >> savefile 을 통해 새로 입력합니다.
		System.out.println("재설정 완료"); // 방금 수정한 객체의 정보를 가져옵니다.
	}// 8/7/2021

	/*******************************************/

	/*******************************************/
	/* Search */
	/*******************************************/
	/*******************************************/

	public void listAll() {
		for (Emp e : loadFile()) {
			System.out.println(e.toString());
		}
	}

	public void searchByEmpno(String empno) {
		for (Emp e : loadFile()) {
			if (e.getEmpNo().equals(empno)) {
				System.out.println(e.toString());
			}
		}
	}

	public int searchByEmpnoMod(String empno) {

		int index = 0;

		for (Emp e : loadFile()) {

			if (e.getEmpNo().equals(empno)) {
				index = e.getPointer();
				break;

			} else {
				index = -1;
			}
		}
		return index;
	}

	public void searchByName(String name) {
		for (Emp e : loadFile()) {
			if (e.getName().equals(name)) {
				System.out.println(e.toString());
			}
		}
	}

	public void searchByRank(String rank) {
		for (Emp e : loadFile()) {
			if (e.getRank().equals(rank)) {
				System.out.println(e.toString());
			}
		}
	}

	public void searchByState(String state) {
		for (Emp e : loadFile()) {
			if (e.getState().equals(state)) {
				System.out.println(e.toString());
			}
		}
	}

	public void searchPromote() {
		for (Emp e : loadFile()) {
			if (e.getCapScore() >= 80) {
				System.out.println(e.toString());
			}
		}
	}

	public void searchPunish() {
		for (Emp e : loadFile()) {
			if (e.getCapScore() <= 20) {
				System.out.println(e.toString());
			}
		}
	}

	/*******************************************/

	/*******************************************/
	/* FileSave and FileLoad */
	/*******************************************/
	/*******************************************/

	public ArrayList<Emp> loadFile() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream in = null;
		ArrayList<Emp> empArrayList = null;

		try {
			fis = new FileInputStream(Path.EMPLIST);
			bis = new BufferedInputStream(fis);
			in = new ObjectInputStream(bis);

			Object object = in.readObject();
			empArrayList = (ArrayList<Emp>) object;

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

			try {
				in.close();
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return empArrayList;

	}

	public void saveFile(Object object) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;

		try {

			fos = new FileOutputStream(Path.EMPLIST, false);
			bos = new BufferedOutputStream(fos);
			out = new ObjectOutputStream(bos);

			out.writeObject(object);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void makeAdminPath() {

		File f = new File(Path.ADMINID);
		if (!f.exists()) {
			empArrayList.add((new Emp("admin", "0000")));
			saveAdminFile(empArrayList);
			empArrayList = null;
		}
	}
	

	public void modAdmin(String id, String password) {
		ArrayList<Emp> empArrayList = loadAdminFile(); // loadFile 메소드를 통해 기존 저장되어있던 파일을 empArrayList에 담습니다.
		empArrayList.get(0).setId(id);
		empArrayList.get(0).setPassword(password);
		saveAdminFile(empArrayList); // loadFile >> empArrayList >> saveFile 을 통해 새로 입력합니다.
	}

	public ArrayList<Emp> loadAdminFile() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream in = null;
		ArrayList<Emp> empArrayList = null;

		try {
			fis = new FileInputStream(Path.ADMINID);
			bis = new BufferedInputStream(fis);
			in = new ObjectInputStream(bis);

			Object object = in.readObject();
			empArrayList = (ArrayList<Emp>) object;

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

			try {
				in.close();
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return empArrayList;

	}

	public void saveAdminFile(Object object) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;

		try {

			fos = new FileOutputStream(Path.ADMINID, false);
			bos = new BufferedOutputStream(fos);
			out = new ObjectOutputStream(bos);

			out.writeObject(object);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
