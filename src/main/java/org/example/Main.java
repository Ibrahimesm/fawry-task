class Engine {
    String type;
    int internalSpeed;
    Engine(String type) {
        this.type = type;
        this.internalSpeed = 0;
    }
    void increase() {
        internalSpeed++;
    }
    void decrease() {
        if (internalSpeed > 0)
            internalSpeed--;
    }
    void work(int carSpeed) {
        System.out.println(type + " engine working");
        increase();
    }
}

class GasEngine extends Engine {
    GasEngine() {
        super("Gas");
    }
}

class ElectricEngine extends Engine {
    ElectricEngine() {
        super("Electric");
    }
}

class HybridEngine extends Engine {
    GasEngine gas = new GasEngine();
    ElectricEngine electric = new ElectricEngine();
    HybridEngine() {
        super("Hybrid");
    }
    @Override
    void work(int carSpeed) {
        if (carSpeed < 50) {
            System.out.println("Electric engine working");
            electric.increase();
        } else {
            System.out.println("Gas engine working");
            gas.increase();
        }
    }
    @Override
    void decrease() {
        if (electric.internalSpeed > 0)
            electric.decrease();
        else
            gas.decrease();
    }
}

class Car {
    Engine engine;
    int speed;
    boolean started = false;
    Car(Engine engine) {
        this.engine = engine;
        this.speed = 0;
    }
    void start() {
        started = true;
        speed = 0;
        System.out.println(engine.type + " car started");
    }
    void stop() {
        if (speed != 0) {
            System.out.println("Speed must be 0 to stop!");
            return;
        }
        started = false;
        System.out.println(engine.type + " car stopped");
    }
    void accelerate() {
        if (!started) {
            System.out.println("Start car first!");
            return;
        }
        if (speed + 20 <= 200)
            speed += 20;
        System.out.println(engine.type + " car speed = " + speed);
        engine.work(speed);
    }
    void brake() {
        if (speed - 20 >= 0)
            speed -= 20;
        else
            speed = 0;
        engine.decrease();
        System.out.println(engine.type + " car speed = " + speed);
        engine.work(speed);
    }
    void changeEngine(Engine newEngine) {
        engine = newEngine;
        System.out.println("Engine changed to " + engine.type);
    }
}

class CarFactory {
    Car createCar(String type) {
        if (type.equals("Gas"))
            return new Car(new GasEngine());
        else if (type.equals("Electric"))
            return new Car(new ElectricEngine());
        else
            return new Car(new HybridEngine());
    }
    void replaceEngine(Car car, String type) {
        if (type.equals("Gas"))
            car.changeEngine(new GasEngine());
        else if (type.equals("Electric"))
            car.changeEngine(new ElectricEngine());
        else
            car.changeEngine(new HybridEngine());
    }
}

public class Main {
    public static void main(String[] args) {
        CarFactory factory = new CarFactory();
        // Gas car
        Car gas = factory.createCar("Gas");
        gas.start();
        gas.accelerate();
        gas.brake();
        gas.stop();
        System.out.println("-----");
        // Electric car
        Car electric = factory.createCar("Electric");
        electric.accelerate(); // before start
        electric.start();
        electric.accelerate();
        electric.accelerate();
        electric.brake();
        electric.stop();
        System.out.println("-----");
        // Hybrid car
        Car hybrid = factory.createCar("Hybrid");
        hybrid.start();
        hybrid.accelerate();
        hybrid.accelerate();
        hybrid.accelerate(); // speed 60, gas engine
        hybrid.stop(); // speed not 0, cant stop
        hybrid.brake();
        hybrid.brake();
        hybrid.brake();
        hybrid.stop();
        System.out.println("-----");
        // replace engine
        factory.replaceEngine(hybrid, "Gas");
        hybrid.start();
        hybrid.accelerate();
    }
}