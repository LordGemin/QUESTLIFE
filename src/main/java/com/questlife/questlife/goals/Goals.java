package main.java.com.questlife.questlife.goals;

import main.java.com.questlife.questlife.skills.Skills;
import java.util.Date;

/**
 * Created by Gemin on 10.04.2017.
 */
public abstract class Goals {

    private Date deadline;
    private Date duration;
    private String name;
    private int amountOfWork;
    private int complexity;
    private Skills[] associatedSkills;
    private Goals overarchingGoal;

    public Goals(Date deadline, Date duration, String name, int amountOfWork,
                 int complexity, Skills[] associatedSkills, Goals overarchingGoal) {
        this.deadline = deadline;
        this.duration = duration;
        this.name = name;
        this.amountOfWork = amountOfWork;
        this.complexity = complexity;
        this.associatedSkills = associatedSkills;
        this.overarchingGoal = overarchingGoal;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountOfWork() {
        return amountOfWork;
    }

    public void setAmountOfWork(int amountOfWork) {
        this.amountOfWork = amountOfWork;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public Skills[] getAssociatedSkills() {
        return associatedSkills;
    }

    public void setAssociatedSkills(Skills[] associatedSkills) {
        this.associatedSkills = associatedSkills;
    }

    public Goals getOverarchingGoal() {
        return overarchingGoal;
    }

    public void setOverarchingGoal(Goals overarchingGoal) {
        this.overarchingGoal = overarchingGoal;
    }
}
