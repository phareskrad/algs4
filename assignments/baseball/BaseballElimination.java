package assignments;

import algs4.*;
import stdlib.In;
import stdlib.StdOut;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Leon on 8/14/15.
 */
public class BaseballElimination {
    private HashMap<String, Integer> teamMap;
    private int[] wins;
    private int[] losses;
    private int[] remainings;
    private int[][] against;
    private FlowNetwork flowNetwork;
    private boolean[] eliminated;
    private Iterable<String>[] certificate;
    private boolean[] cached;
    private int N;

    //constructor, take file name as input
    public BaseballElimination(String filename) {
        In in = new In(filename);
        String l = in.readLine();
        N = Integer.parseInt(l);
        teamMap = new HashMap<String, Integer>();
        wins = new int[N];
        losses = new int[N];
        remainings = new int[N];
        against = new int[N][N];
        eliminated = new boolean[N];
        certificate = new Iterable[N];
        cached = new boolean[N];

        int i = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(" +");
            String team = tokens[0];
            teamMap.put(team, i);
            wins[i] = Integer.parseInt(tokens[1]);
            losses[i] = Integer.parseInt(tokens[2]);
            remainings[i] = Integer.parseInt(tokens[3]);

            for (int j = 0; j < N; j++) {
                against[i][j] = Integer.parseInt(tokens[4 + j]);
            }
            i++;
        }
    }
    //all team names
    public Iterable<String> teams() {
        return teamMap.keySet();
    }
    //check whether one team is eliminated
    public boolean isEliminated(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        int index = getIndex(team);
        if (!cached[index]) {
            runElimination(team);
            cached[index] = true;
        }
        return eliminated[index];
    }
    //certificate teams of elimination
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        int index = getIndex(team);
        if (!cached[index]) {
            runElimination(team);
            cached[index] = true;
        }
        return certificate[index];
    }
    //current wins of one team
    public int wins(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        int index = getIndex(team);
        return wins[index];
    }
    //current losses of one team
    public int losses(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        int index = getIndex(team);
        return losses[index];
    }
    //remaining games of one team
    public int remaining(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        int index = getIndex(team);
        return remainings[index];
    }
    //remaining games between two teams
    public int against(String team1, String team2) {
        if (!teamMap.containsKey(team1) || !teamMap.containsKey(team2)) throw new IllegalArgumentException();
        int i1 = getIndex(team1);
        int i2 = getIndex(team2);

        return against[i1][i2];
    }
    //helper function for running trivia elimination process (win1 + remain1 - win2 < 0)
    private void runTriviaElimination(String team) {
        Bag<String> c = new Bag<String>();
        int index = getIndex(team);

        for (String t : teams()) {
            if (!t.equals(team) && compareWins(team, t) < 0) {
                c.add(t);
            }
        }
        if (c.size() > 0) {
            certificate[index] = c;
            eliminated[index] = true;
        }

    }
    //helper function for computing win1 + remain1 - win2
    private int compareWins(String team1, String team2) {
        return wins(team1) + remaining(team1) - wins(team2);
    }
    //helper function for running non trivia elimination using max flow algorithm
    private void runNonTriviaElimination(String team) {
        String[] indices = constructIndices(team);
        constructFlowNetWork(indices);
        runFordFulkerson(indices);
    }
    //helper function for constructing vertices index mapping for teams
    private String[] constructIndices(String team) {
        String[] indices = new String[N];
        indices[0] = team;
        int i = 1;
        for (String t: teams()) {
            if (!t.equals(team)) indices[i++] = t;
        }
        return indices;
    }
    //helper function for constructing flownetwork
    private void constructFlowNetWork(String[] indices) {
        int V = 2 + (N - 1)*(N- 1);
        flowNetwork = new FlowNetwork(V);

        String team = indices[0];
        //let s = 0, t = V - 1;
        int v = 1;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (i > j) {
                    v++;
                    continue;
                }
                else if (i == j){
                    String other = indices[i];
                    flowNetwork.addEdge(new FlowEdge(v, V - 1, compareWins(team, other)));
                    v++;
                }
                else {
                    String t1 = indices[i];
                    String t2 = indices[j];
                    flowNetwork.addEdge(new FlowEdge(0, v, against(t1 ,t2)));
                    flowNetwork.addEdge(new FlowEdge(v, (i - 1)*(N - 1) + i % N, Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(v, (j - 1)*(N - 1) + j % N, Double.POSITIVE_INFINITY));
                    v++;
                }
            }
        }

        //System.out.println(flowNetwork);


    }
    //helper function for running ford-fulkerson on the flownetwork, check whether teams are in min-cut and save data in cache
    private void runFordFulkerson(String[] indices) {
        int V = 2 + (N - 1)*(N - 1);
        FordFulkerson ff = new FordFulkerson(flowNetwork, 0, V - 1);
        Bag<String> c = new Bag<String>();

        for (int i = 1; i < N; i++) {
            boolean inMinCut = ff.inCut((i - 1)*(N - 1) + i % N);
            if (inMinCut) {
                c.add(indices[i]);
            }
        }

        if (c.size() > 0) {
            eliminated[getIndex(indices[0])] = true;
            certificate[getIndex(indices[0])] = c;
        }

    }
    //helper function for combining trivia and non trivia elimination process
    private void runElimination(String team) {
        runTriviaElimination(team);
        int index = getIndex(team);
        if (N > 2 && !eliminated[index]) {
            runNonTriviaElimination(team);
        }
    }
    //helper function for get index for one team
    private int getIndex(String team) {
        return teamMap.get(team);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("./resources/baseball/teams4.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }


    }


}
