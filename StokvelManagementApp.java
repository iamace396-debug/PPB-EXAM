import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;
public class StokvelManagementApp{
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat("0.00");
		
		String[] memberId;
		String[] name;
		double[] monthlyContribution;
		int[] yearsInGroup = new int[6];
		int[] missedPayments = new int[6];
		String[] payoutStatus = new String[6];
		
		memberId =new String[]{"M201", "M202", "M203", "M204", "M205", "M206"};
		name =new String[]{"Thandi Mokoena", "Sipho Dlamini", "Lerato Nkosi", "Pieter van Wyk", "Zanele Khumalo", "Themba Sithole"};
		monthlyContribution =new double[]{500.00, 750.00, 300.00, 1000.00, 650.00, 400.00};
		
		populateYearsInGroup(yearsInGroup);
		populateMissedPayments(memberId, missedPayments);
		determinePayoutStatus(yearsInGroup, missedPayments, payoutStatus);
		
		char option;
		
		do{
			option = getUserOption();
			
			switch(option) {
				
				case '1':
				case 'D':
				case 'd':
					displayAllMembers(memberId, name, monthlyContribution, yearsInGroup, missedPayments, payoutStatus);
					break;
					
				case '2':
				case 'S':
				case 's':
				
					System.out.print("Enter the member ID to search for: ");
					String member = input.next();
					
					int pos = searchMember(member, memberId);
					
					if(pos != -1) {
						
						System.out.println("ID : " + memberId[pos]);
						System.out.println("Name : " + name[pos]);
						System.out.println("Contribution : R" + df.format(monthlyContribution[pos]));
						System.out.println("Missed : " + missedPayments[pos]);
						System.out.println("Status : " + payoutStatus[pos]);
					}
					
					else {
						
						System.out.println("Member ID " + member + " was not found.");
					}
					
					break;
					
				case '3':
				case 'O':
				case 'o':
					
					sortByContribution(memberId, name, monthlyContribution, yearsInGroup, missedPayments, payoutStatus);
					System.out.println("Members sorted by contribution (highest to lowest): ");
					displayAllMembers(memberId, name, monthlyContribution, yearsInGroup, missedPayments, payoutStatus);
					break;
					
				case '4':
				case 'L':
				case 'l':
				
					int low = determineLowestContribution(monthlyContribution);
					int high = determineHighestContribution(monthlyContribution);
					
					System.out.println("Lowest contribution : " + name[low] + "(R" + df.format(monthlyContribution[low]) + ")");
					System.out.println("Highest contribution : " + name[high] + "(R" + df.format(monthlyContribution[high]) + ")");
					break;
					
				case '5':
				case 'A':
				case 'a':
					
					double averageContributions = calculateAverageContribution(monthlyContribution); 
					System.out.println("Average contribution: R" + df.format(averageContributions));
					break;
					
				case '6':
				case 'X':
				case 'x':
					
					System.out.println("Goodbye");
					break;
					
				default: 
					
					System.out.println("Invalid option. Please choose 1/D, 2/S, 3/O, 4/L,");
					System.out.println("5/A or 6/X.");
			}
		}while(option != '6' && option != 'X' && option != 'x');
		
	}
	
	public static void populateYearsInGroup(int[] yearsInGroup) {
		
		Random rand = new Random();
		
		for(int i=0; i<yearsInGroup.length; i++){
			
			yearsInGroup[i] = rand.nextInt(10) +1;
		}
	} 
	
	public static void populateMissedPayments(String[] memberId, int[] missedPayments) {
		
		Scanner input = new Scanner(System.in);
		for(int i=0; i<missedPayments.length; i++){
			
			System.out.print("Enter the number of missed payments for " + memberId[i] + ": ");
			int missed = input.nextInt();
			
			while(!validateMissedPayments(missed)) {
				
				System.out.println("Invalid number. Please enter a value from 0 to 12.");
				System.out.print("Enter the number of missed payments for " + memberId[i] + ": ");
				missed = input.nextInt();	
			}
				missedPayments[i] = missed;
		}
	}
	
	private static boolean validateMissedPayments(int missed) {
		
		boolean valid;
		
		if(missed >=0 && missed <= 12){
			
			valid = true;
		}
		
		else {
			
			valid = false;
		}
		
			return valid;
	} 
	
	public static char getUserOption() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("===== MASAKHANE STOKVEL =====");
		System.out.println("1/D - Display all members");
		System.out.println("2/S - Search for a member");
		System.out.println("3/O - Sort by contribution");
		System.out.println("4/L - Display lowest and highest contribution");
		System.out.println("5/A - Display average monthly contribution");
		System.out.println("6/X - Exit");
		
		System.out.print("Enter your option: ");
		char option = input.next().charAt(0);
		
		return option;
	} 
	
	public static void determinePayoutStatus(int[] yearsInGroup, int[] missedPayments, String[] payoutStatus) {
		
		for(int i=0; i<yearsInGroup.length; i++){
			
			if(yearsInGroup[i] >= 5) {
				if(missedPayments[i] >= 0 && missedPayments[i] <= 1){
					
					payoutStatus[i] = "Priority Payout";
				}
				
				if(missedPayments[i] >= 2 && missedPayments[i] <= 3){
					
					payoutStatus[i] = "Normal Payout";
				}
				
				if(missedPayments[i] >= 4){
					
					payoutStatus[i] = "Under Review";
				}
			}
			
			if(yearsInGroup[i] < 5) {
				if(missedPayments[i] == 0){
					
					payoutStatus[i] = "Normal Payout";
				}
				
				if(missedPayments[i] >= 1 && missedPayments[i] <= 3){
					
					payoutStatus[i] = "Waiting List";
				}
				
				if(missedPayments[i] >= 4){
					
					payoutStatus[i] = "Under Review";
				}	
			}
		}
	} 
	
	public static int searchMember(String member, String[] memberId) {
		
		int index = -1;
		
		for(int i=0; i<memberId.length; i++){
			if(memberId[i].equalsIgnoreCase(member)){
				
				index = i;
				break;
			}
		}
			return index;
	} 
	
	public static void displayAllMembers(String[] memberId, String[] name, double[] monthlyContribution, int[] yearsInGroup, int[] missedPayments, String[] payoutStatus) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		System.out.println("ID \tMember \tContribution \tYears \tMissed \tStatus");
		
		for(int i=0; i<yearsInGroup.length; i++){
			
			System.out.println(memberId[i] +"\t" + name[i] + "\t" + df.format(monthlyContribution[i]) + "\t" + yearsInGroup[i] + "\t" + missedPayments[i] + "\t"+ payoutStatus[i]);
		}
	} 
	
	public static void sortByContribution(String[] memberId, String[] name, double[] monthlyContribution, int[] yearsInGroup, int[] missedPayments, String[] payoutStatus) {
		
		String tempMember;
		String tempName;
		double tempMonthly;
		int tempYears;
		int tempMissed;
		String tempStatus;
		
		for(int i=0; i<monthlyContribution.length - 1; i++){
			for(int j=0; j<monthlyContribution.length - 1; j++){
				if(monthlyContribution[j] < monthlyContribution[j+1]){
					
					tempMember = memberId[j];
					memberId[j] = memberId[j+1];
					memberId[j+1] = tempMember;
					
					tempName = name[j];
					name[j] = name[j+1];
					name[j+1] = tempName;
					
					tempMonthly = monthlyContribution[j];
					monthlyContribution[j] = monthlyContribution[j+1];
					monthlyContribution[j+1] = tempMonthly;
					
					tempYears = yearsInGroup[j];
					yearsInGroup[j] = yearsInGroup[j+1];
					yearsInGroup[j+1] = tempYears;
					
					tempMissed = missedPayments[j];
					missedPayments[j] = missedPayments[j+1];
					missedPayments[j+1] = tempMissed;
					
					tempStatus = payoutStatus[j];
					payoutStatus[j] = payoutStatus[j+1];
					payoutStatus[j+1] = tempStatus;
				}
			}	
		}
	} 
	
	public static int determineLowestContribution(double[] monthlyContribution) {
		
		int index = 0;
		double lowest = monthlyContribution[0];

		for(int i=0; i<monthlyContribution.length; i++){
			
			if(monthlyContribution[i] < lowest){
				
				lowest = monthlyContribution[i];
				index = i;
			}
		}
			return index;
	} 
	
	public static int determineHighestContribution(double[] monthlyContribution) {
		
		int index = 0;
		double highest = monthlyContribution[0];
		
		for(int i=0; i<monthlyContribution.length; i++){
			
			if(monthlyContribution[i] > highest){
				
				highest = monthlyContribution[i];
				index = i;
			}
		}
			return index;
	}
	
	public static double calculateAverageContribution(double[] monthlyContribution) {
		
		double total = 0;
		
		for(int i=0; i<monthlyContribution.length; i++){
			
			total += monthlyContribution[i];
		}
		
			double average = total/monthlyContribution.length;
			
			return average;
	}
}