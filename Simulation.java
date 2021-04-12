import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private ArrayList<Bird> sample = new ArrayList<>();
    private ArrayList<Integer> numberOfHawksPerRound = new ArrayList<>();
    private ArrayList<Integer> numberOfDovesPerRound = new ArrayList<>();
    private ArrayList<Integer> numberOfBirdsPerRound = new ArrayList<>();
    private static Random random = new Random();

    public ArrayList<Bird> getSample() {
        return sample;
    }

    public ArrayList<Integer> getNumberOfHawksPerRound() {
        return numberOfHawksPerRound;
    }

    public ArrayList<Integer> getNumberOfDovesPerRound() {
        return numberOfDovesPerRound;
    }

    public ArrayList<Integer> getNumberOfBirdsPerRound() {
        return numberOfBirdsPerRound;
    }

    public int getNumberOfHawks() {
        int numberOfHawks = 0;
        for(int i = 0; i < sample.size(); i++) {
            if(sample.get(i).isAggressive()) {
                numberOfHawks++;
            }
        }
        return numberOfHawks;
    }

    public int getNumberOfDoves() {
        int numberOfDoves = 0;
        for(int i = 0; i < sample.size(); i++) {
            if(!sample.get(i).isAggressive()) {
                numberOfDoves++;
            }
        }
        return numberOfDoves;
    }

    public void run() {
        ArrayList<Bird> sampleCopy = new ArrayList<>();
        sampleCopy = (ArrayList) sample.clone();
        ArrayList<Bird> runResult = new ArrayList<>();
        while(!(sampleCopy.isEmpty() || sampleCopy.size() == 1)) {
            //Picks the values between 0 and list.size() - 1
            int firstBirdPicked = random.nextInt(sampleCopy.size());
            int secondBirdPicked = random.nextInt(sampleCopy.size());
            while(secondBirdPicked == firstBirdPicked) {
                secondBirdPicked = random.nextInt(sampleCopy.size());
            }
            if(sampleCopy.get(firstBirdPicked).isAggressive() && sampleCopy.get(secondBirdPicked).isAggressive()) {
                //Two hawks face each other.
                //One will gain 50 points from winning and the other will lose 100 points from serious injury.
                //Decides whether the first bird is the winner.
                //Average pay off for hawks (50-100)/2 = -25
                boolean firstBirdIsTheWinner = random.nextBoolean();
                if(firstBirdIsTheWinner) {
                    //First bird wins 50 points.
                    sampleCopy.get(firstBirdPicked).addPoints(30);
                    //Second bird loses 100 points
                    sampleCopy.get(secondBirdPicked).subtractPoints(100);
                } else {
                    //First bird loses 100 points.
                    sampleCopy.get(firstBirdPicked).subtractPoints(100);
                    //Second bird wins 100 points
                    sampleCopy.get(secondBirdPicked).addPoints(30);
                }
            } else if(sampleCopy.get(firstBirdPicked).isAggressive() && !sampleCopy.get(secondBirdPicked).isAggressive()) {
                //First bird is a hawk, and the second bird is the dove.
                //Hawk will always gain 50 points. Dove will always gain nothing.
                //Average pay off for hawk is +50, average pay off for dove is 0
                sampleCopy.get(firstBirdPicked).addPoints(30);
            } else if(!sampleCopy.get(firstBirdPicked).isAggressive() && sampleCopy.get(secondBirdPicked).isAggressive()) {
                //Second bird is a hawk, and the first bird is the dove.
                //Hawk will always gain 50 points. Dove will always gain nothing.
                //Average pay off for hawk is +50, average pay off for dove is 0
                sampleCopy.get(secondBirdPicked).addPoints(30);
            } else {
                //Both birds are doves.
                //One will gain 50 points from winning but lose 10 points from wasting energy. (gain 40 points in total)
                //The other will lose 10 points from wasting energy.
                //Average pay off for doves (40-10)/2 = +15
                boolean firstBirdIsTheWinner = random.nextBoolean();
                if(firstBirdIsTheWinner) {
                    //First bird wins 50 points.
                    sampleCopy.get(firstBirdPicked).addPoints(20);
                    //Second bird loses 100 points
                    sampleCopy.get(secondBirdPicked).subtractPoints(10);
                } else {
                    //First bird loses 100 points.
                    sampleCopy.get(firstBirdPicked).subtractPoints(10);
                    //Second bird wins 100 points
                    sampleCopy.get(secondBirdPicked).addPoints(20);
                }
            }
            //This means that if the bird is at or over 100 points, it will have an offspring of its type.
            if(sampleCopy.get(firstBirdPicked).getPoints() >= 100) {
                runResult.add(new Bird(sampleCopy.get(firstBirdPicked).isAggressive()));
            }
            if(sampleCopy.get(secondBirdPicked).getPoints() >= 100) {
                runResult.add(new Bird(sampleCopy.get(secondBirdPicked).isAggressive()));
            }
            //Birds will be added to the runResult if it has points over -100.
            //This means that the birds will be eliminated if it has points at -100 or below.
            if(sampleCopy.get(firstBirdPicked).getPoints() > -100) {
                runResult.add(sampleCopy.get(firstBirdPicked));
            }
            if(sampleCopy.get(secondBirdPicked).getPoints() > -100) {
                runResult.add(sampleCopy.get(secondBirdPicked));
            }
            //Remove both birds from the sampleCopy.
            if(firstBirdPicked > secondBirdPicked) {
                sampleCopy.remove(firstBirdPicked);
                sampleCopy.remove(secondBirdPicked);
            } else {
                sampleCopy.remove(secondBirdPicked);
                sampleCopy.remove(firstBirdPicked);
            }
        }
        if(sampleCopy.size() == 1) {
            runResult.add(sampleCopy.get(0));
        }
        sample = (ArrayList) runResult.clone();
        //Records the number of each round.
        numberOfHawksPerRound.add(getNumberOfHawks());
        numberOfDovesPerRound.add(getNumberOfDoves());
        numberOfBirdsPerRound.add(sample.size());
    }

    public void getGraph() {
        System.out.println("❶ stands for 1% of the birds being hawk.");
        System.out.println("① stands for 1% of the birds being dove.");
        for(int i = 0; i < numberOfBirdsPerRound.size(); i++) {
            double percentageOfHawkDouble = (numberOfHawksPerRound.get(i)*100/numberOfBirdsPerRound.get(i));
            int percentageOfHawk = 0;
            if(percentageOfHawkDouble >= (int) percentageOfHawkDouble && percentageOfHawkDouble < (((int) percentageOfHawkDouble) + 0.5)) {
                percentageOfHawk = (int) percentageOfHawkDouble;
            } else {
                percentageOfHawk =  ((int) percentageOfHawkDouble) + 1;
            }
            int percentageOfDove = 100 - percentageOfHawk;
            for(int j = 0; j < percentageOfHawk; j++) {
                System.out.print("⓿");
            }
            for(int j = 0; j < percentageOfDove; j++) {
                System.out.print("⓪");
            }
            System.out.println("|| Round " + i + " ||");
        }
    }
}
