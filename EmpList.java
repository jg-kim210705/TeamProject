
import java.io.Serializable;
import java.util.ArrayList;


public class EmpList {

    ArrayList<Emp> empArrayList;


    {
        empArrayList = new ArrayList<Emp>();

    }


}


class Emp implements Serializable {


    private String name;
    private String idNum; // 주민번호
    private String empNo;
    private String rank;
    private String phoneNum;
    private String state;
    private String id;
    private String password;
    private int year;
    private long salaly;
    private int capScore;
    private int pointer;

    {
        salaly = 24000000;
        capScore = 50;
        state = "입사";
        pointer = 0;

    }

    public Emp(long salaly) {
        this.salaly = salaly;
    }

    public Emp(String name, String idNum, String rank, String phoneNum, int year) {
        this.name = name;
        this.idNum = idNum;
        this.rank = rank;
        this.phoneNum = phoneNum;
        this.year = year;
        this.salaly = (long) (rankToSal() * salaly + salaly * ((double) year / 10));
        this.empNo = "0000";
        this.id = empNo + "@samjo.com";
        this.password = "1234";
        this.state = "N"; 

    }

    public Emp(String id, String password){
        this.id = id;
        this.password = password;
    }


    public String makeEmpno() {

        String rankInEmpno = (rank.equals("사장")) ? "SSS" :
                (rank.equals("이사")) ? "SS" :
                        (rank.equals("부장")) ? "S" :
                                (rank.equals("차장")) ? "A" :
                                        (rank.equals("과장")) ? "B" :
                                                (rank.equals("대리")) ? "C" :
                                                        (rank.equals("주임")) ? "D" :
                                                                (rank.equals("사원")) ? "F" : "F";


        if (pointer >= 1000) {
        	empNo = rankInEmpno + pointer;
        } else if (pointer >= 100) {
        	empNo = rankInEmpno + "0" + pointer;
        } else if (pointer >= 10) {
        	empNo = rankInEmpno + "00" + pointer;
        } else {
        	empNo = rankInEmpno + "000" + pointer;
        }


        return empNo;
    }


    public double rankToSal() {
        double rankIncen = (rank.equals("사장")) ? 2 :
                (rank.equals("이사")) ? 1.8 :
                        (rank.equals("부장")) ? 1.5 :
                                (rank.equals("차장")) ? 1.4 :
                                        (rank.equals("과장")) ? 1.3 :
                                                (rank.equals("대리")) ? 1.2 :
                                                        (rank.equals("주임")) ? 1.1 :
                                                                (rank.equals("사원")) ? 1 : 1;

        return rankIncen;
    }

    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empno) {
        this.empNo = empno;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getyear() {
        return year;
    }

    public void setyear(int year) {
        this.year = year;
    }

    public long getSalaly() {
        return salaly;
    }

    public void setSalaly(long salaly) {
        this.salaly = salaly;
    }

    public int getCapScore() {
        return capScore;
    }

    public void setCapScore(int capScore) {
        this.capScore = capScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "이름 \t: " + name + " || " +
                "주민등록번호 : " + idNum + "\n" +
                "사번 \t: " + empNo + " || " +
                "직급 \t: " + rank + "\n" +
                "휴대전화번호 : " + phoneNum + "\n" +
                "연차 \t: " + year + " || " +
                "연봉 \t: " + salaly + "\n" +
                "역량점수 \t: " + capScore + " || " +
                "상태 \t: " + state;
    }
}

