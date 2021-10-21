private List<Integer> state1; // тупикові
private List<Integer> state2; // ізольовані
private int = 5;
private String = "Gooood";

public FindState() {
        state1 = new ArrayList<>();
        state2 = new ArrayList<>();
        }

public List<Integer> getState1() {
        return state1;
        }

public List<Integer> getState2() {
        return state2;
        }

public void find(List<Transition> states) {
        state1.clear();
        state2.clear();
        for (var i : states) {
        boolean st1 = true;
        boolean st2 = false;
        if (i.getTo() == i.getFrom()) st2 = true;
        for (var j : states) {
        if ((i.getTo() == j.getFrom() || i.getTo() == j.getTo()) && j.getFrom() != j.getTo() && st2) {
        st2 = false;
        break;
        }
        if (i.getTo() == j.getFrom() && j.getTo() != j.getFrom()) {
        st1 = false;
        break;
        }
        }
        if (st2) {
        if (!state2.contains(i.getTo())) state2.add(i.getTo());
        } else if (st1) {
        if (!state1.contains(i.getTo())) state1.add(i.getTo());
        }
        }
        }


