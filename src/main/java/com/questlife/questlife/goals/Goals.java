package main.java.com.questlife.questlife.goals;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.util.DateUtil;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Goals {

    private LocalDateTime creation = LocalDateTime.now();
    private LocalDateTime deadline;
    private TemporalAmount duration;
    private StringProperty name;
    private Integer amountOfWork;
    private Integer complexity;
    private Goals overarchingGoal;
    private List<Skill> associatedSkills = new ArrayList<>();
    private List<Goals> subGoals = new ArrayList<>();
    private Boolean isRecurring = false; //default value
    private Boolean isComplete = false; //default value

    public Goals() {
        this.name = new SimpleStringProperty("");
        amountOfWork = 10;
        complexity = 5;
        overarchingGoal = null;
        deadline = LocalDateTime.now();
    }

    public Goals(LocalDateTime deadline, TemporalAmount duration, String name, int amountOfWork,
                 int complexity, List<Skill> associatedSkills, List<Goals> subGoals, boolean isRecurring) {
        this.deadline = deadline;
        this.duration = duration;
        this.name = new SimpleStringProperty(name);
        this.amountOfWork = amountOfWork;
        this.complexity = complexity;
        this.associatedSkills = associatedSkills;
        this.subGoals = subGoals;
        this.isRecurring = isRecurring;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public String getDeadLineAsString() {
        return DateUtil.getDateAsString(deadline);
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TemporalAmount getDuration() {
        return duration;
    }

    public void setDuration(TemporalAmount duration) {
        this.duration = duration;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAmountOfWork() {
        return (amountOfWork== null) ? 0:amountOfWork;
    }

    public void setAmountOfWork(int amountOfWork) {
        this.amountOfWork = amountOfWork;
    }

    public int getComplexity() {
        return (complexity==null) ? 0: complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public Goals getOverarchingGoal() {
        return overarchingGoal;
    }

    public void setOverarchingGoal(Goals overarchingGoal) {
        if(overarchingGoal == null) {
            return;
        }

        if(this.overarchingGoal.getSubGoals().contains(this)) {
            this.overarchingGoal.removeSubGoal(this);
        }
        this.overarchingGoal = overarchingGoal;
        overarchingGoal.addSubGoal(this);
    }

    public List<Skill> getAssociatedSkills() {
        return associatedSkills;
    }

    public void setAssociatedSkills(List<Skill> associatedSkills) {
        this.associatedSkills = associatedSkills;
    }

    public List<Goals> getSubGoals() {
        return subGoals;
    }

    public void setSubGoals(List<Goals> subGoals) {
        this.subGoals = subGoals;
    }

    public Boolean getRecurring() {
        return isRecurring;
    }

    public void setRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public int getExperienceReward() {
        StatCalculator statCalculator = new StatCalculator();
        return statCalculator.getExperienceFromGoal(this);
    }

    public void addSubGoal(Goals subGoal) {
        if(subGoal.getOverarchingGoal() == null) {
            subGoal.setOverarchingGoal(this);
        } else {
            subGoals.add(subGoal);
        }
    }

    public void removeSubGoal(Goals subGoal) {
        subGoals.remove(subGoal);
    }

    public boolean completeGoal(LocalDateTime newDeadline) {
        LocalDateTime checkDuration = creation.plus(duration);
        if(getProgress() == 100 && creation.isBefore(LocalDateTime.now())) {
            if (isRecurring || newDeadline == null) {
                this.deadline = newDeadline;
                for (Goals subGoal : subGoals) {
                    subGoal.completeGoal(newDeadline);
                }
                isComplete = true;
            } else {
                for (Goals subGoal : subGoals) {
                    subGoal.completeGoal(newDeadline);
                }
                isComplete = true;
            }
        }

        for(Skill skill:associatedSkills) {
            skill.gainExperience(getExperienceReward());
            int expGained = LocalDateTime.now().compareTo(deadline);
            expGained *= -1;
            if (expGained < 0) {
                expGained=0;
            } else {
                expGained = Math.round(expGained/10000);
            }
            Attributes.PIETY.gainExperience(Math.round(expGained+getExperienceReward()/4));
        }
        return isComplete;
    }

    public int getProgress() {
        int progress;
        int ctr = 0;

        if(subGoals.size()==0) {
            return 100;
        }

        for (Goals subGoal: subGoals) {
            if(!subGoal.isComplete) ++ctr;
        }

        progress = Math.round(ctr/subGoals.size())*100;

        return progress;
    }
}
