package main.java.com.questlife.questlife.goals;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
@XmlRootElement
public class Goals {

    @XmlID
    @XmlAttribute
    private String id;
    private long creation = System.currentTimeMillis();
    private LocalDateTime deadline;
    private long duration;
    private String name;
    private Integer amountOfWork;
    private Integer complexity;

    @XmlIDREF
    private Goals overarchingGoal;

    @XmlElement(name = "subGoals")
    @XmlIDREF
    private List<Goals> subGoals = new ArrayList<>();
    private List<Skill> associatedSkills = new ArrayList<>();
    private Boolean isRecurring = false; //default value
    private Boolean isComplete = false; //default value

    public Goals() {
        this.name = "";
        amountOfWork = 10;
        complexity = 5;
        overarchingGoal = null;
        deadline = LocalDateTime.now();
    }

    public Goals(LocalDateTime deadline, long duration, String name, int amountOfWork,
                 int complexity, List<Skill> associatedSkills, List<Goals> subGoals, boolean isRecurring) {
        this.deadline = deadline;
        this.duration = duration;
        this.name = name;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
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

    @XmlJavaTypeAdapter(SkillListBindAdapter.class)
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
        if(getProgress() == 100) {
            if (isRecurring && newDeadline != null) {
                this.deadline = newDeadline;
                for (Goals subGoal : subGoals) {
                    subGoal.completeGoal(newDeadline);
                }
                isComplete = false;
            } else {
                isRecurring = false;
                for (Goals subGoal : subGoals) {
                    subGoal.completeGoal(newDeadline);
                }
                isComplete = true;
            }
        }

        for(Skill skill:associatedSkills) {
            skill.gainExperience(getExperienceReward());
        }

        // get in milliseconds the time the goal was finished before deadline
        long expGained = deadline.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()-System.currentTimeMillis();
        if (expGained < 0) {
            expGained=0;
        } else {
            // One experience point per minute earlier than deadline.
            // One minute = 60000 milliseconds
            expGained = Math.round(expGained/60000.0f);
        }
        Attributes.PIETY.gainExperience(Math.round(expGained));

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
