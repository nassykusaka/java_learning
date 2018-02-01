package org.learning.animals;

public class Fish extends Animal implements Abilities {

    public Fish(String name, Long tall, Long weight, String cover, String limbs, Integer lifeExpectancy, String habitat) {
        this.name = name;
        this.tall = tall;
        this.weight = weight;
        this.cover = "scales";
        this.limbs = "fins";
        this.lifeExpectancy = lifeExpectancy;
        this.habitat = habitat;
    }

    public void feeding(){
        System.out.println("Feeding source: plankton.");
    }
    public void moving(){
        System.out.println("Swimming with help " + this.limbs + ". Live in " + habitat);
    }
    public void breathing(){
        System.out.println("Breathing with gills.");
    }
}