package com.gameprocessor.entities;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.entities.items.Sendable;

public class SkillManager implements Sendable {

    public String race;
    public Integer level;
    public SkillManager()
    {
        level = 1;
    }
    public int getLevel()
    {
        return level;
    }
    public void setRace(String race)
    {
        this.race = race;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Выберите умение: ";
    }

    @Override
    public Actions getActions() {
        var actions = Actions.builder();
        if(race.equals("Human")) actions.addAction("forceAttack");
        else if (race.equals("Dwarf")) actions.addAction("areaAttack");
        if(level >= 2)actions.addAction("heal");
        return actions.build();
    }
}
