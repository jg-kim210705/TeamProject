import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import data.Path;



/*
 * 출퇴근 기록을 담는 클래스
 */
 
public class Attendance_Record {
	private String empNo;
	private String empName;
	private String date;
	private String time;
	private String workingTime; //일한시간
	private String attendance;	//가감여부 
	private FileWriter fw;
	private BufferedWriter bw;
	{
		try {
			fw= new FileWriter(Path.EMPATTENDANCE,true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Attendance_Record() {

	}

	public Attendance_Record(String empNo, String empName, String date, String time, String workingTime,
			String attendance) {

		this.empNo = empNo;
		this.empName = empName;
		this.date = date;
		this.time = time;
		this.workingTime = workingTime; 
		this.attendance = attendance; 


	}	

	public String getEmpno() {
		return empNo;
	}

	public void setEmpno(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	
	

	@Override
	public String toString() {
		return  empNo + "," + empName + "," + date + "," + time + "," + workingTime + "," + attendance ;
	}

	public void writeAttendanceData() {
		
		try {			

			bw.write(toString());
			bw.newLine();

		} catch (IOException e) {
			System.out.println("AttendanceData()");
			e.printStackTrace();
		}finally {
			try {
				bw.close();
				fw.close();
			} catch (Exception e2) {

				System.out.println("AttendanceData() finally"); //오류문구추가
			}
			
		}
	}

}
