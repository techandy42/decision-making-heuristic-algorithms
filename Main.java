import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Simulation simulation = new Simulation();
        boolean numberSetUpDone = false;
        int numberOfBirds = 0;
        while(!numberSetUpDone) {
            System.out.println("Type in the number of birds in the simulation. Make sure the number is an integer bigger than 1 and smaller or equal to 10.");
            String numberSetUpDoneString = scanner.nextLine();
            if(stringIsInteger(numberSetUpDoneString)) {
                numberOfBirds = Integer.parseInt(numberSetUpDoneString);
                if(numberOfBirds > 1 && numberOfBirds <= 10) {
                    numberSetUpDone = true;
                } else {
                    System.out.println("The number must be an integer bigger than 1 and smaller or equal to 10.");
                }
            } else {
                System.out.println("The number is not valid. Please try again.");
            }
        }
        double percentageOfHawk = 0;
        boolean percentageSetUpDone = false;
        while(!percentageSetUpDone) {
            System.out.println("Type in the percentage of hawks (aggressive birds) in the simulation, between 0 and 100 (Including 0 and 100).");
            String percentageOfHawkString = scanner.nextLine();
            if(stringIsDouble(percentageOfHawkString)) {
                percentageOfHawk = Double.parseDouble(percentageOfHawkString);
                if(percentageOfHawk >= 0 && percentageOfHawk <= 100) {
                    percentageSetUpDone = true;
                } else {
                    System.out.println("The percentage must be between 0 and 100 (Including 0 and 100).");
                }
            } else {
                System.out.println("The number is not valid. Please try again.");
            }
        }
        int numberOfRounds = 0;
        boolean numberOfRoundsSetDone = false;
        while(!numberOfRoundsSetDone) {
            System.out.println("Type in the number of rounds of simulation, an integer between 1 and 200 (including 1 and 200)");
            String numberOfRoundsString = scanner.nextLine();
            if(stringIsInteger(numberOfRoundsString)) {
                numberOfRounds = Integer.parseInt(numberOfRoundsString);
                if(numberOfRounds >= 1 && numberOfRounds <= 200) {
                    numberOfRoundsSetDone = true;
                } else {
                    System.out.println("The number must be an an integer between 1 and 200 (including 1 and 200)");
                }
            } else {
                System.out.println("The number is not valid. Please try again.");
            }
        }
        System.out.println("Number of total birds: " + numberOfBirds);
        double numberOfHawksDouble = (numberOfBirds*percentageOfHawk/100);
        int numberOfHawks = 0;
        if(numberOfHawksDouble >= (int) numberOfHawksDouble && numberOfHawksDouble < (((int) numberOfHawksDouble) + 0.5)) {
            numberOfHawks = (int) numberOfHawksDouble;
        } else {
            numberOfHawks =  ((int) numberOfHawksDouble) + 1;
        }
        System.out.println("Number of hawks (aggressive): " + numberOfHawks);
        int numberOfDoves = numberOfBirds - numberOfHawks;
        System.out.println("Number of doves (pacific): " + numberOfDoves);

        for(int i = 0; i < numberOfHawks; i++) {
            simulation.getSample().add(new Bird(true));
        }

        for(int i = 0; i < numberOfDoves; i++) {
            simulation.getSample().add(new Bird(false));
        }
        simulation.getNumberOfHawksPerRound().add(simulation.getNumberOfHawks());
        simulation.getNumberOfDovesPerRound().add(simulation.getNumberOfDoves());
        simulation.getNumberOfBirdsPerRound().add(simulation.getSample().size());
        for(int i = 0; i < numberOfRounds; i++) {
            simulation.run();
        }
        simulation.getGraph();
    }

    public static boolean stringIsInteger(String stringNumber) {
        if(stringNumber == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(stringNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean stringIsDouble(String stringNumber) {
        if(stringNumber == null) {
            return false;
        }
        try {
            double number = Double.parseDouble(stringNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
