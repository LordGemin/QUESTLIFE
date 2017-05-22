package main.java.com.questlife.questlife.goals;

import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.util.*;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Goals {

    @XmlID
    private String id;

    private LocalDateTime deadline;
    private long duration;
    private String name;
    private Integer amountOfWork;
    private Integer complexity;

    private Goals overarchingGoal;

    private Collection<Goals> subGoals = new ArrayList<>();
    private Collection<Skill> associatedSkills = new ArrayList<>();
    private Boolean isRecurring = false; //default value
    private Boolean isComplete = false; //default value

    public Goals() {
        this.name = "";
        amountOfWork = 10;
        complexity = 5;
        this.id = UUID.randomUUID().toString();
        overarchingGoal = this;
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

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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
        if(overarchingGoal==null) {
            return this;
        }
        return overarchingGoal;
    }

    @XmlIDREF
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

    public Collection<Skill> getAssociatedSkills() {
        return associatedSkills;
    }

    public List<Skill> getAssociatedSkillsAsList() {
        List<Skill> skillList = new ArrayList<>();
        skillList.addAll(associatedSkills);
        return skillList;
    }

    public void setAssociatedSkills(List<Skill> associatedSkills) {
        this.associatedSkills = associatedSkills;
    }

    public List<Goals> getSubGoals() {
        List<Goals> subGoals = new ArrayList<>();
        subGoals.addAll(this.subGoals);
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

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public int getExperienceReward() {
        return StatCalculator.getExperienceFromGoal(this);
    }

    public void addSubGoal(Goals subGoal) {
        if(subGoal.getOverarchingGoal() == null) {
            subGoal.setOverarchingGoal(this);
        } else {
            subGoals.add(subGoal);
        }
    }

    private void removeSubGoal(Goals subGoal) {
        subGoals.remove(subGoal);
    }

    public void completeGoal(LocalDateTime newDeadline) {
        if(subGoals.contains(overarchingGoal)) {
            subGoals.remove(overarchingGoal);
        }

        if(getProgress() == 100) {
            if (isRecurring && newDeadline != null) {
                this.deadline = newDeadline;
                for (Goals subGoal : subGoals) {
                    subGoal.setDeadline(newDeadline);
                }
                isComplete = false;
            } else {
                isRecurring = false;
                for (Goals subGoal : subGoals) {
                    subGoal.setDeadline(newDeadline);
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

        if(progress > 100)
            progress = 100;

        return progress;
    }
}
