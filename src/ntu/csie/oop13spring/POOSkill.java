package ntu.csie.oop13spring;

public abstract class POOSkill{
    /**
       the skill is defined as "something"
       that can change the HP or AGI of any POOPet
    */
    public abstract void act(POOPet pet);
}

class POOTinyAttackSkill extends POOSkill{
    public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 0)
            pet.setHP(hp - 1);
    }
}

