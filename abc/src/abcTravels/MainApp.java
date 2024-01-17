package abcTravels;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {
	static  Map<String,User>mp=new HashMap<>();
	static String fname;
	static String lname;
	static String phno;
	static String gen;
	static String email;
	static String password;
	static int failCount;
	static int accountStatus; 
	static boolean exit=false;
	static int opt;
	static double totalfare;
	static LocalDate custom;
	static boolean reschedule=false;
	static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) {
		preDefinedData(); 

		opt=0;
		while(!exit) {//for continuous iteration
			switch(opt) {
			case 0:{
				getLogo();
				System.out.println();
				System.out.println("Please Choose The Following Options"+"\n"+"1.New User Registration"+"\n"+"2.Lock Account"+"\n"+"3.Plan Journey"+"\n"+"4.Resheduling");
				opt=sc.nextInt();
				break;
			}
			case 1:{
				registration();
				break;
			}
			case 2:{
				login();
				break;
			}
			case 3:{
				planjourney();//this method evaluates the total fare based upon destination,date
				System.out.println("Do You Want to Reschedule Your Date"+"\n"+"Type 1 as Yes and 2 as NO");
				int resop=sc.nextInt();
				if(resop==1) {reschedule=true;  opt=4; break;}
				else {System.out.println("HAPPY JOURNEY!!!!!!!!");  opt=0; break;}


			}
			case 4:{
				if(reschedule==true) {
					rescheduling();//this method enables the user to reschedule the date
					opt=0;
					break;
				}
				else {
					System.out.println("You Cannot Choose This Option Without Booking Tickets");
					opt=0;
					break;
				}

			}
			default:{
				System.out.println("You Have Choosed  a Wrong Option Please Choose Correct Option");
				opt=0;
				break;
			}
			}
			//exit=true;
			//sc.close();
		}
	}

	/*
	 * gets the logo of ABC TRAVELS this method TROWS an exception(FILE NOT FOUND
	 * EXCEPTION) if the file is not found
	 */
	static void getLogo() {
		try {
			File f=new File("E:\\javaEclipseProjects\\abc\\src\\logo.txt");//Make sure to give correct file path of logo

			FileInputStream fis=new FileInputStream(f);
			int i=0;
			while((i=fis.read())!=-1) {
				System.out.print((char)i);
				i=fis.read();

			}
			fis.close();
		} catch (Exception e) {
			System.out.println("WELCOME TO ABC TRAVELS");
		}
	}
	static void registration() {
		System.out.println("Please Enter The First Name");
		fname=sc.next();
		System.out.println("Please Enter The Last Name");
		lname=sc.next();
		System.out.println("Please Enter The Mobile Number");
		phoneverify(sc.next());
		System.out.println("Please Choose The Options With Respect To Your Gender"+"\n"+"1.Male"+"\n"+"2.Female");
		assigngender( sc.nextInt());
		System.out.println("Please Enter Your E-Mail Id");
		emaiverify(sc.next());
		System.out.println("Please Enter Your Password");
		password=sc.next();
		System.out.println("Your Registration was Successful!!!!!");
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("You are Redirected to Login Page");
		System.out.println("--------------------------------------------------------------------------------------");
		failCount=0;
		accountStatus=0;
		mp.put( phno, new User( fname,  lname,  phno,  gen,  email,  password,  failCount,accountStatus));
		opt=2;
		return;
	}

	/*
	 * this method verifies weather the given phone number is valid using regex
	 * INDIAN PHONE number with 10 digits is considered as a valid phone number
	 */
	static void phoneverify(String phoo) {

		Pattern pphone=Pattern.compile("[6789][0-9]{9}");
		Matcher m=pphone.matcher(phoo);
		if(m.find() &&m.group().equals(phoo)) {
			phno=phoo;
		}
		else {
			System.out.println("Enter Correct Phone Number");
			phoneverify(sc.next());
		}

	}

	/*
	 * This method verifies weather the given e-mail id is valid or not by using
	 * regex This method only Accepts a GMAIL ID as a Valid E-mail id
	 */
	static void emaiverify(String emaill) {
		Pattern em=Pattern.compile("[a-zA-Z][0-9a-zA-Z_]*@gmail.com");
		Matcher mm=em.matcher(emaill);
		if(mm.find() && mm.group().equals(emaill)) {
			email=String.valueOf(emaill);
		}
		else {
			System.out.println("Please Enter The Valid Email Address");
			emaiverify(sc.next());
		}

	}
	static void assigngender(int n) {
		switch(n) {
		case 1:{
			gen="Male";
			break;
		}
		case 2:{
			gen="Female";
			break;
		}
		default:{
			System.out.println("Please Choose The Valid Gender Option");
			break;
		}

		}
	}
	//this acts as a database representing users	
	static void preDefinedData() {
		mp.put("9393939391",new User( "srinivas",  "Pilli",  "9393939391",  "Male",  "abc123@gmail.com",  "Srinivas", 0,
				0));
		mp.put("9393939392",new User( "harsha",  "Pilli",  "9393939392",  "Male",  "abc123@gmail.com",  "Srinivas", 0,
				0));
		mp.put("9393939393",new User( "srinivas",  "Pilli",  "9393939393",  "Male",  "abc123@gmail.com",  "Srinivas", 0,
				0));
	}
	//validating user credentials which are in map of Users
	static void login() {
		System.out.println("Enter your Phone Number");
		String tempphone=sc.next();
		System.out.println("Enter your Password");
		String temppassword=sc.next();
		phoneverify(tempphone); 
		if(mp.containsKey(phno)) {
			if(mp.get(phno).failCount<6) {
				if(mp.get(phno).password.equals(temppassword)) {
					System.out.println("You Have Logged Successfully!!!!!!!");
					System.out.println("---------------------------------------------------------------------------------------------------------------------------");
					System.out.println("You Can Proceed to Booking");
					System.out.println("---------------------------------------------------------------------------------------------------------------------------");
					opt=3;
					return;
				}
				else {
					mp.get(phno).failCount++;
					System.out.println("Your Have Entered Wrong Passowrd!!!! Please Retry");
					opt=2;
				}
			}
			else {
				System.out.println("Your Login Attempts Are Exceeded And Your Account Is Locked");
			}
		}
		else {
			System.out.println("The Entered Phone Number is Not Registered Please Register");
			opt=1;
			return;
		}
	}
	static void planjourney() {
		System.out.println("Please Choose The Following Options to Select Your Source and Destination like(1,2,3,4,5)");
		System.out.println("OPT '1':  VIJAYAWADA JN - BZA (VIJAYAWADA)-------To-------VISAKHAPATNAM - VSKP (VISAKHAPATNAM)");
		System.out.println("OPT '2':  VISAKHAPATNAM - VSKP (VISAKHAPATNAM)-------To-------VIJAYAWADA JN - BZA (VIJAYAWADA)");
		System.out.println("OPT '3':  HYDERABAD DECAN - HYB (SECUNDERABAD)-------To-------VISAKHAPATNAM - VSKP (VISAKHAPATNAM)");
		System.out.println("OPT '4':  VISAKHAPATNAM - VSKP (VISAKHAPATNAM)-------To-------HYDERABAD DECAN - HYB (SECUNDERABAD)");
		System.out.println("OPT '5':  HYDERABAD DECAN - HYB (SECUNDERABAD)-------To-------MUMBAI CENTRAL - MMCT (MUMBAI)");
		System.out.println("OPT '6':  MUMBAI CENTRAL - MMCT (MUMBAI)-------To-------HYDERABAD DECAN - HYB (SECUNDERABAD)");
		System.out.println("OPT '7':  MUMBAI CENTRAL - MMCT (MUMBAI)-------To-------DELHI - DLI (NEW DELHI)");
		System.out.println("OPT '8':  DELHI - DLI (NEW DELHI)-------To-------MUMBAI CENTRAL - MMCT (MUMBAI)");
		int desopt=sc.nextInt();
		System.out.println("Enter Number Of Seats You Want To Book");
		int seatno=sc.nextInt();
		dateverify();
		switch(desopt) {
		case 1:
		case 2:{
			totalfare=699*seatno;
			break;
		}
		case 3:
		case 4:{
			totalfare=999*seatno;
			break;
		}
		case 5:
		case 6:{
			totalfare=1999*seatno;
			break;
		}
		case 7:
		case 8:{
			totalfare=2999*seatno;
			break;
		}
		}
		if(custom.getDayOfWeek().toString().toLowerCase().equals("saturday")||custom.getDayOfWeek().toString().toLowerCase().equals("sunday")) {
			totalfare+=200;
		}
		gst(totalfare);





	}

	/*
	 * this verifies weather the DATE is given in correct format [YY-MM-DD]
	 * YY-year,MM-month,DD-day This method Throws an EXCEPTION if user gives a wrong
	 * format
	 */
	static void dateverify() {
		try {
			System.out.println("Enter the Date on which you want to book in the format-->(YY-MM-DD)..... EXAMPLE--(2022-01-05)");
			String k=sc.next();
			LocalDate tempcustom=LocalDate.parse(k);
			int differdate=tempcustom.compareTo(LocalDate.now());
			if(differdate>0 || differdate==0) {
				custom=tempcustom;
			}
			else {
				System.out.println("you can book dates after today not before today"+"PLEASE INSERT PROPER DATE!!!!");
				dateverify();
			}
		}
		catch(Exception e) {
			System.out.println("You Have Entered The Date in Wrong Format please Enter in (YY-MM-DD) EXAMPLE--(2022-01-05) ");
			dateverify();
		}


	}
	//this calculates the GST over Tickets where GST is taken at @12%
	static void gst(double g) {
		System.out.println("Your journey fare is "+g+" The GST is " +(g*0.12));
		totalfare=(g+(g*0.12));
		System.out.println("Your total fare including GST is "+(g+(g*0.12)));
		reschedule=true;
	}
	static void rescheduling() {
		System.out.println("You Are Trying To Rescheduling Your Date Of Journey");
		LocalDate temp=custom;
		dateverify();
		if(custom.getDayOfWeek().toString().toLowerCase().equals("sunday")||custom.getDayOfWeek().toString().toLowerCase().equals("saturday")) {
			if(temp.getDayOfWeek().toString().toLowerCase().equals("sunday")||temp.getDayOfWeek().toString().toLowerCase().equals("saturday")) {
				System.out.println("There has been no change in your Total Fare");
				System.out.println("Total Fare is "+totalfare);
				System.out.println("HAPPY JOURNEY!!!!!!!!");
				System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			}
			else {
				System.out.println("As you Have booked on Sunday or Saturday The price has been Surged by 200");
				System.out.println("Total Fare is "+(totalfare+200));
				System.out.println("HAPPY JOURNEY!!!!!!!!");
				System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			}
		}
		else {
			if(temp.getDayOfWeek().toString().toLowerCase().equals("sunday")||temp.getDayOfWeek().toString().toLowerCase().equals("saturday")) {
				System.out.println("Your Total Fare Has been Reduced by 200");
				System.out.println("Total Fare is "+(totalfare-200));
				System.out.println("HAPPY JOURNEY!!!!!!!!");
				System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			}
			else {
				System.out.println("There has been no change in your Total Fare");
				System.out.println("Total Fare is "+totalfare);
				System.out.println("HAPPY JOURNEY!!!!!!!!");
				System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			}

		}
		

	}
}


