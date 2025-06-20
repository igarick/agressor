package org.example.entities;

import org.example.Coordinates;
import org.example.Move;
import org.example.SimulationMap;
import org.example.dao.AliveEntity;
import org.example.searchPath.Path;


import java.util.List;


//        // else -> look for Grass -> if find it -> move to Grass -> eat Grass
//        // if health > 50 -> look for available squares -> random move

public abstract class Creature extends Entity implements AliveEntity {
    private final int speed;
    private int health;

    public Creature(Coordinates coordinates, int speed, int health) {
        super(coordinates);
        this.speed = speed;
        this.health = health;
    }

    public Move getCoordinatesForMove(SimulationMap simulationMap) {
        List<Coordinates> path = Path.findPath(this, simulationMap, this::isTarget);

        if (path.size() > 1 && path.size() <= this.getSpeed()) {
            return new Move(this.coordinates, path.get(path.size() - 2));
        } else if (path.size() > this.getSpeed()) {
            return new Move(this.coordinates, path.get(this.getSpeed() - 1));
        } else if (path.size() == 1) {
            Entity entity = simulationMap.getEntity(path.get(0));
            interactWithTarget(entity);
            if (entity instanceof AliveEntity aliveEntity) {
                if (!aliveEntity.isAlive(simulationMap)) {
                    return new Move(this.coordinates, path.get(0));
                } else {
                    return new Move(this.coordinates, this.coordinates);
                }
            }
        }
        return new Move(this.coordinates, this.coordinates);
    }


//    public void makeMove(SimulationMap simulationMap) {
//        List<Coordinates> path = Path.findPath(this,simulationMap, this::isTarget);
//
//        if (path.size() > 2 && path.size() <= this.getSpeed()) {
//            simulationMap.makeMove(this.coordinates, path.get(path.size() - 2));
//        } else if (path.size() >= this.getSpeed()) {
//            simulationMap.makeMove(this.coordinates, path.get(this.getSpeed()));
//        } else if (path.size() == 2) {
//            simulationMap.makeMove(this.coordinates, path.get(1));
//        }
//    }


public int getSpeed() {
    return speed;
}

public int getHealth() {
    return health;
}

public void adjustHealth(int delta) {
    this.health += delta;
}

public void restoreToMaxHealth(int health) {
    this.health = health;
}


public void dropToMinHealth(int health) {
    this.health = health;
}

public abstract boolean isTarget(Entity entity);

public abstract void interactWithTarget(Entity entity);

public abstract boolean canMoveThrough(Entity entity);

public abstract boolean isPrey();

//    abstract boolean isHealthInBounds(int healthAmount);


//    protected void increaseHealth(int health) {
//        if (isHealthInBounds(health)) {
//            this.health += health;
//        } else {
//            this.health = 100;
//        }
//    }
//
//    protected boolean isHealthInBounds(int health) {
//        return !(this.health + health > 100);
//    }
}


//Абстрактный класс, наследуется от Entity.
// Существо, имеет скорость (сколько клеток может пройти
// за 1 ход), количество HP. Имеет метод makeMove() -
// сделать ход.


