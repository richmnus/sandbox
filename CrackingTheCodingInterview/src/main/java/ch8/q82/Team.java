package ch8.q82;

import java.util.ArrayList;
import java.util.List;

public class Team {

    List<Respondant> respondants;

    public Team() {
        respondants = new ArrayList<Respondant>();
    }

    void addTeamMember(Respondant member) {
        respondants.add(member);
    }

}
